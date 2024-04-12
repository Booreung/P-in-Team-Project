package src.naver.pin_project.view;

import src.naver.pin_project.data.OrderInfo;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class OrderListScreen extends JFrame {

    public OrderListScreen(ArrayList<OrderInfo> ordered_list) {
        setTitle("주문 내역"); // 프레임 제목 설정
        setSize(750, 460); // 프레임 크기 설정
        setLocationRelativeTo(null);

        // 패널 생성
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(138, 133, 133)); // 배경색 설정

        // 레이블 생성
        JLabel titleLabel = new JLabel("주문 내역");
        titleLabel.setFont(getCustomFont("src/naver/pin_project/lib/온글잎밑미.ttf", 30f)); // 커스텀 폰트 설정
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE); // 폰트 색상 설정
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // 여백 설정

        // 테이블 생성
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // 셀 편집 불가능하도록 설정
            }
        };
        JTable table = new JTable(model);
        table.setFont(getCustomFont("src/naver/pin_project/lib/온글잎밑미.ttf", 16f)); // 커스텀 폰트 설정
        table.setRowHeight(30);
        table.setBackground(new Color(138,133,133)); // 테이블 배경색 설정

        // 테이블 헤더 설정
        JTableHeader header = table.getTableHeader();
        header.setFont(getCustomFont("src/naver/pin_project/lib/온글잎밑미.ttf", 16f)); // 커스텀 폰트 설정
        header.setForeground(Color.black); // 헤더 폰트 색상 설정
        header.setBackground(new Color(252, 235, 131)); // 헤더 배경색 설정
        header.setBorder(new LineBorder(new Color(237, 180, 81), 1));

        // 모델에 열 추가
        model.addColumn("주문번호");
        model.addColumn("주문시간");
        model.addColumn("음식 이름");
        model.addColumn("가격");
        model.addColumn("수량");
        model.addColumn("총 가격");

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

        // 열의 셀 렌더러 설정
        TableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        ((DefaultTableCellRenderer) centerRenderer).setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            table.setBackground(Color.white);
            table.setBorder((new LineBorder(new Color(138,133,133))));
        }

        // 스크롤 가능한 패널에 테이블 추가
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(new Color(138, 133, 133)); // 패널 배경색 설정
        scrollPane.setBorder((new LineBorder(new Color(138,133,133),1)));


        // 뒤로가기 버튼 생성
        JButton backButton = new JButton("뒤로가기");
        backButton.setFont(getCustomFont("src/naver/pin_project/lib/온글잎밑미.ttf", 20f)); // 커스텀 폰트 설정
        backButton.setBackground(new Color(176, 255, 169));
        backButton.setForeground(Color.BLACK);
        backButton.setBorder(BorderFactory.createLineBorder(new Color(38, 171, 50), 1));
        backButton.setPreferredSize(new Dimension(120, 40));
        backButton.addActionListener(e -> dispose()); // 창 닫기

        // 버튼 패널 생성
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(138, 133, 133)); // 배경색 설정
        buttonPanel.add(backButton);

        // 메인 패널에 컴포넌트 추가
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        getContentPane().add(mainPanel);
        setVisible(true);
    }

    // 날짜를 문자열로 포맷하는 메서드
    private String formatDate(java.util.Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    // 사용자 정의 폰트 가져오기
    private Font getCustomFont(String fontPath, float fontSize) {
        try {
            return Font.createFont(Font.TRUETYPE_FONT, new File(fontPath)).deriveFont(fontSize);
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }
    }

}
