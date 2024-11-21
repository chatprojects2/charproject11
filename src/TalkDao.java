import util.DBConnectionMgr;

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
        String mem_nick = null;//닉네임을 왜 지변으로 하죠? - 기초가 아니다.
        StringBuilder idCheck = new StringBuilder();
        StringBuilder sql_login = new StringBuilder();
        int status = 2;//1이면 아이디 존재함. -1:아이디 없음, mem_nick이 없다면 비번이 틀린거다.
        try {
            idCheck.append("SELECT ");
            idCheck.append("  NVL((SELECT 1 FROM dual ");
            idCheck.append("  WHERE EXISTS (SELECT mem_name FROM tomato_member ");
            idCheck.append("    WHERE mem_id=?) ");
            idCheck.append("     ),-1) AS isID ");
            idCheck.append("     FROM dual");
            conn = dbMgr.getConnection();
            pstmt1 = conn.prepareStatement(idCheck.toString());
            pstmt1.setString(1, user_id);
            rs = pstmt1.executeQuery();
            if (rs.next()) {
                status = rs.getInt("isID");
            }
            if (status == 1) {//아이디가 존재하면 비번을 비교한다.
                sql_login.append("SELECT mem_nick FROM tomato_member");
                sql_login.append(" WHERE mem_id=?");
                sql_login.append(" AND mem_pw=?");
                pstmt2 = conn.prepareStatement(sql_login.toString());
                pstmt2.setString(1, user_id);
                pstmt2.setString(2, user_pw);
                rs = pstmt2.executeQuery();
                if (rs.next()) {
                    mem_nick = rs.getString("mem_nick");
                } else {
                    mem_nick = "비밀번호가 맞지 않습니다.";
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return mem_nick;
    }


    //회원가입
    public int talkInsert(TalkVO tvo) {
        System.out.println("talkInsert 호출 성공");
        int result = -1; //1 입력 성공, 0 입력 실패 -> 초기값을 그 외의 값으로 줌
        StringBuilder sql = new StringBuilder();
        sql.append("insert into tomato_member values(?,?,?,?,?,?,?)");
        int i = 1;
        try {
            conn = dbMgr.getConnection();
            pstmt1 = conn.prepareStatement(sql.toString());
            //sql ?은 1번부터 시작
            //i++ 변수 > 컬럼 변경되어도 그대로 사용할 수 있으니까
            //변수 초기값 0 -> ++i, 1 --> i++
            pstmt1.setString(i++, tvo.getMem_id());
            pstmt1.setString(i++, tvo.getMem_pw());
            pstmt1.setString(i++, tvo.getMem_nick());
            pstmt1.setString(i++, tvo.getMem_name());
            pstmt1.setString(i++, tvo.getGender());
            pstmt1.setInt(i++, tvo.getZipcode()); ////int !!!!!!!!!!!!!!!!!!!
            pstmt1.setString(i++, tvo.getMel_addr());
            result = pstmt1.executeUpdate();
            if (result == 1) {
                System.out.println("정상적으로 처리되었습니다.");
            } else System.out.println("회원가입에 실패하였습니다.");
        } catch (SQLException se) {
            System.out.println(sql.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    //아이디 중복검사
    public boolean memIdCheck(String user_id) {
        StringBuilder sql = new StringBuilder();
        sql.append("select mem_id from tomato_member where mem_id=?");
        try {
            conn = dbMgr.getConnection();
            pstmt1 = conn.prepareStatement(sql.toString());
            //sql ?은 1번부터 시작
            //i++ 변수 > 컬럼 변경되어도 그대로 사용할 수 있으니까
            //변수 초기값 0 -> ++i, 1 --> i++
            pstmt1.setString(1, user_id);
            rs = pstmt1.executeQuery();
            boolean bl = rs.next();
            return bl;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }/////end of memIdCheck


    //친구 찾기
    public List<TalkVO> searchFriend(TalkVO tvo) {
        List<TalkVO> tList = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("select mem_id, mem_nick, mem_name from tomato_member");
        if (tvo != null && "mem_id".equals(tvo.getGubun())) {
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


    //아이디 비번 찾기
    public List<String> findIDPW(String whichfind, String keyword1, String keyword2) {
        List<String> tList = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("select mem_id, mem_pw from tomato_member");
        if (whichfind.equals("id")) {
            sql.append("  where mem_name=? and mel_addr=?");
        } else if (whichfind.equals("pw")) {
            sql.append("  where mem_id=? and mem_name=?");
        }
        try {
            conn = dbMgr.getConnection();
            pstmt1 = conn.prepareStatement(sql.toString());
            pstmt1.setString(1, keyword1);
            pstmt1.setString(2, keyword2);
            rs = pstmt1.executeQuery();
            if (rs.next()) {
                tList.add(rs.getString("mem_id"));
                tList.add(rs.getString("mem_pw"));
            }
        } /*catch (SQLException e) {
            System.out.println(sql.toString());
        } */catch (Exception e) {
            e.printStackTrace();
        } finally { // 예외가 발생하더라도 무조건 실행이 되어야만 하는 것
            //사용한 자원 반납하기 - 생성된 역순으로 해준다 - 생략하면 처리는 되지만 명시적으로 처리하는 것 - 자바튜닝
            dbMgr.freeConnection(conn, pstmt1, rs);
        }
        System.out.println("tList size: " + tList.size());
        return tList;
    }
}
