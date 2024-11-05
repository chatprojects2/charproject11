package jdbc.step1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/*
SELECT mem_nick, mem_name
    FROM tomato_member
    WHERE mem_id=:user_id - 파라미터 String-varchar2(10)
    AND mem_pw=:user_pw; - 파라미터 String-varchar2(10)
 */
public class LoginAction {
    Connection con;// 물리적으로 떨어져있는 오라클 서버와 연결 통로를 확보
    Statement stmt;// 위에서 연결된 경로로 쿼리문을 전달하기와 처리 요청하기에 대한 메소드가 있다
    ResultSet rs;// 오라클 작동되는 커서를 구현한 인터페이스이다.
    // 파라미터의 갯수와 타입 그리고 리턴타입까지 선택할 수 있어야
    // 나는 메소드를 설계할 수 있다.
    public void login(String user_id, String user_pw) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT mem_nick, mem_name");
        sql.append(" FROM tomato_member");
        sql.append(" WHERE mem_id='"+user_id+"'");
        sql.append(" AND mem_pw ='"+user_pw+"'");
        try {
            Class.forName(OracleConnection._DRIVER);
            con = DriverManager.getConnection(OracleConnection._URL,OracleConnection._USER,OracleConnection._PW);
            stmt = con.createStatement();
            rs=stmt.executeQuery(sql.toString());
            if (rs.next()) {
                System.out.println(rs.getString(1)+" "+rs.getString(2));
            }else {
                System.out.println("입력하신 아이디나 혹은 비번이 맞지 않습니다.");
            }
        } catch (Exception e) {
            System.out.println(sql.toString());
            System.out.println(e.toString());
        }
    }
    public static void main(String[] args) {
        LoginAction loginAction = new LoginAction();
        loginAction.login("tomato","1234");

    }
}
