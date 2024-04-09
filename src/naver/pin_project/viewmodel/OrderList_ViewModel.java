package src.naver.pin_project.viewmodel;

import src.naver.pin_project.db.OjdbcConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderList_ViewModel {

    public static List<String> getCartInfo(Connection conn, int orderNumber, Timestamp orderTime) throws SQLException {
        List<String> cartInfo = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            String SELECT_CART_QUERY = "SELECT c_name, c_ea, c_price FROM cart WHERE c_no = ? AND c_time = ?";
            stmt = conn.prepareStatement(SELECT_CART_QUERY);
            stmt.setInt(1, orderNumber);
            stmt.setTimestamp(2, orderTime);
            rs = stmt.executeQuery();
            while (rs.next()) {
                String foodName = rs.getString("c_name");
                int quantity = rs.getInt("c_ea");
                int totalPrice = rs.getInt("c_price");
                String info = "음식: " + foodName + ", 수량: " + quantity + ", 가격: " + totalPrice + "원";
                cartInfo.add(info);
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        }
        return cartInfo;
    }

}
