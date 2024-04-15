package src.naver.pin_project.view;



import src.naver.pin_project.data.*;
import src.naver.pin_project.db.DBHelper;
import src.naver.pin_project.db.OjdbcConnection;
import src.naver.pin_project.game_feature.GameMenu;
import src.naver.pin_project.game_feature.GameRecord;
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
import java.util.*;
import java.util.List;

public class MainScreen extends JPanel {
    private User loggedInUser;

    private FoodOrderScreen foodOrderScreen;
    private Map<Food, Integer> selectedFoods;
    private int orderNumber;
    private Timestamp orderTime;
    public static String UserName;
    public static String UserID;
    public ArrayList<OrderInfo> ordered_list = new ArrayList<>();

    private User user;

    public MainScreen(CardLayout cardLayout, User loggedInUser, JPanel cardPanel) {

        // 폰트 파일 경로
        String fontPath = "src/naver/pin_project/lib/온글잎밑미.ttf";
        // 원하는 폰트 크기로 폰트 로드
        Font customFont = CustomFont.loadFont(fontPath, 25f);
        // UI에 폰트 적용
        CustomFont.setUIFont(customFont);

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
        buttonPanel.add(gameStartbtn);
        buttonPanel.setBackground(Color.decode("#8A8585"));

        callbtn.setBackground(Color.decode("#B0FFA9"));
        rankbtn.setBackground(Color.decode("#B0FFA9"));
        myrecordbtn.setBackground(Color.decode("#B0FFA9"));
        gameStartbtn.setBackground(Color.decode("#B0FFA9"));

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

        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(callbtn);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(rankbtn);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(myrecordbtn);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(gameStartbtn);
        buttonPanel.add(Box.createVerticalStrut(10));

        // 중앙 패널
        foodOrderScreen = new FoodOrderScreen(450, 500);
        add(foodOrderScreen, BorderLayout.CENTER);
        foodOrderScreen.setLocation(83, 0);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton orderlistbtn = new JButton("주문내역");
        orderlistbtn.setBackground(Color.decode("#FCEB83"));
        orderlistbtn.setFont(orderlistbtn.getFont().deriveFont(25f));
        JButton cartbtn = new JButton("장바구니");
        cartbtn.setBackground(Color.decode("#8DFFF3"));
        cartbtn.setFont(cartbtn.getFont().deriveFont(25f));
        bottomPanel.add(orderlistbtn);
        bottomPanel.add(cartbtn);
        bottomPanel.setBackground(Color.decode("#8A8585"));

        orderlistbtn.setPreferredSize(buttonSize);
        cartbtn.setPreferredSize(buttonSize);
        orderlistbtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        cartbtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(bottomPanel, BorderLayout.SOUTH);

        ImageIcon profileIcon = new ImageIcon("src/naver/pin_project/lib/P-in 팀로고 1.png");
        JLabel profileLabel = new JLabel(profileIcon);

        profileLabel.setToolTipText("프로필 보기"); // 마우스 오버시 툴팁 설정
        // 사용자 이름 라벨
        this.UserName =loggedInUser.getUserName();
        this.UserID = loggedInUser.getUserId();
        JLabel nameLabel = new JLabel(UserName, JLabel.CENTER);
        nameLabel.setForeground(Color.decode("#FFFFFF"));

        nameLabel.setFont(nameLabel.getFont().deriveFont(Font.BOLD, 20));
        JPanel profilePanel = new JPanel();
        profilePanel.setLayout(new BorderLayout());
        profilePanel.add(profileLabel, BorderLayout.CENTER);
        profilePanel.add(nameLabel, BorderLayout.SOUTH);
        profilePanel.setBackground(Color.decode("#8A8585"));

        JPanel westPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTH;
        westPanel.add(buttonPanel, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(150, 0, 0, 0);
        westPanel.add(profilePanel, gbc);
        westPanel.setBackground(Color.decode("#8A8585"));

        add(westPanel, BorderLayout.WEST);

        cartbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedFoods.putAll(foodOrderScreen.getSelectedFoods());
                if (orderNumber == -1) {
                    orderNumber = generateRandomNumber();
                    orderTime = new Timestamp(System.currentTimeMillis());
                }
                addToCart();
                foodOrderScreen.displayShoppingCart();
            }
        });

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

                // StaffCallScreen을 모달 다이얼로그로 열기
                StaffCallScreen staffCallScreen = new StaffCallScreen(MainScreen.this);
                staffCallScreen.setVisible(true);
            }
        });


        rankbtn.addActionListener(new ActionListener() { // rankbtn 버튼에 ActionListener 추가
            @Override
            public void actionPerformed(ActionEvent e) { // ActionListener의 actionPerformed 메서드 재정의
                SwingUtilities.invokeLater(() -> { // 이벤트 디스패치 스레드에서 실행되도록 함
                    // 랭킹 데이터를 가져오는 메서드를 호출하여 실시간 및 월간 랭킹 데이터를 가져옴
                    List<List<Ranking>> rankingData = Ranking_ViewModel.getRankingData();
                    List<Ranking> realTimeRankingList = rankingData.get(0); // 실시간 랭킹 데이터
                    List<Ranking> monthlyRankingList = rankingData.get(1); // 월간 랭킹 데이터

                    // RankingScreen 객체를 생성하여 실시간 및 월간 랭킹 데이터를 전달하여 화면을 생성
                    RankingScreen rankingScreen = new RankingScreen(realTimeRankingList, monthlyRankingList);
                    rankingScreen.setVisible(true); // 랭킹 화면을 표시
                });
            }
        });


        myrecordbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(() -> {
                    GameRecord gameRecord = new GameRecord();
                    gameRecord.setVisible(true);
                    // 호출되지 않더라도 빈 테이블을 보여주기 위해 추가
                    gameRecord.fetchDataFromDatabase();
                });
            }
        });


        // 볼링 게임 시작

        gameStartbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("게임 시작");
                GameMenu gameMenu = new GameMenu();
                gameMenu.setVisible(true);
            }
        });

        orderlistbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 주문 내역 화면 연결
                new OrderListScreen(ordered_list);
            }
        });
    }



    private void addToCart() {
        Connection conn = null; // 데이터베이스 연결을 위한 Connection 객체 초기화
        int newOrderNumber = generateRandomNumber(); // 임의의 주문번호 생성
        Timestamp newOrderTime = new Timestamp(System.currentTimeMillis()); // 현재 시간을 이용해 주문시간 생성

        try {
            conn = OjdbcConnection.getConnection(); // 데이터베이스 연결 획득
            for (Map.Entry<Food, Integer> entry : selectedFoods.entrySet()) { // 선택된 음식 목록을 반복
                Food food = entry.getKey(); // Map에서 키(음식)를 가져옴
                int quantity = entry.getValue(); // Map에서 값(수량)를 가져옴
                if (quantity > 0) { // 수량이 0보다 큰 경우에만 처리
                    // DBHelper를 사용하여 데이터베이스에 카트에 음식을 추가하는 메서드 호출
                    DBHelper.addToCart(conn, food.getFood_name(), quantity, food.getFood_price(), newOrderNumber, newOrderTime);
                    // 주문 목록에 새로운 주문 정보를 추가
                    ordered_list.add(new OrderInfo(newOrderTime, newOrderNumber, food.getFood_name(), food.getFood_price(), quantity));
                }
            }
            // 장바구니에 음식이 추가되었음을 알리는 팝업 메시지 표시
            JOptionPane.showMessageDialog(this, "장바구니에 추가되었습니다.");
            selectedFoods.clear(); // 선택된 음식 목록을 초기화

        } catch (SQLException ex) { // SQL 예외 처리
            ex.printStackTrace(); // 예외 출력
        } finally {
            if (conn != null) { // Connection 객체가 null이 아닌 경우에만 처리
                try {
                    conn.close(); // 데이터베이스 연결 닫기
                } catch (SQLException ex) {
                    ex.printStackTrace(); // 예외 출력
                }
            }
        }
    }


    private int generateRandomNumber() {
        Random random = new Random();
        return random.nextInt(90000000) + 10000000;
    }


}
