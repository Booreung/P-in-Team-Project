import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class OjdbcConnection {
    private static String driverName = "com.mysql.cj.jdbc.Driver";
    private static String url = "jdbc:mysql://localhost:3306/pin?useSSL=false&allowPublicKeyRetrieval=true&useLegacyAuth=false";
    private static String user = "sm";
    private static String pw = "tmdals1234!!";

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
