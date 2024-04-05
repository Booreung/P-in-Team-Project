package src.naver.pin_project.game_feature;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameMenu extends JFrame {
    String userId = "test"; //메인메뉴로부터 받아올 유저아이디
    String userName = "홍길동"; //메인메뉴로부터 받아올 유저 닉네임
    //유저아이디 하나로 DB에서 받아올수 있지만 굳이.. 싶어서 메인변수 받아와도 좋을거같다!

    public GameMenu() {//처음 게임메뉴
        setTitle("Main Screen");
        setSize(800, 480);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 상단 이미지
        ImageIcon imageIcon = new ImageIcon("src/naver/pin_project/game_feature/img_asset/bowling.gif");
        JLabel imageLabel = new JLabel(imageIcon);

        // 하단 패널
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(Color.DARK_GRAY);

        // 왼쪽 userId 표시되는 라벨
        JLabel userIdLabel = new JLabel("User ID: " + userId);
        userIdLabel.setForeground(Color.WHITE);
        bottomPanel.add(userIdLabel, BorderLayout.WEST);

        // 버튼 패널
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.DARK_GRAY);

        JButton mainMenuButton = new JButton("메인메뉴로");
        JButton startGameButton = new JButton("게임시작");
        JButton myRecordsButton = new JButton("내 기록");

        // 버튼 스타일 설정
        mainMenuButton.setBackground(Color.GRAY);
        mainMenuButton.setForeground(Color.WHITE);
        startGameButton.setBackground(Color.GRAY);
        startGameButton.setForeground(Color.WHITE);
        myRecordsButton.setBackground(Color.GRAY);
        myRecordsButton.setForeground(Color.WHITE);

        // 버튼 눌렸을 때 스타일 변경
        mainMenuButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mainMenuButton.setBackground(Color.DARK_GRAY);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                mainMenuButton.setBackground(Color.GRAY);
            }
        });

        startGameButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                startGameButton.setBackground(Color.DARK_GRAY);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                startGameButton.setBackground(Color.GRAY);
            }
        });

        myRecordsButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                myRecordsButton.setBackground(Color.DARK_GRAY);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                myRecordsButton.setBackground(Color.GRAY);
            }
        });

        buttonPanel.add(mainMenuButton);
        buttonPanel.add(startGameButton);
        buttonPanel.add(myRecordsButton);

        bottomPanel.add(buttonPanel, BorderLayout.EAST);

        add(bottomPanel, BorderLayout.SOUTH);
        add(imageLabel, BorderLayout.NORTH);

        // 버튼 이벤트 처리
        startGameButton.addActionListener(new ActionListener() { //게임실행 화면으로 넘어가는 기능
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new GameFrame().setVisible(true);
            }
        });

        mainMenuButton.addActionListener(new ActionListener() { //메인메뉴로 돌아가기
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        myRecordsButton.addActionListener(new ActionListener() {//게임 기록 볼수 있는창. 구현중....
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new GameRecord().setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                GameMenu mainScreen = new GameMenu();
                mainScreen.setVisible(true);
            }
        });
    }
}
