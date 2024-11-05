package project;
//수정완료 123123123123
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginForm extends JFrame implements ActionListener {
	// 선언부
	String imgPath = "D:\\workspace_java\\basic1\\src\\image\\image\\talk\\";
	ImageIcon img = new ImageIcon(imgPath + "main.png");
	JLabel jlb_id = new JLabel("아이디");
	JLabel jlb_pw = new JLabel("패스워드");
	Font font = new Font("휴먼매직체", Font.BOLD, 18);
	JTextField jtf_id = new JTextField("tomato");
	JPasswordField jpf_pw = new JPasswordField("123");
	JButton btn_find = new JButton(new ImageIcon(imgPath + "ID,PW.jpg"));
	JButton btn_join = new JButton(new ImageIcon(imgPath + "confirm.png"));
	JButton btn_login = new JButton(new ImageIcon(imgPath + "LO.jpg"));
	TalkDao tDao = new TalkDao();
	String nickName = null;

	// 생성자
	public LoginForm() {
		System.out.println("LoginForm 디폴트 생성자");
		initDisplay();
	}

	class Mypanel extends JPanel {
		public void paintComponent(Graphics g) {
			g.drawImage(img.getImage(), 0, 0, null);
			setOpaque(false);
			super.paintComponent(g);
		}
	}

	// 화면 처리부
	public void initDisplay() {
		System.out.println("initDisplay 호출");
		btn_find.addActionListener(this);
		btn_join.addActionListener(this);
		btn_login.addActionListener(this);
		setContentPane(new Mypanel());
		this.setLayout(null);

		// 아이디 라인
		jlb_id.setBounds(15, 200, 80, 40);
		jlb_id.setFont(font);
		jtf_id.setBounds(80, 200, 185, 40);
		this.add(jlb_id);
		this.add(jtf_id);

		// 비밀번호 라인
		jlb_pw.setBounds(8, 240, 80, 40);
		jlb_pw.setFont(font);
		jpf_pw.setBounds(80, 240, 185, 40);
		this.add(jlb_pw);
		this.add(jpf_pw);

		// ID/PW 버튼 라인 추가
		btn_find.setBounds(175, 285, 120, 40);
		this.add(btn_find);

		// 회원가입 버튼 라인 추가
		btn_join.setBounds(45, 285, 120, 40);
		this.add(btn_join);

		// 로그인 버튼 라인 추가
		btn_login.setBounds(280, 190, 40, 85);
		this.add(btn_login);

		this.setLocation(800, 250);
		this.setSize(350, 600);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	// 메인 메소드
	public static void main(String[] args) {
		new LoginForm();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();

		// 로그인 버튼이 클릭되었을 경우
		if (obj == btn_login) {
			if ("".equals(jtf_id.getText()) || "".equals(jpf_pw.getText())) {
				JOptionPane.showMessageDialog(this, "아이디와 비밀번호를 확인하세요.", "INFO", JOptionPane.INFORMATION_MESSAGE);
				return;
			}

			try {
				String mem_id = jtf_id.getText();
				String mem_pw = jpf_pw.getText();
				String mem_nick = tDao.login(mem_id, mem_pw);

				if ("비밀번호가 맞지 않습니다.".equals(mem_nick) || "아이디가 존재하지 않습니다.".equals(mem_nick)) {
					JOptionPane.showMessageDialog(this, "아이디 또는 비밀번호가 틀립니다.");
					jpf_pw.setText("");
					return;
				} else {
					nickName = mem_nick;
					System.out.println("오라클 서버에서 받아온 대화명 =====> " + nickName);
					this.setVisible(false);
					jtf_id.setText("");
					jpf_pw.setText("");
					TalkClient tc = new TalkClient(this);
				}
			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
		}
		// 회원가입 버튼이 클릭된 경우
		else if (obj == btn_join) {
			// 회원가입 화면을 띄움
			MemberShipForm msf = new MemberShipForm();
			msf.setVisible(true);
			JOptionPane.showMessageDialog(this, "회원가입요청", "INFO", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
	}
}
