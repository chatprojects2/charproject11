package project;

import javax.swing.*;

public class AddressSearch extends JDialog {
    MemberShipForm memberShipForm;
    //todo 주소검색창 구현하기
    public AddressSearch(MemberShipForm memberShipForm) {
        this.memberShipForm = memberShipForm;
        initDisplay();
    }
    
    public void initDisplay(){

        this.setTitle("Address Search");
        this.setSize(600, 700);
        this.setVisible(false);
        
    }


    public static void main(String[] args) {

    }
    
    
    
    
    
}
