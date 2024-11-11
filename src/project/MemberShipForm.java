package project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MemberShipForm extends JFrame implements ActionListener {

    public static final String KAKAO_API_KEY = "c2d95ac533dad9386cc3ab5b88a2b9a8";
    TalkVO tVO = null;

    TalkDao talkDao = new TalkDao();
    JPanel jp_center = new JPanel();
    JLabel jlb_id = new JLabel("아이디");
    JTextField jtf_id = new JTextField(10);
    JLabel jlb_pw = new JLabel("패스워드");
    JButton jbtn_showPw = new JButton("보기"); // 패스워드 보이기 버튼
    JPasswordField jtf_pw = new JPasswordField(10);
    JLabel jlb_name = new JLabel("이름");
    JTextField jtf_name = new JTextField(30);
    JLabel jlb_email = new JLabel("이메일");
    JTextField jtf_email = new JTextField(20);
    JScrollPane jsp = null;
    JPanel jp_south = new JPanel();
    JButton jbtn_idCheck = new JButton("중복 확인");
    JButton jbtn_ins = new JButton("등록");
    JButton jbtn_close = new JButton("닫기");

    JLabel jlb_nickname = new JLabel("닉네임");
    JTextField jtf_nickname = new JTextField(20);

    JLabel jlb_address = new JLabel("주소");
    JTextField jtf_address = new JTextField(20);
    JButton jbtn_addressSearch = new JButton("주소 찾기");

    JButton		jbtn_file = new JButton("파일찾기");
    JTextField 	jtf_file = new JTextField(35);
    JLabel		jlb_img	 = new JLabel("이미지");

    Container cont = getContentPane();
    String path = "C:\\workspace_java\\basic1\\src\\image\\image\\talk\\";

    JFileChooser chooser = new JFileChooser();

    boolean isPasswordVisible = false; // 패스워드가 보이는 상태를 저장하는 변수

    public MemberShipForm() {

        initDisplay();
    }

    public void setValue(TalkVO talkVO){
        if(talkVO == null){
            setimg("");
        }

        else{
            setimg(getimg());
        }
    }

    public void initDisplay() {
        jp_center.setLayout(null);
        jlb_id.setBounds(20, 20, 100, 20);
        jtf_id.setBounds(120, 20, 120, 20);
        jbtn_idCheck.setBounds(250, 20, 100, 20); // 아이디 중복 체크

        jlb_pw.setBounds(20, 45, 100, 20);
        jtf_pw.setBounds(120, 45, 120, 20);

        jbtn_showPw.setBounds(250, 45, 70, 20); // 패스워드 보이기 버튼 위치


        jlb_name.setBounds(20, 95, 100, 20);
        jtf_name.setBounds(120, 95, 150, 20);

        jlb_email.setBounds(20, 70, 100, 20);
        jtf_email.setBounds(120, 70, 150, 20);



        jlb_nickname.setBounds(20, 120, 100, 20);
        jtf_nickname.setBounds(120, 120, 150, 20);


        jlb_address.setBounds(20, 145, 100, 20);
        jtf_address.setBounds(120, 145, 150, 20);
        jbtn_addressSearch.setBounds(280, 145, 100, 20);

        jbtn_file.setBounds(20, 220, 90, 20);
        jtf_file.setBounds(120, 220, 250, 20);
        jlb_img.setBorder(BorderFactory.createEtchedBorder());
        jlb_img.setBounds(120, 245, 150, 150);

        jp_center.add(jbtn_idCheck); // 중복 확인 버튼 추가
        jp_center.add(jlb_id);
        jp_center.add(jtf_id);
        jp_center.add(jlb_pw);
        jp_center.add(jtf_pw);
        jp_center.add(jbtn_showPw);
        jp_center.add(jlb_name);
        jp_center.add(jtf_name);
        jp_center.add(jlb_email);
        jp_center.add(jtf_email);

        jp_center.add(jlb_nickname);
        jp_center.add(jtf_nickname);

        jp_center.add(jbtn_file);
        jp_center.add(jtf_file);
        jp_center.add(jlb_img);


        jp_center.add(jlb_address);
        jp_center.add(jtf_address);
        jp_center.add(jbtn_addressSearch);

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


        // 버튼 이벤트 추가해주기
        jbtn_ins.addActionListener(this);
        jbtn_close.addActionListener(this);
        jbtn_idCheck.addActionListener(this);
        jbtn_showPw.addActionListener(this);
        jbtn_addressSearch.addActionListener(this);
        jbtn_file.addActionListener(this);
    }

    public String getimg(){
        //Path 객체 생성 - 메소드로 생성하기 가능함(중상)
        Path path = Paths.get(jtf_file.getText());
        //파일명 추출
        String fileName = path.getFileName().toString();
        //파일명 출력 - C:\workspace_java\basic1\src\image\image\book\\
        System.out.println("파일명"+fileName);
        return fileName;
    }

    public void setimg(String img){
        ImageIcon icon = new ImageIcon(path+img);
        //이미지가 300*400보다 커서 스케일을 조정합니다.
        //원본 이미지 아이콘에서 이미지 객체 추출
        Image originallmg = icon.getImage();
        //추출된 이미지의 크기를 조절하여 새로운 image 객체 생성하기
        Image changelmg = originallmg.getScaledInstance(150,150,Image.SCALE_SMOOTH);
        //새로운 이미지로 이미지 아이콘 객체를 생성하기
        ImageIcon scaleicon = new ImageIcon(changelmg);

        jlb_img.setIcon(scaleicon);
    }




    @Override
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();
        if(obj == jbtn_file){//파일 찾기 버튼 누른거야?
            //[열기] 대화상자를 오픈한다.
            //경로를 줄 때 상대경로[그대로 재사용이 가능함]와 절대경로[처음부터 끝까지 다 주는것 - 경로를 일일이 바꾼다.]
            chooser.setCurrentDirectory(new File(".\\src"));
            int inRet = chooser.showOpenDialog(this);
            if(inRet == JFileChooser.APPROVE_OPTION){
                //파일을 여는 처리를 한다.
                //파일과 관련된 처리도 경로가 안 맞는 경우이거나 파일에 문제가 있을 수 ㅣㅇㅆ어
                //반드시 예외처리를 하도록 강제하고 있습니다.
                try{
                    File file = chooser.getSelectedFile();
                    //선택한 파일의 경로를 통해서 BufferedReader 객체를 작성
                    BufferedReader myReader = new BufferedReader(new FileReader(file.getAbsoluteFile()));
                    System.out.println(file.getAbsoluteFile());
                    String cpath = file.getAbsolutePath();
                    jtf_file.setText(cpath);
                    /////////////////////////이미지 미리보기 시작//////////////////////
                    ImageIcon icon = new ImageIcon(cpath);
                    Image originalimg = icon.getImage();
                    Image changelmg = originalimg.getScaledInstance(150,150,Image.SCALE_SMOOTH);
                    ImageIcon changeicon  = new ImageIcon(changelmg);
                    jlb_img.setIcon(changeicon);
                    //revalidate()는 새 구성요소가 추가되거나 이전 구성 요소가 제거되면
                    //컨테이너에서 호출됩니다.
                    //이 호출은 레이아웃 관리자에게 새 구성 요소 목록을 기반으로 재설정하도록
                    //지시하는 명령입니다.
                    cont.revalidate();
                    /////////////////////////이미지 미리보기 시작//////////////////////
                }catch (Exception ex){
                    throw new RuntimeException(ex);
                }
            }
        }
        // if (obj == jbtn_zipcode) {
        //     zcs.initDisplay();
        // }
        if (obj == jbtn_ins) {
            //회원 가입 DB 삽입
            TalkVO member = new TalkVO();
            member.setMem_id(jtf_id.getText().trim());
            member.setMem_pw(new String(jtf_pw.getPassword()));
            member.setMem_name(jtf_name.getText().trim());
            member.setMem_nick(jtf_nickname.getText().trim());
            member.setEmail(jtf_email.getText().trim());
            member.setImg(getimg());
            int result = talkDao.insertMember(member);
            if (result > 0) {
                JOptionPane.showMessageDialog(this, "회원가입이 완료되었습니다.");
            } else {
                JOptionPane.showMessageDialog(this, "회원가입에 실패했습니다.");
            }

        } else if (obj == jbtn_close) {
            this.dispose(); // 현재 창만 닫기
        } else if (obj == jbtn_idCheck) {
            // 아이디 중복 확인 버튼 클릭 시 처리
            String userId = jtf_id.getText().trim();
            if (userId.isEmpty()) {
                JOptionPane.showMessageDialog(this, "아이디를 입력하세요.");
                return;
            }

            // 중복 여부 확인
            boolean isDuplicate = talkDao.checkDuplicateId(userId);
            if (isDuplicate) {
                JOptionPane.showMessageDialog(this, "이미 존재하는 아이디입니다.","WARNING",JOptionPane.WARNING_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "사용 가능한 아이디입니다.");
            }
        }
        else if (obj == jbtn_showPw) {
            // 패스워드 보이기 버튼 클릭 시 패스워드 보이기/숨기기
            if (isPasswordVisible) {
                jtf_pw.setEchoChar('*'); // 숨김 모드로 변경
                jbtn_showPw.setText("보기");
            } else {
                jtf_pw.setEchoChar((char) 0); // 보이기 모드로 변경
                jbtn_showPw.setText("숨기기");
            }
            isPasswordVisible = !isPasswordVisible; // 상태 변경
        }
        else if (obj == jbtn_addressSearch){
            String query = jtf_address.getText().trim();
            if (!query.isEmpty()) {
                jtf_address.setText(query);
                System.out.println(query);
            } else {
                JOptionPane.showMessageDialog(this, "주소를 입력하세요.");
            }
        }

    }

//    public static void main(String[] args) {
//        MemberShipForm msf = new MemberShipForm();
//        msf.setVisible(true);
//    }





}