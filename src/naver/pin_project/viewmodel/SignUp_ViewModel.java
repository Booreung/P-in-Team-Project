package src.naver.pin_project.viewmodel;

import src.naver.pin_project.db.OjdbcConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SignUp_ViewModel {
    public int registerUser(String userid, String username, String pw) throws SQLException {
        Connection con = null; // 데이터베이스 연결을 관리하기 위한 Connection 객체를 초기화
        String sql = "insert into user(userid,username,userpw) values(?,?,?)"; //사용자 데이터를 데이터베이스에 삽입

        try {
            con = OjdbcConnection.getConnection(); // OjdbcConnection 클래스를 사용하여 데이터베이스 연결을 수립합니다.
            PreparedStatement psmt = con.prepareStatement(sql); // 매개변수화된 SQL 쿼리를 실행하기 위한 PreparedStatement 객체를 생성
            psmt.setString(1, userid); // SQL 쿼리의 첫 번째 매개변수 값을 제공된 userid로 설정합니다.
            psmt.setString(2, username); // SQL 쿼리의 두 번째 매개변수 값을 제공된 username으로 설정합니다.
            psmt.setString(3, pw); // SQL 쿼리의 세 번째 매개변수 값을 제공된 비밀번호로 설정합니다.


            return psmt.executeUpdate();  // 사용자 데이터를 데이터베이스에 삽입하고 영향을 받은 행 수를 반환
        } catch (Exception e){
            System.out.println(e.getMessage());
            return 0;
        }

    }
}
