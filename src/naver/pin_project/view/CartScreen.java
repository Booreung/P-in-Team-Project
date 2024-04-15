package src.naver.pin_project.view;

import src.naver.pin_project.data.Food;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

// CartScreen 클래스는 장바구니 화면을 관리하는 클래스입니다. (키워드: 장바구니 화면)
public class CartScreen extends JPanel {
    // 선택된 음식과 수량을 저장하는 Map (키워드: 선택된 음식, 수량)
    private Map<Food, Integer> selectedFoods;
    // 총 주문 금액을 표시하는 텍스트 필드 (키워드: 총 주문 금액)
    private JTextField totalPriceTextField;

    // CartScreen 클래스의 생성자입니다. (키워드: 생성자)
    public CartScreen(Map<Food, Integer> selectedFoods) {
        // 선택된 음식과 수량 Map 초기화 (키워드: 초기화)
        this.selectedFoods = selectedFoods;
        // 레이아웃 설정 (키워드: 레이아웃)
        setLayout(new BorderLayout());

        // JTextArea를 사용하여 선택된 음식 정보를 표시하는 부분을 생성합니다. (키워드: 선택된 음식 정보)
        JTextArea cartTextArea = new JTextArea();
        // cartTextArea의 배경색을 설정합니다.
        cartTextArea.setBackground(Color.decode("#8A8585"));
        // cartTextArea의 글자색을 설정합니다.
        cartTextArea.setForeground(Color.white);
        // cartTextArea를 편집 불가능 상태로 설정합니다.
        cartTextArea.setEditable(false);
        // cartTextArea의 폰트 크기를 현재 폰트 크기의 2배로 설정합니다. (키워드: 폰트 크기)
        cartTextArea.setFont(cartTextArea.getFont().deriveFont(cartTextArea.getFont().getSize() * 2.0f));
        // cartTextArea를 JScrollPane에 추가하고 이를 scrollPane으로 이름 지어줍니다. 이를 통해 cartTextArea가 길어질 경우 스크롤 기능이 생깁니다.
        JScrollPane scrollPane = new JScrollPane(cartTextArea);
        // scrollPane의 배경색을 설정합니다.
        scrollPane.setBackground(Color.decode("#8A8585"));

        // 선택된 음식 정보 표시
        // selectedFoods 맵에 저장된 모든 키-값 쌍을 반복하면서 처리합니다. 이때, 키는 Food 객체이고, 값은 해당 음식을 선택한 수량입니다.
        for (Map.Entry<Food, Integer> entry : selectedFoods.entrySet()) {
            Food food = entry.getKey(); // 선택된 음식 정보를 가져옵니다.
            int quantity = entry.getValue(); // 선택한 음식의 수량을 가져옵니다.
            int totalPriceForFood = food.getFood_price() * quantity; // 해당 음식의 총 가격을 계산합니다 (단가 * 수량).
            if(quantity>0){ // 만약 선택한 음식의 수량이 0보다 크면
                cartTextArea.append("★ 음식 ★ : " + food.getFood_name() + ", ★ 수량 ★ : " + quantity +
                        ",★ 가격 ★ : " + totalPriceForFood + " 원\n");} // 장바구니 텍스트 영역에 음식의 이름, 수량, 총 가격을 추가합니다.
        }
        add(scrollPane, BorderLayout.CENTER); // 스크롤 패널을 화면 중앙에 추가합니다.


        // 하단에 총 주문 금액을 표시하기 위한 코드입니다. (키워드: 총 주문 금액)
        // 먼저, calculateTotal() 메서드를 호출하여 총 주문 금액을 계산합니다.
        int total = calculateTotal();
        // 계산한 총 주문 금액을 표시할 JTextField를 생성하며, 총 주문 금액을 문자열로 변환하여 표시합니다.
        totalPriceTextField = new JTextField("◆ 총: " + total + " 원 ◆");
        // JTextField의 배경색을 설정합니다.
        totalPriceTextField.setBackground(Color.decode("#FCEB83"));
        // JTextField를 편집 불가능 상태로 만듭니다. 이렇게 하면 사용자가 직접 총 주문 금액을 수정할 수 없습니다.
        totalPriceTextField.setEditable(false);
        // JTextField의 텍스트를 가운데 정렬합니다.
        totalPriceTextField.setHorizontalAlignment(JTextField.CENTER);
        // JTextField의 글자 크기를 현재 크기의 2배로 설정합니다. (키워드: 폰트 크기)
        totalPriceTextField.setFont(totalPriceTextField.getFont().deriveFont(totalPriceTextField.getFont().getSize() * 2.0f));
        // 이제 생성한 totalPriceTextField를 현재 패널의 남쪽(BorderLayout.SOUTH)에 추가합니다.
        add(totalPriceTextField, BorderLayout.SOUTH);

        // "이 버튼을 누르면 ★결제창★으로 넘어갑니다" 라는 텍스트를 가진 결제 버튼을 생성합니다. (키워드: 결제 버튼 생성)
        JButton payButton = new JButton("이 버튼을 누르면 ★결제창★으로 넘어갑니다");

        // payButton의 현재 폰트를 가져옵니다.
        Font buttonFont = payButton.getFont();

        // 폰트의 크기를 2배로 증가시킨 새로운 폰트를 payButton에 설정합니다. (키워드: 폰트 크기 조정)
        payButton.setFont(buttonFont.deriveFont(buttonFont.getSize() * 2.0f)); // 폰트 크기 200% 증가 (키워드: 폰트 크기)

        // payButton을 클릭하면 "결제창이 열립니다"라는 메시지를 보여주고, PaymentScreen을 새로 생성하여 결제 화면을 보여줍니다. (키워드: 결제 화면 열기)
        payButton.addActionListener(e -> {
            // 결제 화면 열기 (키워드: 결제 화면)
            JOptionPane.showMessageDialog(this, "결제창이 열립니다");
            new PaymentScreen();
        });

        // payButton의 배경색을 #B0FFA9로 설정합니다. (키워드: 버튼 색상 설정)
        payButton.setBackground(Color.decode("#B0FFA9"));

        // payButton을 현재 패널의 북쪽에 추가합니다. (키워드: 버튼 추가)
        add(payButton, BorderLayout.NORTH);

        // 현재 패널의 선호 사이즈를 650x430으로 설정합니다. (키워드: 패널 사이즈 설정)
        setPreferredSize(new Dimension(650, 430));
    }

    // 'calculateTotal' 메서드는 총 주문 금액을 계산합니다.
    private int calculateTotal() {
        // 'total' 변수를 0으로 초기화합니다. 이 변수에 각 음식의 가격과 수량을 곱한 값을 더할 것입니다.
        int total = 0;
        // 선택된 음식들의 Map에서 각 항목을 가져와 처리합니다.
        for (Map.Entry<Food, Integer> entry : selectedFoods.entrySet()) {
            // Map에서 key인 'Food' 객체를 가져옵니다.
            Food food = entry.getKey();
            // Map에서 value인 수량을 가져옵니다.
            int quantity = entry.getValue();
            // 해당 음식의 가격과 수량을 곱한 값을 총액에 더합니다.
            total += food.getFood_price() * quantity;
        }
        // 계산된 총 주문 금액을 반환합니다.
        return total;
    }

}
