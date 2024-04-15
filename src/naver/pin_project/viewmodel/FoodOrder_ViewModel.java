package src.naver.pin_project.viewmodel;

import src.naver.pin_project.data.Food;
import src.naver.pin_project.db.OjdbcConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// FoodOrder_ViewModel 클래스는 음식 메뉴 데이터를 관리하는 뷰모델 클래스입니다.
public class FoodOrder_ViewModel {
    // Food 객체를 저장하는 변수
    private Food food;

    // 생성자에서 food 변수 초기화
    public FoodOrder_ViewModel() {
        this.food = food;
    }

    // 데이터베이스에서 음식 메뉴 목록을 가져오는 메서드
    public List<Food> getFoodMenu() throws SQLException {
        Connection con = null;
        PreparedStatement psmt = null;
        ResultSet rs = null;

        try {
            // JDBC 연결을 가져옴
            con = OjdbcConnection.getConnection();
            // 모든 음식 항목을 선택하는 SQL 쿼리
            String query = "SELECT * FROM Food";
            psmt = con.prepareStatement(query);

            // 쿼리를 실행하고 결과 집합을 받음
            rs = psmt.executeQuery();
            List<Food> foodList = new ArrayList<>();

            // 결과 집합을 반복하면서 Food 객체를 생성하고 리스트에 추가
            while (rs.next()) {
                String foodName = rs.getString("food_name");
                int foodPrice = rs.getInt("food_price");
                String foodImage = rs.getString("food_image");
                boolean foodEtc = rs.getBoolean("food_etc");
                Food retrievedFood = new Food(foodName, foodPrice, foodImage, foodEtc);
                foodList.add(retrievedFood);
            }

            // Food 객체 목록을 반환
            return foodList;
        } catch (SQLException e) {
            // 예외가 발생하면 적절하게 처리하기 위해 다시 던짐
            throw e;
        } finally {
            // 연결된 리소스들을 닫음
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    // 로그하거나 적절하게 처리
                    e.printStackTrace();
                }
            }
            if (psmt != null) {
                try {
                    psmt.close();
                } catch (SQLException e) {
                    // 로그하거나 적절하게 처리
                    e.printStackTrace();
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    // 로그하거나 적절하게 처리
                    e.printStackTrace();
                }
            }
        }
    }
}