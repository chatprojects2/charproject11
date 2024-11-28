package project;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FindIdPwView extends JDialog implements ActionListener {
    //JPanel jp_cardbox      = new JPanel();

    TalkDao dao = new TalkDao();
    JPanel jp_idmiss      = new JPanel(); //1.첫 화면 도화지
    JPanel jp_idfind      = new JPanel(); //2.아이디 찾기 성공했을 시 도화지
    JPanel jp_pwmiss      = new JPanel(); //3.비밀번호찾기 탭 눌렀을 때 도화지
    JPanel jp_pwfind      = new JPanel(); //4.비밀번호찾기 성공했을 시 도화지
    JPanel jp_north      = new JPanel();
    JLabel jlb_idfind     = new JLabel();//2번 도화지에 붙이는 JLabel
    JLabel jlb_name      = new JLabel("이름"); //1번 도화지에 붙는 JLabel
    JLabel jlb_phone      = new JLabel("이메일"); //1번 도화지에 붙는 JLabel
    JLabel jlb_id          = new JLabel("아이디"); //3번 도화지에 붙는 JLabel
    JLabel jlb_name2     = new JLabel("이름"); //3번 도화지에 붙는 JLabel
    JLabel jlb_pwupdate   = new JLabel("비밀번호");
    JLabel jlb_pwupdate2  = new JLabel("비밀번호확인");
    JButton jbtn_pwupdate = new JButton("비밀번호 변경");//3번 도화지에 붙이는 비밀번호 찾기 버튼 (성공시 4번 도화지로 전환)
    JButton jbtn_pwsearch = new JButton("비밀번호 찾기");//3번 도화지에 붙이는 비밀번호 찾기 버튼 (성공시 4번 도화지로 전환)
    JButton jbtn_gotopwmiss = new JButton("비밀번호찾기"); // 2번 도화지에 붙는 비밀번호 찾기 버튼(성공지 3번도화지로 전환)
    JButton jbtn_idsearch = new JButton("아이디찾기");//아이디 찾기 검색버튼 (성공지 2번도화지로 전환)
    JButton jbtn_idmiss   = new JButton("아이디찾기");//아이디 찾기 상단메뉴(JPanel-1번도화지 전환용)
    JButton jbtn_pwmiss   = new JButton("비밀번호찾기"); //비밀번호 찾기 상단메뉴(JPanel-3번도화지 전환용)


    JPasswordField jtf_pwupdate2 = new JPasswordField() {
        @Override
        public void setBorder(Border border) {

        }
    };
    JPasswordField jtf_pwupdate = new JPasswordField() {
        @Override
        public void setBorder(Border border) {

        }
    };
    JTextField jtf_name   = new JTextField() {
        @Override
        public void setBorder(Border border) {

        }
    };
    JTextField jtf_phone  = new JTextField() {
        @Override
        public void setBorder(Border border) {

        }
    };
    JTextField jtf_name2 = new JTextField() {
        @Override
        public void setBorder(Border border) {

        }
    };
    JTextField jtf_id = new JTextField() {
        @Override
        public void setBorder(Border border) {

        }
    };
    FindIdPwView infomiss  = null;

    Font f = new Font("맑은 고딕", Font.PLAIN,13);

    LoginForm loginForm =null;
    public FindIdPwView(LoginForm loginForm) {
        this.loginForm = loginForm;
        initDisplay();
    }

    public void initDisplay(){


        jbtn_idmiss.addActionListener(this);
        jbtn_pwmiss.addActionListener(this);
        jbtn_pwsearch.addActionListener(this);
        jbtn_gotopwmiss.addActionListener(this);
        jbtn_idsearch.addActionListener(this);
        jbtn_pwupdate.addActionListener(this);
        //상단 메뉴바의 도화지에 버튼 두 개 붙이기+버튼정의
        jbtn_pwmiss.setBorderPainted(false);
        jbtn_pwmiss.setBackground(Color.white);
        jbtn_pwmiss.setFont(f);
        jbtn_idmiss.setBorderPainted(false);
        jbtn_idmiss.setBackground(new Color(253,220,81));
        jbtn_idmiss.setFont(f);
        jp_north.add(jbtn_idmiss);
        jp_north.add(jbtn_pwmiss);

        //상단 메뉴바를 위한 도화지 붙이기
        jp_north.setLayout(new GridLayout());
        this.add("North",jp_north);

        //4번도화지에 컨텐츠 정의
        jlb_pwupdate.setBounds(40,78,80,40);
        jlb_pwupdate.setFont(f);
        jlb_pwupdate2.setBounds(15,128,80,40);
        jlb_pwupdate2.setFont(f);
        jtf_pwupdate.setBounds(100,78,250,40);
        jtf_pwupdate2.setBounds(100,128,250,40);
        jbtn_pwupdate.setBorderPainted(false);
        jbtn_pwupdate.setForeground(Color.WHITE);
        jbtn_pwupdate.setBackground(new Color(64,64,64));
        jbtn_pwupdate.setBounds(100, 193, 180, 40);
        jp_pwfind.add(jlb_pwupdate);
        jp_pwfind.add(jlb_pwupdate2);
        jp_pwfind.add(jtf_pwupdate);
        jp_pwfind.add(jtf_pwupdate2);
        jp_pwfind.add(jbtn_pwupdate);
        jp_pwfind.setLayout(new BorderLayout());

        //3번 도화지에 컨텐츠 정의
        jbtn_pwsearch.setBorderPainted(false);
        jbtn_pwsearch.setForeground(Color.WHITE);
        jbtn_pwsearch.setBackground(new Color(64,64,64));
        jbtn_pwsearch.setBounds(100, 193, 180, 40);
        jbtn_pwsearch.setFont(f);
        jp_pwmiss.add(jbtn_pwsearch);
        jlb_id.setBounds(45,128,80,40);
        jlb_id.setFont(f);
        jlb_name2.setBounds(52,78,80,40);
        jlb_name2.setFont(f);
        jp_pwmiss.add(jlb_name2);
        jp_pwmiss.add(jlb_id);
        jtf_name2.setBounds(100,78,250,40);
        jtf_id.setBounds(100,128,250,40);
        jp_pwmiss.add(jtf_id);
        jp_pwmiss.add(jtf_name2);
        jp_pwmiss.setLayout(new BorderLayout());

        //2번 도화지에 컨텐츠 정의
        jbtn_gotopwmiss.setBorderPainted(false);
        jbtn_gotopwmiss.setForeground(Color.WHITE);
        jbtn_gotopwmiss.setBackground(new Color(64,64,64));
        jbtn_gotopwmiss.setBounds(90,180,200,35);
        jlb_idfind.setBounds(95,90,300,35);
        jlb_idfind.setFont(new Font("맑은 고딕",Font.BOLD,13));
        jp_idfind.add(jbtn_gotopwmiss);
        jp_idfind.add(jlb_idfind);
        jp_idfind.setLayout(new BorderLayout());

        //1번 도화지에 컨텐츠 정의
        jlb_name.setBounds(50,50,40,40);
        jlb_name.setFont(f);
        jlb_phone.setBounds(30,100,80,40);
        jlb_phone.setFont(f);
        jp_idmiss.add(jlb_name);
        jp_idmiss.add(jlb_phone);
        jtf_name.setBounds(100,50,250,40);
        jtf_phone.setBounds(100,100,250,40);
        jp_idmiss.add(jtf_phone);
        jp_idmiss.add(jtf_name);
        jbtn_idsearch.setBorderPainted(false);
        jbtn_idsearch.setForeground(Color.WHITE);
        jbtn_idsearch.setBackground(new Color(64,64,64));
        jbtn_idsearch.setBounds(100, 165, 180, 40);
        jbtn_idsearch.setFont(f);
        jp_idmiss.add(jbtn_idsearch);
        jp_idmiss.setLayout(new BorderLayout());


        //jdl_infomiss.add("Center",jp_cardbox);
        //JDialog, 메인 프레임 정의
        jp_pwfind.setSize(400,300);
        jp_pwmiss.setSize(400,300);
        jp_idfind.setSize(400,300);
        jp_idmiss.setSize(400,300);
        this.add("Center",jp_pwfind);//4번도화지 orange
        this.add("Center",jp_pwmiss);//3번도화지 orange
        this.add("Center",jp_idfind);//2번도화지 gray
        this.add("Center",jp_idmiss);//1번도화지
        jp_pwfind.setVisible(false);
        jp_pwmiss.setVisible(false);
        jp_idfind.setVisible(false);
        jp_idmiss.setVisible(true);
        this.setTitle("아이디/비밀번호 찾기");
        this.setSize(400, 300);
        this.setVisible(false);
        ///action
        jtf_id.setText("");
        jtf_name.setText("");
        jtf_name2.setText("");
        jtf_phone.setText("");
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if(source==jbtn_idmiss){
            jp_pwfind.setVisible(false);
            jp_pwmiss.setVisible(false);
            jp_idfind.setVisible(false);
            jp_idmiss.setVisible(true);
            jbtn_pwmiss.setBackground(Color.white);
            jbtn_idmiss.setBackground(new Color(253,220,81));
        }
        else if(source==jbtn_pwmiss){
            jp_idmiss.setVisible(false);
            jp_idfind.setVisible(false);
            jp_pwfind.setVisible(false);
            jp_pwmiss.setVisible(true);
            jbtn_idmiss.setBackground(Color.white);
            jbtn_pwmiss.setBackground(new Color(253,220,81));

        }
        else if(source==jbtn_idsearch){
            String id = dao.findID(jtf_name.getText(),jtf_phone.getText());
            if(id!=null){
                JOptionPane.showMessageDialog(this,"당신의 아이디는 "+id+"입니다.","INFO",JOptionPane.INFORMATION_MESSAGE);
            }
            else{
                JOptionPane.showMessageDialog(this,"이름 또는 비밀번호가 일치하지않습니다.","WARNING",JOptionPane.WARNING_MESSAGE);
            }

        }
        else if(source==jbtn_gotopwmiss){
            jp_idfind.setVisible(false);
            jp_idmiss.setVisible(false);
            jp_pwfind.setVisible(false);
            jp_pwmiss.setVisible(true);
            jbtn_idmiss.setBackground(Color.white);
            jbtn_pwmiss.setBackground(new Color(253,220,81));
        }

        else if (source==jbtn_pwsearch) {
            String pw = dao.findPW(jtf_name2.getText(),jtf_id.getText());
            if(pw!=null){
                JOptionPane.showMessageDialog(this,"당신의 비밀번호는 "+pw+"입니다.","INFO",JOptionPane.INFORMATION_MESSAGE);
            }
            else{
                JOptionPane.showMessageDialog(this,"이름 또는 아이디가 일치하지않습니다.","WARNING",JOptionPane.WARNING_MESSAGE);
            }

        }


    }




    public static void main(String[] args) {
    }
}