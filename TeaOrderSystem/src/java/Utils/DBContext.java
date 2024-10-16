package Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBContext {

    // Configuration for MySQL connection
    private final String serverName = "localhost";
    private final String dbName = "drinkingorder";
    private final String portNumber = "3306"; // Default MySQL port
    private final String userID = "root"; // Replace with your MySQL username
    private final String password = "root"; // Replace with your MySQL password

    // Method to establish a database connection
    public Connection getConnection() {
        Connection conn = null;
        String url = "jdbc:mysql://" + serverName + ":" + portNumber + "/" + dbName + "?allowPublicKeyRetrieval=true&useSSL=true&serverTimezone=UTC";

        try {
            // Load MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Establish the connection
            conn = DriverManager.getConnection(url, userID, password);
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Failed to establish a connection to the MySQL database.");
            e.printStackTrace();
        }

        return conn;
    }
}
