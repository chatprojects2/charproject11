package jdbc.step1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class OracleConnection2 {
    // 드라이버 클래스를 메모리에 올린다. -  제조사로 부터 제공되는 클래스이다.
    public static final String DRIVER = "oracle.jdbc.OracleDriver";
    // 물리적으로 떨어져있는 오라클 서버의 드라이버 이름과 ip주소, 포트 번호,SID이름
    // thin방식-멀티티어에서 사용하는 방식-동시에 많은 사람이 접속할 때 이용하는 방식, oci방식
    public static final String URL = "jdbc:oracle:thin:@192.168.0.29:1521:orcl11";
    // 계정이름
    public static final String USER = "scott";
    // 비번
    public static final String PW = "tiger";
    Connection con=null;
    Statement stmt=null;
    ResultSet rs=null;
    public OracleConnection2() {
        try {
            Class.forName(DRIVER);
            con= DriverManager.getConnection(URL, USER, PW);
        }catch (Exception e) {
            throw new RuntimeException(e);
    }
        System.out.println(con);
        String sql = "SELECT empno, ename, deptno FROM emp";
        try {
         stmt = con.createStatement();
         rs = stmt.executeQuery(sql);
         while(rs.next()) {
             System.out.println(rs.getInt("empno")+" "+rs.getString("ename")+" "+rs.getInt("deptno"));
         }
        }catch (Exception e) {
            System.out.println(e.toString());
        }
    }
    public static void main(String[] args) {
        new OracleConnection2();
    }
}
/*
Java API를 활용하여 오라클 서버에 접속하는 코드를 작성해봅니다.
1. 오라클 회사가 제공하는 드라이버 클래스(ojdbc6.jar)를 로딩한다.
 */

