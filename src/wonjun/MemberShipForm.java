package wonjun;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;

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
    JButton jbtn_file = new JButton("사진찾기");
    JTextField jtf_file = new JTextField(35);

    //String t_img;

    JLabel jlb_img = new JLabel("이미지");
    String path = "C:\\workspace_java\\basic1\\src\\image\\image\\book\\";
    JFileChooser chooser = new JFileChooser();
    Container cont = getContentPane();

    // 선택된 이미지 파일 경로를 저장할 변수
    String selectedImagePath = null;

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

        jbtn_file.setBounds(15, 150, 90, 20);
        jtf_file.setBounds(120, 150, 200, 20);

        jlb_img.setBorder(BorderFactory.createEtchedBorder());
        jlb_img.setBounds(120, 200, 150, 150);

        jbtn_file.addActionListener(this);

        jp_center.add(jbtn_file);
        jp_center.add(jtf_file);
        jp_center.add(jlb_img);

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
        jbtn_ins.addActionListener(this);
        jbtn_close.addActionListener(this);

        this.add("South", jp_south);
        jsp = new JScrollPane(jp_center);
        this.add("Center", jsp);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setTitle("회원가입");
        this.setSize(400, 500);
        this.setVisible(true);
    }

    public void setimg(String img) {
        ImageIcon icon = new ImageIcon(img);
        Image originalImg = icon.getImage();
        Image changedImg = originalImg.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(changedImg);
        jlb_img.setIcon(scaledIcon);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();

        if (obj == jbtn_file) {
            chooser.setCurrentDirectory(new File(".\\src"));
            int inRet = chooser.showOpenDialog(this);
            if (inRet == JFileChooser.APPROVE_OPTION) {
                try {
                    File file = chooser.getSelectedFile();
                    String cpath = file.getAbsolutePath();
                    jtf_file.setText(cpath);
                    selectedImagePath = cpath; // 선택된 이미지 경로 저장
                    setimg(cpath);
                    cont.revalidate();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        } else if (obj == jbtn_ins) {
            // 등록 버튼을 누를 시, 정보 출력
            String id = jtf_id.getText();
            String pw = jtf_pw.getText();
            String name = jtf_name.getText();
            String email = jtf_email.getText();

            if (id.isEmpty() || pw.isEmpty() || name.isEmpty() || email.isEmpty() || selectedImagePath == null) {
                JOptionPane.showMessageDialog(this, "모든 필드를 입력하고 이미지를 선택하세요.", "경고", JOptionPane.WARNING_MESSAGE);
            } else {
                // 회원 정보 확인
                JOptionPane.showMessageDialog(this, "등록되었습니다!\n" +
                        "아이디: " + id + "\n" +
                        "이름: " + name + "\n" +
                        "이메일: " + email + "\n" +
                        "이미지 경로: " + selectedImagePath);
            }
        } else if (obj == jbtn_close) {
            this.dispose();
        }
    }

    public static void main(String[] args) {
        new MemberShipForm().setVisible(true);
    }
}
