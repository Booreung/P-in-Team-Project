package src.naver.pin_project.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class OjdbcConnection {
    private static String driverName = "com.mysql.cj.jdbc.Driver";


    private static String url = "jdbc:mysql://175.209.41.173:3308/pin";
    private static String user = "root";
    private static String pw = "q123";



    static {
        try {
            Class.forName(driverName);
        }
        catch (ClassNotFoundException e){
            e.printStackTrace();

        }
    }
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url,user,pw);
    }
}
