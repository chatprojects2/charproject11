package project;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChatingRoom extends JFrame implements ActionListener {
    private static final int CAPACITY = 2;

    TalkClient client; //채팅 서버에 들어온 클라이언트 주소번지
    String nickName;
    JPanel jp_center = new JPanel(); //색상, 정렬, 버튼 JLabel
    JScrollPane jsp_center = new JScrollPane(jp_center, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    JPanel jp_south = new JPanel();
    JTextField jtf_msg = new JTextField(20);
    JButton jb_msg = new JButton("Send");
    int i = 1; // 홀수이면 오른쪽(초록), 짝수이면 왼쪽(오렌지) 설정


    public ChatingRoom(TalkClient client) {
        this.client = client;

    }
    public void set(String nickName,boolean isView){
        this.nickName = nickName;
        jtf_msg.addActionListener(this);
        jb_msg.addActionListener(this);
        jp_center.setLayout(new GridLayout(0,1)); // 대화가 언제 끝날지 모르니까 row값으로 0을 준다.
        jp_south.setLayout(new BorderLayout());
        jp_south.add("Center", jtf_msg);
        jp_south.add("East", jb_msg);

        this.add("Center", jsp_center);
        this.add("South", jp_south);
        this.setSize(500,400);
        this.setVisible(isView);
        this.setTitle(nickName+"의 채팅창");


    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();
        if (obj == jb_msg || obj == jtf_msg) {
            sendMessage();
        }
    }

    private void sendMessage() {
        String message = jtf_msg.getText();
        if (!message.isEmpty()) {
            client.sendMessage(nickName + ": " + message); // 메시지 서버로 전송
            displayMessage(nickName, message);
            jtf_msg.setText(""); // 입력 필드 초기화
        }
    }

    private void displayMessage(String nickName, String message) {
        JPanel jp_msg = new JPanel();
        JLabel lbl_msg = new JLabel("<html><body style='width: 200px;'><b>" + nickName + ":</b> " + message + "</body></html>");
        lbl_msg.setOpaque(true);
        lbl_msg.setBorder(new EmptyBorder(10, 15, 10, 15));
        lbl_msg.setBackground(i % 2 == 1 ? new Color(144, 238, 144) : new Color(255, 165, 0));
        lbl_msg.setForeground(i % 2 == 1 ? Color.BLACK : Color.WHITE);
        jp_msg.setLayout(new FlowLayout(i % 2 == 1 ? FlowLayout.RIGHT : FlowLayout.LEFT));
        jp_msg.add(lbl_msg);
        jp_center.add(jp_msg);
        jp_center.revalidate();
        jp_center.repaint();
        jsp_center.getVerticalScrollBar().setValue(jsp_center.getVerticalScrollBar().getMaximum());
        i++;
    }
}
