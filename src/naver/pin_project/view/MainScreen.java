
package src.naver.pin_project.view;

import src.naver.pin_project.data.Ranking;
import src.naver.pin_project.data.User;
import src.naver.pin_project.game_feature.GameMenu;
import src.naver.pin_project.viewmodel.Ranking_ViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.List;

import src.naver.pin_project.data.Food;
import src.naver.pin_project.db.DBHelper;
import src.naver.pin_project.db.OjdbcConnection;

public class MainScreen extends JPanel {
    private User loggedInUser;
    private FoodOrderScreen foodOrderScreen; // FoodOrderScreen 객체 선언
    private Map<Food, Integer> selectedFoods; // 선택한 음식과 수량을 저장할 Map
    private int orderNumber; // 주문번호를 저장할 변수
    private Timestamp orderTime; // 주문 시간을 저장할 변수

    private User user;
    public MainScreen(CardLayout cardLayout, User loggedInUser, JPanel cardPanel){

        this.loggedInUser = loggedInUser;
        this.selectedFoods = new HashMap<>();
        this.orderNumber = -1;
        setLayout(new BorderLayout());

        // 좌측 상단 버튼 3개 1열로
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        JButton callbtn = new JButton("직원 호출");
        JButton rankbtn = new JButton("랭킹");
        JButton myrecordbtn = new JButton("내 기록");
        JButton gameStartbtn = new JButton("볼링");
        buttonPanel.add(callbtn);
        buttonPanel.add(rankbtn);
        buttonPanel.add(myrecordbtn);

        // 버튼 사이즈 조절, 텍스트 중앙 정렬
        Dimension buttonSize = new Dimension(150, 40);
        callbtn.setPreferredSize(buttonSize);
        rankbtn.setPreferredSize(buttonSize);
        myrecordbtn.setPreferredSize(buttonSize);
        gameStartbtn.setPreferredSize(buttonSize);

        callbtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        rankbtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        myrecordbtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        gameStartbtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        callbtn.setMaximumSize(buttonSize);
        rankbtn.setMaximumSize(buttonSize);
        myrecordbtn.setMaximumSize(buttonSize);
        gameStartbtn.setMaximumSize(buttonSize);

        callbtn.setHorizontalAlignment(SwingConstants.CENTER);
        rankbtn.setHorizontalAlignment(SwingConstants.CENTER);
        myrecordbtn.setHorizontalAlignment(SwingConstants.CENTER);
        gameStartbtn.setHorizontalAlignment(SwingConstants.CENTER);

        // 버튼 사이의 간격 조절을 위해 패널에 BorderLayout 적용
        buttonPanel.add(Box.createVerticalStrut(10)); // 첫 번째 버튼 위에 간격 추가
        buttonPanel.add(callbtn);
        buttonPanel.add(Box.createVerticalStrut(10)); // 버튼 사이에 간격 추가
        buttonPanel.add(rankbtn);
        buttonPanel.add(Box.createVerticalStrut(10)); // 버튼 사이에 간격 추가
        buttonPanel.add(myrecordbtn);
        buttonPanel.add(Box.createVerticalStrut(10)); // 마지막 버튼 아래에 간격 추가
        buttonPanel.add(gameStartbtn);
        buttonPanel.add(Box.createVerticalStrut(10));


        add(buttonPanel, BorderLayout.WEST);

        foodOrderScreen = new FoodOrderScreen(450, 500); // Example width and height
        add(foodOrderScreen, BorderLayout.CENTER);
        foodOrderScreen.setLocation(83, 0); // Example coordinates


        // 중앙 패널 => 여기엔 테이블이 들어가야함
        JPanel centerPanel = new JPanel();
        add(centerPanel, BorderLayout.CENTER);

        // 우측 하단 버튼 2개(주문내역, 장바구니)
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton orderlistbtn = new JButton("주문내역");
        JButton cartbtn = new JButton("장바구니");
        bottomPanel.add(orderlistbtn);
        bottomPanel.add(cartbtn);

        // 버튼 크기 조절, 텍스트 중앙정렬
        orderlistbtn.setPreferredSize(buttonSize);
        cartbtn.setPreferredSize(buttonSize);
        orderlistbtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        cartbtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(bottomPanel, BorderLayout.SOUTH);

        // 프로필 이미지(터치 가능) -> 터치하면 개인정보 수정으로
        ImageIcon profileIcon = new ImageIcon("src/naver/pin_project/lib/img.png");
        JLabel profileLabel = new JLabel(profileIcon);
        profileLabel.setToolTipText("프로필 보기"); // 마우스 오버시 툴팁 설정
        // 사용자 이름 라벨
        JLabel nameLabel = new JLabel(loggedInUser.getUserName());
        // 프로필 판넬
        JPanel profilePanel = new JPanel();
        profilePanel.setLayout(new BorderLayout());
        profilePanel.add(profileLabel, BorderLayout.NORTH);
        profilePanel.add(nameLabel, BorderLayout.EAST);
        // 전체 판넬의 프로필 판넬 추가
        add(profilePanel, BorderLayout.EAST);

        // 장바구니 버튼 이벤트 처리
        cartbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedFoods.putAll(foodOrderScreen.getSelectedFoods());
                if (orderNumber == -1) { // 주문번호가 없는 경우
                    orderNumber = generateRandomNumber(); // 새로운 주문번호 생성
                    orderTime = new Timestamp(System.currentTimeMillis()); // 현재 시간(초까지 포함)으로 주문 시간 생성
                }
                addToCart(orderNumber, orderTime); // 장바구니 버튼 클릭 시 addToCart 메서드 호출
                foodOrderScreen.displayShoppingCart();
            }
        });


        // 개인정보 클릭 이벤트
        profileLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cardPanel.add(new MyPageScreen(cardLayout, loggedInUser, cardPanel),"mypage");
                cardLayout.show(cardPanel, "mypage");
            }
        });

        // 직원 호출 화면 연결
        callbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                cardPanel.add(new StaffCallScreen(MainScreen.this),"staffcall");
                cardLayout.show(cardPanel,"staffcall");

                System.out.println("직원 호출 화면 연결");
            }
        });
        //수정완료


        // 랭킹 다이알로그 창 띄우기
        rankbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(() -> {
                    List<List<Ranking>> rankingData = Ranking_ViewModel.getRankingData(); // 실시간 및 이달의 랭킹 정보를 가져옴
                    List<Ranking> realTimeRankingList = rankingData.get(0); // 실시간 랭킹 리스트
                    List<Ranking> monthlyRankingList = rankingData.get(1); // 이달의 랭킹 리스트

                    RankingScreen rankingScreen = new RankingScreen(realTimeRankingList, monthlyRankingList); // 랭킹스크린 객체 생성
                    rankingScreen.setVisible(true); // 랭킹스크린을 화면에 표시
                });

            }
        });

        // 내 기록 다이얼로그 창 띄우기
        myrecordbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("내 기록 다이얼로그 창 띄우기");
            }
        });

        // 주문내역 화면 연결
        gameStartbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("게임 시작");
                new GameMenu().setVisible(true);
            }
        });

        //주문내역 화면 연결
        orderlistbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("주문내역 화면 연결");
            }
        });
// Inside the constructor of MainScreen class

        /*cartbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Call the method to display the shopping cart
                foodOrderScreen.displayShoppingCart();
            }
        });*/
    }

    private void addToCart(int orderNumber, Timestamp orderTime) {
        Connection conn = null;
        try {
            conn = OjdbcConnection.getConnection();
            for (Map.Entry<Food, Integer> entry : selectedFoods.entrySet()) {
                Food food = entry.getKey();
                int quantity = entry.getValue();
                if (quantity > 0) {
                    DBHelper.addToCart(conn, food.getFood_name(), quantity, food.getFood_price(), orderNumber, orderTime);
                }
            }
            JOptionPane.showMessageDialog(this, "장바구니에 추가되었습니다.");
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    private int generateRandomNumber() {
        // 1억 범위 내에서 랜덤한 정수 생성
        Random random = new Random();
        return random.nextInt(90000000) + 10000000;
    }


}
