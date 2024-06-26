package src.naver.pin_project.view;

import src.naver.pin_project.data.CustomFont;
import src.naver.pin_project.view.ChatScreen;
import src.naver.pin_project.viewmodel.Chat_ViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StaffCallScreen extends JDialog {

    private JPanel buttonPanel;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JButton button5;
    private JButton button6;
   private JLabel staffOrderLabel;
   private JLabel staffOrderLabel1;
   private JLabel staffOrderLabel2;
    private ChatScreen chatScreen;

    private MainScreen mainScreen;

    public StaffCallScreen(MainScreen mainScreen) {

        // 폰트 파일 경로
        String fontPath = "src/naver/pin_project/lib/온글잎밑미.ttf";
        // 원하는 폰트 크기로 폰트 로드
        Font customFont = CustomFont.loadFont(fontPath, 25f);
        // UI에 폰트 적용
        CustomFont.setUIFont(customFont);

        this.mainScreen = mainScreen;
        setTitle("직원호출");
        setSize(730, 530);
        setLocationRelativeTo(null);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.BLACK);
        topPanel.setPreferredSize(new Dimension(getWidth(), 40));

        // 좌측 상단 P-in 로고
        JLabel titleLabel = new JLabel("P-in");
        titleLabel.setForeground(Color.WHITE);
        topPanel.add(titleLabel, BorderLayout.WEST);

        JButton cancelButton = new JButton("닫기");
        cancelButton.setBackground(Color.decode("#B0FFA9"));
        cancelButton.setForeground(Color.black);

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // 다이얼로그 닫기
            }
        });

        topPanel.add(cancelButton, BorderLayout.EAST); // 닫기 버튼 우측에 배치

        add(topPanel, BorderLayout.NORTH); // 상단 패널을 다이얼로그에 추가

        JPanel mainPanel = new JPanel(new BorderLayout()); // 메인 패널 생성
        add(mainPanel); // 메인 패널을 다이얼로그에 추가

        buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints(); // GridBagLayout의 컴포넌트를 배치 및 크기조절 제약 조건 설정 변수
        buttonPanel.setBackground(Color.decode("#8A8585"));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);

        button1 = new JButton("핀이 안나와요");
        button1.setFont(button1.getFont().deriveFont(Font.BOLD, 23));
        button1.setPreferredSize(new Dimension(150, 120));
        button1.setBackground(Color.decode("#FCEB83"));
        button1.setForeground(Color.black);
        button1.setBorder(BorderFactory.createLineBorder(Color.decode("#FCEB83")));

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(StaffCallScreen.this, "관리자에게 전달되었습니다.");
            }
        });
        buttonPanel.add(button1, gbc);

        gbc.gridx = 1;
        button2 = new JButton("볼이 안나와요");
        button2.setFont(button1.getFont().deriveFont(Font.BOLD, 23));
        button2.setPreferredSize(new Dimension(150, 120));
        button2.setBackground(Color.decode("#FCEB83"));
        button2.setForeground(Color.black);
        button2.setBorder(BorderFactory.createLineBorder(Color.decode("#FCEB83")));
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(StaffCallScreen.this, "관리자에게 전달되었습니다.");
            }
        });
        buttonPanel.add(button2, gbc);

        gbc.gridx = 2;
        button3 = new JButton("직원 호출");
        button3.setFont(button1.getFont().deriveFont(Font.BOLD, 23));
        button3.setPreferredSize(new Dimension(150, 120));
        button3.setBackground(Color.decode("#FCEB83"));
        button3.setForeground(Color.black);
        button3.setBorder(BorderFactory.createLineBorder(Color.decode("#FCEB83")));
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Chat_ViewModel.startServer();
                    }
                }).start();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        chatScreen = new ChatScreen();
                    }
                }).start();
            }
        });
        buttonPanel.add(button3, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        button4 = new JButton("리그로 바꿔주세요 ");
        button4.setFont(button1.getFont().deriveFont(Font.BOLD, 23));
        button4.setPreferredSize(new Dimension(150, 120));
        button4.setBackground(Color.decode("#FCEB83"));
        button4.setForeground(Color.black);
        button4.setBorder(BorderFactory.createLineBorder(Color.decode("#FCEB83")));

        button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(StaffCallScreen.this, "관리자에게 전달되었습니다.");
            }
        });
        buttonPanel.add(button4, gbc);

        gbc.gridx = 1;
        button5 = new JButton("오픈으로 바꿔주세요");
        button5.setFont(button1.getFont().deriveFont(Font.BOLD, 23));
        button5.setPreferredSize(new Dimension(150, 120));
        button5.setBackground(Color.decode("#FCEB83"));
        button5.setForeground(Color.black);
        button5.setBorder(BorderFactory.createLineBorder(Color.decode("#FCEB83")));

        button5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(StaffCallScreen.this, "관리자에게 전달되었습니다.");
            }
        });
        buttonPanel.add(button5, gbc);

        gbc.gridx = 2;
        button6 = new JButton("기계가 멈췄어요");
        button6.setFont(button1.getFont().deriveFont(Font.BOLD, 23));
        button6.setPreferredSize(new Dimension(150, 120));
        button6.setBackground(Color.decode("#FCEB83"));
        button6.setForeground(Color.black);
        button6.setBorder(BorderFactory.createLineBorder(Color.decode("#FCEB83")));

        button6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(StaffCallScreen.this, "관리자에게 전달되었습니다.");
            }
        });
        buttonPanel.add(button6, gbc);

        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        // 오른쪽에 위치할 staffOrderPanel을 생성하고 배치
        JPanel staffOrderPanel = new JPanel(new BorderLayout()); // BorderLayout을 사용하여 패널을 생성
        staffOrderPanel.setBackground(Color.WHITE); // 패널의 배경색을 흰색으로 설정

// staffOrderPanel 내에 세로로 컴포넌트를 배치할 staffOrderPanel1을 생성
        JPanel staffOrderPanel1 = new JPanel();
        staffOrderPanel1.setLayout(new BoxLayout(staffOrderPanel1, BoxLayout.Y_AXIS)); // BoxLayout을 사용하여 세로 방향으로 배치

// 요청 안내용을 담을 JLabel들을 생성
        JLabel staffOrderLabel = new JLabel("왼쪽에서\n");
        JLabel staffOrderLabel1 = new JLabel("요청하실 항목을\n");
        JLabel staffOrderLabel2 = new JLabel("선택해주세요.");

        staffOrderLabel1.setForeground(Color.orange); // staffOrderLabel1의 텍스트 색상을 주황색으로 설정

        staffOrderPanel1.add(Box.createVerticalGlue()); // BoxLayout에서의 유연한 여백을 추가
        staffOrderPanel1.add(staffOrderLabel); // staffOrderPanel1에 staffOrderLabel을 추가
        staffOrderPanel1.add(staffOrderLabel1); // staffOrderPanel1에 staffOrderLabel1을 추가
        staffOrderPanel1.add(staffOrderLabel2); // staffOrderPanel1에 staffOrderLabel2을 추가
        staffOrderPanel1.add(Box.createVerticalGlue()); // BoxLayout에서의 여백을 추가

        staffOrderPanel.add(staffOrderPanel1, BorderLayout.CENTER); // staffOrderPanel1을 staffOrderPanel의 중앙에 배치

        mainPanel.add(staffOrderPanel, BorderLayout.EAST); // mainPanel의  우측에 staffOrderPanel을 추가

        setVisible(true);
    }
}
