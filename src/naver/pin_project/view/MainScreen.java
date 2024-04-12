package src.naver.pin_project.view;



import src.naver.pin_project.data.Food;
import src.naver.pin_project.data.OrderInfo;
import src.naver.pin_project.data.Ranking;
import src.naver.pin_project.data.User;
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
        //로그인한 사용자의 정보를 위해
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

        //버튼의 배경색 추가
        callbtn.setBackground(Color.decode("#B0FFA9"));
        rankbtn.setBackground(Color.decode("#B0FFA9"));
        myrecordbtn.setBackground(Color.decode("#B0FFA9"));
        gameStartbtn.setBackground(Color.decode("#B0FFA9"));

        //버튼의 사이즈 조절
        Dimension buttonSize = new Dimension(150, 40);
        callbtn.setPreferredSize(buttonSize);
        rankbtn.setPreferredSize(buttonSize);
        myrecordbtn.setPreferredSize(buttonSize);
        gameStartbtn.setPreferredSize(buttonSize);

        //컴포넌트의 중앙정렬(가로축으로)
        callbtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        rankbtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        myrecordbtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        gameStartbtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        //버튼의 사이즈 조절
        callbtn.setMaximumSize(buttonSize);
        rankbtn.setMaximumSize(buttonSize);
        myrecordbtn.setMaximumSize(buttonSize);
        gameStartbtn.setMaximumSize(buttonSize);

        //버튼의 수직 중앙정렬
        callbtn.setHorizontalAlignment(SwingConstants.CENTER);
        rankbtn.setHorizontalAlignment(SwingConstants.CENTER);
        myrecordbtn.setHorizontalAlignment(SwingConstants.CENTER);
        gameStartbtn.setHorizontalAlignment(SwingConstants.CENTER);

        //버튼을 패널에 추가할때 각각 여백을 두기위한 작업
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

        //주문내역, 장바구니 버튼의 생성
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton orderlistbtn = new JButton("주문내역");
        orderlistbtn.setBackground(Color.decode("#FCEB83"));
        JButton cartbtn = new JButton("장바구니");
        cartbtn.setBackground(Color.decode("#8DFFF3"));
        bottomPanel.add(orderlistbtn);
        bottomPanel.add(cartbtn);
        bottomPanel.setBackground(Color.decode("#8A8585"));

        //버튼의 사이즈 조절 및 중앙정렬
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

        //BorderLayout 특성상 동서남북, 가운데에 패널이 위치하게 된다
        //우리 프로젝트에서는 서쪽 패널에 위 아래에 각각 버튼패널, 프로필패널이 들어가야하기 때문에
        //서쪽 패널을 2행 1열의 느낌으로 설정
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

        //스크린에 있는 버튼마다 이벤트 리스너

        //장바구니 화면 연결 : 푸드오더 스크린에서의 정보를 넘김
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

        //마이페이지 화면 연결
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

        //랭킹 화면 연결
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

        //내 기록 화면 연결
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


        // 볼링 게임 화면 연결
        gameStartbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("게임 시작");
                GameMenu gameMenu = new GameMenu();
                gameMenu.setVisible(true);
            }
        });

        //주문내역 화면 연결
        orderlistbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 주문 내역 화면 연결
                new OrderListScreen(ordered_list);
            }
        });
    }



    private void addToCart() {
        Connection conn = null;
        int newOrderNumber = generateRandomNumber();
        Timestamp newOrderTime = new Timestamp(System.currentTimeMillis());

        try {
            conn = OjdbcConnection.getConnection();
            for (Map.Entry<Food, Integer> entry : selectedFoods.entrySet()) {
                Food food = entry.getKey();
                int quantity = entry.getValue();
                if (quantity > 0) {
                    DBHelper.addToCart(conn, food.getFood_name(), quantity, food.getFood_price(), newOrderNumber, newOrderTime);
                    ordered_list.add(new OrderInfo(newOrderTime, newOrderNumber, food.getFood_name(), food.getFood_price(), quantity));
                }
            }
            JOptionPane.showMessageDialog(this, "장바구니에 추가되었습니다.");
            selectedFoods.clear();

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
        Random random = new Random();
        return random.nextInt(90000000) + 10000000;
    }


}
