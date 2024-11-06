package project01;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FindIDPWForm extends JFrame implements ActionListener {
    JPanel jp_center = new JPanel();
    JLabel jlb_name = new JLabel("이름");
    JTextField jtf_name = new JTextField(30);
    JLabel jlb_email = new JLabel("이메일");
    JTextField jtf_email = new JTextField(20);
    JScrollPane jsp = null;
    JPanel jp_south = new JPanel();
    JButton jbtn_ins = new JButton("찾기");
    JButton jbtn_close = new JButton("닫기");

    public FindIDPWForm() {
        initDisplay();
    }

    public void initDisplay() {
        jp_center.setLayout(null);

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

        // 버튼에 ActionListener 추가
        jbtn_ins.addActionListener(this);
        jbtn_close.addActionListener(this);

        this.add("South", jp_south);
        jsp = new JScrollPane(jp_center);
        this.add("Center", jsp);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setTitle("ID 찾기");
        this.setSize(400, 500);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();
        if (obj == jbtn_ins) {
            FindPW fpw = new FindPW();
            fpw.setVisible(true);
        } else if (obj == jbtn_close) {
            this.dispose(); // 현재 창만 닫기
        }
    }

    public static void main(String[] args) {
        new FindIDPWForm();
    }
}