package src.naver.pin_project.view;

import src.naver.pin_project.data.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainScreen extends JPanel {
    private User loggedInUser;
    private User user;
    public MainScreen(User loggedInUser){
        this.loggedInUser = loggedInUser;
        //좌측 상단 버튼 3개 1열로
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        JButton callbtn = new JButton("직원 호출");
        JButton rankbtn = new JButton("랭킹");
        JButton myrecordbtn = new JButton("내 기록");
        buttonPanel.add(callbtn);
        buttonPanel.add(rankbtn);
        buttonPanel.add(myrecordbtn);

        //버튼 사이즈 조절, 텍스트 중앙 정렬
        Dimension buttonSize = new Dimension(150, 40);
        callbtn.setMaximumSize(buttonSize);
        callbtn.setHorizontalAlignment(SwingConstants.CENTER);
        rankbtn.setMaximumSize(buttonSize);
        rankbtn.setHorizontalAlignment(SwingConstants.CENTER);
        myrecordbtn.setMaximumSize(buttonSize);
        myrecordbtn.setHorizontalAlignment(SwingConstants.CENTER);

        // 버튼 사이의 간격 조절을 위해 패널에 BorderLayout 적용
        buttonPanel.add(Box.createVerticalStrut(10)); // 첫 번째 버튼 위에 간격 추가
        buttonPanel.add(callbtn);
        buttonPanel.add(Box.createVerticalStrut(10)); // 버튼 사이에 간격 추가
        buttonPanel.add(rankbtn);
        buttonPanel.add(Box.createVerticalStrut(10)); // 버튼 사이에 간격 추가
        buttonPanel.add(myrecordbtn);
        buttonPanel.add(Box.createVerticalStrut(10)); // 마지막 버튼 아래에 간격 추가


        add(buttonPanel, BorderLayout.WEST);

        //중앙 패널 => 여기엔 테이블이 들어가야함
        //예준이형 화이팅


        //우측 하단 버튼 2개(주문내역, 장바구니)
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton orderlistbtn = new JButton("주문내역");
        JButton cartbtn = new JButton("장바구니");
        bottomPanel.add(orderlistbtn);
        bottomPanel.add(cartbtn);

        //버튼 크기 조절 , 텍스트 중앙정렬
        orderlistbtn.setMaximumSize(buttonSize);
        orderlistbtn.setHorizontalAlignment(SwingConstants.CENTER);
        cartbtn.setMaximumSize(buttonSize);
        cartbtn.setHorizontalAlignment(SwingConstants.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        //데이터 전달 확인용 테스터
        JLabel testLabel = new JLabel("사용자 정보: " + loggedInUser.getUserId() + " (" + loggedInUser.getUserName() + ")");
        testLabel.setFont(testLabel.getFont().deriveFont(Font.BOLD, 16));
        add(testLabel, BorderLayout.NORTH);

        //프로필 이미지(터치가능) -> 터치하면 개인정보 수정으로
        ImageIcon profileIcon = new ImageIcon();
        JLabel profileLabel = new JLabel(profileIcon);
        profileLabel.setToolTipText("프로필 보기"); // 마우스 오버시 툴팁 설정

        //개인정보 클릭이벤트
        profileLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("프로필 이미지 클릭");
            }
        });

        //직원 호출 화면 연결
        callbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("직원 호출 화면 연결");
            }
        });

        //랭킹 다이알로그 창 띄우기
        rankbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("랭킹 다이알로그 창 띄우기");
            }
        });

        //내 기록 다이알로그 창 띄우기
        myrecordbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("내 기록 다이알로그 창 띄우기");
            }
        });

        //주문내역 화면 연결
        orderlistbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("주문내역 화면 연결");
            }
        });

        //장바구니 화면 연결
        cartbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("장바구니 화면 연결");
            }
        });

    }

}
