package wonjun;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ModifyDialog extends JFrame implements ActionListener {
    JPanel jp_center = new JPanel();
    JLabel jlb_nickName = new JLabel("닉네임");
    JTextField jtf_nickName = new JTextField(20);
    JLabel jlb_pw = new JLabel("기존 비밀번호");
    JTextField jtf_pw = new JTextField(20);
    JLabel jlb_newPW = new JLabel("새 비밀번호");
    JTextField jtf_newPW = new JTextField(20);
    JLabel jlb_onemoreNewPW = new JLabel("새 비밀번호 확인");
    JTextField jtf_onemoreNewPW = new JTextField(20);
    JScrollPane jsp = new JScrollPane(jp_center);

    JPanel jp_south = new JPanel();
    JButton jb_save = new JButton("저장");
    JButton jb_close = new JButton("닫기");
    JButton jb_CheckID = new JButton("중복검사");
    JButton jb_changePW = new JButton("변경");

    public ModifyDialog(){
        initDisplay();
    }

    public void initDisplay(){
        jp_center.setLayout(null);
        jb_save.addActionListener(this);
        jb_close.addActionListener(this);
        jb_CheckID.addActionListener(this);

        jlb_nickName.setBounds(20, 45, 100, 20);
        jtf_nickName.setBounds(120, 45, 120, 20);
        jlb_pw.setBounds(20, 95, 100, 20);
        jtf_pw.setBounds(120, 95, 150, 20);
        jlb_newPW.setBounds(20,120,100,20);
        jtf_newPW.setBounds(120,120,150,20);
        jlb_onemoreNewPW.setBounds(20,150,100,20);
        jtf_onemoreNewPW.setBounds(120,150,150,20);
        jb_CheckID.setBounds(310,40,85,30);
        this.add(jb_CheckID);
        jb_changePW.setBounds(315,150,75,30);
        this.add(jb_changePW);


        //btn_CheckID.setBounds(200,45,10,10);


        //jlb_email.setBounds(20, 70, 100, 20);
        //jtf_email.setBounds(120, 70, 150, 20);
        jp_center.add(jlb_nickName);
        jp_center.add(jtf_nickName);
        jp_center.add(jlb_pw);
        jp_center.add(jtf_pw);
        jp_center.add(jlb_newPW);
        jp_center.add(jtf_newPW);
        jp_center.add(jlb_onemoreNewPW);
        jp_center.add(jtf_onemoreNewPW);
        //jp_center.add(jb_CheckID);

        jp_south.setLayout(new FlowLayout(FlowLayout.RIGHT));
        jp_south.add(jb_save);
        jp_south.add(jb_close);
        jsp = new JScrollPane(jp_center);
        this.add("Center",jp_center);
        this.add("South",jp_south);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setTitle("개인정보 변경");
        this.setSize(450,600);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new ModifyDialog();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();

        // 찾기 버튼 클릭 시 PW 찾기 화면으로 이동
        if (obj == jb_save) {
            JOptionPane.showMessageDialog(this, "저장이 완료되었습니다.");
            ModifyDialog mdf = new ModifyDialog(); // 화면 생성
            mdf.setVisible(true); // 화면 표시
            dispose(); // 개인정보 변경 창 닫기
        }
        // 닫기 버튼 클릭 시 현재 창만 닫기
        else if (obj == jb_close) {
            this.dispose();
        }
    }
}
