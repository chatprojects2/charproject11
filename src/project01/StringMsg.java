package project01;

import java.util.StringTokenizer;

public class StringMsg {
    public static void main(String[] args) {
        // 글자색 변경하기 - 프로토콜 설계
        // 대화명 변경 -  프로토콜 설계
        // 모든 요청은 반드시 서버를 경유해야한다.
        // 단 오라클 서버(AWS-EC2구축) 사용시에는 이것도 서버이니까 직접 클래스를 연결해도
        // 괜찮다.
        String msg = "200#키위#사과#오늘 스터디할까요?";
        StringTokenizer st = new StringTokenizer(msg,"#");
        String protocol = st.nextToken();//200 저장
        String me = st.nextToken();//키위
        String you = st.nextToken();//사과
        String message = st.nextToken();//오늘 스터디할까요?
        System.out.println(protocol);
        System.out.println(me);
        System.out.println(you);
        System.out.println(message);
    }
}
