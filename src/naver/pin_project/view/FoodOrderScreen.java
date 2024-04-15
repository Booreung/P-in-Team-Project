package src.naver.pin_project.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import src.naver.pin_project.data.CustomFont;
import src.naver.pin_project.data.Food;
import src.naver.pin_project.viewmodel.FoodOrder_ViewModel;

// FoodOrderScreen 클래스는 음식 주문 화면을 관리하는 클래스입니다. (키워드: 음식 주문 화면)
public class FoodOrderScreen extends JPanel {
    // 총 금액을 표시할 텍스트 필드 (키워드: 총 금액)
    private JTextField totalTextField;

    // 카드 레이아웃과 카드 패널 (키워드: 카드 레이아웃, 카드 패널)
    private CardLayout cardLayout;
    private JPanel cardPanel;

    // 선택된 음식과 수량을 저장하는 Map (키워드: 선택된 음식, 수량)
    private Map<Food, Integer> selectedFoods;

    // 주문 번호 (키워드: 주문 번호)
    private int orderNumber;

    // 메인 패널 (키워드: 메인 패널)
    private JPanel mainPanel;

    // FoodOrderScreen 클래스의 생성자입니다. (키워드: 생성자)
    public FoodOrderScreen(int width, int height) {

        // 카드 레이아웃과 카드 패널 초기화 (키워드: 초기화)


        // 폰트 파일 경로
        String fontPath = "src/naver/pin_project/lib/온글잎밑미.ttf";
        // 원하는 폰트 크기로 폰트 로드
        Font customFont = CustomFont.loadFont(fontPath, 15f);
        // UI에 폰트 적용
        CustomFont.setUIFont(customFont);


        this.cardLayout = new CardLayout();
        this.cardPanel = new JPanel(cardLayout);

        // 선택된 음식과 수량을 저장하는 Map 초기화 (키워드: 초기화)
        this.selectedFoods = new HashMap<>();

        // 주문 번호 초기화 (키워드: 초기화)
        this.orderNumber = -1;
        // 배경색 설정 (키워드: 배경색)
        setBackground(Color.decode("#8A8585"));

        try {
            // 데이터베이스에서 음식 목록 가져오기 (키워드: 음식 목록)
            List<Food> foodList;
            // FoodOrder_ViewModel 객체를 생성합니다. (키워드: ViewModel)
            FoodOrder_ViewModel viewModel = new FoodOrder_ViewModel();

            // viewModel의 getFoodMenu() 메서드를 호출하여 음식 목록을 가져옵니다.
            // getFoodMenu() 메서드는 데이터베이스에 접근하여 모든 음식의 정보를 가져와 List<Food>로 반환합니다.
            // 이렇게 가져온 음식 목록은 foodList 변수에 저장됩니다. (키워드: 음식 목록)
            foodList = viewModel.getFoodMenu();

            // 메인 패널 생성 및 레이아웃 설정 (키워드: 메인 패널)
            mainPanel = new JPanel(new GridLayout(0, 3, 10, 10));

            // 음식 목록 표시 (키워드: 음식 목록)
            for (Food food : foodList) {
                JPanel foodPanel = new JPanel(new BorderLayout());
                foodPanel.setBackground(Color.decode("#8A8585"));

                // 이미지 표시 (키워드: 이미지)
                // 음식 이미지 경로를 가져옵니다.
                String imagePath = food.getFood_image();
                // 만약 이미지 경로가 null이 아니면, 이미지를 로드하고 버튼에 설정합니다.
                if (imagePath != null) {
                    // "src/naver/pin_project/lib/" 경로에 있는 이미지를 아이콘으로 만듭니다.
                    ImageIcon icon = new ImageIcon("src/naver/pin_project/lib/" + imagePath);
                    // 아이콘을 이미지로 변환하고, 크기를 100x100으로 조정합니다.
                    Image image = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                    // 조정한 이미지를 아이콘으로 만든 후, 이를 버튼에 설정합니다.
                    JButton button = new JButton(new ImageIcon(image));
                    // 버튼의 배경색을 흰색으로 설정합니다.
                    button.setBackground(Color.white);
                    // 생성한 버튼을 foodPanel의 중앙에 추가합니다.
                    foodPanel.add(button, BorderLayout.CENTER);


                    // 수량 조절 버튼 및 라벨 생성 (키워드: 수량 조절)
                    // FlowLayout으로 수량 조절 버튼 및 라벨을 담을 패널을 생성합니다.
                    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                    // 패널의 배경색을 설정합니다.
                    buttonPanel.setBackground(Color.decode("#8A8585"));
                    // 수량을 표시할 라벨을 생성하고, 초기값을 "0"으로 설정합니다.
                    JLabel quantityLabel = new JLabel("0");
                    // 라벨의 글자색을 흰색으로 설정합니다.
                    quantityLabel.setForeground(Color.white);
                    // 라벨의 정렬 방향을 중앙으로 설정합니다.
                    quantityLabel.setHorizontalAlignment(SwingConstants.CENTER);

                    // "+" 버튼을 생성하고, 배경색을 설정합니다.
                    JButton addButton = new JButton("+");
                    addButton.setBackground(Color.decode("#B0FFA9"));
                    // "+" 버튼이 눌렸을 때 수량을 1 증가시키는 리스너를 추가합니다.
                    addButton.addActionListener(new QuantityButtonListener(food, quantityLabel, new JLabel(), 1));

                    // "-" 버튼을 생성하고, 배경색을 설정합니다.
                    JButton subtractButton = new JButton("-");
                    subtractButton.setBackground(Color.decode("#FF66CC"));
                    // "-" 버튼이 눌렸을 때 수량을 1 감소시키는 리스너를 추가합니다.
                    subtractButton.addActionListener(new QuantityButtonListener(food, quantityLabel, new JLabel(), -1));

                    // 패널에 "+" 버튼, 수량 라벨, "-" 버튼을 추가합니다.
                    buttonPanel.add(addButton);
                    buttonPanel.add(quantityLabel);
                    buttonPanel.add(subtractButton);

                    // 재고 라벨 생성 (키워드: 재고)
                    // 재고 라벨을 null로 초기화합니다.
                    JLabel stockLabel = null;
                    // 만약 해당 음식의 재고가 없다면,
                    if (!food.isFood_etc()) {
                        // "재고 없음"이라는 글자를 가진 라벨을 생성하고,
                        stockLabel = new JLabel("재고 없음");
                        // 라벨의 글자색을 흰색으로 설정하며,
                        stockLabel.setForeground(Color.white);
                        // 패널에 재고 라벨을 추가합니다.
                        buttonPanel.add(stockLabel);
                    }

                    // 음식 패널의 글자색을 흰색으로 설정합니다.
                    foodPanel.setForeground(Color.white);
                    // 음식 패널에 수량 조절 버튼 및 라벨이 들어있는 패널을 남쪽에 추가합니다.
                    foodPanel.add(buttonPanel, BorderLayout.SOUTH);
                    // 만약 음식 이미지의 경로가 null이라면,
                } else {
                    // 에러 메시지를 출력합니다.
                    System.out.println("Error: Image path is null");
                }

                // 음식 이름과 가격을 라벨에 표시합니다. (키워드: 음식 이름, 가격)
                JLabel label = new JLabel("▶" + food.getFood_name() + "▶ " + food.getFood_price() + " 원");
                // 라벨의 글자색을 흰색으로 설정합니다.
                label.setForeground(Color.white);
                // 라벨의 정렬 방향을 중앙으로 설정합니다.
                label.setHorizontalAlignment(SwingConstants.CENTER);
                // 라벨을 foodPanel의 상단에 추가합니다.
                foodPanel.add(label, BorderLayout.NORTH);
                // 메인 패널의 배경색을 설정합니다.
                mainPanel.setBackground(Color.decode("#8A8585"));
                // foodPanel을 메인 패널에 추가합니다.
                mainPanel.add(foodPanel);
            }

            // 메인 패널을 스크롤 팬에 추가합니다. (키워드: 메인 패널)
            JScrollPane scrollPane = new JScrollPane(mainPanel);
            // 스크롤 팬의 크기를 설정합니다.
            scrollPane.setPreferredSize(new Dimension(515, 400));
            // 스크롤 팬을 이 패널에 추가합니다.
            add(scrollPane);

            // 총 주문 금액을 표시하는 텍스트 필드를 생성합니다. (키워드: 총 주문 금액)
            totalTextField = new JTextField("◆ 총: 0 원 ◆");
            // 텍스트 필드의 크기를 설정합니다.
            totalTextField.setPreferredSize(new Dimension(200, 20));

            // 텍스트 필드의 정렬 방향을 중앙으로 설정합니다.
            totalTextField.setHorizontalAlignment(JTextField.CENTER);
            // 텍스트 필드를 이 패널의 상단에 추가합니다.

            totalTextField.setEditable(false);
            totalTextField.setFocusable(false);
            totalTextField.setHorizontalAlignment(JTextField.CENTER);
            totalTextField.setEditable(false); // 텍스트 필드를 읽기 전용으로 설정
            totalTextField.setFocusable(false); // 포커스 설정 방지

            add(totalTextField, BorderLayout.NORTH);

        // SQL 예외가 발생하면 적절하게 처리합니다.
        }catch(SQLException e){
                e.printStackTrace();
            }

            // 패널의 크기를 설정합니다. (키워드: 크기)
            setPreferredSize(new Dimension(width, height));
            setSize(650, 430);
        }

        // 선택한 음식과 그 수량을 Map 형태로 반환하는 메서드입니다. (키워드: 반환)
        public Map<Food, Integer> getSelectedFoods () {
            return selectedFoods;
        }


        // resetQuantityLabels 메서드는 모든 음식 항목의 수량 라벨을 0으로 설정하고 선택된 음식 목록을 클리어하는 메서드입니다.
        public void resetQuantityLabels () {
            // 메인 패널의 모든 컴포넌트를 반복합니다.
            for (Component component : mainPanel.getComponents()) {
                // 컴포넌트가 JPanel인 경우만 처리합니다.
                if (component instanceof JPanel) {
                    JPanel foodPanel = (JPanel) component;
                    // 음식 패널의 모든 내부 컴포넌트를 반복합니다.
                    for (Component innerComponent : foodPanel.getComponents()) {
                        // 내부 컴포넌트가 JPanel인 경우만 처리합니다.
                        if (innerComponent instanceof JPanel) {
                            JPanel buttonPanel = (JPanel) innerComponent;
                            // 버튼 패널의 모든 컴포넌트를 반복합니다.
                            for (Component buttonComponent : buttonPanel.getComponents()) {
                                // 컴포넌트가 JLabel인 경우만 처리합니다.
                                if (buttonComponent instanceof JLabel) {
                                    JLabel label = (JLabel) buttonComponent;
                                    // 라벨의 텍스트 색상을 흰색으로 설정합니다.
                                    label.setForeground(Color.white);
                                    // 라벨의 텍스트를 "0"으로 설정합니다.
                                    label.setText("0");
                                }
                            }
                        }
                    }
                }
            }
            // 선택된 음식 목록을 클리어합니다.
            selectedFoods.clear();
            // 총 주문 금액을 업데이트합니다.
            updateTotal();
        }


        // 장바구니 화면을 표시하는 메서드 (키워드: 장바구니)
        public void displayShoppingCart () {
            // 선택된 음식들에 대한 정보를 가지고 있는 CartScreen 객체를 생성합니다. (키워드: CartScreen 객체 생성)
            CartScreen cartScreen = new CartScreen(selectedFoods);
            // 새로운 프레임을 생성하고, 이 프레임의 제목을 "장바구니"로 설정합니다. (키워드: 프레임 생성)
            JFrame frame = new JFrame("장바구니");
            // 생성한 프레임에 CartScreen 객체를 추가합니다. (키워드: 프레임에 객체 추가)
            frame.add(cartScreen);
            // 프레임의 크기를 내부의 컴포넌트에 맞게 조정합니다. (키워드: 프레임 크기 조정)
            frame.pack();
            // 프레임을 화면에 보이게 합니다. (키워드: 프레임 보이게 하기)
            frame.setVisible(true);
            // 프레임을 화면의 중앙에 위치시킵니다. (키워드: 프레임 위치 설정)
            frame.setLocationRelativeTo(null);

            // 현재 선택된 모든 음식들에 대한 수량 라벨을 기본 값으로 초기화합니다. (키워드: 수량 라벨 초기화)
            resetQuantityLabels();
        }

        // 'QuantityButtonListener' 클래스는 사용자가 "+" 또는 "-" 버튼을 클릭하여 음식의 수량을 조절할 때의 동작을 정의한 ActionListener입니다. (키워드: 리스너)
        private class QuantityButtonListener implements ActionListener {
            private Food food; // 선택된 음식 항목
            private JLabel quantityLabel; // 수량을 표시하는 레이블
            private int quantityChange; // 수량 변경량 (증가 또는 감소)
            private JLabel stockLabel; // 재고 상태를 표시하는 레이블

            // 'QuantityButtonListener' 클래스의 생성자입니다. 선택된 음식 항목, 수량 레이블, 재고 레이블, 수량 변경량을 매개변수로 받습니다. (키워드: 생성자)
            public QuantityButtonListener(Food food, JLabel quantityLabel, JLabel stockLabel, int quantityChange) {
                this.food = food; // 선택된 음식 항목을 설정합니다.
                this.quantityLabel = quantityLabel; // 수량 레이블을 설정합니다.
                this.stockLabel = stockLabel; // 재고 레이블을 설정합니다.
                this.quantityChange = quantityChange; // 수량 변경량을 설정합니다.
            }

            @Override
            // 이 메서드는 "+" 또는 "-" 버튼이 클릭될 때 실행되는 액션 리스너입니다.
            public void actionPerformed(ActionEvent e) {
                if (food.isFood_etc()) { // 재고가 있을 경우
                    // 현재 표시된 수량을 가져와서 정수로 변환합니다.
                    int quantity = Integer.parseInt(quantityLabel.getText());
                    // 수량을 증가 또는 감소시킵니다. (quantityChange 값이 +1이면 증가, -1이면 감소)
                    quantity += quantityChange;
                    // 수량이 0 미만이면 0으로 설정합니다.
                    if (quantity < 0) {
                        quantity = 0;
                    }
                    // 변경된 수량을 라벨에 다시 표시합니다.
                    quantityLabel.setText(String.valueOf(quantity));
                    // 선택된 음식과 그 수량을 맵에 저장합니다.
                    selectedFoods.put(food, quantity);
                    // 총 금액을 업데이트합니다.
                    updateTotal();
                } else { // 재고가 없을 경우
                    // "재고 없음" 텍스트를 재고 라벨에 표시합니다.
                    stockLabel.setText("재고 없음");
                }
            }
        }



        // 이 메서드는 선택된 음식의 총 주문 금액을 계산하고 그 값을 텍스트 필드에 표시하는 역할을 합니다. (키워드: 총 주문 금액, 계산)
        private void updateTotal() {
            int total = 0; // 총 주문 금액을 저장할 변수를 선언합니다.
            // 선택된 음식의 Map에 대해 반복하여 각 음식의 가격과 선택된 수량을 곱한 값을 총 주문 금액에 더합니다.
            for (Map.Entry<Food, Integer> entry : selectedFoods.entrySet()) {
                Food food = entry.getKey(); // 현재 음식을 가져옵니다.
                int quantity = entry.getValue(); // 현재 음식의 수량을 가져옵니다.
                total += food.getFood_price() * quantity; // 현재 음식의 가격과 수량을 곱한 값을 총 주문 금액에 더합니다.
            }
            // 계산된 총 주문 금액을 텍스트 필드에 표시합니다.
            totalTextField.setText("◆ 총: " + total + " 원 ◆");
        }
    }


