package src.naver.pin_project.viewmodel;

import src.naver.pin_project.db.OjdbcConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SignUp_ViewModel {
    public int registerUser(String userid, String username, String pw) throws SQLException {
        Connection con = null;
        String sql = "insert into user(userid,username,pw) values(?,?,?)";

        try {
            con = OjdbcConnection.getConnection();
            PreparedStatement psmt = con.prepareStatement(sql);
            psmt.setString(1, userid);
            psmt.setString(2, username);
            psmt.setString(3, pw);

            return psmt.executeUpdate();
        } catch (Exception e){
            System.out.println(e.getMessage());
            return 0;
        }

    }
}
