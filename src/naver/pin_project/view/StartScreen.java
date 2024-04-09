package src.naver.pin_project.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class StartScreen extends JPanel {
    private static final String[] IMAGE_PATHS = {
            "src/naver/pin_project/lib/start1.jpeg",
            "src/naver/pin_project/lib/start2.jpeg",
            "src/naver/pin_project/lib/start3.jpg",
            "src/naver/pin_project/lib/start4.jpg",
            "src/naver/pin_project/lib/start5.jpg"
    };
    private int currentIndex = 0;
    private JLabel backgroundLabel;
    private ImageIcon[] imageIcons;

    public StartScreen(CardLayout cardLayout, JPanel cardPanel){

        setLayout(new BorderLayout());

        JPanel startPanel = new JPanel();
        backgroundLabel = new JLabel();
        startPanel.add(backgroundLabel);
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

        //이미지의 전환을 위한 스레드 사용
        Thread imageThread = new Thread(() -> {
            try{
                while(true){
                    changeBackgroundImage();
                    //3초 대기 후 다음 이미지로
                    Thread.sleep(3000);
                }
            }
            catch (InterruptedException ex){
                ex.printStackTrace();
            }
        });
        imageThread.start();
    }

    private void changeBackgroundImage(){
        String imagePath = IMAGE_PATHS[currentIndex];
        ImageIcon imageIcon = new ImageIcon(imagePath);
        backgroundLabel.setIcon(imageIcon);
        currentIndex = (currentIndex + 1)% IMAGE_PATHS.length;
    }

}
