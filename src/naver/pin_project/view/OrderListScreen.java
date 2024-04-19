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

    // OrderListScreen 클래스 생성자
    public OrderListScreen(ArrayList<OrderInfo> ordered_list) {
        // 프레임 제목 설정
        setTitle("주문 내역");
        // 프레임 크기 설정
        setSize(750, 460);
        // 화면 중앙에 프레임 위치 설정
        setLocationRelativeTo(null);

        // 메인 패널 생성
        JPanel mainPanel = new JPanel(new BorderLayout());
        // 메인 패널 배경색 설정
        mainPanel.setBackground(new Color(138, 133, 133));

        // 제목 레이블 생성
        JLabel titleLabel = new JLabel("주문 내역");
        // 제목 레이블 폰트 설정
        titleLabel.setFont(getCustomFont("src/naver/pin_project/lib/온글잎밑미.ttf", 30f));
        // 제목 레이블 가운데 정렬
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        // 제목 레이블 폰트 색상 설정
        titleLabel.setForeground(Color.WHITE);
        // 제목 레이블 여백 설정
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 테이블 생성
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // 셀 편집 불가능하도록 설정
            }
        };
        JTable table = new JTable(model);
        // 테이블 폰트 설정
        table.setFont(getCustomFont("src/naver/pin_project/lib/온글잎밑미.ttf", 16f));
        // 테이블 셀 높이 설정
        table.setRowHeight(30);
        // 테이블 배경색 설정
        table.setBackground(new Color(138,133,133));
        table.setForeground(Color.white);

        // 테이블 헤더 설정
        JTableHeader header = table.getTableHeader();

        // 테이블 헤더 폰트 설정
        header.setFont(getCustomFont("src/naver/pin_project/lib/온글잎밑미.ttf", 16f));
        // 테이블 헤더 폰트 색상 설정
        header.setForeground(Color.black);
        // 테이블 헤더 배경색 설정
        header.setBackground(new Color(252, 235, 131));
        // 테이블 헤더 테두리 설정
        header.setBorder(new LineBorder(new Color(237, 180, 81), 1));

        // 모델에 열 추가
        model.addColumn("주문번호");
        model.addColumn("주문시간");
        model.addColumn("음식 이름");
        model.addColumn("가격");
        model.addColumn("수량");
        model.addColumn("총 가격");

        // 모델에 행 추가
        // 생성자 내에서 주문 내역을 검사하고, 주문 내역이 없는 경우에만 경고 대화상자를 표시하고 창을 닫습니다.
        if (ordered_list.isEmpty()) {
            // 주문 내역이 없는 경우 경고 대화상자 표시
            JOptionPane.showMessageDialog(this, "주문 내역이 없습니다.", "주문 내역", JOptionPane.WARNING_MESSAGE);
            // 창을 닫고 생성자를 종료
            return;
        } else {
            // 주문 내역이 있는 경우 테이블에 주문 정보를 추가합니다.
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
        }

        // 열의 셀 렌더러 설정
        TableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        // 셀 가운데 정렬
        ((DefaultTableCellRenderer) centerRenderer).setHorizontalAlignment(SwingConstants.CENTER);
        // 테이블 각 열에 적용
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            table.setBackground(new Color(138, 133, 133));
            table.setBorder((new LineBorder(new Color(138,133,133))));
        }

        // 스크롤 가능한 패널에 테이블 추가
        JScrollPane scrollPane = new JScrollPane(table);
        // 패널 배경색 설정
        scrollPane.getViewport().setBackground(new Color(138, 133, 133));
        // 패널 테두리 설정
        scrollPane.setBorder((new LineBorder(new Color(138,133,133),1)));

        // 뒤로가기 버튼 생성
        JButton backButton = new JButton("뒤로가기");
        // 버튼 폰트 설정
        backButton.setFont(getCustomFont("src/naver/pin_project/lib/온글잎밑미.ttf", 20f));
        // 버튼 배경색 설정
        backButton.setBackground(new Color(176, 255, 169));
        // 버튼 폰트 색상 설정
        backButton.setForeground(Color.BLACK);
        // 버튼 테두리 설정
        backButton.setBorder(BorderFactory.createLineBorder(new Color(38, 171, 50), 1));
        // 버튼 크기 설정
        backButton.setPreferredSize(new Dimension(120, 40));
        // 뒤로가기 버튼 클릭 시 창 닫기
        backButton.addActionListener(e -> dispose());

        // 버튼 패널 생성
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        // 패널 배경색 설정
        buttonPanel.setBackground(new Color(138, 133, 133));
        // 버튼 패널에 뒤로가기 버튼 추가
        buttonPanel.add(backButton);

        // 메인 패널에 컴포넌트 추가
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        getContentPane().add(mainPanel);
        setVisible(true); // 프레임 표시
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
