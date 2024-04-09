package src.naver.pin_project.view;

import src.naver.pin_project.data.OrderInfo;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class OrderListScreen extends JFrame {

    public OrderListScreen(ArrayList<OrderInfo> orederd_list) {

        setTitle("주문 내역 화면");
        setSize(600, 450);

        // 주문 내역을 표시할 JTextArea 생성
        JTextArea orderTextArea = new JTextArea();
        orderTextArea.setEditable(false); // 편집 불가능하도록 설정
        orderTextArea.setFont(new Font("Arial", Font.PLAIN, 20)); // 폰트 설정
        // JTextArea를 스크롤 가능하도록 JScrollPane에 추가
        JScrollPane scrollPane = new JScrollPane(orderTextArea);

        // JFrame에 JScrollPane 추가
        getContentPane().add(scrollPane);

        // 화면 가운데에 위치하도록 설정
        setLocationRelativeTo(null);
        setVisible(true);

        for(OrderInfo obj:orederd_list){
            orderTextArea.append(obj.toString());
        }
    }

}
