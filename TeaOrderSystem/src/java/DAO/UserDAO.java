package dao;

import Model.User;
import java.sql.*;

public class UserDAO {
    private Connection con;

    public UserDAO(Connection con) {
        this.con = con;
    }

    public boolean registerUser(User user) {
        boolean status = false;
        try {
            String query = "INSERT INTO users(name, email, password) VALUES (?, ?, ?)";
            PreparedStatement ps = this.con.prepareStatement(query);
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            int i = ps.executeUpdate();
            if (i > 0) {
                status = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }
}
