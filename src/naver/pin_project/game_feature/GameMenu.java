package src.naver.pin_project.game_feature;

import src.naver.pin_project.view.MainScreen;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

class StyledButtonUI extends BasicButtonUI {

    @Override
    public void installUI (JComponent c) {
        super.installUI(c);
        AbstractButton button = (AbstractButton) c;
        button.setOpaque(false);
        button.setBorder(new EmptyBorder(5, 15, 5, 15));
    }

    @Override
    public void paint (Graphics g, JComponent c) {
        AbstractButton b = (AbstractButton) c;
        paintBackground(g, b, b.getModel().isPressed() ? 2 : 0);
        super.paint(g, c);
    }

    private void paintBackground (Graphics g, JComponent c, int yOffset) {
        Dimension size = c.getSize();
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(c.getBackground().darker());
        g.fillRoundRect(0, yOffset, size.width, size.height - yOffset, 10, 10);
        g.setColor(c.getBackground());
        g.fillRoundRect(0, yOffset, size.width, size.height + yOffset - 5, 10, 10);
    }
}

class ImagePanel extends JPanel {
    public BufferedImage backgroundImage;

    public ImagePanel(String imagePath) {
        try {
            backgroundImage = ImageIO.read(new File(imagePath));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(backgroundImage.getWidth(), backgroundImage.getHeight());
    }
}

public class GameMenu extends JFrame {

    public String UserID = MainScreen.UserID;//메인메뉴로부터 받아올 유저아이디
    public String UserName = MainScreen.UserID;//메인메뉴로부터 받아올 유저 닉네임
    //유저아이디 하나로 DB에서 받아올수 있지만 굳이.. 싶어서 메인변수 받아와도 좋을거같다!

    public GameMenu() {//처음 게임메뉴
        setTitle("볼링게임");
        setSize(730, 530);
        setLocationRelativeTo(null);
        setResizable(false);

        // 상단 이미지
        ImageIcon imageIcon = new ImageIcon("src/naver/pin_project/game_feature/img_asset/bowling.gif");
        JLabel imageLabel = new JLabel(imageIcon);

        /*
        // 하단 패널
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(Color.decode("#8A8585"));
*/
        // 하단 패널 (ImagePanel 사용)
        ImagePanel bottomPanel = new ImagePanel("src/naver/pin_project/game_feature/img_asset/bgbg.png");
        bottomPanel.setPreferredSize(new Dimension(730, 56));
        bottomPanel.setLayout(new BorderLayout());
        // 왼쪽 userId 표시되는 라벨
        JLabel userIdLabel = new JLabel(UserName+" 님 환영합니다! 즐거운 시간 되세요.");
        userIdLabel.setForeground(Color.WHITE);
        bottomPanel.add(userIdLabel, BorderLayout.WEST);

        // 버튼 패널
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.black);

        ImageIcon mainBtn = new ImageIcon("src/naver/pin_project/game_feature/img_asset/MAIN_MENU.png");
        ImageIcon mainBtn_press = new ImageIcon("src/naver/pin_project/game_feature/img_asset/MAIN_MENU_PRESS.png");
        ImageIcon myRecordBtn = new ImageIcon("src/naver/pin_project/game_feature/img_asset/MY_RECORD.png");
        ImageIcon myRecordBtn_press = new ImageIcon("src/naver/pin_project/game_feature/img_asset/MY_RECORD_PRESS.png");
        ImageIcon startGameBtn = new ImageIcon("src/naver/pin_project/game_feature/img_asset/GAME_START.png");
        ImageIcon startGameBtn_press = new ImageIcon("src/naver/pin_project/game_feature/img_asset/GAME_START_PRESS.png");

        // 이미지 크기 조정
        mainBtn = resizeImageIcon(mainBtn, 120, 50);
        mainBtn_press = resizeImageIcon(mainBtn_press, 120, 50);
        myRecordBtn = resizeImageIcon(myRecordBtn, 120, 50);
        myRecordBtn_press = resizeImageIcon(myRecordBtn_press, 120, 50);
        startGameBtn = resizeImageIcon(startGameBtn, 120, 50);
        startGameBtn_press = resizeImageIcon(startGameBtn_press, 120, 50);


        JButton mainMenuButton = new JButton(mainBtn);
        mainMenuButton.setBorderPainted(false);
        mainMenuButton.setPreferredSize(new Dimension(120, 50));
        mainMenuButton.setRolloverIcon(mainBtn_press);
        JButton startGameButton = new JButton(startGameBtn);
        startGameButton.setBorderPainted(false);
        startGameButton.setPreferredSize(new Dimension(120, 50));
        startGameButton.setRolloverIcon(startGameBtn_press);
        JButton myRecordsButton = new JButton(myRecordBtn);
        myRecordsButton.setBorderPainted(false);
        myRecordsButton.setPreferredSize(new Dimension(120, 50));
        myRecordsButton.setRolloverIcon(myRecordBtn_press);



        /*
        // 버튼 스타일 설정
        mainMenuButton.setBackground(Color.decode("#FCEB83"));
        mainMenuButton.setForeground(Color.black);
        startGameButton.setBackground(Color.decode("#B0FFA9"));
        startGameButton.setForeground(Color.black);
        myRecordsButton.setBackground(Color.decode("#8DFFF3"));
        myRecordsButton.setForeground(Color.black);
*/
        /*
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

         */

        buttonPanel.add(mainMenuButton);
        buttonPanel.add(startGameButton);
        buttonPanel.add(myRecordsButton);

        bottomPanel.add(buttonPanel, BorderLayout.EAST);
        buttonPanel.setBackground(Color.decode("#282828"));

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

        myRecordsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(() -> {
                    GameRecord gameRecord = new GameRecord();
                    gameRecord.setVisible(true);
                    // 호출되지 않더라도 빈 테이블을 보여주기 위해 추가
                    gameRecord.fetchDataFromDatabase();
                });
            }
        });
    }
    public static ImageIcon resizeImageIcon(ImageIcon icon, int width, int height) {
        Image img = icon.getImage();
        Image newImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(newImg);
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
