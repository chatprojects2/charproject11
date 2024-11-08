package jdbc.lib.book;

//import day3.variable.B;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/*
BookDialog에서 수정이나 입력일 때 insert가 1이거나 update도 1이면 성공
이때 BookApp의 도서 목록 페이지를 새로고침 한다. -요구사항이 있다면....
BookApp 의 메소드를 BookDialog에서 호출해야 한다.
이 때 부모창의 주소원본을 가져한다.
 */
public class BookApp extends JFrame implements ActionListener {
    BookDialog bd = new BookDialog(this);
    BookDao bDao = new BookDao();
    JMenuBar mb = new JMenuBar();
    JMenu jm_file = new JMenu("File");
    JMenuItem jmi_dbcon = new JMenuItem("DB연결");
    JSeparator se_file = new JSeparator();
    JMenuItem jmi_exit = new JMenuItem("Exit");
    JMenu jm_edit = new JMenu("Edit");
    JMenuItem jmi_all = new JMenuItem("전체조회");
    JMenuItem jmi_ins = new JMenuItem("입력");
    JMenuItem jmi_det = new JMenuItem("상세보기");
    JMenuItem jmi_upd = new JMenuItem("수정");
    JMenuItem jmi_del = new JMenuItem("삭제");
    String imgPath = "C:\\workspace_java\\basic1\\src\\image\\image\\book\\";

    JToolBar jtBar = new JToolBar();
    JButton btn_all = new JButton("전체조회");
    JPanel jp_center = new JPanel();
    JPanel jp_center_north = new JPanel();
    String[] gubuns = {"책제목", "저자", "출판사"};
    JComboBox jcb_gubuns = new JComboBox(gubuns);
    JTextField jtf_keyword = new JTextField(20);
    JButton jbtn_search = new JButton("검색");

    JButton btn_ins = new JButton("입력", new ImageIcon(imgPath + "new.gif"));
    JButton btn_det = new JButton("상세보기", new ImageIcon(imgPath + "detail.gif"));
    JButton btn_upd = new JButton("수정", new ImageIcon(imgPath + "update.gif"));

    JButton btn_del = new JButton("삭제", new ImageIcon(imgPath + "delete.gif"));
    String[] cols = {"도서번호","도서명", "저자","출판사"};
    String[][] data = new String[0][4];
    DefaultTableModel dtm_book = new DefaultTableModel(data, cols);
    JTable jtb_book = new JTable(dtm_book);
    JScrollPane jsp_book = new JScrollPane(jtb_book,
            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    public BookApp() {
        initDisplay();
    }
    public void refreshData(){
        System.out.println("새로고침 호출");
        List<BookVO> bList = bDao.getBookList(new BookVO());
        //UI 초기화 작업이다. - 초기화
        //전체조회를 누르면 버튼이 비활성화 되지 않아서 계속 반복적으로 누를 수 있다.
        //그래서 기존에 출력된 정보를 가지고 뒤에 추가되고 있다.
        while (dtm_book.getRowCount() > 0) {
            //0번째를 계속해서 삭제하게 되는데 첫번째가 삭제되고 나면
            // dtm_book의 index값이 1씩 줄어들게 된다.
            dtm_book.removeRow(0);
        }
        for(int i=0;i<bList.size();i++){
            BookVO bvo = bList.get(i);//0,1,2,3,4,5이면 탈출함
            Vector<Object> v = new Vector<>();
            v.add(bvo.getB_no());
            v.add(bvo.getB_name());
            v.add(bvo.getB_author());
            v.add(bvo.getB_publish());
            dtm_book.addRow(v);
        }
    }
    //입력일 때 호출 - 입력 버튼 | 입력 메뉴 아이템
    public void insertActionPerformed(){
        System.out.println("입력 버튼 | 입력 메뉴 아이템");
        bd.set("입력", true, null, true);

    }
    //상세보기일 때  호출 - 상세보기버튼 | 상세보기 메뉴 아이템
    public void detailActionPerformed(){
        System.out.println("상세보기버튼 | 상세보기 메뉴 아이템");
        //bd.set("입력", true, null, false);
        int one = -1;
        one = jtb_book.getSelectedRow();
        int b_no = 0;
        b_no = Integer.parseInt(dtm_book.getValueAt(one, 0).toString());
        //one += 1;
        if(one < 0) {
            JOptionPane.showMessageDialog(this, "상세조회할 로우를 선택하세요."
                    , "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        BookVO pbvo = new BookVO();
        //b_no = 0인 상태
        pbvo.setB_no(b_no);//0이었지만 여기서 값이 바뀜. 0보다 큰 값으로 변함
        List<BookVO> bList = bDao.getBookList(pbvo);//pk 있어서 한 건만 조회
        if(bList.size() == 1){
            BookVO bvo = bList.get(0);//사용자가 선택한 로우의 값을 담았다. 난 null이 아니야
            bd.set("상세보기",true, bvo, false);
        }else{
            JOptionPane.showMessageDialog(this,"조회결과가 없습니다.","info",JOptionPane.INFORMATION_MESSAGE);
            return;
        }
    }
    //수정일때 호출 - 수정버튼 | 수정 메뉴 아이템 일때
    public void updateActionPerformed(){
        System.out.println("수정버튼 | 수정 메뉴 아이템 일때");
        bd.set("입력", true, null, true);
    }
    //삭제 일때 호출 - 삭제버튼 | 삭제 메뉴 아이템 일때
    public void deleteActionPerformed(){
        System.out.println("삭제버튼 | 삭제 메뉴 아이템 일때");
        //당신이 선택한 로우는 얼마인가요?
        int index[] = jtb_book.getSelectedRows();
        int one = jtb_book.getSelectedRow();
        if(index.length == 0){
            JOptionPane.showMessageDialog(this, "삭제할 로우를 선택하세요."
                    , "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }else{
            //삭제할 로우를 선택했다면 여기로.......
            int b_no = 0;
            b_no = Integer.parseInt(dtm_book.getValueAt(one,0).toString());
            //System.out.println("당신이 선택한 로우의 도서 번호 :"+b_no);
            int result = bDao.bookDelete(b_no);
            if(result == 1){//삭제가 성공하면 1을 반환하고 실패하면 0을 반환받음.
                refreshData();
            }
        }
    }

    public void initDisplay(){
        //북쪽에는 jp_center_north속지, 중앙에는 jsp_book붙임
        jp_center.setLayout(new BorderLayout());
        jp_center_north.setLayout(new BorderLayout());
        jp_center_north.add("West",jcb_gubuns);
        jp_center_north.add("Center",jtf_keyword);
        jp_center_north.add("East",jbtn_search);
        jp_center.add("North",jp_center_north);
        jp_center.add("Center",jsp_book);

        btn_ins.addActionListener(this);
        jmi_ins.addActionListener(this);
        btn_det.addActionListener(this);
        jmi_det.addActionListener(this);
        btn_upd.addActionListener(this);
        jmi_upd.addActionListener(this);
        btn_del.addActionListener(this);
        jmi_del.addActionListener(this);
        btn_all.addActionListener(this);
        jmi_all.addActionListener(this);

        jm_file.add(jmi_dbcon);
        jm_file.add(se_file);
        jm_file.add(jmi_exit);
        jm_edit.add(jmi_all);
        jm_edit.add(jmi_ins);
        jm_edit.add(jmi_det);
        jm_edit.add(jmi_upd);
        jm_edit.add(jmi_del);

        mb.add(jm_file);
        mb.add(jm_edit);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setJMenuBar(mb);//메뉴바는 배치하는 것이 아니고 this에 직접 붙임

        jtBar.add(btn_all);
        jtBar.add(btn_ins);
        jtBar.add(btn_det);
        jtBar.add(btn_upd);
        jtBar.add(btn_del);

        this.add("North", jtBar);
        this.add("Center", jp_center);
        this.setSize(700, 500);
        this.setVisible(true);
    }
    public static void main(String[] args) {
        JFrame.setDefaultLookAndFeelDecorated(true);
        //13번의 멤버는 언제 생성이 되는가? 내 자신 객체를 생성할 때 초기화 및 생성됨
        BookApp ba = new BookApp();
        //20이 실행(new포함)되면
        //A a  = null, a = new A();
        //선언부  a는 주소번지 할당이 되기 전임. null.toString();
        //a = new A()했을 때 주소번지 생성됨.
        //13번에서 this는 20번 ba주소번지와 같은 값을 가진다.
        //디폴트 생성자는 없을 때 자동으로 생성해(누가? JVM) 주지만
        //파라미터를 갖는 생성자는 파라미터를 예측할 수가 없어서 대신 못해줌
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();
        //서로 같은 역할을 하는 메뉴아이템과 버튼에 대해서 하나의 메소드로 설계해본다. - 학습목표
        if(obj == btn_all || obj == jmi_all){
            refreshData();
        }
        else if(obj == btn_ins || obj == jmi_ins){
            insertActionPerformed();
        }
        else if(obj == btn_upd || obj == jmi_upd){
            updateActionPerformed();
        }
        else if(obj == btn_del || obj == jmi_del){
            deleteActionPerformed();
        }
        //상세보기 버튼 이나 상세보기 메뉴 아이템을 눌렀을 때
        else if(obj == btn_det || obj == jmi_det){
            detailActionPerformed();
        }
    }
}
/*
메뉴바 추가하기, 툴바 추가하기
같은 메뉴를 메뉴바와 툴바에 모두 추가하여서 메소드 재사용성 구현 실습
이벤트 처리를 담당하는 클래스로 설계하기 - ActionListener

삭제는 BookApp.java에서 처리한다.
상세조회, 수정, 입력은 BookDialog.java 처리한다.
이 중에서 수정하기와 상세조회는 오라클 서버를 경유해야 한다.
왜냐면 기존에 있는 정보를 수정하거나 상세조회하는 것이므로 조회결과를
화면에 출력해야 한다.
입력은 오라클 서버를 경유하지 않고 BookDialog 화면에서 새로 입력받은
정보를 오라클 서버에 입력하는 것이다.(INSERT문)
BookDialog.java와 BookDao.java사이에서 처리가 진행된다.
BookDialog.java에서 BookDao의 메소드를 호출할 때 사용자가 입력한 값을
파라미터로 넘겨야 하는데 컬럼의 갯수만큼 넘기는 것보다 booVO주소번지를
하나 넘겨서 그 안에 선언된 멤버변수값을 insert문에 넣는 것이 더 나은 방법이다.
BookDialog에서는 BookDao의 인스턴스화를 해야 한다.
BookDialog에서 BookDao의 메소드를 호출한다.

화면과 로직을 분리하자.
M - 모델(DB연동,업무처리)
V - 뷰(BookApp, BookDialog)
C - 컨트롤

개발 패턴, 방법론

입력

BookDialog(저장) -> BookDao(bookInsert insert문) -> BookDialog(닫힘) -> BookApp(새로고침 - select문) ->
insert into book152 values(seq_book152_no.nextval, ?,?,?,?,?)

pstmt.setString(1, "책제목");
pstmt.setString(2, "저자");
pstmt.setString(3, "출판사");
pstmt.setString(4, "책소개");
pstmt.setString(5, "이미지");

BookVO pbvo - 파라미터
1 or 0 - 리턴터입 - excuteUpdate()


수정
- 저장 프로시저
BookDialog(getbooklist(b_no)-1건-select문)-bookdialog(저장) - BookDao(bookUpdate - update문) - BokApp(새로고침-refreshData()-select문)

update book152
    set b_name = ?
        , b_author = ?
        , b_publish = ?
        , b_info = ?
        , b_img = ?
    where b_no = ?

pstmt.setString(1, "책제목");
pstmt.setString(2, "저자");
pstmt.setString(3, "출판사");
pstmt.setString(4, "책소개");
pstmt.setString(5, "이미지");
pstmt.setString(6,5);



BookApp
입력 - set(입력 | 수정 | 상세보기, boolean isView, BookVO, boolean deitable )
수정 - 오라클서버 경유
상세보기 - 오라클 서버 경유

BookDialog - this.setTitle(title), this.setVisible(false -> true)
set(){
    setTitle(title)
    setVisiable(isView)
    setValue(bVO) - 상세보기, 수정일때
    setEditable(editable) - true이면 수정할 수 있어, fales이면 수정할 수 없어
}


상세보기

BookDialog(getbooklist(b_no)-1건-select문)-bookdialog(단지 출력만 할뿐)

삭제

BookApp - b-no -BookDao(delete문) - BookApp(refreshData()호출 - select문)
delete from book152 where b_no =?

전체조회
BookApp - BookDao(select문)

- 조건검색 고려해 본다.
select b_no, b_name, b_author, b_publish, b_info, b_img from book152
 where b_name = 'java';

 where b_no = 1;
 */