package project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TalkDao {
    DBConnectionMgr dbMgr = DBConnectionMgr.getInstance();
    Connection conn = null;
    PreparedStatement pstmt1 = null;
    PreparedStatement pstmt2 = null;
    ResultSet rs = null;

    public String login(String user_id, String user_pw) {
        String mem_nick = null; // 로그인 성공 시 반환될 닉네임
        StringBuilder idCheck = new StringBuilder();
        StringBuilder sql_login = new StringBuilder();
        int status = 2; // 1이면 아이디 존재함. -1:아이디 없음, mem_nick이 없다면 비밀번호가 틀린거다.

        try {
            // 아이디 존재 여부 확인
            idCheck.append("SELECT");
            idCheck.append("     NVL((SELECT 1 FROM dual");
            idCheck.append("             WHERE EXISTS (SELECT mem_name FROM tomato_member");
            idCheck.append("                     WHERE mem_id=?) ");
            idCheck.append("     ),-1) isID ");
            idCheck.append("     FROM dual");

            conn = dbMgr.getConnection();
            pstmt1 = conn.prepareStatement(idCheck.toString());
            pstmt1.setString(1, user_id);
            rs = pstmt1.executeQuery();

            if (rs.next()) {
                status = rs.getInt("isID");
            }

            // 아이디가 존재하지 않는 경우
            if (status == -1) {
                return "아이디가 존재하지 않습니다."; // 아이디가 존재하지 않으면 이 메시지를 반환
            }

            // 아이디가 존재하는 경우 비밀번호 확인
            if (status == 1) {
                sql_login.append("SELECT mem_nick FROM tomato_member");
                sql_login.append(" WHERE mem_id=?");
                sql_login.append(" AND mem_pw=?");
                pstmt2 = conn.prepareStatement(sql_login.toString());
                pstmt2.setString(1, user_id);
                pstmt2.setString(2, user_pw);
                rs = pstmt2.executeQuery();

                // 비밀번호가 맞는 경우
                if (rs.next()) {
                    mem_nick = rs.getString("mem_nick"); // 닉네임을 반환
                } else {
                    return "비밀번호가 맞지 않습니다."; // 비밀번호가 틀리면 이 메시지를 반환
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            // 자원 해제
            try {
                if (rs != null) rs.close();
                if (pstmt1 != null) pstmt1.close();
                if (pstmt2 != null) pstmt2.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace(); // 예외 발생 시 스택 트레이스를 출력
            }
        }

        return mem_nick; // 로그인 성공 시 닉네임 반환
    }
}
