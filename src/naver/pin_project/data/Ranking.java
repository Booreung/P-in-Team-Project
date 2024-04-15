package src.naver.pin_project.data;

import java.util.Date;

public class Ranking {
    private String userId;
    private String userName;
    private Date gameDate;
    private int point;

    //랭킹에 필요한 정보 유저아이디, 유저이름, 게임날짜, 게임점수
    public Ranking(String userId, String userName, Date gameDate, int point) {
        this.userId = userId;
        this.userName = userName;
        this.gameDate = gameDate;
        this.point = point;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getGameDate() {
        return gameDate;
    }

    public void setGameDate(Date gameDate) {
        this.gameDate = gameDate;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }
}
