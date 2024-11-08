package jdbc.lib.book;
//MVC패턴 - 데이터관련된 것은 Model계층입니다. -CRUD작업
//select, insert, update, delete
//import com.util.DBConnectionMGR;


import com.lib.DBConnectionMgr;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDao {
    //Spring 프레임워크가 기본적으로 객체 라이프사이클 관리하는 메커니즘
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

    /************************************************
     * 도서 삭제 구현하기
     * delete from book152 where b_no = ?
     * @param
     * @return int -1이면 삭제성공, 0이면 실패
     ************************************************/

    public int bookDelete(int b_no){
        int result = -1;//1이면 삭제 성공, 0이면 삭제 실패, 그래서 초기값을 -1로함
        StringBuilder sql = new StringBuilder();
        sql.append("delete from book152 where b_no = ?");
        try{
            conn = dbMgr.getConnection();
            psmt = conn.prepareStatement(sql.toString());
            psmt.setInt(1,b_no);
            result = psmt.executeUpdate();
        }catch (SQLException e){
            //쿼리문에 부적합한 식별자(컬럼명 오타), 테이블이나 뷰(오브젝트-select문-금융) 이름이 없습니다.
            System.out.println(sql.toString());
        }catch (Exception e){
            e.printStackTrace();//에러메시지도 stack에 히스토리를 가짐. 출력함
        }finally {
            dbMgr.freeConnection(conn,psmt);
        }
        return result;//성공이면 1, 실패면 0
    }

    /************************************************
     * 도서 정보 수정하기
     * update book152
     *   set b_name =?
     *       b_author =?
     *       b_publish =?
     *   where b_no = ?
     * @param bvo or int b-no
     * @return int -1이면 삭제성공, 0이면 실패
     ************************************************/


    public int bookUpdate(BookVO bvo){
        int result = -1;//1이면 수정 성공, 0이면 수정 실패, 그래서 초기값을 -1로함
        return result;
    }

    /************************************************
     *
     * insert into book152 values(1,'혼자공부하는 파이썬', '윤인성','한빛미디어','혼자 해도 충분하다! 1:1 과외하듯 배우는 파이썬 프로그래밍 자습서(파이썬 최신 버전 반영) 이 책은 독학으로 프로그래밍 언어를 처음 배우려는 입문자가, 혹은 파이썬을 배우려는 입문자가 ‘꼭 필요한 내용을 제대로’ 학습할 수 있도록 구성했다. ‘무엇을’, ‘어떻게’ 학습해야 할지조차 모르는 입문자의 막연한 마음을 살펴, 과외 선생님이 알려주듯 친절하게, 그러나 핵심적인 내용만 콕콕 집어준다. 책의 첫 페이지를 펼쳐서 마지막 페이지를 덮을 때까지, 혼자서도 충분히 파이썬을 배울 수 있다는 자신감과 확신이 계속될 것이다!','1.jpg');
     * insert into book152 values(2,'Do it 점프 투 파이썬','박응용','이지스퍼블리싱','문과생도 중고등학생도 직장인도 프로그래밍에 눈뜨게 만든 바로 그 책이 전면 개정판으로 새로 태어났다! 2016년 《Do it! 점프 투 파이썬》으로 출간되었던 이 책은 약 4년 동안의 피드백을 반영하여 초보자가 더 빠르게 입문하고, 더 깊이 있게 공부할 수 있도록 개정되었다. 특히 ‘나 혼자 코딩’과 ‘코딩 면허 시험 20제’ 등 독자의 학습 흐름에 맞게 문제를 보강한 점이 눈에 띈다. 실습량도 두 배로 늘었다.4년 동안 압도적 1위! 위키독스 누적 방문 200만! 수많은 대학 및 학원의 교재 채택 등! 검증은 이미 끝났다. 코딩을 처음 배우는 중고등학생부터 나만의 경쟁력이 필요한 문과생, 데이터 분석과 인공지능/머신러닝으로 커리어를 뻗어 나가고 싶은 직장인까지! 프로그래밍의 세계에 풍덩 빠져 보자.','2.jpg');
     * insert into book152 values(3,'파이썬 머신러닝 완벽가이드','권철민','위키북스','《파이썬 머신러닝 완벽 가이드》는 이론 위주의 머신러닝 책에서 탈피해 다양한 실전 예제를 직접 구현해 보면서 머신러닝을 체득할 수 있도록 만들었습니다. 캐글과 UCI 머신러닝 리포지토리에서 난이도가 있는 실습 데이터를 기반으로 실전 예제를 구성했고, XGBoost, LightGBM, 스태킹 기법 등 캐글의 많은 데이터 사이언스에서 애용하는 최신 알고리즘과 기법에 대해 매우 상세하게 설명했습니다. 이번 개정판에서는 사이킷런 및 기타 라이브러리의 업데이트에 따른 전반적인 내용 및 소스코드 업데이트와 함께 질의 사항이 많은 부분들에 대한 상세한 설명을 추가했습니다.','3.jpg');
     * @param bvo
     * @return
     ************************************************/

    public int bookInsert(BookVO bvo){
        System.out.println("북인서트 성공");
        int result = -1;//1이면 삭제 성공, 0이면 삭제 실패, 그래서 초기값을 -1로함
        StringBuilder sql = new StringBuilder();
        sql.append("insert into book152 values(seq_book152_no.nextval,?,?,?,?,?)");
        int i = 1;
        try{
            conn = dbMgr.getConnection();
            psmt = conn.prepareStatement(sql.toString());
            //? 자리를 채우는 값을 설정할 때는 1번 부터 입니다.
            //컬럼이 추가되거나 컬럼의 순서가 바뀌면 숫자를 일일이 바꾸어야 하니까
            //번호 대신에 변수로 처리 합니다. - 후위연산자 - 먼저 대입 나중에 증가
            //변수 i의 초기값이 0이면 ++i가 맞고 초기값을 1로 하면 i++이 맞다.
            psmt.setString(i++,bvo.getB_name());//책제목
            psmt.setString(i++,bvo.getB_author());//저자
            psmt.setString(i++,bvo.getB_publish());//출판사
            psmt.setString(i++,bvo.getB_info());//책소개
            psmt.setString(i++,bvo.getB_img());//책이미지
            result = psmt.executeUpdate();
        }catch (SQLException e){
            //쿼리문에 부적합한 식별자(컬럼명 오타), 테이블이나 뷰(오브젝트-select문-금융) 이름이 없습니다.
            System.out.println(sql.toString());
        }catch (Exception e){
            e.printStackTrace();//에러메시지도 stack에 히스토리를 가짐. 출력함
        }
        return result;//성공이면 1, 실패면 0
    }
}
