package src.naver.pin_project.view;

import src.naver.pin_project.data.Ranking;
import src.naver.pin_project.viewmodel.Ranking_ViewModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class RankingScreen extends JFrame {
    private JTable realTimeTable; // 실시간 랭킹을 표시할 테이블
    private JTable monthlyTable; // 이달의 랭킹을 표시할 테이블

    // 생성자
    public RankingScreen(List<Ranking> realTimeRankingList, List<Ranking> monthlyRankingList) {
        setTitle("랭킹 정보"); // 프레임 타이틀 설정
        setSize(600, 460); // 프레임 크기 설정
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 프레임이 닫힐 때 프로그램 종료
        Collections.sort(realTimeRankingList, Comparator.comparingInt(Ranking::getPoint).reversed());
        Collections.sort(monthlyRankingList, Comparator.comparingInt(Ranking::getPoint).reversed());

        // 테이블의 열 이름 설정
        String[] columnNames = {"유저 아이디", "유저 이름", "게임 날짜", "점수"};

        // 테이블 열 제목에 사용할 폰트 설정
        Font tableFont = getCustomFont("src/naver/pin_project/lib/온글잎밑미.ttf", 15f);

        // 실시간 랭킹과 이달의 랭킹을 위한 DefaultTableModel 생성
        DefaultTableModel realTimeModel = new DefaultTableModel(columnNames, 0);
        DefaultTableModel monthlyModel = new DefaultTableModel(columnNames, 0);

        // JTable 생성 및 모델 설정
        realTimeTable = new JTable(realTimeModel);
        monthlyTable = new JTable(monthlyModel);

        // 테이블 폰트 및 셀 크기 설정
        realTimeTable.setFont(tableFont);
        monthlyTable.setFont(tableFont);
        realTimeTable.setRowHeight(30); // 셀 높이 설정
        monthlyTable.setRowHeight(30); // 셀 높이 설정

        // JTableHeader 폰트 설정
        setTableHeaderFont(realTimeTable.getTableHeader(), tableFont);
        setTableHeaderFont(monthlyTable.getTableHeader(), tableFont);

        // JScrollPane을 사용하여 테이블을 감싸줌
        JScrollPane realTimeScrollPane = new JScrollPane(realTimeTable);
        JScrollPane monthlyScrollPane = new JScrollPane(monthlyTable);

        // 레이블 제목 폰트 설정
        Font labelFont = getCustomFont("src/naver/pin_project/lib/온글잎밑미.ttf", 40f);

        // 이미지 아이콘 생성
        ImageIcon labelIcon = getScaledImageIcon("src/naver/pin_project/lib/볼링핀.png", 50, 50);

        // 실시간 랭킹 레이블 생성
        JLabel realTimeLabel = createLabel("실시간 랭킹", labelIcon, labelFont);

        // 이달의 랭킹 레이블 생성
        JLabel monthlyLabel = createLabel("이달의 랭킹", labelIcon, labelFont);

        // 두 개의 테이블과 레이블을 담을 패널 생성
        JPanel tablePanel = createTablePanel(realTimeScrollPane, monthlyScrollPane, realTimeLabel, monthlyLabel);

        // 프레임의 중앙에 테이블 패널 추가
        getContentPane().add(tablePanel, BorderLayout.CENTER);

        // 새로고침 버튼 생성 및 이벤트 처리
        JButton refreshButton = new JButton("새로고침");
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 새로고침 버튼 클릭 시 데이터 새로고침
                refreshData();
            }
        });

        // 뒤로가기 버튼 생성 및 이벤트 처리
        JButton backButton = new JButton("뒤로가기");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 뒤로가기 버튼 클릭 시 현재 창 닫기
                dispose(); // 현재 창 닫기
                // 이전 창 또는 메인 화면 등으로 이동하는 코드 추가
            }
        });

        // 버튼을 담을 패널 생성
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        buttonPanel.add(refreshButton); // 새로고침 버튼 추가
        buttonPanel.add(backButton); // 뒤로가기 버튼 추가

        // 프레임의 아래쪽에 버튼 패널 추가
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
    }

    // 새로고침 기능 구현
    private void refreshData() {
        // 실시간 랭킹 정보 가져오기
        List<Ranking> realTimeRankingList = fetchRealTimeRankingData();
        // 이달의 랭킹 정보 가져오기
        List<Ranking> monthlyRankingList = fetchMonthlyRankingData();
        // 테이블 데이터 업데이트
        updateTableData(realTimeRankingList, monthlyRankingList);
    }

    // 사용자 정의 폰트 가져오기
    private Font getCustomFont(String fontPath, float fontSize) {
        try {
            return Font.createFont(Font.TRUETYPE_FONT, new File(fontPath)).deriveFont(fontSize);
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    // JTableHeader의 폰트 설정
    private void setTableHeaderFont(JTableHeader header, Font font) {
        header.setFont(font);
        header.setBackground(new Color(204, 0, 204)); // 헤더 배경색 설정
    }

    // 이미지 아이콘 크기 조절하여 가져오기
    private ImageIcon getScaledImageIcon(String imagePath, int width, int height) {
        ImageIcon originalIcon = new ImageIcon(imagePath);
        Image originalImage = originalIcon.getImage();
        Image scaledImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }

    // 레이블 생성 메서드
    private JLabel createLabel(String text, ImageIcon icon, Font font) {
        JLabel label = new JLabel(text, icon, SwingConstants.LEFT);
        label.setFont(font);
        label.setHorizontalTextPosition(SwingConstants.RIGHT);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        return label;
    }

    // 테이블을 담은 패널 생성 메서드
    private JPanel createTablePanel(JScrollPane realTimeScrollPane, JScrollPane monthlyScrollPane, JLabel realTimeLabel, JLabel monthlyLabel) {
        JPanel tablePanel = new JPanel(new GridLayout(1, 2)); // 1행 2열의 그리드 레이아웃
        tablePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // 여백 추가
        tablePanel.setBackground(new Color(204, 153, 255)); // 배경색 설정

        JPanel realTimePanel = new JPanel(new BorderLayout()); // 실시간 랭킹을 담을 패널
        realTimePanel.add(realTimeLabel, BorderLayout.NORTH); // 레이블을 패널의 상단에 추가
        realTimePanel.add(realTimeScrollPane, BorderLayout.CENTER); // 테이블 스크롤 패널을 패널의 중앙에 추가
        realTimePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // 여백 추가
        realTimePanel.setBackground(new Color(204, 153, 255)); // 배경색 설정

        JPanel monthlyPanel = new JPanel(new BorderLayout()); // 이달의 랭킹을 담을 패널
        monthlyPanel.add(monthlyLabel, BorderLayout.NORTH); // 레이블을 패널의 상단에 추가
        monthlyPanel.add(monthlyScrollPane, BorderLayout.CENTER); // 테이블 스크롤 패널을 패널의 중앙에 추가
        monthlyPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // 여백 추가
        monthlyPanel.setBackground(new Color(204, 153, 255)); // 배경색 설정

        tablePanel.add(realTimePanel); // 실시간 랭킹 패널 추가
        tablePanel.add(monthlyPanel); // 이달의 랭킹 패널 추가

        return tablePanel; // 테이블을 담은 패널 반환
    }

    // 실시간 랭킹 정보를 가져오는 메서드
    private static List<Ranking> fetchRealTimeRankingData() {
        // Ranking_ViewModel 클래스를 활용하여 실시간 랭킹 정보를 가져옴
        List<List<Ranking>> rankingData = Ranking_ViewModel.getRankingData();
        // 첫 번째 리스트에는 실시간 랭킹 정보가 들어있음
        return rankingData.get(0);
    }

    // 이달의 랭킹 정보를 가져오는 메서드
    private static List<Ranking> fetchMonthlyRankingData() {
        // Ranking_ViewModel 클래스를 활용하여 이달의 랭킹 정보를 가져옴
        List<List<Ranking>> rankingData = Ranking_ViewModel.getRankingData();
        // 두 번째 리스트에는 이달의 랭킹 정보가 들어있음
        List<Ranking> monthlyRankingList = rankingData.get(1);


        // 이달의 랭킹 중에서 4월에 해당하는 데이터만 필터링하여 반환
        List<Ranking> filteredMonthlyRankingList = new ArrayList<>();
        for (Ranking ranking : monthlyRankingList) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(ranking.getGameDate());
            if (cal.get(Calendar.MONTH) == Calendar.APRIL) {
                filteredMonthlyRankingList.add(ranking);
            }
        }
        return filteredMonthlyRankingList;
    }



    // 테이블 데이터를 업데이트하는 메서드
    private void updateTableData(List<Ranking> realTimeRankingList, List<Ranking> monthlyRankingList) {
        // 실시간 랭킹 테이블 모델 업데이트
        DefaultTableModel realTimeModel = (DefaultTableModel) realTimeTable.getModel();
        realTimeModel.setRowCount(0); // 기존 데이터 지우기
        // 점수에 대해 내림차순으로 정렬
        realTimeRankingList.sort(Comparator.comparingInt(Ranking::getPoint).reversed());
        for (Ranking ranking : realTimeRankingList) {
            realTimeModel.addRow(new Object[]{ranking.getUserId(), ranking.getUserName(), ranking.getGameDate(), ranking.getPoint()});
        }

        // 이달의 랭킹 테이블 모델 업데이트
        DefaultTableModel monthlyModel = (DefaultTableModel) monthlyTable.getModel();
        monthlyModel.setRowCount(0); // 기존 데이터 지우기
        // 점수에 대해 내림차순으로 정렬
        monthlyRankingList.sort(Comparator.comparingInt(Ranking::getPoint).reversed());
        for (Ranking ranking : monthlyRankingList) {
            monthlyModel.addRow(new Object[]{ranking.getUserId(), ranking.getUserName(), ranking.getGameDate(), ranking.getPoint()});
        }
    }


    // 메인 메서드
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // 실시간 랭킹 정보를 가져오는 코드
            List<Ranking> realTimeRankingList = fetchRealTimeRankingData();
            // 이달의 랭킹 정보를 가져오는 코드
            List<Ranking> monthlyRankingList = fetchMonthlyRankingData();
            // 랭킹 스크린 객체 생성
            RankingScreen rankingScreen = new RankingScreen(realTimeRankingList, monthlyRankingList);
            // 랭킹 스크린을 화면에 표시
            rankingScreen.setVisible(true);
        });
    }
}