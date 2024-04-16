package src.naver.pin_project.viewmodel;

import src.naver.pin_project.db.OjdbcConnection;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;


public class MyPage_ViewModel {
    //사용자 정보를 update 하는 메서드
    public boolean update(String msgId, String msgName, String msgPw, String newmsgId) {
        //데이터베이스 연결을 위한 Connection 초기화
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

            int rowsAffected = pstmt.executeUpdate(); //업데이트 된 행의 수를 반환 받음

            // JOptionPane을 통해 update 성공 여부 메시지 출력
            if (rowsAffected == 1) {
                JOptionPane.showMessageDialog(null, "회원정보 수정이 완료되었습니다.");
                return true;

            } else {
                JOptionPane.showMessageDialog(null, "회원정보 수정에 실패하였습니다.");
                return false;
            }
        } catch (Exception e2) {
            //e2.printStackTrace();
            JOptionPane.showMessageDialog(null,"이미 존재하는 아이디입니다.");
            return false;
        }

    }

    // 사용자 정보를 delete 하는 메서드
    public boolean delete(String msgId) {
        Connection con = null;

        try {
            con = OjdbcConnection.getConnection();

            // 정말 삭제하시겠습니까?를 묻는 메시지 창
            int option = JOptionPane.showConfirmDialog(null, "정말 삭제하시겠습니까?", "삭제 확인", JOptionPane.YES_NO_OPTION);

            if (option == JOptionPane.YES_OPTION) {
                String query = "DELETE FROM user WHERE userid = ?";
                PreparedStatement pstmt = con.prepareStatement(query);
                pstmt.setString(1, msgId);

                int rowsAffected = pstmt.executeUpdate(); // 삭제 된 행의 수를 반환 받음

                // JOptionPane을 통해 delete 성공 여부 메시지 출력
                if (rowsAffected == 1) {
                    JOptionPane.showMessageDialog(null, "회원정보 삭제가 완료되었습니다.");
                    return true;
                } else {
                    JOptionPane.showMessageDialog(null, "회원정보 삭제에 실패하였습니다.");
                    return false;
                }
            } else if (option == JOptionPane.NO_OPTION || option == JOptionPane.CLOSED_OPTION) {
                // "아니요"를 누르거나 창을 닫은 경우
                return false;
            }
        } catch (Exception e3) {
            e3.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}