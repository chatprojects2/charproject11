package jdbc.lib.book;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class BookDaoTest {
    JFrame jFrame = new JFrame();
    BookDao bookDao = new BookDao();

    public int bookDeleteTest(int b_no){

        int result = -1;
        result = bookDao.bookDelete(b_no);
        return result;
    }

    public void getBookList(){
        System.out.println("호출성공");
        BookVO pbvo = new BookVO();
        pbvo.setB_no(0);
        List<BookVO> list = bookDao.getBookList(pbvo);

        System.out.println(list.size());
        System.out.println(list.get(0).getB_no());
        System.out.println(list.get(0).getB_name());
        System.out.println(list.get(0).getB_author());
    }
    public static void main(String[] args) {
        BookDaoTest bdt = new BookDaoTest();
        bdt.getBookList();
        int result = -1;
        result = bdt.bookDeleteTest(4);
        if(result == 1){
            JOptionPane.showMessageDialog(bdt.jFrame,"삭제성공하였습니다.");
            bdt.getBookList();
        }else {
            JOptionPane.showMessageDialog(bdt.jFrame, "삭제 실패");
            //return; if문에서 return을 만나면 호출된 메소드를 탈출함.
        }

        result = -1;
        BookVO pbvo = new BookVO(0,"책제목5","강감찬","원출판","책소개5","5.png");
        result = bdt.bookDao.bookInsert(pbvo);
        if(result == 1){
            JOptionPane.showMessageDialog(bdt.jFrame,"입력 성공하였습니다.");
            bdt.getBookList();
        }else {
            JOptionPane.showMessageDialog(bdt.jFrame, "입력 실패");
            return;
        }

    }
}
/*
테스트 시나리오를 알고 잇다 - 잘하는사람
화면이 없이도 파라미터와 리턴타입을 고려하여 테스트가 가능하다
 */