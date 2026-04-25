import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    static Connection con;
    public static Connection createConnection() {
        try {
            // MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // உங்க MySQL Username Password மாத்திக்கோங்க
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/busbooking", "root", "root"); 
        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }
}
