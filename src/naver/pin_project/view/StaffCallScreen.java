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

        JLabel titleLabel = new JLabel("P-in");
        titleLabel.setForeground(Color.WHITE);
        topPanel.add(titleLabel, BorderLayout.WEST);

        JButton cancelButton = new JButton("닫기");
        cancelButton.setBackground(Color.decode("#B0FFA9"));

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // 다이얼로그 닫기
            }
        });

        topPanel.add(cancelButton, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel(new BorderLayout());
        add(mainPanel);

        buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        buttonPanel.setBackground(Color.decode("#8A8585"));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);

        button1 = new JButton("핀이 안나와요");
        button1.setFont(button1.getFont().deriveFont(Font.BOLD, 23));
        button1.setPreferredSize(new Dimension(150, 120));
        button1.setBackground(Color.decode("#FCEB83"));
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
        button6.setBorder(BorderFactory.createLineBorder(Color.decode("#FCEB83")));

        button6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(StaffCallScreen.this, "관리자에게 전달되었습니다.");
            }
        });
        buttonPanel.add(button6, gbc);

        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        JPanel staffOrderPanel = new JPanel(new BorderLayout());
        staffOrderPanel.setBackground(Color.WHITE);

        staffOrderLabel = new JLabel("<html><div style='text-align:center;'>" +
                "<span style='font-size:20pt; color:orange;'>" +
                "<font color='gray'>왼쪽에서</font><br/>" +
                " 요청하실 항목을<br/>" +
                " 선택<font color='gray'>해주세요</font></span></div></html>");


        staffOrderPanel.add(staffOrderLabel, BorderLayout.CENTER);

        mainPanel.add(staffOrderPanel, BorderLayout.EAST);

        setVisible(true);
    }
}
