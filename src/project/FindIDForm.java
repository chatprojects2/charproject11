package project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FindIDForm extends JFrame implements ActionListener {
    JPanel jp_center = new JPanel();
    JLabel jlb_name = new JLabel("이름");
    JTextField jtf_name = new JTextField(30);
    JLabel jlb_email = new JLabel("이메일");
    JTextField jtf_email = new JTextField(20);
    JLabel jlb_nick = new JLabel("아이디");
    JTextField jtf_nick = new JTextField(20);
    JScrollPane jsp = null;
    JPanel jp_south = new JPanel();
    JButton jbtn_ins = new JButton("찾기");
    JButton jbtn_close = new JButton("닫기");

    public FindIDForm() {
        initDisplay();
    }

    public void initDisplay() {
        jp_center.setLayout(null);
        jlb_nick.setBounds(20, 45, 100, 20);
        jtf_nick.setBounds(120, 45, 120, 20);
        jlb_name.setBounds(20, 95, 100, 20);
        jtf_name.setBounds(120, 95, 150, 20);
        jlb_email.setBounds(20, 70, 100, 20);
        jtf_email.setBounds(120, 70, 150, 20);
        jp_center.add(jlb_name);
        jp_center.add(jtf_name);
        jp_center.add(jlb_email);
        jp_center.add(jtf_email);
        jp_south.setLayout(new FlowLayout(FlowLayout.RIGHT));
        jp_south.add(jbtn_ins);
        jp_south.add(jbtn_close);
        this.add("South", jp_south);
        jsp = new JScrollPane(jp_center);
        this.add("Center", jsp);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setTitle("ID 찾기");
        this.setSize(400, 500);
        this.setVisible(true);

        // ActionListener 추가
        jbtn_ins.addActionListener(this);
        jbtn_close.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();

        // 찾기 버튼 클릭 시 PW 찾기 화면으로 이동
        if (obj == jbtn_ins) {
            JOptionPane.showMessageDialog(this, "찾기 버튼이 눌렸습니다.");
            FindPWForm fpf = new FindPWForm(); // PW 찾기 화면 생성
            fpf.setVisible(true); // PW 찾기 화면 표시
            dispose(); // 현재 ID 찾기 화면 닫기
        }
        // 닫기 버튼 클릭 시 현재 창만 닫기
        else if (obj == jbtn_close) {
            this.dispose();
        }
    }

    public static void main(String[] args) {
        FindIDForm msf = new FindIDForm();
        msf.setVisible(true);
    }
}
