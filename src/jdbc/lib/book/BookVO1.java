package jdbc.lib.book;

import java.awt.print.Book;

public class BookVO1 {
    private int b_no;
    private String b_name;
    private String b_author;
    private String b_publish;
    private String b_info;
    //여기서 파일 업로드 처리는 하지 않습니다. 그래서 이미지 이름만 저장
    private String b_img;

    public BookVO1(){}
    public BookVO1(int b_no, String b_name, String b_author, String b_publish, String b_info, String b_img){
        this.b_no = b_no;
        this.b_name = b_name;
        this.b_author = b_author;
        this.b_publish = b_publish;
        this.b_info = b_info;
        this.b_img = b_img;
    }

    public int getB_no() {
        return b_no;
    }

    public void setB_no(int b_no) {
        this.b_no = b_no;
    }

    public String getB_name() {
        return b_name;
    }

    public void setB_name(String b_name) {
        this.b_name = b_name;
    }

    public String getB_author() {
        return b_author;
    }

    public void setB_author(String b_author) {
        this.b_author = b_author;
    }

    public String getB_publish() {
        return b_publish;
    }

    public void setB_publish(String b_publish) {
        this.b_publish = b_publish;
    }

    public String getB_info() {
        return b_info;
    }

    public void setB_info(String b_info) {
        this.b_info = b_info;
    }

    public String getB_img() {
        return b_img;
    }

    public void setB_img(String b_img) {
        this.b_img = b_img;
    }


}
