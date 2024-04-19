package src.naver.pin_project.game_feature;

import src.naver.pin_project.view.MainScreen;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.sql.*;

public class GameRecord extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    public String UserID = MainScreen.UserID;//메인메뉴로부터 받아올 유저아이디
    public String UserName = MainScreen.UserName;//메인메뉴로부터 받아올 유저 닉네임

    public GameRecord() {
        setTitle(UserName+"님의 기록");
        setSize(800, 480);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);



        // Create the table model
        String[] columnNames = {"유저아이디", "게임코드", "게임날짜", "점수"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        table.setPreferredScrollableViewportSize(new Dimension(750, 400));
        table.setBackground(Color.decode("#5B5D61"));
        table.setForeground(Color.decode("#FFFFC0"));
        table.setGridColor(Color.decode("#3D3E41"));
        table.setRowHeight(30);
        setColumnHeaderBackgroundColor(Color.decode("#88DBFF"));

        // Create the update button
        JButton updateButton = new JButton("업데이트");
        updateButton.setBackground(Color.decode("#FCEB83"));
        updateButton.setForeground(Color.black);
        updateButton.addActionListener(e -> fetchDataFromDatabase());

        // Add the table and button to the content pane
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
        contentPane.add(new JScrollPane(table));
        contentPane.add(updateButton);
        contentPane.setBackground(Color.decode("#5B5D61"));
        setContentPane(contentPane);
    }
    private void setColumnHeaderBackgroundColor(Color color) {
        JTableHeader header = table.getTableHeader();
        header.setBackground(color);
    }

    public void fetchDataFromDatabase() {
        String query = "SELECT * FROM game WHERE userid = ? ORDER BY game_date DESC";
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://175.209.41.173:3308/pin", "root", "q123");
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            // Set the parameter for the prepared statement
            pstmt.setString(1, UserID);

            // Clear the table model
            tableModel.setRowCount(0);

            // Execute the query
            try (ResultSet rs = pstmt.executeQuery()) {
                // Add the data to the table model
                while (rs.next()) {
                    String userId = rs.getString("userid");
                    int gameCode = rs.getInt("game_code");
                    String gameDate = rs.getString("game_date");
                    int score = rs.getInt("game_point");
                    tableModel.addRow(new Object[]{userId, gameCode, gameDate, score});
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameRecord gameRecord = new GameRecord();
            gameRecord.setVisible(true);
            // 호출되지 않더라도 빈 테이블을 보여주기 위해 추가
            gameRecord.fetchDataFromDatabase();
        });
    }
}