package project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MembershipWithdrawal extends JDialog implements ActionListener {
    private final TalkClient client;
    private JTextField passwordField;
    private JButton confirmButton;
    private JButton cancelButton;
    private JLabel userIdLabel;

    public MembershipWithdrawal(TalkClient client) {
        this.client = client;
        initDisplay();
    }

    public void initDisplay() {
        this.setSize(600, 400);
        this.setLayout(new GridLayout(4, 1, 10, 10)); // 4개의 행 구성

        userIdLabel = new JLabel("아이디: " + client.nickName); // 아이디 표시
        this.add(userIdLabel);

        JLabel messageLabel = new JLabel("탈퇴를 원하시면 비밀번호를 입력하세요:");
        this.add(messageLabel);

        passwordField = new JPasswordField(10);
        this.add(passwordField);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        confirmButton = new JButton("확인");
        cancelButton = new JButton("취소");
        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButton);
        this.add(buttonPanel);

        confirmButton.addActionListener(this);
        cancelButton.addActionListener(this);

        this.pack(); // 컴포넌트에 맞게 다이얼로그 크기 조정
        this.setVisible(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == confirmButton) {
            String password = passwordField.getText();
            if (password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "비밀번호를 입력해주세요.", "경고", JOptionPane.WARNING_MESSAGE);
                return;
            }

            JOptionPane.showMessageDialog(this, "탈퇴 처리되었습니다.");
            this.dispose();
        } else if (e.getSource() == cancelButton) {
            this.dispose();
        }
    }

    public void set(String nickName, boolean isView) {
        this.setTitle(nickName + "님의 회원탈퇴창");
        this.setVisible(isView);
    }
}

