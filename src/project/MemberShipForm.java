package project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MemberShipForm extends JFrame implements ActionListener {
    JPanel jp_center = new JPanel();
    JLabel jlb_id = new JLabel("아이디");
    JTextField jtf_id = new JTextField(10);
    JLabel jlb_pw = new JLabel("패스워드");
    JTextField jtf_pw = new JTextField(10);
    JLabel jlb_name = new JLabel("이름");
    JTextField jtf_name = new JTextField(30);
    JLabel jlb_email = new JLabel("이메일");
    JTextField jtf_email = new JTextField(20);
    JScrollPane jsp = null;
    JPanel jp_south = new JPanel();
    JButton jbtn_ins = new JButton("등록");
    JButton jbtn_close = new JButton("닫기");

    public MemberShipForm() {
        initDisplay();
    }

    public void initDisplay() {
        jp_center.setLayout(null);
        jlb_id.setBounds(20, 20, 100, 20);
        jtf_id.setBounds(120, 20, 120, 20);
        jlb_pw.setBounds(20, 45, 100, 20);
        jtf_pw.setBounds(120, 45, 120, 20);
        jlb_name.setBounds(20, 95, 100, 20);
        jtf_name.setBounds(120, 95, 150, 20);
        jlb_email.setBounds(20, 70, 100, 20);
        jtf_email.setBounds(120, 70, 150, 20);
        jp_center.add(jlb_id);
        jp_center.add(jtf_id);
        jp_center.add(jlb_pw);
        jp_center.add(jtf_pw);
        jp_center.add(jlb_name);
        jp_center.add(jtf_name);
        jp_center.add(jlb_email);
        jp_center.add(jtf_email);
        jp_south.setLayout(new FlowLayout(FlowLayout.RIGHT));
        jp_south.add(jbtn_ins);
        jp_south.add(jbtn_close);
        this.add("South",jp_south);
        jsp = new JScrollPane(jp_center);
        this.add("Center",jsp);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE); // 프로그램 전체 종료가 아닌 창만 닫기
        this.setTitle("회원가입");
        this.setSize(400, 500);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();
        // if (obj == jbtn_zipcode) {
        //     zcs.initDisplay();
        // }
        if (obj == jbtn_ins) {
            JOptionPane.showMessageDialog(this, "등록 버튼이 눌렸습니다.");
        } else if (obj == jbtn_close) {
            this.dispose(); // 현재 창만 닫기
        }
    }

    public static void main(String[] args) {
        MemberShipForm msf = new MemberShipForm();
        msf.setVisible(true);
    }

}
