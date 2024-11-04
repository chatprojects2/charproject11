package threadtalk;

import java.util.StringTokenizer;

public class StringMsg {
    public static void main(String[] args) {
        String msg = "200#키위#사과#오늘 스터디할까요?";
        StringTokenizer st = new StringTokenizer(msg, "#");
        String protocol = st.nextToken(); //200저장
        String me =st.nextToken(); //키위
        String you = st.nextToken(); //사과
        String message = st.nextToken(); //오늘 스터디할까요?
        System.out.println(protocol);
        System.out.println(me);
        System.out.println(you);
        System.out.println(message);                     
    }
}
