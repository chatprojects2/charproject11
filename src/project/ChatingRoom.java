package project;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChatingRoom extends JFrame implements ActionListener {
    TalkClient client;
    String nickName;
    String recipient; // 수신자 정보 추가
    JPanel jp_center = new JPanel();
    JScrollPane jsp_center = new JScrollPane(jp_center, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    JPanel jp_south = new JPanel();
    JTextField jtf_msg = new JTextField(20);
    JButton jb_msg = new JButton("Send");

    public ChatingRoom(TalkClient client, String recipient) {
        this.client = client;
        this.recipient = recipient;
        initDisplay();
    }

    private void initDisplay() {
        jtf_msg.addActionListener(this);
        jb_msg.addActionListener(this);
        jp_center.setLayout(new GridLayout(0, 1));
        jp_south.setLayout(new BorderLayout());
        jp_south.add("Center", jtf_msg);
        jp_south.add("East", jb_msg);

        this.add("Center", jsp_center);
        this.add("South", jp_south);
        this.setSize(500, 400);
        this.setVisible(false);
    }

    public void set(String nickName, boolean isView) {
        this.nickName = nickName;
        this.setTitle(nickName + "의 채팅창");
        this.setVisible(isView);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jb_msg || e.getSource() == jtf_msg) {
            sendMessage();
        }
    }

    private void sendMessage() {
        String message = jtf_msg.getText();
        if (!message.isEmpty()) {
            try {

                client.oos.writeObject("300#" + nickName + "#" + recipient + "#" + message); // 서버로 메시지 전송
                displayMessage(nickName, message); // 본인 메시지 표시
                jtf_msg.setText(""); //메시지창 초기화
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void displayMessage(String sender, String message) {
        JPanel jp_msg = new JPanel();
        JLabel lbl_msg = new JLabel("<html><body style='width: 200px;'><b>" + sender + ":</b> " + message + "</body></html>");
        lbl_msg.setOpaque(true);
        lbl_msg.setBorder(new EmptyBorder(10, 15, 10, 15));

        boolean isSelf = sender.equals(nickName);
        lbl_msg.setBackground(isSelf ? new Color(144, 238, 144) : new Color(255, 165, 0));
        lbl_msg.setForeground(isSelf ? Color.BLACK : Color.WHITE);
        jp_msg.setLayout(new FlowLayout(isSelf ? FlowLayout.RIGHT : FlowLayout.LEFT));

        jp_msg.add(lbl_msg);
        jp_center.add(jp_msg);
        jp_center.revalidate();
        jp_center.repaint();
        SwingUtilities.invokeLater(() -> {
            jsp_center.getVerticalScrollBar().setValue(jsp_center.getVerticalScrollBar().getMaximum());
        });
    }
}
