package threadtalk;

import com.util.DBConnectionMgr;
import jdbc.step1.DeptDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DBUnitTest {
    DBConnectionMgr dbMgr = null;
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;


    public DBUnitTest() {
        dbMgr = new DBConnectionMgr().getInstance();
        conn = dbMgr.getConnection();
        dbTest();

    }

    public void dbTest(){
        System.out.println(conn);
        String query = "SELECT deptno,dname,loc FROM dept";
        List<DeptDTO> list = new ArrayList<DeptDTO>();
        try{
            pstmt = conn.prepareStatement(query);
            //executeQuery()는 select일 때 사용함. 리턴타입이 ResultSet
            rs = pstmt.executeQuery(); //executeUpdate() : int - INSERT, UPDATE, DELETE
            while(rs.next()){
                DeptDTO dto;
                dto = new DeptDTO(rs.getInt("deptno"),rs.getString("dname"),rs.getString("loc"));//deptno, dname, loc - 나는 클래스이다. 변수를 3가지 선언가능함.
                list.add(dto);
                //private - dvo.setDeptno(10) - > deptno = 10

            }
            System.out.println(list.size());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        new DBUnitTest();
    }
}
