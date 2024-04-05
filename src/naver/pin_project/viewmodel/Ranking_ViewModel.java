package src.naver.pin_project.viewmodel;

import src.naver.pin_project.data.Ranking;
import src.naver.pin_project.db.OjdbcConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Ranking_ViewModel {

    // 랭킹 정보를 가져오는 메서드
    public static List<List<Ranking>> getRankingData() {
        // 반환할 랭킹 데이터를 저장할 리스트 생성
        List<List<Ranking>> rankingData = new ArrayList<>();
        // 실시간 랭킹 데이터를 저장할 리스트 생성
        List<Ranking> realTimeRankingList = new ArrayList<>();
        // 월별 랭킹 데이터를 저장할 리스트 생성
        List<Ranking> monthlyRankingList = new ArrayList<>();

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // 데이터베이스 연결
            conn = OjdbcConnection.getConnection();
            // 랭킹 정보를 가져오기 위한 SQL 쿼리문 작성
            String realTimeQuery = "SELECT u.userid, u.username, g.game_date, g.game_point FROM Game g JOIN User u ON g.userid = u.userid";
            // PreparedStatement를 사용하여 쿼리 실행
            stmt = conn.prepareStatement(realTimeQuery);
            rs = stmt.executeQuery();

            // ResultSet에서 데이터를 가져와서 랭킹 객체 생성 후 리스트에 추가
            while (rs.next()) {
                String userId = rs.getString("userid");
                String userName = rs.getString("username");
                Date gameDate = rs.getDate("game_date");
                int gameCode = rs.getInt("game_point");

                Ranking ranking = new Ranking(userId, userName, gameDate, gameCode);
                realTimeRankingList.add(ranking);
                monthlyRankingList.add(ranking);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                // 사용한 자원 반환
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


        // 실시간 랭킹 데이터와 월별 랭킹 데이터를 각각의 리스트에 저장 후 반환할 리스트에 추가
        rankingData.add(realTimeRankingList);
        rankingData.add(monthlyRankingList);

        return rankingData;
    }
}
