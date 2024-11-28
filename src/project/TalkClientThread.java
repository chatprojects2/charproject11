package project;

import javax.swing.*;
import java.util.StringTokenizer;
import java.util.Vector;

public class TalkClientThread extends Thread {
	TalkClient tc = null;
	boolean isChatingRoomOpen = false; // 1:1 채팅방 열린 상태 확인

	public TalkClientThread(TalkClient tc) {
		this.tc = tc;
	}

	/*
	 * 서버에서 말한 내용을 들어봅시다.
	 */
	public void run() {
		boolean isStop = false;
		while (!isStop) {
			try {
				String msg = "";//100#apple
				msg = (String) tc.ois.readObject();
				StringTokenizer st = null;
				int protocol = 0;//100|200|201|202|500
				if (msg.contains("이미 접속한 사용자입니다.")) {
					// LoginForm에 로그인 중복할 때 경고창 표시 요청
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							tc.setVisible(false);
							JOptionPane.showMessageDialog(null, "이미 접속한 사용자입니다.", "로그인 오류", JOptionPane.ERROR_MESSAGE);

						}
					});
				}
				else if (msg != null) {
					st = new StringTokenizer(msg, "#");
					protocol = Integer.parseInt(st.nextToken());//100
				}
				switch (protocol) {
					case 100: {//100#apple
						String nickName = st.nextToken();
						tc.jta_display.append(nickName + "님이 입장하였습니다.\n");
						Vector<String> v = new Vector<>();
						v.add(nickName);
						tc.dtm.addRow(v);
					}
					break;
					case 200: {

					}
					break;
					case 201: {
						String nickName = st.nextToken();
						String message = st.nextToken();
						tc.jta_display.append("[" + nickName + "]" + message + "\n");
						tc.jta_display.setCaretPosition
								(tc.jta_display.getDocument().getLength());
					}
					break;
					case 300: { // 1:1 대화 메시지 프로토콜
						String sender = st.nextToken();     // 송신자
						String recipient = st.nextToken();  // 수신자
						String message = st.nextToken();    // 메시지 내용

						// 현재 사용자가 송신자나 수신자인 경우에만 메시지 표시
						if (tc.nickName.equals(recipient) || tc.nickName.equals(sender)) {
							if (tc.chatingRoom == null || !tc.chatingRoom.isVisible()) {
								// 수신자가 본인일 때만 채팅창을 엽니다.
								if (tc.nickName.equals(recipient)) {
									tc.chatingRoom = new ChatingRoom(tc, sender); // 송신자와의 채팅창 생성
									tc.chatingRoom.set(tc.nickName, true);
								}
							}

							// 메시지가 송신자로부터 다시 돌아온 경우만 화면에 표시하도록 수정
							if (!sender.equals(tc.nickName)) {
								tc.chatingRoom.displayMessage(sender, message);
							}
						}
						break;
					}
					case 202: {
						String nickName = st.nextToken();
						String afterName = st.nextToken();
						String message = st.nextToken();
						//테이블에 대화명 변경하기
						for (int i = 0; i < tc.dtm.getRowCount(); i++) {
							String imsi = (String) tc.dtm.getValueAt(i, 0);
							if (nickName.equals(imsi)) {
								tc.dtm.setValueAt(afterName, i, 0);
								break;
							}
						}
						//채팅창에 타이틀바에도 대화명을 변경처리 한다.
						if (nickName.equals(tc.nickName)) {
							tc.setTitle(afterName + "님의 대화창");
							tc.nickName = afterName;
						}
						tc.jta_display.append(message + "\n");
					}
					break;
					case 500: {
						String nickName = st.nextToken();
						tc.jta_display.append(nickName + "님이 퇴장 하였습니다.\n");
						tc.jta_display.setCaretPosition
								(tc.jta_display.getDocument().getLength());
						for (int i = 0; i < tc.dtm.getRowCount(); i++) {
							String n = (String) tc.dtm.getValueAt(i, 0);
							if (n.equals(nickName)) {
								tc.dtm.removeRow(i);
							}
						}
					}
					break;
				}////////////end of switch
			} catch (Exception e) {
				// TODO: handle exception
			}
		}////////////////////end of while
	}
}////////////////////////end of run














