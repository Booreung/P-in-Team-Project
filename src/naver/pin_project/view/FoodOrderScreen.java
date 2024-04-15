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

public class FoodOrderScreen extends JPanel {
    private JTextField totalTextField;
    private JTextField totalPriceTextField; // Corrected variable name
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private Map<Food, Integer> selectedFoods;
    private int orderNumber; // 주문번호를 저장할 변수
    private JPanel mainPanel; // Declare mainPanel as a class-level field

    public FoodOrderScreen(int width, int height) {

        // 폰트 파일 경로
        String fontPath = "src/naver/pin_project/lib/온글잎밑미.ttf";
        // 원하는 폰트 크기로 폰트 로드
        Font customFont = CustomFont.loadFont(fontPath, 15f);
        // UI에 폰트 적용
        CustomFont.setUIFont(customFont);

        this.cardLayout = new CardLayout();
        this.cardPanel = new JPanel(cardLayout);
        this.selectedFoods = new HashMap<>();
        this.orderNumber = -1; // 초기값으로 -1 설정
        setBackground(Color.decode("#8A8585"));
        try {
            List<Food> foodList;
            FoodOrder_ViewModel viewModel = new FoodOrder_ViewModel();
            foodList = viewModel.getFoodMenu();
            mainPanel = new JPanel(new GridLayout(0, 3, 10, 10));

            for (Food food : foodList) {
                JPanel foodPanel = new JPanel(new BorderLayout());
                foodPanel.setBackground(Color.decode("#8A8585"));

                String imagePath = food.getFood_image();
                if (imagePath != null) {
                    ImageIcon icon = new ImageIcon("src/naver/pin_project/lib/" + imagePath);
                    Image image = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                    JButton button = new JButton(new ImageIcon(image));
                    button.setBackground(Color.white);
                    foodPanel.add(button, BorderLayout.CENTER);

                    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                    buttonPanel.setBackground(Color.decode("#8A8585"));
                    JLabel quantityLabel = new JLabel("0");
                    quantityLabel .setForeground(Color.white);
                    quantityLabel.setHorizontalAlignment(SwingConstants.CENTER);


                    JButton addButton = new JButton("+");
                    addButton.setBackground(Color.decode("#B0FFA9"));
                    addButton.addActionListener(new QuantityButtonListener(food, quantityLabel, new JLabel(), 1)); // 추가된 행

                    JButton subtractButton = new JButton("-");
                    subtractButton.setBackground(Color.decode("#FF66CC"));
                    subtractButton.addActionListener(new QuantityButtonListener(food, quantityLabel, new JLabel(), -1)); // 추가된 행

                    buttonPanel.add(addButton);
                    buttonPanel.add(quantityLabel);
                    buttonPanel.add(subtractButton);

                    JLabel stockLabel = null;
                    if (!food.isFood_etc()) {
                        stockLabel= new JLabel("재고 없음");
                        stockLabel.setForeground(Color.white);
                        buttonPanel.add(stockLabel);
                    }

                    foodPanel.setForeground(Color.white);
                    foodPanel.add(buttonPanel, BorderLayout.SOUTH);
                } else {
                    System.out.println("Error: Image path is null");
                }

                JLabel label = new JLabel("▶" + food.getFood_name() + "▶ " + food.getFood_price() + " 원");
                label.setForeground(Color.white);
                label.setAlignmentX(Component.CENTER_ALIGNMENT);
                foodPanel.add(label, BorderLayout.NORTH);
                mainPanel.setBackground(Color.decode("#8A8585"));
                mainPanel.add(foodPanel);
            }

            JScrollPane scrollPane = new JScrollPane(mainPanel);
            scrollPane.setPreferredSize(new Dimension(515, 400));
            add(scrollPane);

            totalTextField = new JTextField("◆ 총: 0 원 ◆");
            totalTextField.setPreferredSize(new Dimension(200, 20));
            totalTextField.setEditable(false);
            totalTextField.setFocusable(false);
            totalTextField.setHorizontalAlignment(JTextField.CENTER);
            totalTextField.setEditable(false); // 텍스트 필드를 읽기 전용으로 설정
            totalTextField.setFocusable(false); // 포커스 설정 방지
            add(totalTextField, BorderLayout.NORTH);


        } catch (SQLException e) {
            e.printStackTrace();
        }
        setPreferredSize(new Dimension(width, height));
        setSize(650, 430);
        //setSize(750, 580);
    }

    // 선택한 음식과 수량을 Map으로 반환하는 메서드
    public Map<Food, Integer> getSelectedFoods() {
        return selectedFoods;
    }

    public void resetQuantityLabels() {
        // 모든 선택된 음식의 수량 라벨을 0으로 설정합니다.
        for (Component component : mainPanel.getComponents()) {
            if (component instanceof JPanel) {
                JPanel foodPanel = (JPanel) component;
                for (Component innerComponent : foodPanel.getComponents()) {
                    if (innerComponent instanceof JPanel) {
                        JPanel buttonPanel = (JPanel) innerComponent;
                        for (Component buttonComponent : buttonPanel.getComponents()) {
                            if (buttonComponent instanceof JLabel) {
                                JLabel label = (JLabel) buttonComponent;
                                label.setForeground(Color.white);
                                label.setText("0");
                            }
                        }
                    }
                }
            }
        }



        // 선택된 음식 목록을 초기화합니다.
        selectedFoods.clear();
        // 총 가격을 업데이트합니다.
        updateTotal();
    }
    // Method to display the shopping cart
    public void displayShoppingCart() {
        CartScreen cartScreen = new CartScreen(selectedFoods);
        JFrame frame = new JFrame("장바구니");
        frame.add(cartScreen);
        frame.pack();
        frame.setVisible(true);
        // 수량 라벨을 초기화합니다.
        resetQuantityLabels();
    }


    private class QuantityButtonListener implements ActionListener {
        private Food food;
        private JLabel quantityLabel;
        private int quantityChange;
        private JLabel stockLabel;
        public QuantityButtonListener(Food food, JLabel quantityLabel,JLabel stockLabel, int quantityChange) {
            this.food = food;
            this.quantityLabel = quantityLabel;
            this.stockLabel = stockLabel;
            this.quantityChange = quantityChange;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (food.isFood_etc()) { // 재고 있음
                int quantity = Integer.parseInt(quantityLabel.getText());
                quantity += quantityChange;
                if (quantity < 0) {
                    quantity = 0;
                }
                quantityLabel.setText(String.valueOf(quantity));
                selectedFoods.put(food, quantity);
                updateTotal();
            } else { // 재고 없음
                stockLabel.setText("재고 없음");
            }
        }
    }

    private void updateTotal() {
        int total = 0;
        for (Map.Entry<Food, Integer> entry : selectedFoods.entrySet()) {
            Food food = entry.getKey();
            int quantity = entry.getValue();
            total += food.getFood_price() * quantity;
        }
        totalTextField.setText("◆ 총: " + total + " 원 ◆");
        totalTextField.setFocusable(false); // 텍스트 필드에 포커스 설정 방지
        totalTextField.setEditable(false); // 텍스트 필드를 읽기 전용으로 설정


    }


}