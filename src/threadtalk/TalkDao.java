package threadtalk;

import com.util.DBConnectionMgr;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TalkDao {
    DBConnectionMgr dbConnectionMgr = new DBConnectionMgr();
    Connection conn = dbConnectionMgr.getConnection();
    PreparedStatement pstmt1 = null;
    PreparedStatement pstmt2 = null;
    ResultSet rs = null;
    public String login(String user_id, String user_pw) {
        String mem_nick = null;
        StringBuilder idCheck = new StringBuilder();
        StringBuilder sql_login = new StringBuilder();
        int status =2; //1이면 아이디 존재함. -1: 아이디 없음.
        try{
            idCheck.append("select ");
            idCheck.append("  NVL((SELECT 1 from dual");
            idCheck.append(" where exists (select mem_name from tomato_member");
            idCheck.append("  where mem_id =: user_id)");
            idCheck.append("  ),-1) isID ");
            idCheck.append(" FROM dual");
            conn = dbConnectionMgr.getConnection();
            pstmt1 = conn.prepareStatement(idCheck.toString());
            pstmt1.setString(1, user_id);
            rs = pstmt1.executeQuery();
            while (rs.next()) {
                status = rs.getInt("isID");
            }
            if (status == 1) {
                sql_login.append("SELECT mem_nick FROM tomato_member");
                sql_login.append(" WHERE mem_id=?");
                sql_login.append(" AND mem_pw=?");
                pstmt2 = conn.prepareStatement(sql_login.toString());
                pstmt2.setString(1, user_id);
                pstmt2.setString(2, user_pw);
                rs=pstmt2.executeQuery();
                if (rs.next()) {
                    mem_nick = rs.getString("mem_nick");

                }else {
                    mem_nick = "비밀번호가 맞지 않습니다.";
                }

            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }




        return mem_nick;
    }
}
