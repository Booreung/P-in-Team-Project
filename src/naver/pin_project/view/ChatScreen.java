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
        setLocationRelativeTo(null);
        // UI
        chatArea = new JTextArea(20, 40);
        chatArea.setEditable(false);
        JScrollPane chatScrollPane = new JScrollPane(chatArea);
        add(chatScrollPane, BorderLayout.CENTER);
        JPanel sendPanel = new JPanel(new BorderLayout());
        sendPanel.setPreferredSize(new Dimension(getWidth(), 40));// 왼쪽 정렬 FlowLayout 사용

        messageField = new JTextField(30);
        Font textFieldFont = messageField.getFont();
        messageField.setFont(new Font(textFieldFont.getName(), textFieldFont.getStyle(), 20));
        messageField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        JButton sendButton = new JButton("전송");
        sendButton.setFont(sendButton.getFont().deriveFont(Font.BOLD));


        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        sendPanel.add(messageField, BorderLayout.CENTER);
        sendPanel.add(sendButton, BorderLayout.EAST);
        add(sendPanel, BorderLayout.SOUTH);

        pack();
        setVisible(true);
        setLocationRelativeTo(null);


        //ChatClient 고객이 서버에게 연결
        try {
            Socket socket = new Socket("localhost", 8081); // 서버에 연결
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

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
        String message = messageField.getText();
        out.println(message);
        chatArea.append("나: " + message + "\n");
        messageField.setText("");
    }

    // 서버로부터 메시지 수신 스레드
    private class ReceiveMessageThread implements Runnable {
        public void run() {
            try {
                String message;
                while ((message = in.readLine()) != null) {
                    chatArea.append("직원:" + message + "\n");
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

}