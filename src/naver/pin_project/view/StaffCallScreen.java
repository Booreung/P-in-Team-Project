package src.naver.pin_project.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class StaffCallScreen extends JPanel {

    private JFrame frame;
    private JPanel buttonPanel; // 버튼을 담을 패널
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JButton button5;
    private JButton button6;
    private JLabel staffOrderLabel;
    private MainScreen mainScreen;
    private Socket clientSocket;

    public StaffCallScreen(MainScreen mainScreen) {
        this.mainScreen = mainScreen;

        frame = new JFrame("직원호출");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 화면의 너비를 가져옴
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int) screenSize.getWidth();

        JPanel topPanel = new JPanel(new BorderLayout()); // BorderLayout 사용
        topPanel.setBackground(Color.BLACK); // 배경색 설정
        topPanel.setPreferredSize(new Dimension(screenWidth, 40)); // 너비와 높이 설정

        JLabel titleLabel = new JLabel("P-in"); // 제목 레이블 생성
        titleLabel.setForeground(Color.WHITE); // 흰색 글씨 설정
        topPanel.add(titleLabel, BorderLayout.WEST); // 레이블을 좌측에 추가

        JButton cancelButton = new JButton("닫기"); // 닫기 버튼 생성
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 닫기 버튼 클릭 시 MainScreen으로 화면 전환
                frame.dispose(); // 현재 화면 닫기

                // MainScreen의 패널을 보이도록 설정
                mainScreen.setVisible(true);
            }
        });

        topPanel.add(cancelButton, BorderLayout.EAST); // 닫기 버튼을 우측에 추가

        frame.add(topPanel, BorderLayout.NORTH); // 프레임의 상단에 패널 추가

        JPanel mainPanel = new JPanel(new BorderLayout());
        frame.add(mainPanel);

        buttonPanel = new JPanel(new GridBagLayout()); // GridBagLayout 사용

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; // 첫 번째 열
        gbc.gridy = 0; // 첫 번째 행
        gbc.insets = new Insets(10, 10, 10, 10); // 여백 설정

        button1 = new JButton("핀이 안나와요");
        button1.setFont(button1.getFont().deriveFont(Font.BOLD, 15));
        button1.setPreferredSize(new Dimension(150, 120)); // 크기 설정
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "관리자에게 전달되었습니다.");
            }
        });
        buttonPanel.add(button1, gbc);

        gbc.gridx = 1; // 두 번째 열
        button2 = new JButton("볼이 안나와요");
        button2.setFont(button1.getFont().deriveFont(Font.BOLD, 15));
        button2.setPreferredSize(new Dimension(150, 120)); // 크기 설정
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "관리자에게 전달되었습니다.");
            }
        });
        buttonPanel.add(button2, gbc);

        gbc.gridx = 2; // 세 번째 열
        button3 = new JButton("직원 호출");
        button3.setFont(button1.getFont().deriveFont(Font.BOLD, 15));
        button3.setPreferredSize(new Dimension(150, 120)); // 크기 설정
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 직원 호출 버튼 클릭 시 클라이언트 소켓을 생성하고 서버에 연결
                try {
                    System.out.println("관리자에게 연결중입니다...");
                    clientSocket = new Socket("localhost", 8000); // 서버 포트를 수정하세요
                    // 클라이언트 소켓이 생성되고 서버에 연결되었음을 확인하기 위해 메시지 출력
                    System.out.println("관리자와 연결되었습니다.");

                    // 여기에 채팅창을 띄우고 채팅 기능을 활성화하는 코드를 추가하세요
                    openChatWindow(clientSocket);

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        buttonPanel.add(button3, gbc);

        gbc.gridx = 0; // 첫 번째 열
        gbc.gridy = 1; // 두 번째 행
        button4 = new JButton("리그로 바꿔주세요 ");
        button4.setFont(button1.getFont().deriveFont(Font.BOLD, 13));
        button4.setPreferredSize(new Dimension(150, 120)); // 크기 설정
        button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "관리자에게 전달되었습니다.");
            }
        });
        buttonPanel.add(button4, gbc);

        gbc.gridx = 1; // 두 번째 열
        button5 = new JButton("오픈으로 바꿔주세요");
        button5.setFont(button1.getFont().deriveFont(Font.BOLD, 12));
        button5.setPreferredSize(new Dimension(150, 120)); // 크기 설정
        button5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "관리자에게 전달되었습니다.");
            }
        });
        buttonPanel.add(button5, gbc);

        gbc.gridx = 2; // 세 번째 열
        button6 = new JButton("기계가 멈췄어요");
        button6.setFont(button1.getFont().deriveFont(Font.BOLD, 15));
        button6.setPreferredSize(new Dimension(150, 120)); // 크기 설정
        button6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "관리자에게 전달되었습니다.");
            }
        });
        buttonPanel.add(button6, gbc);

        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        JPanel staffOrderPanel = new JPanel(new BorderLayout()); // BorderLayout을 사용하는 패널 생성
        staffOrderPanel.setBackground(Color.WHITE); // 배경색 설정

        staffOrderLabel = new JLabel("<html><div style='text-align:center;'>" +
                "<span style='font-size:20pt; color:orange;'>" +
                "<font color='gray'>왼쪽에서</font><br/>" +
                " 요청하실 항목을<br/>" +
                " 선택<font color='gray'>해주세요</font></span></div></html>");

        staffOrderPanel.add(staffOrderLabel, BorderLayout.CENTER); // 레이블을 패널에 추가

        mainPanel.add(staffOrderPanel, BorderLayout.EAST); // 패널을 메인 패널에 추가

        frame.setVisible(true);
    }
    // openChatWindow 메소드 추가
    private void openChatWindow(Socket clientSocket) {
        // 채팅창을 띄우는 코드를 여기에 추가합니다.
        JFrame chatFrame = new JFrame("직원 호출 채팅");
        chatFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // 채팅창의 내용을 표시할 JTextArea 생성
        JTextArea chatArea = new JTextArea();
        chatArea.setEditable(false); // 사용자가 직접 편집하지 못하도록 설정
        JScrollPane scrollPane = new JScrollPane(chatArea);

        // 메시지를 입력할 JTextField 생성
        JTextField messageField = new JTextField();
        messageField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = messageField.getText();
                // 메시지를 서버로 보내는 코드를 추가하세요
                sendMessageToServer(clientSocket, message);
                messageField.setText(""); // 메시지 입력 필드 초기화
            }
        });

        // 채팅창에 컴포넌트 추가
        chatFrame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        chatFrame.getContentPane().add(messageField, BorderLayout.SOUTH);

        // 채팅창 크기 설정
        chatFrame.setSize(400, 300);
        chatFrame.setVisible(true);
    }

    // sendMessageToServer 메소드 추가
    private void sendMessageToServer(Socket clientSocket, String message) {
        try {
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            out.println(message);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

