package src.naver.pin_project.viewmodel;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Chat_ViewModel {
    public static void startServer() {
        try {
            ServerSocket serverSocket = new ServerSocket(8080);
            System.out.println("서버가 시작되었습니다.");

            while(true) {
                // 클라이언트의 연결을 대기
                Socket clientSocket = serverSocket.accept();
                System.out.println("클라이언트가 연결되었습니다.");

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

                // 소켓 및 서버 소켓 닫기
                out.close();
                in.close();
                clientSocket.close();
                serverSocket.close();
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }


    }

}
