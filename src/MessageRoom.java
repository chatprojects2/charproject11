import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MessageRoom extends JFrame implements ActionListener {

    JPanel jp_center = new JPanel(); // 메인 패널
    JScrollPane jsp_center = new JScrollPane(jp_center, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    JPanel jp_south = new JPanel();
    JTextField jtf_msg = new JTextField(20);
    JButton jb_msg = new JButton("Send");
    int i = 1; // 홀수이면 jp_msg1에 추가, 짝수이면 jp_msg2에 추가

    public MessageRoom() {
        initDisplay();
    }

    public void initDisplay(){
        jtf_msg.addActionListener(this);
        jb_msg.addActionListener(this);
        jp_center.setLayout(new GridLayout(0, 1)); // 행을 추가할 때마다 메시지가 자동으로 줄 바꿈됨
        jp_south.setLayout(new BorderLayout());
        jp_south.add("Center", jtf_msg);
        jp_south.add("East", jb_msg);

        this.add("Center", jsp_center);
        this.add("South", jp_south);
        this.setTitle("Message Room");
        this.setSize(500,600);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new MessageRoom();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == jtf_msg || source == jb_msg) {
            JPanel jp_msg1 = new JPanel(new FlowLayout(FlowLayout.RIGHT));  // 오른쪽 정렬 패널
            JPanel jp_msg2 = new JPanel(new FlowLayout(FlowLayout.LEFT));   // 왼쪽 정렬 패널
            JLabel lbl_msg = new JLabel("<html><body style='width: 200px;'>" + jtf_msg.getText() + "</body></html>");

            lbl_msg.setOpaque(true);
            lbl_msg.setBorder(new EmptyBorder(10, 15, 10, 15)); // 말풍선 내부 패딩

            if (i % 2 == 1) { // 홀수일 때 오른쪽 (초록색 말풍선)
                lbl_msg.setBackground(new Color(144, 238, 144)); // 연한 초록
                lbl_msg.setForeground(Color.BLACK);
                jp_msg1.add(lbl_msg); // jp_msg1에 메시지 추가
                jp_center.add(jp_msg1); // jp_center에 jp_msg1 추가
            } else { // 짝수일 때 왼쪽 (오렌지색 말풍선)
                lbl_msg.setBackground(new Color(255, 165, 0)); // 오렌지
                lbl_msg.setForeground(Color.WHITE);
                jp_msg2.add(lbl_msg); // jp_msg2에 메시지 추가
                jp_center.add(jp_msg2); // jp_center에 jp_msg2 추가
            }
            jp_center.revalidate();
            jp_center.repaint();
            jtf_msg.setText("");

            // 스크롤바 아래로 자동 이동
            SwingUtilities.invokeLater(() -> jsp_center.getVerticalScrollBar().setValue(jsp_center.getVerticalScrollBar().getMaximum()));

            i++; // 메시지 인덱스 증가
        }
    }
}
