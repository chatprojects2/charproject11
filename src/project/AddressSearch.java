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
    String[] cols = {"우편번호","도로명주소"};
    String[][] data = new String[0][2];
    DefaultTableModel tableModel = new DefaultTableModel(data,cols);
    JTable table = new JTable(tableModel);
    JScrollPane scrollPane = new JScrollPane(table);
    private static final String API_KEY  = "API_KEY";
    JTextField searchField = new JTextField(40);
    JButton searchButton = new JButton("검색");

    
    public AddressSearch(MemberShipForm memberShipForm){
        this.memberShipForm = memberShipForm;
        initDisplay();


    }



public List<Map<String, Object>> addressSearch(String query) {
    List<Map<String, Object>> list1 = null;
    OkHttpClient client = new OkHttpClient().newBuilder()
            .build();
    Request request = new Request.Builder()
            .url("https://business.juso.go.kr/addrlink/addrLinkApi.do?confmKey="+API_KEY+"=&keyword=" + query + "&resultType=json&countPerPage=30")
            .get() // GET 메소드를 명시
            .addHeader("Cookie", "clientid=010040776024; elevisor_for_j2ee_uid=0a48w3jr8zwjf")
            .build();

    try (Response response = client.newCall(request).execute()) {
        if (response.isSuccessful() && response.body() != null) {
            String json = response.body().string();
            Gson gson = new Gson();
            Map<String, Object> resultMap = gson.fromJson(json, Map.class);
            if (resultMap.containsKey("results")) {
                Map<String, Object> results = (Map<String, Object>) resultMap.get("results");
                if (results.containsKey("juso")) {

                    list1 = (List<Map<String, Object>>) results.get("juso");
                }
            }
        } else {
            System.out.println("Request failed: " + response.code());
        }
    } catch (IOException e) {
        e.printStackTrace();
    }

    return list1;
}



        public void initDisplay() {

            this.setTitle("Address Search");
            this.setSize(800, 500);


            searchField.addActionListener(this);
            searchButton.addActionListener(this);

            jp_north.setLayout(new BorderLayout());
            jp_north.add(searchField, BorderLayout.CENTER);
            jp_north.add(searchButton, BorderLayout.EAST);

            this.add(jp_north, BorderLayout.NORTH);
            this.add(scrollPane, BorderLayout.CENTER);
            addTableClickListener();

            this.setVisible(false);


        }


    public static void main(String[] args) {
        new AddressSearch(null).setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        while(tableModel.getRowCount() > 0){
            tableModel.removeRow(0);
        }
        if(source == searchButton||source == searchField){
            if (searchField.getText().equals("")) {
                JOptionPane.showMessageDialog(this,"주소를 입력해주세요","WARNING",JOptionPane.WARNING_MESSAGE);
            }
            List<Map<String,Object>> list2 = addressSearch(searchField.getText());
            if(list2 ==  null){
                JOptionPane.showMessageDialog(this,"상세 주소를 입력해주세요","WARNING",JOptionPane.WARNING_MESSAGE);

            }
            for (int i = 0; i < list2.size(); i++) {
                Map<String,Object> map =list2.get(i);
                Vector<Object> v =new Vector<>();
                v.add(map.get("zipNo"));
                v.add(map.get("roadAddr"));
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

    }

    private void addTableClickListener() {
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // 더블클릭 감지
                if (e.getClickCount() == 2) {
                    int row = table.getSelectedRow(); // 더블클릭된 행의 인덱스

                    if (row != -1) { // 유효한 행이 선택되었는지 확인
                        // 첫 번째 열 데이터(우편번호) 가져오기
                        String zipCode = (String) tableModel.getValueAt(row, 0);
                        // 두 번째 열 데이터(도로명 주소) 가져오기
                        String roadAddress = (String) tableModel.getValueAt(row, 1);
                        System.out.println("선택된 행의 정보: 우편번호=" + zipCode + ", 도로명 주소=" + roadAddress);

                        if (memberShipForm != null) {
                            memberShipForm.jtf_zipcode.setText(zipCode);
                            memberShipForm.jtf_address.setText(roadAddress);

                            AddressSearch.this.dispose();
                        }
                    }
                }
            }
        });
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
    
    
    
    

