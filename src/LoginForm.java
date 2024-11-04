import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginForm extends JFrame implements ActionListener {
    // 메인 패널
    private JPanel mainPanel = new JPanel();
    private JPanel topPanel = new JPanel();
    private JLabel titleLabel = new JLabel("Kakao Login");
    private JPanel inputPanel = new JPanel();
    private JTextField usernameField = new JTextField();
    private JPasswordField passwordField = new JPasswordField();

    private JPanel buttonPanel = new JPanel();
    private JButton loginButton = new JButton("로그인");
    private JButton joinButton = new JButton("회원가입");

    public LoginForm() {
        initDisplay();
    }

    void initDisplay(){
        this.setTitle("Login Form");
        this.setSize(350, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        //메인 패널
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        // 상단 패널

        topPanel.setBackground(new Color(254, 229, 0)); // 카톡 노란색
        titleLabel.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        topPanel.add(titleLabel);

        // 입력 패널

        inputPanel.setLayout(new GridLayout(5, 1, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        inputPanel.setBackground(Color.WHITE);


        usernameField.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
        usernameField.setBorder(BorderFactory.createTitledBorder("아이디"));


        passwordField.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
        passwordField.setBorder(BorderFactory.createTitledBorder("비밀번호"));

        inputPanel.add(usernameField);
        inputPanel.add(passwordField);

        // 로그인 버튼 패널

        buttonPanel.setBackground(Color.WHITE);

        loginButton.setFont(new Font("맑은 고딕", Font.BOLD, 16));
        loginButton.setBackground(new Color(254, 229, 0));
        loginButton.setForeground(Color.BLACK);
        loginButton.setFocusPainted(false);
        loginButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        loginButton.setPreferredSize(new Dimension(250, 40));

        joinButton.setFont(new Font("맑은 고딕", Font.BOLD, 16));
        joinButton.setBackground(new Color(254, 229, 0));
        joinButton.setForeground(Color.BLACK);
        joinButton.setFocusPainted(false);
        joinButton.setPreferredSize(new Dimension(250, 50));
        joinButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        buttonPanel.add(loginButton);
        inputPanel.add(buttonPanel);
        inputPanel.add(joinButton);
        
        this.loginButton.addActionListener(this);
        this.joinButton.addActionListener(this);

        // 메인 패널에 컴포넌트 추가
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(inputPanel, BorderLayout.CENTER);

        this.add(mainPanel);
        this.setVisible(true);
    }


    public static void main(String[] args) {
        new LoginForm();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == loginButton) {
            String username = usernameField.getText();
            String password = String.valueOf(passwordField.getPassword());
            System.out.println(username+" "+password);
        }
        if(source == joinButton){
            System.out.println("회원 가입 필요");
        }


    }
}
