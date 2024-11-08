package wonjun;

import jdbc.lib.book.BookVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MemberShipFormDAO {
    DBConnectionMgr dbMgr = DBConnectionMgr.getInstance();
    Connection conn = null;
    PreparedStatement psmt = null;
    ResultSet rs = null;

    /***************************************************
     * 도서 목록 조회 및 상세조회 구현
     * selcet b_no, b_name, b_author, b_publish, b_info, b_img
     *  from book152
     * where n_no=? 1건조회하기
     * select b_no, b_name, b_author, b_publish, b_info, b_img
     *  from book152 전체조회
     * @param pbvo
     * @return List<BookVO> : BookVO가 한건만 담을수 있다
     * 한 건이면 bList.size() = 1 이면 한 건이다.
     * bList.size() > 1이면 여러 건이다.
     ***************************************************/
    public List<BookVO> getBookList(BookVO pbvo){
        System.out.println("getbooklist 호출 성공"+pbvo.getB_no());
        List<BookVO> bList = new ArrayList<>();
        System.out.println(bList.size());
        StringBuilder sql = new StringBuilder();
        sql.append("select b_no, b_name, b_author,b_publish,b_info,b_img");
        sql.append(" from book152");
        if(pbvo.getB_no() > 0){
            sql.append(" where b_no = ?");
        }else{
            sql.append(" order by b_no asc");
        }

        try{
            conn = dbMgr.getConnection();
            psmt = conn.prepareStatement(sql.toString());
            if(pbvo.getB_no() > 0){
                psmt.setInt(1,pbvo.getB_no());
            }
            rs = psmt.executeQuery();
            BookVO bvo = null;
            while (rs.next()){
                bvo = new BookVO();
                bvo.setB_no(rs.getInt("b_no"));
                bvo.setB_name(rs.getString("b_name"));
                bvo.setB_author(rs.getString("b_author"));
                bvo.setB_publish(rs.getString("b_publish"));
                bvo.setB_info(rs.getString("b_info"));
                bvo.setB_img(rs.getString("b_img"));
                bList.add(bvo);
            }
        }catch (SQLException se){
            System.out.println(sql.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }finally {//예외가 발생하더라도 무조건 실행된다.
            dbMgr.freeConnection(conn,psmt,rs);//사용한 자원 반납하기 - 생성된 역순으로 해준다.
            //생략하면 처리는 되지만 명시적으로 처리하는 것이다. -자바튜닝
        }
        return bList;
    }
}
