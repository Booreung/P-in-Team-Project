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
        orderlistbtn.setFont(orderlistbtn.getFont().deriveFont(Font.PLAIN, 25));
        orderlistbtn.setBackground(Color.decode("#FCEB83"));
        JButton cartbtn = new JButton("장바구니");
        cartbtn.setFont(cartbtn.getFont().deriveFont(Font.PLAIN, 25));
        cartbtn.setBackground(Color.decode("#8DFFF3"));
        bottomPanel.add(orderlistbtn);
        bottomPanel.add(cartbtn);
        bottomPanel.setBackground(Color.decode("#8A8585"));

        orderlistbtn.setPreferredSize(buttonSize);
        cartbtn.setPreferredSize(buttonSize);
        orderlistbtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        cartbtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(bottomPanel, BorderLayout.SOUTH);

        ImageIcon profileIcon = new ImageIcon("src/naver/pin_project/lib/팀로고 1.png");
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

        // 키워드: 장바구니 버튼 액션리스너 추가
        cartbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 키워드: 선택된 음식들을 전체 선택된 음식 리스트에 추가
                selectedFoods.putAll(foodOrderScreen.getSelectedFoods());
                // 키워드: 주문 번호가 아직 생성되지 않았다면, 새로운 주문 번호와 주문 시간 생성
                if (orderNumber == -1) {
                    orderNumber = generateRandomNumber(); // 주문 번호 생성
                    orderTime = new Timestamp(System.currentTimeMillis()); // 주문 시간 생성
                }
                // 키워드: 선택된 음식이 있을 경우 처리
                if(!selectedFoods.entrySet().isEmpty()){
                    int total = 0; // 총 금액을 저장할 변수
                    // 선택된 음식들의 리스트를 순회하며, 각 음식의 가격과 수량을 곱해 총 금액 계산
                    for (Map.Entry<Food, Integer> entry : selectedFoods.entrySet()) {
                        Food food = entry.getKey(); // 음식 정보 가져오기
                        int quantity = entry.getValue(); // 선택된 음식의 수량
                        total += food.getFood_price() * quantity; // 음식 가격과 수량 곱해 총 금액에 더하기
                    }
                    // 키워드: 수량이 0이 아닌 음식이 있는지, 총 금액이 0 이상인지 확인 후 처리
                    if(!selectedFoods.values().contains(0)){
                        addToCart(); // 장바구니에 추가
                        foodOrderScreen.displayShoppingCart(); // 장바구니 화면 표시
                    }
                    else if(total>0){
                        addToCart(); // 장바구니에 추가
                        foodOrderScreen.displayShoppingCart(); // 장바구니 화면 표시
                    }
                    else {
                        // 키워드: 선택된 메뉴가 없는 경우 경고 메시지 출력
                        JOptionPane.showMessageDialog(null, "선택된 메뉴가 없습니다. 메뉴를 선택해주세요", "메뉴선택오류", JOptionPane.WARNING_MESSAGE);
                    }}
                else{
                    // 키워드: 선택된 메뉴가 없는 경우 경고 메시지 출력
                    JOptionPane.showMessageDialog(null, "선택된 메뉴가 없습니다. 메뉴를 선택해주세요", "메뉴선택오류", JOptionPane.WARNING_MESSAGE);
                }
            }
        });


        profileLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {


                if(loggedInUser.getUserName().equals("게스트")){
                    JOptionPane.showMessageDialog(null,"회원가입 후 이용가능합니다.","비회원 접근",JOptionPane.WARNING_MESSAGE);
                }
                else{
                    // MyPageScreen을 JDialog로 생성하여 열기
                    MyPageScreen mypageScreen = new MyPageScreen(cardLayout, loggedInUser, cardPanel);
                    mypageScreen.setVisible(true);
                }

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


        rankbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(() -> {
                    List<List<Ranking>> rankingData = Ranking_ViewModel.getRankingData();
                    List<Ranking> realTimeRankingList = rankingData.get(0);
                    List<Ranking> monthlyRankingList = rankingData.get(1);

                    RankingScreen rankingScreen = new RankingScreen(realTimeRankingList, monthlyRankingList);
                    rankingScreen.setVisible(true);
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



    // 장바구니에 선택한 음식을 추가하는 메서드
    private void addToCart() {
        // 데이터베이스 연결 객체
        Connection conn = null;
        // 주문번호 생성을 위한 랜덤 숫자
        int newOrderNumber = generateRandomNumber();
        // 주문 시간 기록을 위한 타임스탬프
        Timestamp newOrderTime = new Timestamp(System.currentTimeMillis());

        try {
            // 데이터베이스 연결
            conn = OjdbcConnection.getConnection();
            // 선택된 음식에 대해 반복
            for (Map.Entry<Food, Integer> entry : selectedFoods.entrySet()) {
                Food food = entry.getKey();
                int quantity = entry.getValue();
                // 수량이 0보다 큰 경우에만 장바구니에 추가
                if (quantity > 0) {
                    // 장바구니에 추가하는 DBHelper 메서드 호출
                    DBHelper.addToCart(conn, food.getFood_name(), quantity, food.getFood_price(), newOrderNumber, newOrderTime);
                    // 주문 정보를 리스트에 추가
                    ordered_list.add(new OrderInfo(newOrderTime, newOrderNumber, food.getFood_name(), food.getFood_price(), quantity));
                }
            }
            // 장바구니에 추가되었음을 알리는 메시지
            JOptionPane.showMessageDialog(this, "장바구니에 추가되었습니다.");
            // 선택된 음식 목록 클리어
            selectedFoods.clear();

        } catch (SQLException ex) {
            // 오류 발생 시, 오류 내용 출력
            ex.printStackTrace();
        } finally {
            // 데이터베이스 연결이 null이 아니면, 연결 종료
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    // 연결 종료 중 오류 발생 시, 오류 내용 출력
                    ex.printStackTrace();
                }
            }
        }
    }

    // 랜덤 숫자 생성 메서드
    private int generateRandomNumber() {
        // 랜덤 객체 생성
        Random random = new Random();
        // 1억 미만의 랜덤한 숫자 반환
        return random.nextInt(90000000) + 10000000;
    }


}
