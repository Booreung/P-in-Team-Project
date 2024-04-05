package src.naver.pin_project.game_feature;

public class GameDTO {
    private String userid;
    private String username;
    private String userpw;
    private int game_code;
    private String game_date;
    private int game_point;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserpw() {
        return userpw;
    }

    public void setUserpw(String userpw) {
        this.userpw = userpw;
    }

    public int getGame_code() {
        return game_code;
    }

    public void setGame_code(int game_code) {
        this.game_code = game_code;
    }



    public int getGame_point() {
        return game_point;
    }

    public void setGame_point(int game_point) {
        this.game_point = game_point;
    }
}

