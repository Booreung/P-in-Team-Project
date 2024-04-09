package src.naver.pin_project.viewmodel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Chat_ViewModel {
    private static ServerSocket serverSocket;
    private static boolean isServerRunning;
    private static List<ClientHandler> clientHandlers = new ArrayList<>();

    public static void startServer() {
        isServerRunning = true;
        System.out.println("서버에 접속중입니다.");

        try {
            boolean serverStarted = false;
            int portNumber = 8080;
            while (!serverStarted) {
                try {
                    serverSocket = new ServerSocket(portNumber);
                    serverStarted = true;
                } catch (BindException e) {
                    System.out.println("포트 " + portNumber + "가 이미 사용 중입니다. 잠시 기다립니다...");
                    Thread.sleep(1000); // 1초 동안 대기
                    portNumber++; // 다음 포트로 시도
                }
            }

            System.out.println("서버가 포트 " + portNumber + "에서 시작되었습니다.");

            while (isServerRunning) {
                // 클라이언트의 연결을 대기
                Socket clientSocket = serverSocket.accept();
                TimeUnit.MILLISECONDS.sleep(500);
                System.out.println("서버에 연결되었습니다.");
                TimeUnit.MILLISECONDS.sleep(700);
                System.out.println("클라이언트가 연결되었습니다.");

                // 새로운 스레드에서 클라이언트 처리
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                clientHandlers.add(clientHandler);
                clientHandler.start();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static class ClientHandler extends Thread {
        private final Socket clientSocket;

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        public void run() {
            try {
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));

                // 클라이언트로부터 메시지 수신 및 송신
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    System.out.println("클라이언트: " + inputLine);
                    // 직원의 답변 메시지
                    String replyMessage = consoleReader.readLine();
                    out.println(replyMessage);
                }

                // 소켓 닫기
                out.close();
                in.close();
                clientSocket.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                try {
                    clientSocket.close(); // 클라이언트 소켓 닫기
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
