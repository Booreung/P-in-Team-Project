package src.naver.pin_project.game_feature;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

public class GameFrame extends JFrame { //이곳은 게임실행창입니다!
    GameDTO gameDto = new GameDTO(); //게임 데이터 저장하는 객체 생성
    GameMenu gameMenu = new GameMenu(); //게임메뉴창으로 다시 돌아가기위해
    ArrayList<String> scores = new ArrayList<>(); //공을 굴리고 나서 점수
    ArrayList<String> sum_scores = new ArrayList<>();// 공을 다 굴리고 총합점수
    DBConnector dbConnector = new DBConnector();//디비 객체 생성
    private JProgressBar loadingBar;
    public GameFrame() {
        setTitle("Game");
        setSize(730, 530);
        ImageIcon imageIcon = new ImageIcon("src/naver/pin_project/game_feature/img_asset/b_menu.jpg");
        JLabel imageLabel = new JLabel(imageIcon);//게임 실행창 게임메뉴


        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.decode("#8A8585"));

        JButton backButton = new JButton("뒤로가기");
        JButton exitButton = new JButton("게임종료");
        JButton gameButton = new JButton("볼링치기");
        backButton.setBackground(Color.decode("#FCEB83")); // 그레이 버튼
        backButton.setForeground(Color.black);
        exitButton.setBackground(Color.decode("#B0FFA9"));
        exitButton.setForeground(Color.black);
        gameButton.setBackground(Color.decode("#8DFFF3"));
        gameButton.setForeground(Color.black);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                backButton.setBackground(Color.DARK_GRAY); // 누르면 다크 그레이로 변경
                // ...
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exitButton.setBackground(Color.DARK_GRAY); // 누르면 다크 그레이로 변경
                // ...
            }
        });

        gameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameButton.setBackground(Color.DARK_GRAY); // 누르면 다크 그레이로 변경
                // ...
            }
        });

        buttonPanel.add(backButton);
        buttonPanel.add(exitButton);
        buttonPanel.add(gameButton);

        add(buttonPanel, BorderLayout.SOUTH);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // 현재 화면 닫기
                new GameMenu().setVisible(true); // 메인화면 열기
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();// 프로그램 종료
            }
        });
        gameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /*Component removeBackground = imageLabel;
                Container container = getContentPane();
                container.remove(removeBackground);
                container.remove(buttonPanel);*/

                SwingUtilities.invokeLater(() -> {
                    LoadingAnimation();
                });
            }
        });
        add(imageLabel, BorderLayout.NORTH);

    }
    public void result_game(){
        JFrame frame = new JFrame("게임결과!!");
        frame.setSize(800, 480);


        JButton cButton = new JButton("확인");
        cButton.setBackground(Color.decode("#B0FFA9"));
        cButton.setForeground(Color.black);
        JPanel buttonPanel = new JPanel();

        frame.getContentPane().add(createScorecard(1, gameMenu.UserName), BorderLayout.CENTER);//메뉴에서 받아온 아이디
        frame.getContentPane().add(cButton, BorderLayout.SOUTH);
        frame.setBackground(Color.decode("#8A8585"));

        cButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // close window
            }
        });

        frame.pack();
        frame.setVisible(true);
    }
    private JPanel createScorecard(int numPlayers, String name) {
        JPanel p = new JPanel(new GridBagLayout());
        System.out.println("gameframe:"+name);
        p.setBackground(Color.decode("#B0FFA9"));
        p.setForeground(Color.white);

        p.add(new JLabel("  "+name+" 님  "), gbc(0, 1, 1, 1,2));
        p.add(new JLabel("      "), gbc(0, 1, 1, 1,2));
        for (int x = 1; x <= 10; x++) {
            p.add(new JLabel(Integer.toString(x)), gbc(x, 0, 1, 1,0));
        }//표 윗쪽 라벨 삽입

        for (int y = 1; y <= numPlayers; y++) {
            JTextArea textArea = new JTextArea(2, 10);
            textArea.setBackground(Color.decode("#B0FFA9"));
            textArea.setForeground(Color.black);
            p.add(textArea, gbc(0, y, 1, 1,0));

            for (int i = 1; i <= 10; i++) {
                int score_sum = Integer.parseInt(sum_scores.remove(0));
                p.add(createFrame(2,score_sum), gbc(i, y, 1, 1,0));
            }
        }

        return p;
    }
    private JPanel createFrame(int entries,int score_sum) {

        JLabel label = new JLabel(String.valueOf(score_sum));
        label.setBackground(Color.decode("#8A8585"));
        label.setForeground(Color.black);

        JPanel p = new JPanel(new GridBagLayout());
        p.setBorder(BorderFactory.createLineBorder(Color.black, 2));
        for (int i = 0; i < entries; i++) {

            JTextField jTextField = new JTextField(4);
            jTextField.setEditable(false);
            //String a = scores[j];
            //System.out.println(j + "째 배열: " + scores[j] + " , " + (j + 1) + "째 배열: " + scores[j + 1]);
            //jTextField.setText(a);
            String score_element = scores.remove(0);
            jTextField.setText(score_element);
            jTextField.setBackground(Color.ORANGE);
            jTextField.setForeground(Color.BLACK);

            p.add(jTextField, gbc(i, 0, 1, 1, 0));
        }

        p.add(label, gbc(0, 1, 2, 1,0));

        return p;
    }
    private GridBagConstraints gbc(int x, int y, int colspan, int rowspan, int widx) { //점수표 디자인 설정
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = colspan;
        gbc.gridheight = rowspan;
        gbc.weightx = widx;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        return gbc;
    }
    public JFrame LoadingAnimation() {
        JFrame frame = new JFrame();
        frame.setTitle("볼링치는중...");
        frame.setSize(400, 200);
        frame.setBackground(Color.decode("#8A8585"));
        //frame.setLocationRelativeTo(null);

        loadingBar = new JProgressBar();
        loadingBar.setStringPainted(true);
        frame.getContentPane().add(loadingBar, BorderLayout.CENTER);
        //frame.pack();
        frame.setVisible(true);

        Timer timer = new Timer(5, new ActionListener() {
            int progress = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                progress++;
                loadingBar.setValue(progress * 100 / 100); // 0~100 사이 값으로 설정
                loadingBar.setString(progress + "....    공이 굴러가구 있습니다!!!");
                loadingBar.setBackground(Color.decode("#8A8585"));
                loadingBar.setForeground(Color.decode("#FCEB83"));


                if (progress == 100) {
                    frame.dispose();
                    //new GameFrame().setVisible(true);
                    try {
                        ball();// 윈도우를 닫습니다.
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    } catch (ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
        timer.start();

        return frame;
    }
    public void ball() throws SQLException, ClassNotFoundException {
        System.out.println("시작");
        final int FRAMES = 10;
        final int PINS = 10;
        int totalScore = 0;
        Random random = new Random();
        // 0부터 99999 사이의 랜덤 정수 생성
        gameDto.setUserid(gameMenu.UserID);
        int game_code = random.nextInt(1000000000);
        gameDto.setGame_code(game_code);
        //일단 디티오에 데이터 저장
        //아래는 잘 불러오는지 디버그용. 지워도 상관 없음
        int gcd = gameDto.getGame_code();
        String gid = gameDto.getUserid();
        System.out.println("생성된 게임코드: " + gcd);
        System.out.println("플레이어: " + gid);


        for (int frame = 1; frame <= FRAMES; frame++) {
            int firstRoll = roll(random, PINS);
            int secondRoll = 0;

            // 스트라이크
            if (firstRoll == PINS) {
                System.out.println("Frame " + frame + ": STRIKE!");
                totalScore += PINS + roll(random, PINS);
                //scores[count]=String.valueOf('X');
                //scores[count+1]=String.valueOf('/');
                //System.out.println(count+"째 배열: "+scores[count]+" , "+(count+1)+"째 배열: "+scores[count+1]);
                scores.add("X");
                scores.add("/");
                sum_scores.add(String.valueOf(totalScore));


            } else {
                secondRoll = roll(random, PINS - firstRoll);
                System.out.println("Frame " + frame + ": " + firstRoll + " / " + secondRoll);
                //scores[count]=String.valueOf(firstRoll);
                //scores[count+1]=String.valueOf(secondRoll);
                //System.out.println(count+"째 배열: "+scores[count]+" , "+(count+1)+"째 배열: "+scores[count+1]);
                scores.add(String.valueOf(firstRoll));
                scores.add(String.valueOf(secondRoll));



                // 스페어
                if (firstRoll + secondRoll == PINS) {
                    totalScore += PINS + roll(random, PINS);
                    sum_scores.add(String.valueOf(totalScore));
                } else {
                    totalScore += firstRoll + secondRoll;
                    sum_scores.add(String.valueOf(totalScore));
                }
            }
        }
        System.out.println("Total Score: " + totalScore);
        gameDto.setGame_point(totalScore); //점수 저장
        //디비클래스에게 지금껏 저장한값 매개변수로 넘겨줌. 디비는 이거가지고 디비에다 집어넣을것임...
        dbConnector.saveGameRecords(gameDto.getGame_code(), gameDto.getUserid(), gameDto.getGame_point());
        result_game();//점수판 표시
    }

    // 주어진 핀 수 범위 내에서 랜덤하게 핀을 넘어뜨림
    public static int roll(Random random, int pins) {
        return random.nextInt(pins + 1);
    }
}