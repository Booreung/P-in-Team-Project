package src.naver.pin_project.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class StartScreen extends JPanel {

    public StartScreen(CardLayout cardLayout, JPanel cardPanel){

        setLayout(new BorderLayout());

        JPanel startPanel = new JPanel();
        JLabel startLabel = new JLabel("시작 화면");
        startPanel.add(startLabel);
        add(startPanel, BorderLayout.CENTER);

        //패널의 어디든 클릭하였을때 다음으로 넘어가게끔
        //마우스 이벤트 처리
        startPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                cardLayout.show(cardPanel,"login");
            }
        });

    }

}
