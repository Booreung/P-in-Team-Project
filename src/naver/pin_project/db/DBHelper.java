package src.naver.pin_project.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
}
