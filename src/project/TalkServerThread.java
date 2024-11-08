package project;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.StringTokenizer;

public class TalkServerThread extends Thread {
	public TalkServer ts = null;
	Socket client = null;
	ObjectOutputStream oos = null;
	ObjectInputStream ois = null;
	String chatName = null;//현재 서버에 입장한 클라이언트 스레드 닉네임 저장
	public TalkServerThread(TalkServer ts) {//ts는 TalkServer에서 this와 동일한 주소번지임
		this.ts = ts;//멤버변수와 동기화
		this.client = ts.socket;//멤버변수 동기화 - run메소드에서 사용하니까
		try {
			oos = new ObjectOutputStream(client.getOutputStream());//말하기 객체생성
			ois = new ObjectInputStream(client.getInputStream());//듣기 객체 생성
			String msg = (String)ois.readObject();//듣기 처리함 100#키위, 100#사과, 100#토마토
			ts.jta_log.append(msg+"\n");//서버 JTextArea에 출력하기
			StringTokenizer st = new StringTokenizer(msg,"#");//토큰이 샾을 기준으로 문자열 자르기
			st.nextToken();//100 - 100사용하지 않음 - 그래서 대입연산자로 저장하지 않음
			chatName = st.nextToken();//누가 입장했는지 필요하니까 저장하기
			ts.jta_log.append(chatName+"님이 입장하였습니다.\n");//서버측 로그 창에 키위님이 입장하였습니다. 로그 출력
			//파라미터 자리가 단 두 자리뿐 :앞자리는 제네릭타입, 뒷자리는 Vector
			//TalkServer의 run 메소드 안에서 인스턴스화됨 ts.globalList.size() = 0 이다.
			//사이즈가 0이므로 서버에 처음 들어온 사람은 for문 실행하지 않음
			for(TalkServerThread tst:ts.globalList) {//반복문 돌린다.- 개선된 for문이다. - 전체를 모두 출력할 때
			//이전에 입장해 있는 친구들 정보 받아내기
				//String currentName = tst.chatName;
				this.send(100+"#"+tst.chatName);
			}
			//현재 서버에 입장한 첫번째 클라이언트가 Vector에 추가됨
			ts.globalList.add(this);
			//메소드 앞에 this가 있다면 현재 메모리에 로딩되어 있는 클래스 말함
			this.broadCasting(msg);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
	//현재 나보다 먼저 입장해 있는 친구들 모두에게 메시지 전송하기 구현
	public void broadCasting(String msg) {//100#키위
		for(TalkServerThread tst:ts.globalList) {//이전까지는 globalList.size()0이지만  35번라인에서 하나추가됨
			tst.send(msg);//딱 한번만 실행이 된다.- globalList.size()가 3이면 send메소드가 3번 호출된다.
			//tst자리에 this를 쓴다면 내가 보낸 메시지를 나한테 보낸다. 는 뜻
			//this.send(msg)가 아니라 왜 tst.send(msg) 인가요?
		}
	}
	//클라이언트에게 말하기 구현할 때 여러 명에게 동일한 메소드를 호출해야 합니다.
	//그래서 굳이 메소드로 분리하였다.for문 안에서 send메소드를 호출하면 
	//그 안에 writeObject가 호출됩니다.
	//결론 : 현재 단톡방에 있는 친구들 모두에게 전달할 때 사용된다.
	public void send(String msg) {//한 사람한테 간다.
		try {
			oos.writeObject(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//듣고 말하는 구간이다. - 분리하지 않고 run메소드 안에서 한 번에 하나요?
	//서버는 듣고 말하는 것이다. 말을 지어내지 않는다.
	public void run() {
		String msg = null;//청취한 메시지를 담는 변수이다. 
		boolean isStop = false;//무한 반복
		try {
			//while(true) {//무한루프에 빠질 수 있다.
			//라벨문을 while문 앞에 사용했으므로 70~109번 블록 몽땅 빠져나감.110으로 간다.
			run_start://라벨문이다.강퇴 혹은 강제 종료 while문 탈출함
			while(!isStop) {
				//입장에 대한 처리는 클라이언트가 서버소켓에 접속했을 때 
				//TalkServerThread를 인스턴스화 하고 괄호안에 this를 붙여서 생성자를 호출하였다.
				//바로 여기서 입장처리는 끝냈다.
				msg = (String)ois.readObject();
				ts.jta_log.append(msg+"\n");//클라이언트가 보낸 메시지를 담는 변수 msg출력하기
				//클라이언트측에서 보낸 메시지를 서버가 청취하는데 성공함.
				ts.jta_log.setCaretPosition
				(ts.jta_log.getDocument().getLength());//메시지 늘어날 때 자동으로 스크롤 바를 현재 메시지 이동해준다.
				StringTokenizer st = null;
				int protocol = 0;//100|200|201|202|500
				if(msg !=null) {
					st = new StringTokenizer(msg,"#");
					protocol = Integer.parseInt(st.nextToken());//100|201
				}
				switch(protocol) {
					case 200:{
						
					}break;
					case 201:{//다자간 대화하기 201#키위#오늘 스터디할까?
						String nickName = st.nextToken();//키위
						String message = st.nextToken();//오늘 스터디할까?
						broadCasting(201
								   +"#"+nickName
								   +"#"+message);
					}break;
					case 202:{
						String nickName = st.nextToken();
						String afterName = st.nextToken();
						String message = st.nextToken();
						this.chatName = afterName;
						broadCasting(202
								+"#"+nickName
								+"#"+afterName
        						+"#"+message);
					}break;
					case 500:{
						String nickName = st.nextToken();
						ts.globalList.remove(this);
						broadCasting(500
								+"#"+nickName);
					}break run_start;
				}/////////////end of switch
			}/////////////////end of while			
			///////// 여기로 이동한다.
		} catch (Exception e) {
			// TODO: handle exception
		}
	}/////////////////////////end of run
}
