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
        setSize(800,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //CardLayout 설정
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        //시작 화면
        cardPanel.add(new StartScreen(cardLayout,cardPanel),"start");

        // 로그인 화면 추가
        cardPanel.add(new LoginScreen(cardLayout, cardPanel), "login");
        //회원가입 화면 추가
        //cardPanel.add(new src.naver.pin_project.view1.SignUpScreen(cardLayout,cardPanel),"signup");

        // 메인 화면 추가
        //cardPanel.add(new src.naver.pin_project.view1.MainScreen(user), "main");


        //프레임에 카드패널 추가
        add(cardPanel);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PinApp app = new PinApp();
            app.setVisible(true);
        });
    }
}
