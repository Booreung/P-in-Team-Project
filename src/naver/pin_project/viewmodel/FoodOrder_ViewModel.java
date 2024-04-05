package src.naver.pin_project.viewmodel;

import src.naver.pin_project.data.Food;
import src.naver.pin_project.db.OjdbcConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FoodOrder_ViewModel {

    private Food food;

    public FoodOrder_ViewModel() {
        this.food = food;
    }

    // 음식 메뉴 가져오기 (수정된 쿼리 및 반환 유형)
    public List<Food> getFoodMenu() throws SQLException {
        Connection con = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;

        try {
            con = OjdbcConnection.getConnection();
            String query = "SELECT * FROM Food"; // 모든 음식 항목 선택
            psmt = con.prepareStatement(query);

            rs = psmt.executeQuery();
            List<Food> foodList = new ArrayList<>();

            while (rs.next()) {
                String foodName = rs.getString("food_name"); // 명확성을 위해 컬럼 이름 사용
                int foodPrice = rs.getInt("food_price");
                String foodImage= rs.getString("food_image");
                boolean foodEtc= rs.getBoolean("food_etc");
                Food retrievedFood = new Food(foodName, foodPrice, foodImage,foodEtc); // Food 객체 생성
                foodList.add(retrievedFood);
            }

            return foodList; // Food 객체 목록 반환
        } catch (SQLException e) {
            throw e; // 적절한 처리를 위해 예외 다시 throw
        } finally {
            // 보장된 정리 작업을 위해 finally 블록에서 리소스 닫기
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace(); // 로그하거나 적절하게 처리
                }
            }
            if (psmt != null) {
                try {
                    psmt.close();
                } catch (SQLException e) {
                    e.printStackTrace(); // 로그하거나 적절하게 처리
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace(); // 로그하거나 적절하게 처리
                }
            }
        }
    }
}


