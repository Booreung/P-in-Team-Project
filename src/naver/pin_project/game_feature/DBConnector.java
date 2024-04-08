package src.naver.pin_project.game_feature;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DBConnector {

    private Connection conn;
    private PreparedStatement stmt;
    private ResultSet rs;
    private String user_name;
    GameDTO gameDTO = new GameDTO();
    GameMenu gm = new GameMenu();
    public void loadUserInfo() throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection("jdbc:mysql://175.209.41.173:3306/kiosk_database", "siho", "1234");

    }
    public void saveGameRecords(int game_code, String userid, int game_point) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection("jdbc:mysql://175.209.41.173:3308/pin", "root", "q123");



        System.out.println(game_code+" / "+userid+" / "+game_point);
        insertGameRecords(game_code,userid,game_point);
    }
    public void searchUserName(String userid){

        try{
            String query = "SELECT username FROM user WHERE userid = ?";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, userid);
            rs = stmt.executeQuery();
            if(rs.next()){
                user_name = (rs.getString("username"));
                System.out.println("db: "+user_name);
                gameDTO.setUsername(user_name);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        close();

    }
    public void insertGameRecords(int game_code, String userid, int game_point){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date());

        try{
            String query = "INSERT INTO game (game_code, game_date, userid, game_point) VALUES (?, ?, ?, ?)";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, game_code);
            stmt.setString(2, date);
            stmt.setString(3, userid);
            stmt.setInt(4, game_point);
            stmt.executeUpdate();
            System.out.println(game_code+" / "+date+" / "+userid+" / "+game_point);
        }catch (SQLException e){
            e.printStackTrace();
        }
        close();

    }
    public void close(){
        try{
            if(rs !=null)rs.close();
            if(stmt != null) stmt.close();
            if(conn != null) conn.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}

