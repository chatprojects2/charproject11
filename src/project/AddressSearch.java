package project;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.*;

public class AddressSearch extends JDialog implements ActionListener, MouseListener {
    MemberShipForm memberShipForm;
    JPanel jp_north = new JPanel();
    String[] cols = {"상호명","카테고리","도로명주소"};
    String[][] data = new String[0][3];
    DefaultTableModel tableModel = new DefaultTableModel(data,cols);
    JTable table = new JTable(tableModel);
    JScrollPane scrollPane = new JScrollPane(table);

    JTextField searchField = new JTextField(40);
    JButton searchButton = new JButton("검색");
    String clientId = "YBJ9fFJCXUw7JEqRPvJo"; //애플리케이션 클라이언트 아이디
    String clientSecret = "5fQnhGxF_L"; //애플리케이션 클라이언트 시크릿
    String apiURL = "https://openapi.naver.com/v1/search/local.json";

    
    public AddressSearch(MemberShipForm memberShipForm){
        this.memberShipForm = memberShipForm;
        initDisplay();


    }

    private String removeHtmlTags(String input) {
        return input.replaceAll("<[^>]*>", "");
    }


    public List<Map<String,Object>> addressSearch(String query){
        List<Map<String,Object>> list1 = null;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(apiURL +"?query="+query+"&display=5")
                .addHeader("X-Naver-Client-Id", clientId)
                .addHeader("X-Naver-Client-Secret", clientSecret)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                String json = response.body().string();
                Gson gson = new Gson();
                //JSON응답에서 items 배열만 추출하여 List<Map<String,Object>>형태로 변환
                java.lang.reflect.Type listType = new TypeToken<List<HashMap<String, Object>>>() {
                }.getType();
                Map<String, Object> resultMap = gson.fromJson(json, Map.class);
                list1 = gson.fromJson(gson.toJson(resultMap.get("items")), listType);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list1;
    }





        public void initDisplay() {

            this.setTitle("Address Search");
            this.setSize(800, 500);


            searchField.addActionListener(this);
            searchButton.addActionListener(this);
            // North 패널 설정
            jp_north.setLayout(new BorderLayout());  // BorderLayout 사용
            jp_north.add(searchField, BorderLayout.CENTER);  // 텍스트 필드 중앙 배치
            jp_north.add(searchButton, BorderLayout.EAST);  // 버튼을 동쪽(오른쪽)에 배치

            this.add(jp_north, BorderLayout.NORTH);  // 프레임의 북쪽에 패널 추가
            this.add(scrollPane, BorderLayout.CENTER);  // 스크롤 팬을 센터에 추가

            this.setVisible(false);


        }


    public static void main(String[] args) {
        new AddressSearch(null).setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //todo 더블클릭할 때 이벤트 처리하기
        Object source = e.getSource();
        while(tableModel.getRowCount() > 0){
            tableModel.removeRow(0);
        }
        if(source == searchButton||source == searchField){
            if (searchField.getText().equals("")) {
                JOptionPane.showMessageDialog(this,"주소를 입력해주세요","WARNING",JOptionPane.WARNING_MESSAGE);
            }
            List<Map<String,Object>> list2 = addressSearch(searchField.getText());
            for (int i = 0; i < list2.size(); i++) {
                Map<String,Object> map =list2.get(i);
                Vector<Object> v =new Vector<>();
                v.add(removeHtmlTags((String) map.get("title")));
                v.add(map.get("category"));
                v.add( map.get("roadAddress"));
                tableModel.addRow(v);
            }
            searchField.setText("");

        }




    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // 더블클릭 감지

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getClickCount() == 2 && e.getSource() == tableModel) {
            int row = table.getSelectedRow(); // 더블클릭된 행의 인덱스
            System.out.println(row);
            if (row != -1) { // 행이 선택되었는지 확인
                String title = (String) tableModel.getValueAt(row, 0); // 첫 번째 열 데이터(상호명)
                String category = (String) tableModel.getValueAt(row, 1); // 두 번째 열 데이터(카테고리)
                String address = (String) tableModel.getValueAt(row, 2); // 세 번째 열 데이터(도로명주소)

                // 선택된 행의 정보를 출력하거나 다른 처리를 수행
                System.out.println("선택된 행의 정보: " + title + ", " + category + ", " + address);
            }
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
    
    
    
    

