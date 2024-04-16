package src.naver.pin_project.viewmodel;

import src.naver.pin_project.db.OjdbcConnection;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;


public class MyPage_ViewModel {
    //사용자 정보 update 하는 메서드
    public boolean update(String msgId, String msgName,String msgPw, String newmsgId)  {
        // 데이터베이스 연결을 위한 Connection 초기화
        Connection con = null;

        try {
            con = OjdbcConnection.getConnection(); //데이터베이스 연결

            String userId = msgId; // 업데이트할 사용자의 아이디
            String newUsername = msgName; // 새로운 사용자 이름
            String newPw = msgPw; // 새로운 비밀번호

            String query = "UPDATE user SET userid = ?, username = ?, userpw = ? WHERE userid = ?";

            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, newmsgId);
            pstmt.setString(2, newUsername);
            pstmt.setString(3, newPw);
            pstmt.setString(4, userId);
            //System.out.println();

            int rowsAffected = pstmt.executeUpdate();   //업데이트 된 행의 수를 반환 받음

            // JOptionPane을 통해 업데이트 성공 여부 메시지 출력
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

    //사용자 정보를 삭제하는 메서드
    public boolean delete(String msgId) {
        Connection con = null;

        try {
            con = OjdbcConnection.getConnection();

            String query = "DELETE FROM user WHERE userid = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, msgId);

            int rowsAffected = pstmt.executeUpdate(); //삭제 된 행의 수를 반환 받음

            // JOptionPane을 통해 삭제 성공 여부 메시지 출력
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
