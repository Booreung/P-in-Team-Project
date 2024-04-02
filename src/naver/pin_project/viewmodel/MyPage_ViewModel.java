package src.naver.pin_project.viewmodel;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class MyPage_ViewModel {
    public boolean update(String msgId, String msgName,String msgPw) {
        // JTextField에 입력한 문자열 읽어오기

        String url = "jdbc:mysql://localhost:3306/yujung";
        String user = "root";
        String password = "00000000";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, user, password);

            String userId = msgId; // 업데이트할 사용자의 아이디
            String newUsername = msgName; // 새로운 사용자 이름
            String newPw = msgPw; // 새로운 비밀번호

            String query = "UPDATE User SET userid = ?, username = ?, userpw = ? WHERE userid = ? ";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, userId);
            pstmt.setString(2, newUsername);
            pstmt.setString(3, newPw);
            pstmt.setString(4, userId);

            int rowsAffected = pstmt.executeUpdate();

            // JOptionPane을 통해 메시지 보여주기
            if (rowsAffected == 1) {
                JOptionPane.showMessageDialog(null, "회원정보 수정이 완료되었습니다.");
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "회원정보 수정에 실패하였습니다.");
                return false;
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }

    public boolean delete(String msgId) {
        String url = "jdbc:mysql://localhost:3306/yujung";
        String user = "root";
        String password = "00000000";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, user, password);


            String query = "DELETE FROM User WHERE userid = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, msgId);

            int rowsAffected = pstmt.executeUpdate();
            // JOptionPane을 통해 메시지 보여주기
            if (rowsAffected == 1) {
                JOptionPane.showMessageDialog(null, "회원정보 삭제가 완료되었습니다.");
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "회원정보 삭제에 실패하였습니다.");
                return false;
            }
        } catch (Exception e3) {
            e3.printStackTrace();
            return false;
        }
    }
}