package jdbc.lib.book;

import javax.swing.*;

public class BookNaverSearch extends JDialog {

    BookApp ba = null;

    public BookNaverSearch(){}

    public BookNaverSearch(BookApp ba){
        this.ba = ba;
        initDisplay();
    }

    public void initDisplay(){
        System.out.println("initDisplay");
        System.out.println(this.ba);
    }


    public static void main(String[] args) {
        new BookNaverSearch(null);
    }
}
