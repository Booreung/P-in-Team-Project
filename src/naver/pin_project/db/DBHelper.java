package src.naver.pin_project.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import src.naver.pin_project.data.Food;
import src.naver.pin_project.data.User;

public class DBHelper {
    //사용자 정보를 DB에서 가져오는 메소드
    public static User getUserInfoFromDB(String userId){
        Connection con = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        User user = null;

        try {
            con = OjdbcConnection.getConnection();
            String query = "SELECT * FROM user WHERE userid = ?";
            psmt = con.prepareStatement(query);
            psmt.setString(1,userId);
            rs = psmt.executeQuery();

            if(rs.next()){
                //사용자 정보를 받아왔으면 바로 src.naver.pin_project.data1.User 객체에 저장
                user = new User();
                user.setUserId(rs.getString("userid"));
                user.setUserName(rs.getString("username"));
                user.setPassword(rs.getString("userpw"));
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            try {
                if(rs != null) rs.close();
                if (psmt != null) psmt.close();
                if(con != null) con.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return user;
    }
    // 음식 정보를 DB에서 가져오는 메소드
    public static Food getFoodInfoFromDB(String foodName){
        Connection con = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;
        Food food = null;

        try {
            con = OjdbcConnection.getConnection();
            String query = "SELECT * FROM food WHERE food_name = ?";
            psmt = con.prepareStatement(query);
            psmt.setString(1,foodName);
            rs = psmt.executeQuery();

            if(rs.next()){
                //음식 정보를 받아왔으면 바로 Food 객체에 저장
                String name = rs.getString("food_name");
                int price = rs.getInt("food_price");
                String imageName = rs.getString("food_image");
                boolean foodetc=rs.getBoolean("food_etc");
                food = new Food(name, price, imageName,foodetc);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            try {
                if(rs != null) rs.close();
                if (psmt != null) psmt.close();
                if(con != null) con.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return food;
    }


    private static final String INSERT_CART_QUERY = "INSERT INTO cart (c_no, c_name, c_ea, c_price, c_time) VALUES (?, ?, ?, ?, ?)";

    // 장바구니에 메뉴를 추가하는 메서드
    public static void addToCart(Connection conn, String foodName, int quantity, int foodPrice, int orderNumber, Timestamp orderTime) throws SQLException {
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement(INSERT_CART_QUERY);
            stmt.setInt(1, orderNumber); // c_no 컬럼에 주문번호 설정
            stmt.setString(2, foodName); // c_name 컬럼에 음식 이름 설정
            stmt.setInt(3, quantity); // c_ea 컬럼에 수량 설정
            stmt.setInt(4, foodPrice * quantity); // c_price 컬럼에 총 가격 설정 (음식 가격 * 수량)
            stmt.setTimestamp(5, orderTime); // c_time 컬럼에 주문 시간 설정 (초까지 포함)
            stmt.executeUpdate(); // SQL 쿼리 실행
        } finally {
            if (stmt != null) {
                stmt.close(); // PreparedStatement 객체 닫기
            }
        }
    }
}
