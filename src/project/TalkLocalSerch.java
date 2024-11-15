package project;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.Builder;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.*;

public class TalkLocalSerch extends JDialog implements ActionListener, ItemListener, MouseListener {
    MemberShipForm msf = null;
    JPanel jp_north = new JPanel();
    String[] currentPages = {"시작위치", "1", "2", "3", "4", "5"};
    String[] countPerPages = {"검색결과", "10", "20", "30", "40", "50"};
    String[] cols = {"시", "구", "동", "리"};


    String[][] data = new String[0][4];
    DefaultTableModel dtm_local = new DefaultTableModel(data, cols);
    JTable jtb_local = new JTable(dtm_local);
    JScrollPane jsp_local = new JScrollPane(jtb_local, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

    JTextField jtf_keyword = new JTextField(40);
    JComboBox jcb_currentPage = new JComboBox(currentPages);
    JComboBox jcb_countPerPage = new JComboBox(countPerPages);

    String clientId = "공용키";


    int currentPage = 0;
    int countPerPage = 0;

    //디폴트생성자
    public TalkLocalSerch() {
    }

    public TalkLocalSerch(MemberShipForm msf) {
        this.msf = msf;
        initDisplay();
    }

    public void refreshData(List<Map<String, Object>> list) {
        // 기존 데이터 초기화
        while (dtm_local.getRowCount() > 0) {
            dtm_local.removeRow(0);
        }

        // 새로운 데이터 추가
        if (list != null && !list.isEmpty()) {
            for (Map<String, Object> rMap : list) {
                Vector<Object> v = new Vector<>();

                // 시, 구, 동, 리 항목 추가 (널 체크 및 기본값 대체)
                v.add(rMap.getOrDefault("siNm", ""));     // 시
                v.add(rMap.getOrDefault("sggNm", ""));    // 구
                v.add(rMap.getOrDefault("emdNm", ""));    // 동

                // "리" 항목이 없을 경우 도로명 "rn"으로 대체
                String liNm = (String) rMap.getOrDefault("liNm", "");
                if (liNm.isEmpty()) {
                    liNm = (String) rMap.getOrDefault("rn", ""); // 도로명 주소 사용
                }
                v.add(liNm); // 리 또는 도로명

                dtm_local.addRow(v);
            }
        } else {
            System.out.println("검색 결과가 없습니다.");
        }
    }


    public List<Map<String, Object>> searchlocals(String query, int currentPage, int countPerPage) {
        String apiURL = "https://business.juso.go.kr/addrlink/addrLinkApi.do";
        List<Map<String, Object>> list = new ArrayList<>(); // 초기화된 빈 리스트

        if (currentPage == 0 || countPerPage == 0) {
            currentPage = 1;
            countPerPage = 10;
        }

        OkHttpClient client = new OkHttpClient();
        String fullUrl = apiURL +
                "?confmKey=" + clientId +
                "&keyword=" + query +
                "&currentPage=" + currentPage +
                "&countPerPage=" + countPerPage +
                "&resultType=json";

        Request request = new Request.Builder()
                .url(fullUrl)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                String json = response.body().string();

                // 디버깅용 로그 추가
                System.out.println("API 응답 성공");
                System.out.println("응답 상태 코드: " + response.code());
                System.out.println("응답 본문: " + json);

                Gson gson = new Gson();
                Map<String, Object> resultMap = gson.fromJson(json, Map.class);

                if (resultMap != null && resultMap.containsKey("results")) {
                    Map<String, Object> results = (Map<String, Object>) resultMap.get("results");
                    if (results.containsKey("juso")) {
                        java.lang.reflect.Type listType = new TypeToken<List<HashMap<String, Object>>>() {
                        }.getType();
                        list = gson.fromJson(gson.toJson(results.get("juso")), listType);
                    }
                } else {
                    System.out.println("검색 결과가 없습니다.");
                }
            } else {
                System.out.println("API 호출 실패: " + response.code());
            }
        } catch (Exception e) {
            System.out.println("API 요청 중 예외 발생: " + e.getMessage());
            e.printStackTrace();
        }

        return list;
    }

    public void initDisplay(){
        jtf_keyword.addActionListener(this);
        jcb_currentPage.addItemListener(this);
        jcb_countPerPage.addItemListener(this);
        jp_north.add(jtf_keyword);
        jp_north.add(jcb_currentPage);
        jp_north.add(jcb_countPerPage);
        this.add("North",jp_north);
        this.add("Center",jsp_local);
        addTableClickListener(); // 테이블 클릭 리스너 추가
        this.setSize(700,500);
        this.setVisible(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();
        String query = jtf_keyword.getText();
        if (obj == jtf_keyword) {
            List<Map<String, Object>> list = searchlocals(query, currentPage, countPerPage);

            while (dtm_local.getRowCount() > 0) {
                dtm_local.removeRow(0);
            }

            if (list != null) {  // list가 null이 아닌 경우만 처리
                refreshData(list);
            }
            jtf_keyword.setText("");
        }
    }


    public static void main(String[] args) {
        new TalkLocalSerch(null);
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        Object obj = e.getSource();
        if (obj == jcb_currentPage) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                if (!"시작위치".equals(currentPages[jcb_currentPage.getSelectedIndex()])) {
                    currentPage = Integer.parseInt(currentPages[jcb_currentPage.getSelectedIndex()]);
                }
                System.out.println("선택한 컬럼명은 " + currentPage);//gubun은 멤버변수로 한다.
            }//////// 콤보박스에서 선택한 값이 변경되었을 때 인터셉트 함
        }////////// end of if
        if (obj == jcb_countPerPage) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                if (!"검색결과".equals(countPerPages[jcb_countPerPage.getSelectedIndex()])) {
                    countPerPage = Integer.parseInt(countPerPages[jcb_countPerPage.getSelectedIndex()]);
                }
                System.out.println("선택한 컬럼명은 " + countPerPage);//gubun은 멤버변수로 한다.
            }//////// 콤보박스에서 선택한 값이 변경되었을 때 인터셉트 함
        }////////// end of if
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

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

    // JTable에서 더블 클릭 시 데이터를 가져오는 기능 추가
    private void addTableClickListener() {
        jtb_local.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // 마우스 클릭 횟수가 2번일 때 (더블 클릭)
                if (e.getClickCount() == 2) {
                    // 선택된 행의 인덱스 가져오기
                    int selectedRow = jtb_local.getSelectedRow();

                    if (selectedRow != -1) { // 유효한 행이 선택되었는지 확인
                        // 시, 구, 동, 리 항목 가져오기
                        String siNm = (String) dtm_local.getValueAt(selectedRow, 0); // 시
                        String sggNm = (String) dtm_local.getValueAt(selectedRow, 1); // 구
                        String emdNm = (String) dtm_local.getValueAt(selectedRow, 2); // 동
                        String liNm = (String) dtm_local.getValueAt(selectedRow, 3); // 리 또는 도로명

                        // 전체 주소 문자열 생성
                        String fullAddress = siNm + " " + sggNm + " " + emdNm + " " + liNm;

                        // MemberShipForm의 jtf_address에 값 설정
                        if (msf != null && msf.jtf_address != null) {
                            msf.jtf_address.setText(fullAddress);
                        }

                        // 콘솔 출력 (디버깅용)
                        System.out.println("선택된 주소: " + fullAddress);

                        // 현재 창 닫기
                        setVisible(false);
                    }
                }
            }
        });
    }
}
