package src.naver.pin_project.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

// 결제 화면을 나타내는 클래스
public class PaymentScreen extends JFrame {
    private JButton cardPaymentButton; // 카드 결제 버튼
    private JButton cashPaymentButton; // 현금 결제 버튼
    private String fontPath = "/C:/Users/user/Desktop/네이버클라우드캠프/프로젝트 3.25~4.22/폰트/온글잎 밑미.ttf"; // 사용할 폰트 파일 경로
    private JDialog paymentDialog; // 결제 진행 상태를 나타내는 다이얼로그

    // 생성자: 결제 화면의 초기 설정
    public PaymentScreen() {
        initializeUI();
    }

    // UI 초기화 메서드
    private void initializeUI() {
        setTitle("결제 화면"); // 프레임의 제목 설정
        setSize(450, 300); // 프레임의 크기 설정
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 프로그램 종료 설정

        // 결제 다이얼로그 생성
        createPaymentDialog();

        // 헤더 패널 생성
        JPanel headerPanel = createHeaderPanel();
        // 버튼 패널 생성
        JPanel buttonPanel = createButtonPanel();

        // 헤더 패널을 프레임의 상단에 추가
        add(headerPanel, BorderLayout.NORTH);
        // 버튼 패널을 프레임의 중앙에 추가
        add(buttonPanel, BorderLayout.CENTER);

        // 프레임 크기 자동 조정
        pack();
        // 화면 중앙에 배치
        setLocationRelativeTo(null);
        // 프레임 표시
        setVisible(true);
    }

    // 결제 다이얼로그 생성 메서드
    private void createPaymentDialog() {
        paymentDialog = new JDialog(this, "결제 진행 상태", true);
        paymentDialog.setSize(300, 200); // 다이얼로그 크기 설정
        paymentDialog.setLayout(new BorderLayout()); // 레이아웃 설정
        paymentDialog.setLocationRelativeTo(this); // 다이얼로그를 프레임 중앙에 배치
    }

    // 헤더 패널 생성하는 메서드
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(); // 새로운 패널 생성
        JLabel headerLabel = new JLabel("결제 방법을 선택하세요"); // 라벨 생성
        setCustomFont(headerLabel, 35f); // 라벨에 사용할 폰트 설정
        headerPanel.add(headerLabel); // 헤더 패널에 라벨 추가
        headerPanel.setBackground(Color.WHITE); // 배경색 설정
        return headerPanel; // 생성된 패널 반환
    }

    // 버튼 패널 생성하는 메서드
    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 0)); // 새로운 그리드 레이아웃 패널 생성
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // 여백 추가
        cardPaymentButton = createButton("카드 결제", "credit-card_4021708.png", new Color(255, 227, 243)); // 카드 결제 버튼 생성
        cashPaymentButton = createButton("현금 결제", "loan_2140373.png", new Color(208, 237, 255)); // 현금 결제 버튼 생성
        buttonPanel.add(cardPaymentButton); // 카드 결제 버튼을 버튼 패널에 추가
        buttonPanel.add(cashPaymentButton); // 현금 결제 버튼을 버튼 패널에 추가
        buttonPanel.setBackground(Color.WHITE); // 배경색 설정
        return buttonPanel; // 생성된 패널 반환
    }

    // 버튼을 생성하는 메서드
    private JButton createButton(String text, String imagePath, Color backgroundColor) {
        JButton button;
        try {
            BufferedImage img = ImageIO.read(new File("C:/Users/user/Desktop/네이버클라우드캠프/프로젝트 3.25~4.22/img/" + imagePath)); // 이미지 파일 읽기
            Image scaledImg = img.getScaledInstance(200, 200, Image.SCALE_SMOOTH); // 이미지 크기 조정
            ImageIcon icon = new ImageIcon(scaledImg); // 이미지 아이콘 생성
            button = new JButton(text, icon); // 텍스트와 아이콘을 가진 버튼 생성
        } catch (Exception ex) {
            ex.printStackTrace();
            button = new JButton(text); // 이미지 파일이 없는 경우 텍스트만 있는 버튼 생성
        }
        button.setBackground(backgroundColor); // 배경색 설정
        button.setVerticalTextPosition(SwingConstants.BOTTOM); // 텍스트 위치 설정
        button.setHorizontalTextPosition(SwingConstants.CENTER); // 텍스트 위치 설정
        setCustomFont(button, 25f); // 버튼에 사용할 폰트 설정
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPaymentStatus(text); // 버튼 클릭 시 결제 상태 표시
            }
        });
        return button; // 생성된 버튼 반환
    }

    // 결제 진행 상태를 표시하는 메서드
    private void showPaymentStatus(String paymentType) {
        // 기존의 모든 컴포넌트를 제거합니다.
        paymentDialog.getContentPane().removeAll();
        paymentDialog.getContentPane().setBackground(Color.WHITE);

        // 상태 라벨 생성 및 설정
        JLabel statusLabel = new JLabel("결제 진행 중...", SwingConstants.CENTER);
        setCustomFont(statusLabel, Font.BOLD, 25);
        paymentDialog.add(statusLabel, BorderLayout.CENTER);

        // 결제 방법 라벨 생성 및 설정
        JLabel paymentTypeLabel = new JLabel("결제 방법: " + paymentType, SwingConstants.CENTER);
        setCustomFont(paymentTypeLabel, Font.PLAIN, 20);
        paymentTypeLabel.setForeground(Color.ORANGE); // 전경색 설정
        paymentDialog.add(paymentTypeLabel, BorderLayout.NORTH);

        // 5초 후에 다이얼로그를 닫기 위한 스레드 생성 및 실행
        Thread dialogCloseThread = new Thread(() -> {
            try {
                Thread.sleep(5000); // 5초 대기
                SwingUtilities.invokeLater(() -> {
                    paymentDialog.setVisible(false); // 다이얼로그를 닫습니다.
                });
            } catch (InterruptedException e) {
                // 스레드가 중지될 때 발생하는 예외입니다.
                // 이 예외가 발생하면 다이얼로그를 닫습니다만, 여기서는 아무런 작업도 하지 않습니다.
            }
        });
        dialogCloseThread.start(); // 스레드 실행

        // 다이얼로그 종료 이벤트 처리
        paymentDialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (dialogCloseThread != null && dialogCloseThread.isAlive()) {
                    dialogCloseThread.interrupt(); // 스레드 중지
                }
            }
        });

        paymentDialog.revalidate(); // 다이얼로그를 다시 그리도록 합니다.
        paymentDialog.setVisible(true); // 다이얼로그 표시
    }

    // 컴포넌트에 사용할 사용자 지정 폰트 설정
    private void setCustomFont(Component component, float fontSize) {
        setCustomFont(component, Font.PLAIN, fontSize); // 기본 스타일로 설정
    }

    // 컴포넌트에 사용할 사용자 지정 폰트 설정 (스타일 포함)
    private void setCustomFont(Component component, int style, float fontSize) {
        try {
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File(fontPath)).deriveFont(style, fontSize); // 사용자 지정 폰트 생성
            component.setFont(customFont); // 폰트 설정
        } catch (Exception ex) {
            ex.printStackTrace();
            component.setFont(new Font("Arial", style, (int) fontSize)); // 예외 발생 시 기본 폰트로 설정
        }
    }

    // 메인 메서드: 프로그램 실행 진입점
    public static void main(String[] args) {
        SwingUtilities.invokeLater(PaymentScreen::new); // GUI 스레드에서 프레임 생성
    }
}