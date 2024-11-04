package threadtalk;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginForm extends JFrame implements ActionListener {
	//선언부
	String imgPath = "D:\\workspace_java\\basic1\\src\\image\\talk\\";
	ImageIcon img = new ImageIcon(imgPath+"gg.jpg");
	JLabel jlb_id = new JLabel("아이디");
	JLabel jlb_pw = new JLabel("패스워드");
	Font font =new Font("휴먼매직체",Font.BOLD,15);
	JTextField jtf_id = new JTextField("tomato");
	JPasswordField jpf_pw = new JPasswordField("123");

	JButton btn_login =new JButton(new ImageIcon(imgPath+"login.png"));
	JButton btn_join = new JButton(new ImageIcon(imgPath+"confirm.png"));
	TalkDao talkDao = new TalkDao();
	String nickName= null;




	MemberShipForm memberShipForm = new MemberShipForm(this);
	//생성자
	public LoginForm() {
		System.out.println("LoginForm 호출 성공");
		initDisplay();
	}


	//JPaenl은 다양한 컴포넌트를 담을 수 있다.
	class Mypanel extends JPanel {
		// 컴포넌트가 그려질 때 호출되는 메소드. 패널에 맞춤형 그래픽을 그릴 수 있다.
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g); // 항상 먼저 부모 메서드를 호출해야 합니다.
			// 이미지를 패널의 (0, 0) 위치에 그려준다.
			g.drawImage(img.getImage(), 0, 0, getWidth(), getHeight(), null);
			setOpaque(false); // 패널의 배경을 투명하게 설정
		}
	}


	//화면처리부
	public void initDisplay(){
		btn_login.addActionListener(this);
		btn_join.addActionListener(this);
		setContentPane(new Mypanel());
		this.setLayout(null);
		//아이디 라인
		jlb_id.setBounds(45,200,80,40);
		jlb_id.setFont(font);
		jtf_id.setBounds(110,200,185,40);

		this.add(jlb_id);
		this.add(jtf_id);
		//비밀번호 라인
		jlb_pw.setBounds(45,240,80,40);
		jlb_pw.setFont(font);
		jpf_pw.setBounds(110,240,185,40);
		this.add(jlb_pw);
		this.add(jpf_pw);
		//로그인 버튼 라인 추가
		btn_login.setBounds(175,285,120,40);
		this.add(btn_login);
		//회원가입 버튼 라인 추가
		btn_join.setBounds(45,285,120,40);
		this.add(btn_join);
		this.setLocation(500,250);
		this.setSize(350,600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);

	}


	//메인메소드


	public static void main(String[] args) {
		//인스턴스화 없이 생성자 안에서 화면을 호출한다.
		new LoginForm();
//		new classes.ui.refresh.LoginForm();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();

		if (obj == btn_login) {
			String id = jtf_id.getText();
			String pw = jpf_pw.getText();
			String mem_nick = talkDao.login(id, pw);
			if ("비밀번호가 맞지 않습니다.".equals(mem_nick)) {
				JOptionPane.showMessageDialog(this,"비밀번호가 맞지 않습니다.");
				jpf_pw.setText("");

			}
			else if ("아이디가 존재하지 않습니다.".equals(mem_nick)) {
				JOptionPane.showMessageDialog(this,"아이디가 존재하지 않습니다.");
				jpf_pw.setText("");
				return;

			}else {
				nickName = mem_nick;
				System.out.println("오라클 서버에서 받아온 대화명 =======> "+ nickName);
				this.setVisible(false);
				jtf_id.setText("");
				jpf_pw.setText("");
				//로그인 성공시 파라미터로 LoginForm주소번지를 넘겨서
				//멤버변수들을 사용할 수 있도록 조치한다. - this추가한 생성자 추가할 것.
				//LoginForm을 통해서 아이디와 비번을 입력받고
				//TalkDao의 login메소드 호출 시 입력받은 아이디와 비번을 파라미터로 넘김
				//오라클 서버가 처리하고 응답으로 nickName을 넘겼다.
				//이렇게 받은 대화명은 이 어플이 유지되는 동안에 계속 같은 값을 가져야한다.
				//DB연동을 LoginForm에서 요청하였고 그가 응답을 받았다. 그정보를 클라이언트에 에게 넘긴다.
				//그렇게 하려면 멤버변수이므로 주소번지를 넘겨야한다(얕은복사)
				//원본이므로 LoginForm에서 nickName을 출력하거나 TalkClient에서 출력하거나 같아야함 - 공유 -this 생성자 파라미터로

				TalkClient client = new TalkClient(this);
			}
			try{
				String mem_id = jtf_id.getText();
				String mem_pw = jpf_pw.getText();

			} catch (Exception ex) {
				throw new RuntimeException(ex);
			}
//				else JOptionPane.showMessageDialog(this,"로그인 실패 다시 시도하세요!");
		}else if (obj == btn_join) {
			this.memberShipForm.initDisplay();
//			JOptionPane.showMessageDialog(this,"회원가입요청","WARNING",JOptionPane.WARNING_MESSAGE);
		}

	}
}

