package src.naver.pin_project.view;

import src.naver.pin_project.data.Food;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class CartScreen extends JPanel {
    private Map<Food, Integer> selectedFoods;
    private JTextField totalPriceTextField;

    public CartScreen(Map<Food, Integer> selectedFoods) {
        this.selectedFoods = selectedFoods;
        setLayout(new BorderLayout());

        // Display the quantity and sum of each type of food ordered with a scroll pane in the center
        JTextArea cartTextArea = new JTextArea();
        cartTextArea.setEditable(false);
        cartTextArea.setFont(cartTextArea.getFont().deriveFont(cartTextArea.getFont().getSize() * 1.5f)); // Increase font size by 50%
        JScrollPane scrollPane = new JScrollPane(cartTextArea);
        for (Map.Entry<Food, Integer> entry : selectedFoods.entrySet()) {
            Food food = entry.getKey();
            int quantity = entry.getValue();
            int totalPriceForFood = food.getFood_price() * quantity;
            cartTextArea.append("★ 음식 ★ : " + food.getFood_name() + ", ★ 수량 ★ : " + quantity +
                    ",★ 가격 ★ : " + totalPriceForFood + " 원\n");
        }
        add(scrollPane, BorderLayout.CENTER);

        // Calculate the total of all orders in the south
        int total = calculateTotal();
        totalPriceTextField = new JTextField("◆ 총: " + total + " 원 ◆");
        totalPriceTextField.setEditable(false);
        totalPriceTextField.setHorizontalAlignment(JTextField.CENTER);
        totalPriceTextField.setFont(totalPriceTextField.getFont().deriveFont(totalPriceTextField.getFont().getSize() * 1.5f)); // Increase font size by 50%
        add(totalPriceTextField, BorderLayout.SOUTH);

        // Open a payment window
        JButton payButton = new JButton("이 버튼을 누르면 ★결제창★으로 넘어갑니다");
        payButton.addActionListener(e -> {
            // Implement payment window opening here
            JOptionPane.showMessageDialog(this, "결제창이 열립니다");
            new PaymentScreen();
        });
        add(payButton, BorderLayout.NORTH);
        setPreferredSize(new Dimension(650, 430));

    }

    private int calculateTotal() {
        int total = 0;
        for (Map.Entry<Food, Integer> entry : selectedFoods.entrySet()) {
            Food food = entry.getKey();
            int quantity = entry.getValue();
            total += food.getFood_price() * quantity;
        }
        return total;
    }
}
