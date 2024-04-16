package src.naver.pin_project.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import javax.swing.*;

public class ChatScreen extends JFrame {
    private JTextArea chatArea;
    private JTextField messageField;
    private PrintWriter out;
    private BufferedReader in;

    public ChatScreen() {
        setLocationRelativeTo(null); // 화면 중앙에 위치

        // UI 구성요소
        chatArea = new JTextArea(20, 40); // 채팅 영역
        chatArea.setEditable(false); // 클라이언트가 채팅영역 편집 불가능하게 막기
        JScrollPane chatScrollPane = new JScrollPane(chatArea); // 스크롤 기능 추가
        add(chatScrollPane, BorderLayout.CENTER); // 채팅창 프레임 중앙에 위치
        JPanel sendPanel = new JPanel(new BorderLayout()); // 잔송 패널 생성
        sendPanel.setPreferredSize(new Dimension(getWidth(), 40));// 왼쪽 정렬 FlowLayout 사용

        messageField = new JTextField(30);
        Font textFieldFont = messageField.getFont();
        messageField.setFont(new Font(textFieldFont.getName(), textFieldFont.getStyle(), 20));
        messageField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        }); // 엔터 누르면 메세지 전송

        JButton sendButton = new JButton("전송"); // 전송 버튼
        sendButton.setFont(sendButton.getFont().deriveFont(Font.BOLD));


        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        }); // 전송 버튼 누르면 메세지 전송

        sendPanel.add(messageField, BorderLayout.CENTER); // 메세지 입력 필드 패널 중앙에 추가
        sendPanel.add(sendButton, BorderLayout.EAST); // 전송 버튼 패널 우측에 추가
        add(sendPanel, BorderLayout.SOUTH); // 전송 패널을 프레임 아래쪽에 배치

        pack(); // 컴포넌트 크기에 맞게 프레임 크기 조정
        setVisible(true); // 프레임 화면에 표시
        setLocationRelativeTo(null); // 창 중앙에 위치


        //ChatClient 고객이 서버에게 연결
        try {
            Socket socket = new Socket("localhost", 8081); // 서버에 연결
            out = new PrintWriter(socket.getOutputStream(), true); // 출력스트림
            in = new BufferedReader(new InputStreamReader(socket.getInputStream())); // 입력스트림

            // 연결 메시지 표시
            chatArea.append("직원과 연결되었습니다.\n\n");

            // 서버로부터 메시지 수신 스레드 시작
            new Thread(new ReceiveMessageThread()).start();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // 메시지 전송
    private void sendMessage() {
        String message = messageField.getText(); // 입력된 메세지 가져오기
        out.println(message); // 서버로 메세지 전송
        chatArea.append("나: " + message + "\n"); // 채팅에 내가 보낸 메세지 표시
        messageField.setText(""); // 메세지 입력 필드 초기화
    }

    // 서버로부터 메시지 수신 스레드
    private class ReceiveMessageThread implements Runnable {
        public void run() {
            try {
                String message;
                while ((message = in.readLine()) != null) {
                    chatArea.append("직원:" + message + "\n"); // 직원이 보낸 메세지를 채팅에 표시
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

}