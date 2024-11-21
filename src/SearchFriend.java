import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

public class SearchFriend extends JDialog implements ActionListener, ItemListener {
    
    String[] gubuns = {"전체","아이디","닉네임","이름"};
    String[] cgubuns = {"all","mem_id","mem_nick","mem_name"};
    String gubun = null;
    String keyword = null;

    JComboBox jcb_gubuns = new JComboBox(gubuns);;
    JTextField jtf_search = new JTextField(20);
    JButton bt_search = new JButton("찾기");
    JPanel jp_north = new JPanel();

    String[] cols = {"아이디","닉네임","이름"};
    String[][] data = new String[0][3];
    DefaultTableModel dtm_friend = new DefaultTableModel(data,cols);
    JTable jtb_friend = new JTable(dtm_friend);
    JScrollPane jsp_friend = new JScrollPane(jtb_friend,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
            ,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

    TalkDao tdao = new TalkDao();


    public void searchFri(TalkVO ptvo){

        List<TalkVO> list = tdao.searchFriend(ptvo);

        while(dtm_friend.getRowCount()>0){
            dtm_friend.removeRow(0); //첫째줄 지우기 반복~
        } //end of while

        for(int i=0;i<list.size();i++) {
            List<Object> li = new ArrayList<>();
            TalkVO tvo = list.get(i);
            li.add(tvo.getMem_id());
            li.add(tvo.getMem_nick());
            li.add(tvo.getMem_name());
            dtm_friend.addRow(li.toArray());
            System.out.println(li);
        }
    }

    public void initDisplay(){

        jcb_gubuns.addItemListener(this);
        jtf_search.addActionListener(this);
        bt_search.addActionListener(this);

        jp_north.setLayout(new BorderLayout());
        jp_north.add("West",jcb_gubuns);
        jp_north.add("Center",jtf_search);
        jp_north.add("East",bt_search);

        this.add("North",jp_north);
        this.add("Center",jsp_friend);
        this.setSize(500,300);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        SearchFriend sf = new SearchFriend();
        sf.initDisplay();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if(src == jtf_search || src == bt_search){
            TalkVO ptvo = new TalkVO();
            ptvo.setKeyword(jtf_search.getText());
            ptvo.setGubun(gubun);
            searchFri(ptvo);

        }

    }

    @Override
    public void itemStateChanged(ItemEvent e) {
            Object src = e.getSource();
            if(src == jcb_gubuns){
                if(e.getStateChange() == ItemEvent.SELECTED) {
                    gubun = cgubuns[jcb_gubuns.getSelectedIndex()];
                    System.out.println("선택한 컬럼명은 " + gubun);
                }
            }
    }
}
