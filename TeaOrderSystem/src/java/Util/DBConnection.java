package util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    private static Connection con = null;

    public static Connection getConnection() {
        if (con == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/TeaOrderSystem", "teauser", "yourpassword");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return con;
    }
}
