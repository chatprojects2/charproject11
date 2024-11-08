package bubble;

import javax.swing.*;
import java.awt.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


public class ChatLeft1 extends JPanel{
    public JLabel jlb_left;
    JLabel jlb_right;
    public JLabel jlb_leftimg;
    ImageIcon imageIcon = null;
    JLabel jlb_rightimg;
    JPanel chat_left;
    JPanel chat_right;
    public JLabel jlb_time;
    JLabel jlb_check;
    public JLabel jlb_fid;
    public String pic;
    public String friend_id;
    public String yourprofile = "src/image/profile/agu.png";
    Font f = new Font("맑은 고딕",Font.PLAIN,10);
    public ImageIcon youricon;// = new ImageIcon(yourprofile+pic);
    public Image image;

    String chat_msg = null;

    public void getText(String chat_msg){
        this.chat_msg = chat_msg;
    }
    
    public ChatLeft1(String chat_msg){ ///msg 파라미터 추가

        this.setBackground(new Color(254,231,134));
        jlb_left = new JLabel();
        jlb_time = new JLabel();//시간
        imageIcon = new ImageIcon(yourprofile);
        //이미지 크기 조정하기
        image = imageIcon.getImage();
        image = image.getScaledInstance(50,50,Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(image);
        
        jlb_leftimg = new JLabel(imageIcon);
        jlb_check = new JLabel();
        /////////////////LEFT BUBBLE/////////////////////////////
        String msg = chat_msg; ////////////////////////////241108_Hanna
        jlb_left.setText(getWrappedText(msg, 20));

        // 현재 시간 가져오기    ///////241108_Hanna
        LocalTime now = LocalTime.now();
        // 시간 포맷 설정 (예: HH:mm)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String formattedTime = now.format(formatter);
        // JLabel에 현재 시간 설정
        jlb_time.setText(formattedTime);


        jlb_check.setText("");
        jlb_check.setFont(f);
        jlb_check.setForeground(Color.red);
        chat_left = new ChatLeftBubble1();
        jlb_fid=new JLabel();
        jlb_fid.setText(" "+"tomato");
        GroupLayout chat_leftLayout = new GroupLayout(chat_left);
        chat_left.setLayout(chat_leftLayout);
        chat_leftLayout.setHorizontalGroup(
                chat_leftLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(chat_leftLayout.createSequentialGroup()
                                .addGap(31, 31, 31)//말풍선 안에서 말풍선과 문장 시작부분 gap
                                .addComponent(jlb_left)
                                .addGap(25, 25, 25)
                        )//말풍선 안에서 말풍선끝부분 gap

        );
        chat_leftLayout.setVerticalGroup(
                chat_leftLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(chat_leftLayout.createSequentialGroup()
                                .addGap(6, 6, 6) //말풍선 안에서 말풍선위쪽라인과 텍스트사이 gap
                                .addComponent(jlb_left)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE+10, GroupLayout.DEFAULT_SIZE+10)
                                .addGap(6, 6, 6)
                        ) //말풍선 안에서 말풍선위쪽라인과 텍스트사이 gap )
                //.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))

        );
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(jlb_fid)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(10,10,10)
                                .addComponent(jlb_leftimg)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(chat_left, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addContainerGap()
                                .addGap(10,10,10)
                                .addComponent(jlb_time)
                                .addContainerGap()
                                .addGap(6,6,6)
                                .addComponent(jlb_check))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(jlb_fid)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(20,20,20)
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(jlb_leftimg)
                                        .addComponent(chat_left, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jlb_time)
                                        .addComponent(jlb_check))
                                .addContainerGap()
                                .addGap(18, 18, 18))
        );
    }
    private static String getWrappedText(String text, int lineLength) {
        StringBuilder wrappedText = new StringBuilder("<html>"); //브라우저도 아닌데 되더라
        int length = text.length();

        for (int i = 0; i < length; i += lineLength) {
            if (i + lineLength < length) {
                wrappedText.append(text, i, i + lineLength).append("<br>"); //브라우저도 아닌데 되더라
            } else {
                wrappedText.append(text.substring(i));
            }
        }

        wrappedText.append("</html>");
        return wrappedText.toString();
    }
    public static void main(String[] args) {
    }
}


