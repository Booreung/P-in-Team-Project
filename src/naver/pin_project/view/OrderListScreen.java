package src.naver.pin_project.view;

import src.naver.pin_project.data.OrderInfo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class OrderListScreen extends JFrame {

    public OrderListScreen(ArrayList<OrderInfo> ordered_list) {
        setTitle("주문 내역 화면"); // 프레임 제목 설정
        setSize(800, 450); // 프레임 크기 설정

        // 테이블 모델 생성
        DefaultTableModel model = new DefaultTableModel();
        JTable table = new JTable(model);

        // 모델에 열 추가
        model.addColumn("주문번호");
        model.addColumn("주문시간");
        model.addColumn("음식 이름");
        model.addColumn("가격");
        model.addColumn("수량");
        model.addColumn("총 가격");

        // 모든 열의 폰트 크기를 설정
        table.setFont(new Font("Arial", Font.PLAIN, 20));

        // 모델에 행 추가
        for (OrderInfo order : ordered_list) {
            Object[] rowData = {
                    order.getOrdered_no(), // 주문번호
                    formatDate(order.getOrdered_time()), // 주문시간
                    order.getOrdered_food_name(), // 음식 이름
                    order.getOrdered_food_price(), // 가격
                    order.getOrdered_food_quantity(), // 수량
                    order.getTotalPrice() // 총 가격
            };
            model.addRow(rowData);
        }

        // 테이블을 편집 불가능하도록 설정
        table.setEnabled(false);

        // 셀 높이 설정
        table.setRowHeight(30);

        // 주문시간 열의 너비 설정
        table.getColumnModel().getColumn(1).setPreferredWidth(200);

        // 스크롤 가능한 패널에 테이블 추가
        JScrollPane scrollPane = new JScrollPane(table);

        // 프레임에 스크롤 패널 추가
        getContentPane().add(scrollPane);

        // 프레임을 가운데 정렬하고 표시
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // 날짜를 문자열로 포맷하는 메서드
    private String formatDate(java.util.Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }
}
