package project;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class Search extends JDialog implements ActionListener, ItemListener {
    MemberShipForm msf = null;
    JPanel jp_north = new JPanel();
    String[] cols = {"건물명","도로명주소", "우편번호"};
    int currentPage = 1; // 기본값 설정
    int countPerPage = 10; // 기본값 설정
    JTextField se_address = new JTextField(30);  // 텍스트 필드 크기 조정
    JButton searchButton = new JButton("검색");
    String[][] data = new String[0][3];
    DefaultTableModel dtm_ad = new DefaultTableModel(data, cols);
    JTable jtb_ad = new JTable(dtm_ad);
    JScrollPane jsp_ad = new JScrollPane(jtb_ad,
            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    String confmKey = "your_key";
    String apiURL = "https://business.juso.go.kr/addrlink/addrLinkApi.do";

    public Search(MemberShipForm msf) {
        this.msf = msf;
        initDisplay();
    }

    public void refreshData(List<Map<String, Object>> list) {
        for (Map<String, Object> rMap : list) {
            Vector<Object> v = new Vector<>();
            v.add(rMap.get("bdNm")); // 건물명
            v.add(rMap.get("roadAddr")); // 도로명 주소
            v.add(rMap.get("zipNo")); // 우편번호
            dtm_ad.addRow(v);
        }
    }

    public List<Map<String, Object>> searchAddress(String query, int currentPage, int countPerPage) {
        List<Map<String, Object>> list = null;
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(apiURL).newBuilder();
        urlBuilder.addQueryParameter("confmKey", confmKey);
        urlBuilder.addQueryParameter("keyword", query);
        urlBuilder.addQueryParameter("currentPage", String.valueOf(currentPage));
        urlBuilder.addQueryParameter("countPerPage", String.valueOf(countPerPage));
        urlBuilder.addQueryParameter("resultType", "json"); // JSON 형식으로 받기

        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                String json = response.body().string();
                Gson gson = new Gson();
                Map<String, Object> resultMap = gson.fromJson(json, Map.class);

                // "results"에서 "juso" 배열 추출
                Map<String, Object> results = (Map<String, Object>) resultMap.get("results");
                list = (List<Map<String, Object>>) results.get("juso");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void initDisplay() {
        se_address.addActionListener(this);
        jp_north.add(se_address);
        jp_north.add(searchButton);
        this.setTitle("주소검색");
        this.add("North", jp_north);
        this.add("Center", jsp_ad);
        this.setSize(700, 500);
        this.setVisible(false); // 기본으로 비활성화, 필요시 setVisible(true) 호출
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();
        String query = se_address.getText();
        if (obj == se_address) {
            List<Map<String, Object>> list = searchAddress(query, currentPage, countPerPage);
            // 이전 데이터 초기화
            while (dtm_ad.getRowCount() > 0) {
                dtm_ad.removeRow(0);
            }
            if (list != null) {
                refreshData(list);
            }
            se_address.setText("");
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        Object obj = e.getSource();
    }
}
