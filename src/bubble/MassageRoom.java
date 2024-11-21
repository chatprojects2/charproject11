package bubble;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MassageRoom extends JFrame implements ActionListener {
    JPanel jp_center = new JPanel(); //색상,정렬,버튼,JLabel 수정 가능
    JScrollPane jsp_center = new JScrollPane(jp_center, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    JPanel jp_south = new JPanel();
    JTextField jtf_msg = new JTextField(20);
    JButton jb_msg = new JButton("전송");
    //메시지 홀수이면 초록, 짝수이면 오렌지
    int i = 1; //멤버변수

    public MassageRoom() {
        initDisplay();
    } //end of Message

    @Override
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();
        if (obj == jb_msg || obj == jtf_msg) {
            JPanel jp_msg1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JPanel jp_msg2 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            if(i%2==1){
                jp_msg1.setBackground(Color.green);
                jp_msg1.add(new JLabel("Message: "+jtf_msg.getText()));
                jp_center.add(jp_msg1);
                i++;
            }else {
                jp_msg2.setBackground(Color.orange);
                jp_msg2.add(new JLabel("Message: "+jtf_msg.getText()));
                jp_center.add(jp_msg2);
                i++;
            }
         //   jp_center.add(jp_msg);
            jp_center.revalidate();
            jp_center.repaint();
            jtf_msg.setText("");
            //스크롤바를 자동으로 아래로 이동시키기
            SwingUtilities.invokeLater(
                    () -> jsp_center.getVerticalScrollBar().setValue(jsp_center.getVerticalScrollBar().getMaximum()));
        } //end of if(obj)

    } //end of actionPerformed

    public void initDisplay() {
        jtf_msg.addActionListener(this);
        jb_msg.addActionListener(this);
        jp_center.setLayout(new GridLayout(0, 1)); // 0: 무한한 행 from API
        jp_south.setLayout(new BorderLayout());
        jp_south.add("Center", jtf_msg);
        jp_south.add("East", jb_msg);


        this.add("South", jp_south);
        this.add("Center", jsp_center); ///스크롤 패널 추가해야 스크롤 생김~~!
        this.setSize(500,600);
        this.setVisible(true);
    } //end of initDisplay()

    public static void main(String[] args) {
        new MassageRoom();
    } //end of main

} // end of MassageRoom