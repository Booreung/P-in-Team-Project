package src.naver.pin_project.main;


import src.naver.pin_project.view.LoginScreen;
import src.naver.pin_project.view.StartScreen;

import javax.swing.*;
import java.awt.*;

public class PinApp extends JFrame {
    private CardLayout cardLayout;
    private JPanel cardPanel;

    public PinApp(){
        setTitle("Pin");
        setSize(730,530);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        //메인 어플리케이션 동작에 있어 Start, Login화면을 시작으로 메인까지 연결

        //CardLayout 설정
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        //시작 화면
        cardPanel.add(new StartScreen(cardLayout,cardPanel),"start");

        // 로그인 화면 추가
        cardPanel.add(new LoginScreen(cardLayout, cardPanel), "login");


        //프레임에 카드패널 추가
        add(cardPanel);
    }
    public static void main(String[] args) {
        //단일 스레드를 실행하기 위한 메소드 (공식임)
        SwingUtilities.invokeLater(() -> {
            PinApp app = new PinApp();
            app.setVisible(true);
        });
    }
}
