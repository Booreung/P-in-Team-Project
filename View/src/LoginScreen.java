import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginScreen extends JPanel {
    public LoginScreen(CardLayout cardLayout, JPanel cardPanel){
        setLayout(new BorderLayout());

        JPanel loginPanel = new JPanel();
        JLabel loginLabel = new JLabel("로그인");
        JButton loginBtn = new JButton("로그인");
        JButton joinBtn = new JButton("회원가입");
        JButton nonloginBtn = new JButton("비회원 로그인");

        //로그인 버튼을 누르면 메인 키오스크 화면으로
        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //여기에 로그인 유효 검사랑 로그인 실패했을때
                // 알림을 띄어주는 화면이 필요함
                cardLayout.show(cardPanel,"main");
            }
        });
        joinBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //여기에는 회원가입 화면으로 전환되는 동작
                System.out.println("회원가입 페이지");
            }
        });
        nonloginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //여기는 바로 메인 화면으로 가는데 이름없이 전돨되어야함
                //그리고 비회원 알림이 있어야 하는지는 고민좀
                System.out.println("비회원 로그인 성공");
            }
        });

        loginPanel.add(loginLabel);
        loginPanel.add(loginBtn);
        loginPanel.add(joinBtn);
        loginPanel.add(nonloginBtn);

        add(loginPanel,BorderLayout.CENTER);
    }
}
