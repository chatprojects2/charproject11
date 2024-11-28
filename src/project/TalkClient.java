package project;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class TalkClient extends JFrame implements ActionListener {
	LoginForm loginForm = null;
	ChatingRoom chatingRoom = null;
	//MembershipWithdrawal membershipWithdrawal = null;
	Socket socket = null;
	ObjectOutputStream oos = null; //말 하고 싶을 때
	ObjectInputStream ois = null; //듣기 할 때
	String nickName = null; //닉네임 등록

	JPanel jp_second = new JPanel();
	JPanel jp_second_south = new JPanel();
	JButton jbtn_one = new JButton("1:1");
	JButton jbtn_change = new JButton("대화명변경");
	JButton jbtn_font = new JButton("글자색");
	JButton jbtn_exit = new JButton("나가기");
	JButton jbtn_unsubscribe = new JButton("회원탈퇴");
	JButton jbtn_friend = new JButton("친구 찾기");
	String[] cols = {"대화명"};
	String[][] data = new String[0][1];
	DefaultTableModel dtm = new DefaultTableModel(data, cols);
	JTable jtb = new JTable(dtm);
	JScrollPane jsp = new JScrollPane(jtb);
	JPanel jp_first = new JPanel();
	JPanel jp_first_south = new JPanel();
	JTextField jtf_msg = new JTextField(20); //south속지 center
	JButton jbtn_send = new JButton("전송"); //south속지 east
	JTextArea jta_display = null;
	JScrollPane jsp_display = null;
	Image back = null;

	public TalkClient() {}

	public TalkClient(LoginForm loginForm) {
		this.loginForm = loginForm; //안하면 NullPointerException 발생
		this.nickName = loginForm.nickName; // 닉네임 설정
		initDisplay();
		init();
		jtf_msg.addActionListener(this);
		jbtn_exit.addActionListener(this);
		jbtn_change.addActionListener(this);
		jbtn_friend.addActionListener(this);
		jbtn_unsubscribe.addActionListener(this);
		jbtn_font.addActionListener(this);

	}

	public void initDisplay() {
		this.setLayout(new GridLayout(1, 2));
		jbtn_one.addActionListener(this);
		jp_second.setLayout(new BorderLayout());
		jp_second.add("Center", jsp);

		jp_second_south.setLayout(new GridLayout(3,2));
		jp_second_south.add(jbtn_friend);
		jp_second_south.add(jbtn_one);
		jp_second_south.add(jbtn_change);
		jp_second_south.add(jbtn_font);
		jp_second_south.add(jbtn_unsubscribe);
		jp_second_south.add(jbtn_exit);
		jp_second.add("South", jp_second_south);
		jp_first.setLayout(new BorderLayout());
		jp_first_south.setLayout(new BorderLayout());
		jp_first_south.add("Center", jtf_msg);
		jp_first_south.add("East", jbtn_send);
		back = getToolkit().getImage("src\\athread\\talk\\back.jpg");
		jta_display = new JTextArea() {
			private static final long serialVersionUID = 1L;

			public void paint(Graphics g) {
				g.drawImage(back, 0, 0, this);
				Point p = jsp_display.getViewport().getViewPosition();
				g.drawImage(back, p.x, p.y, null);
				paintComponent(g);
			}
		};
		jta_display.setLineWrap(true);
		jta_display.setOpaque(false);
		Font font = new Font("돋움", Font.BOLD, 25);
		jta_display.setFont(font);
		jsp_display = new JScrollPane(jta_display);
		jp_first.add("Center", jsp_display);
		jp_first.add("South", jp_first_south);
		this.add(jp_first);
		this.add(jp_second);
		this.setTitle("[[" + nickName + "]] 님 대화창");
		this.setSize(800, 550);
		this.setVisible(true);
	}

	public static void main(String[] args) {
		JFrame.setDefaultLookAndFeelDecorated(true);
		TalkClient tc = new TalkClient();
		tc.initDisplay();
		tc.init();
	}

	//소켓 관련 초기화
	public void init() {
		try {
			//서버측의 ip주소 작성하기
			socket = new Socket("127.0.0.1", 3003);
			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());
			// 서버에 입장 메시지 전달
			oos.writeObject(100 + "#" + nickName);
			// 클라이언트 스레드 시작
			TalkClientThread tct = new TalkClientThread(this);
			tct.start();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		Object obj = ae.getSource();
		String msg = jtf_msg.getText();
		if (jbtn_one == obj) { // 1:1 채팅 버튼 클릭
			int selectedRow = jtb.getSelectedRow();
			if (selectedRow == -1) {
				JOptionPane.showMessageDialog(this, "대상을 선택하세요.");
				return;
			}
			String recipient = (String) dtm.getValueAt(selectedRow, 0); // 선택된 대화명 가져오기
			chatingRoom = new ChatingRoom(this, recipient);
			chatingRoom.set(nickName, true);
		}

		else if(obj==jbtn_font){
			Color selectedColor = JColorChooser.showDialog(this, "글자색 선택", Color.BLACK);

			jta_display.setForeground(selectedColor);
		}

		else if (jtf_msg == obj) {
			try {
				oos.writeObject(201 + "#" + nickName + "#" + msg);
				jtf_msg.setText("");
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (jbtn_exit == obj) {
			try {
				oos.writeObject(500 + "#" + this.nickName);
				System.exit(0);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (jbtn_change == obj) {
			String afterName = JOptionPane.showInputDialog("변경할 대화명을 입력하세요.");
			if (afterName == null || afterName.trim().length() < 1) {
				JOptionPane.showMessageDialog(this, "변경할 대화명을 입력하세요", "INFO", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			try {
				TalkDao dao = new TalkDao();
				dao.updateName(nickName, afterName);
				oos.writeObject(202 + "#" + nickName + "#" + afterName + "#" + nickName + "의 대화명이 " + afterName + "으로 변경되었습니다.");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		//친구 찾기
		else if(obj == jbtn_friend) {
			SearchFriend sf =new SearchFriend();

			sf.initDisplay();
		}

		else if(obj == jbtn_unsubscribe) {
			int confirm = JOptionPane.showConfirmDialog(
					this,
					"정말 회원 탈퇴를 진행하시겠습니까?",
					"회원 탈퇴",
					JOptionPane.YES_NO_OPTION
			);
			if (confirm == JOptionPane.YES_OPTION) {
				// 현재 로그인된 nickName을 기반으로 삭제 요청
				TalkDao dao = new TalkDao();
				int result = dao.deleteMember(nickName); // nickName 사용
				if (result > 0) {
					JOptionPane.showMessageDialog(this, "회원 탈퇴가 완료되었습니다.");
					try {
						oos.writeObject(500 + "#" + nickName); // 서버에 탈퇴 알림
						System.exit(0); // 클라이언트 종료
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(this, "회원 탈퇴 중 문제가 발생했습니다. 다시 시도해주세요.");
				}
			}
		}
	}

	public void sendMessage(String message) {
		System.out.println(message);
	}
}
