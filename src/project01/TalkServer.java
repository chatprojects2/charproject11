package project01;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Calendar;
import java.util.List;
import java.util.Vector;
/*
 * main스레드와 통신스레드를 분리하자.
 * 자바는 단일 상속만 가능하여서 Thread를 추가로 상속 불가함
 * 그래서Runnable의 구현체 클래스로 작성하였다.
 * 
 */
public class TalkServer extends JFrame implements Runnable{
	TalkServerThread tst 		= null;
	List<TalkServerThread> 	globalList 	= null;
	ServerSocket 			server 		= null;
	Socket 					socket 		= null;
	JTextArea 				jta_log = new JTextArea(10,30);
	JScrollPane 			jsp_log = new JScrollPane(jta_log
			                                         ,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
			                                         ,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	JPanel 		jp_north = new JPanel();
	JButton 	jbtn_log = new JButton("로그저장");
	String      logPath  = "src\\athread\\talk\\";
	public void initDisplay() {
		jbtn_log.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Object obj = e.getSource();
				if(obj==jbtn_log) {
					String fileName = "log_"+setTimer()+".txt";
					System.out.println(fileName);//log_2020-03-13.txt
					try {
					//자바는 모든 기능 사물 들을 클래스로 설계하도록 유도한다.
					//파일명을 클래스로 만들어주는 API가 있다. -File
						File f = new File(logPath+fileName);
					//파일명만 생성될 뿐 내용까지 만들어주지는 않는다.
					//내용부분을 담는 별도의 클래스가 필요하다.
						PrintWriter pw = 
								new PrintWriter(
										new BufferedWriter(//필터클래스-카메라 필터
												new FileWriter(f.getAbsolutePath())));
					//io패키지에는 단독으로 파일을 컨트롤할 수 있는 클래스가 있고
					//그 클래스에 연결해서 사용하는 필터 클래스가 존재함.(기능을 향상해줌)	
						pw.write(jta_log.getText());
						pw.close();//사용한 입출력 클래스는 반드시 닫아줌.
					} catch (Exception e2) {
						//예외가 발생했을 때 출력함.
						//예외가 발생하지 않으면 실행이 안됨.
						System.out.println(e2.toString());
					}
				}				
			}
		});
		jp_north.setLayout(new FlowLayout(FlowLayout.LEFT));
		jta_log.setBackground(Color.orange);
		jp_north.add(jbtn_log);
		this.add("North",jp_north);
		this.add("Center",jsp_log);
		this.setSize(500, 400);
		this.setVisible(true);
		
	}
	//서버소켓과 클라이언트측 소켓을 연결하기
	//Runnable인터페이스를 implement 했으므로 인터페이스가 제공하는
	// run메소드를 오버라이딩 할 수 있다.
	// 굳이 스레드를 추가한 이유는 기본 main스레드와 통신스레드를 분리하여
	// 안정화를 위한 선택입니다.
	// 화면을 추가하여 로그정보를 출력하도록 UI객체를 활용하였으므로 소켓통신과는 분리하는 것이
	// 안전하다.
	@Override
	public void run() {
		//서버에 접속해온 클라이언트 스레드 정보를 관리할 벡터 생성하기 
		//List의 구현체 클래스에는 ArrayList(싱글스레드 안전)와 Vector(멀티스레드 안전)가 있다.
		globalList = new Vector<>();// 사용자가 접속하면 사용자마다 스레드를 생성하여 벡터에 관리한다(저장)
		boolean isStop = false;//while문에 true나 false 상수보다는 변수로 처리한다.
		// 왜냐면 서버는 24시간 가동되고 언제 사람이 들어올지 모르니까 무한 반복을 
		try {
			server = new ServerSocket(3002);//서버소켓 생성 - 대문 번호를 열어둔다.
			// 만일 클라이언트측에서 접속을 하게되면 클라이언트를 위한 추가 포트가 할당됨
			jta_log.append("Server Ready.........\n");// 서버측에서 클라이언트측과 소통시 로그정보를 출력
			// 클라이언트에서 서버측으로 전송시 에러인지
			// 서버측에서 클라이언트측 전송 시 에러인지 파악하기
			while(!isStop) {
				// socket이 null이 아니면 클라이언트 접속 성공했다.
				socket = server.accept();
				jta_log.append("client info:"+socket+"\n");//클라이언트 정보 출력
				// 사용자에대한 정보를 TalkServerThread에 보내기 - this - TalkServer
				// this를 통해서 Vector<TalkServerThread>:멤버변수와 Socket,OOS(말하기),OIS(청취)
				// 소켓이 있어야만 OOS와 OIS를 인스턴스화 할 수 있다. - 서로 의존관계에 있다.
				// 소켓이 없으면 OOS와 OIS가 null이다.
				
				TalkServerThread tst = new TalkServerThread(this);
				tst.start();// 메소드 오버라이드인 run메소드가 호출된다. - 처리하기
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		TalkServer ts = new TalkServer();
		ts.initDisplay();
		Thread th = new Thread(ts);
		th.start();
	}

	/*******************************************************
	 * 시스템의 오늘 날짜 정보 가져오기
	 * @param 해당사항 없음.
	 * @return 2020-03-13
	 ******************************************************/
	public String setTimer() {
		Calendar cal = Calendar.getInstance();
		int yyyy = cal.get(Calendar.YEAR);
		int mm = cal.get(Calendar.MONTH)+1;
		int day =  cal.get(Calendar.DAY_OF_MONTH);
		return yyyy+"-"+
			   (mm < 10 ? "0"+mm:""+mm)+"-"+
			   (day < 10 ? "0"+day:""+day);
	}////////////////end of setTimer
}
