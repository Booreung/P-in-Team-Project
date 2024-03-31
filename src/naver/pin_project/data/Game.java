package src.naver.pin_project.data;

import java.util.Date;

public class Game {
    private int game_code;
    private int point;
    private Date game_date;

    public int getGame_code() {
        return game_code;
    }

    public void setGameCode(int game_code) {
        this.game_code = game_code;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public Date getGame_date() {
        return game_date;
    }

    public void setGame_date(Date game_date) {
        this.game_date = game_date;
    }
}
