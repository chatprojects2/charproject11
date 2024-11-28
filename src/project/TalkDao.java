package project;

import com.lib.DBConnectionMgr;



import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


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

    // 아이디 중복 체크
    public boolean checkDuplicateId(String user_id) {
        StringBuilder sql_checkId = new StringBuilder();
        boolean isDuplicate = false;
        try {
            sql_checkId.append("SELECT COUNT(1) AS count FROM tomato_member WHERE mem_id = ?");
            conn = dbMgr.getConnection();
            pstmt1 = conn.prepareStatement(sql_checkId.toString());
            pstmt1.setString(1, user_id);
            rs = pstmt1.executeQuery();
            if (rs.next()) {
                isDuplicate = rs.getInt("count") > 0;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt1 != null) pstmt1.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return isDuplicate;
    }


    //회원 가입 멤버 DB에 추가
    public int insertMember(TalkVO member) {
        int result = 0;
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO tomato_member ");
        sql.append("(mem_id, mem_pw, mem_name, email, mem_nick, zipcode, mem_addr) ");
        sql.append("VALUES (?, ?, ?, ?, ?, ?, ?)");

        try {
            conn = dbMgr.getConnection();
            pstmt1 = conn.prepareStatement(sql.toString());
            pstmt1.setString(1, member.getMem_id());
            pstmt1.setString(2, member.getMem_pw());
            pstmt1.setString(3, member.getMem_name());
            pstmt1.setString(4, member.getEmail());
            pstmt1.setString(5, member.getMem_nick());
            pstmt1.setString(6, member.getZipcode());
            pstmt1.setString(7, member.getMem_addr());

            result = pstmt1.executeUpdate(); // 삽입된 행의 수 반환
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt1 != null) pstmt1.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }


    public String findID(String name,String email){
        String id = null;
        StringBuilder sql = new StringBuilder();
        sql.append("select mem_id from tomato_member where mem_name = ? and email = ?");
        try{
            conn = dbMgr.getConnection();
            pstmt1 = conn.prepareStatement(sql.toString());
            pstmt1.setString(1, name);
            pstmt1.setString(2, email);
            rs = pstmt1.executeQuery();
            if(rs.next()){
                id = rs.getString("mem_id");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return id;
    }



    public String findPW(String name,String id){
        String pw = null;
        StringBuilder sql = new StringBuilder();
        sql.append("select mem_pw from tomato_member where mem_name = ? and mem_id = ?");
        try{
            conn = dbMgr.getConnection();
            pstmt1 = conn.prepareStatement(sql.toString());
            pstmt1.setString(1, name);
            pstmt1.setString(2, id);
            rs = pstmt1.executeQuery();
            if(rs.next()){
                pw = rs.getString("mem_pw");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return pw;
    }

    //친구 찾기
    public List<TalkVO> searchFriend(TalkVO tvo) {
        List<TalkVO> tList = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("select mem_id, mem_nick, mem_name from tomato_member");
        if (tvo != null && "all".equals(tvo.getGubun())) {
            System.out.println("mem_id");
        }else if (tvo != null && "mem_id".equals(tvo.getGubun())) {
            System.out.println("mem_id");
            sql.append(" where mem_id like '%'||?||'%'");
        } else if (tvo != null && "mem_nick".equals(tvo.getGubun())) {
            System.out.println("mem_nick");
            sql.append(" where mem_nick like '%'||?||'%'");
        } else if (tvo != null && "mem_name".equals(tvo.getGubun())) {
            System.out.println("mem_name");
            sql.append(" where mem_name like '%'||?||'%'");
        }
        try {
            System.out.println(sql.toString());
            conn = dbMgr.getConnection();
            pstmt1 = conn.prepareStatement(sql.toString());
            //sql ?은 1번부터 시작
            //i++ 변수 > 컬럼 변경되어도 그대로 사용할 수 있으니까
            //변수 초기값 0 -> ++i, 1 --> i++
            pstmt1.setString(1, tvo.getKeyword());
            rs = pstmt1.executeQuery();
            TalkVO tvo1 = null; ///오류해결_241112 강사님
            while (rs.next()) {
                tvo1 = new TalkVO(); ///오류해결_241112 강사님 - velog 오답노트
                tvo1.setMem_id(rs.getString("mem_id"));
                tvo1.setMem_nick(rs.getString("mem_nick"));
                tvo1.setMem_name(rs.getString("mem_name"));
                tList.add(tvo1);
            }
        } /*catch (SQLException e) {
            System.out.println(sql.toString());
        }*/ catch (Exception e) {
            e.printStackTrace();
        } finally { // 예외가 발생하더라도 무조건 실행이 되어야만 하는 것
            //사용한 자원 반납하기 - 생성된 역순으로 해준다 - 생략하면 처리는 되지만 명시적으로 처리하는 것 - 자바튜닝
            dbMgr.freeConnection(conn, pstmt1, rs);
        }
        System.out.println("tList size: " + tList.size());
        return tList;
    }


    public int deleteMember(String userId) {
        int result = 0;  // 삭제된 행의 수를 반환할 변수
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM tomato_member WHERE mem_nick = ?");  // 회원 삭제 쿼리

        try {
            conn = dbMgr.getConnection();  // DB 연결
            pstmt1 = conn.prepareStatement(sql.toString());  // 쿼리 준비
            pstmt1.setString(1, userId);  // 쿼리에 회원 ID 설정
            result = pstmt1.executeUpdate();  // 쿼리 실행, 삭제된 행의 수 반환
        } catch (Exception e) {
            e.printStackTrace();  // 예외 발생 시 에러 출력
        } finally {
            try {
                if (pstmt1 != null) pstmt1.close();  // PreparedStatement 자원 해제
                if (conn != null) conn.close();  // Connection 자원 해제
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return result;  // 삭제 결과 반환
    }

    public int updateName(String nickName,String aftetName){
        int result = -1;  // 삭제된 행의 수를 반환할 변수
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE tomato_member SET mem_nick = ? WHERE mem_nick = ?");  // 회원 삭제 쿼리

        try {
            conn = dbMgr.getConnection();  // DB 연결
            pstmt1 = conn.prepareStatement(sql.toString());  // 쿼리 준비
            pstmt1.setString(1,aftetName);
            pstmt1.setString(2, nickName);  // 쿼리에 회원 ID 설정
            result = pstmt1.executeUpdate();  // 쿼리 실행, 삭제된 행의 수 반환
        } catch (Exception e) {
            e.printStackTrace();  // 예외 발생 시 에러 출력
        } finally {
            try {
                if (pstmt1 != null) pstmt1.close();  // PreparedStatement 자원 해제
                if (conn != null) conn.close();  // Connection 자원 해제
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return result;
    }






}





















