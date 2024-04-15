package src.naver.pin_project.viewmodel;

import src.naver.pin_project.data.User;
import src.naver.pin_project.db.OjdbcConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login_ViewModel {
    private User user;

    public Login_ViewModel(User user){
        this.user = user;
    }

    //로그인 동작
    public boolean isValidUser(){
        //간단한 쿼리문 작업 : 코드만 봐도 충분히 이해할 수 있다고 판단
        Connection con = null;
        String query = "select * from user where userid = ? and userpw =?";

        try{
            con = OjdbcConnection.getConnection();
            PreparedStatement psmt = con.prepareStatement(query);
            psmt.setString(1,user.getUserId());
            psmt.setString(2,user.getPassword());

            ResultSet rs = psmt.executeQuery();
            return rs.next();
        }
        catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }
}

