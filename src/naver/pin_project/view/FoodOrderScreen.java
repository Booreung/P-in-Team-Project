package src.naver.pin_project.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import src.naver.pin_project.data.Food;
import src.naver.pin_project.db.DBHelper;
import src.naver.pin_project.db.OjdbcConnection;
import src.naver.pin_project.viewmodel.FoodOrder_ViewModel;

public class FoodOrderScreen extends JPanel {
    private JTextField totalTextField;
    private JTextField totalPriceTextField; // Corrected variable name
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private Map<Food, Integer> selectedFoods;
    private int orderNumber; // 주문번호를 저장할 변수

    public FoodOrderScreen(int width, int height) {
        this.cardLayout = new CardLayout();
        this.cardPanel = new JPanel(cardLayout);
        this.selectedFoods = new HashMap<>();
        this.orderNumber = -1; // 초기값으로 -1 설정

        try {
            List<Food> foodList;
            FoodOrder_ViewModel viewModel = new FoodOrder_ViewModel();
            foodList = viewModel.getFoodMenu();

            JPanel mainPanel = new JPanel(new GridLayout(0, 3, 10, 10));

            for (Food food : foodList) {
                JPanel foodPanel = new JPanel(new BorderLayout());

                String imagePath = food.getFood_image();
                if (imagePath != null) {
                    ImageIcon icon = new ImageIcon("src/naver/pin_project/lib/" + imagePath);
                    Image image = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                    JButton button = new JButton(new ImageIcon(image));
                    foodPanel.add(button, BorderLayout.CENTER);

                    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                    JLabel quantityLabel = new JLabel("0");
                    quantityLabel.setHorizontalAlignment(SwingConstants.CENTER);

                    JButton addButton = new JButton("+");
                    addButton.addActionListener(new QuantityButtonListener(food, quantityLabel, new JLabel(), 1)); // 추가된 행

                    JButton subtractButton = new JButton("-");
                    subtractButton.addActionListener(new QuantityButtonListener(food, quantityLabel, new JLabel(), -1)); // 추가된 행

                    buttonPanel.add(addButton);
                    buttonPanel.add(quantityLabel);
                    buttonPanel.add(subtractButton);

                    JLabel stockLabel = null;
                    if (!food.isFood_etc()) {
                        stockLabel= new JLabel("재고 없음");
                        buttonPanel.add(stockLabel);
                    }


                    foodPanel.add(buttonPanel, BorderLayout.SOUTH);
                } else {
                    System.out.println("Error: Image path is null");
                }

                JLabel label = new JLabel("▶" + food.getFood_name() + "▶ " + food.getFood_price() + " 원");
                label.setAlignmentX(Component.CENTER_ALIGNMENT);
                foodPanel.add(label, BorderLayout.NORTH);

                mainPanel.add(foodPanel);
            }

            JScrollPane scrollPane = new JScrollPane(mainPanel);
            scrollPane.setPreferredSize(new Dimension(515, 400));
            add(scrollPane);

            totalTextField = new JTextField("◆ 총: 0 원 ◆");
            totalTextField.setPreferredSize(new Dimension(200, 20));
            totalTextField.setHorizontalAlignment(JTextField.CENTER);
            add(totalTextField, BorderLayout.NORTH); // Changed here



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

    // Method to display the shopping cart
    public void displayShoppingCart() {
        CartScreen cartScreen = new CartScreen(selectedFoods);
        JFrame frame = new JFrame("장바구니");
        frame.add(cartScreen);
        frame.pack();
        frame.setVisible(true);
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
    }
}