package src.naver.pin_project.view;

import src.naver.pin_project.data.CustomFont;
import src.naver.pin_project.data.Food;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class CartScreen extends JPanel {
    private Map<Food, Integer> selectedFoods;
    private JTextField totalPriceTextField;
//주석
    public CartScreen(Map<Food, Integer> selectedFoods) {

        // 폰트 파일 경로
        String fontPath = "src/naver/pin_project/lib/온글잎밑미.ttf";
        // 원하는 폰트 크기로 폰트 로드
        Font customFont = CustomFont.loadFont(fontPath, 17f);
        // UI에 폰트 적용
        CustomFont.setUIFont(customFont);

        this.selectedFoods = selectedFoods;
        setLayout(new BorderLayout());
        // Display the quantity and sum of each type of food ordered with a scroll pane in the center
        JTextArea cartTextArea = new JTextArea();
        cartTextArea.setBackground(Color.decode("#8A8585"));
        cartTextArea.setForeground(Color.white);
        cartTextArea.setEditable(false);
        cartTextArea.setFont(cartTextArea.getFont().deriveFont(cartTextArea.getFont().getSize() * 2.0f)); // Increase font size by 50%
        JScrollPane scrollPane = new JScrollPane(cartTextArea);
        scrollPane.setBackground(Color.decode("#8A8585"));
        for (Map.Entry<Food, Integer> entry : selectedFoods.entrySet()) {
            Food food = entry.getKey();
            int quantity = entry.getValue();
            int totalPriceForFood = food.getFood_price() * quantity;
            if(quantity>0){
                cartTextArea.append("★ 음식 ★ : " + food.getFood_name() + ", ★ 수량 ★ : " + quantity +
                        ",★ 가격 ★ : " + totalPriceForFood + " 원\n");}
        }
        add(scrollPane, BorderLayout.CENTER);

        // Calculate the total of all orders in the south
        int total = calculateTotal();
        totalPriceTextField = new JTextField("◆ 총: " + total + " 원 ◆");
        totalPriceTextField.setBackground(Color.decode("#FCEB83"));
        totalPriceTextField.setEditable(false);
        totalPriceTextField.setHorizontalAlignment(JTextField.CENTER);
        totalPriceTextField.setFont(totalPriceTextField.getFont().deriveFont(totalPriceTextField.getFont().getSize() * 2.0f)); // Increase font size by 50%
        add(totalPriceTextField, BorderLayout.SOUTH);

        // Open a payment window
        JButton payButton = new JButton("이 버튼을 누르면 ★결제창★으로 넘어갑니다");
        // 버튼의 현재 글꼴을 가져옵니다.
        Font buttonFont = payButton.getFont();
// 글꼴의 크기를 현재 크기의 2배로 설정합니다.
        payButton.setFont(buttonFont.deriveFont(buttonFont.getSize() * 2.0f));


        payButton.addActionListener(e -> {
            // Implement payment window opening here
            JOptionPane.showMessageDialog(this, "결제창이 열립니다");
            new PaymentScreen();
        });
        payButton.setBackground(Color.decode("#B0FFA9"));
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
