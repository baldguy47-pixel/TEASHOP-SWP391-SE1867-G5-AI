package DAO;

import Model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDAO {

    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    public UserDAO() {
        try {
            // Initialize the connection in the constructor
            conn = new DBContext().getConnection();
        } catch (Exception ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Create (Register)
    public boolean registerUser(User user) {
        String query = "INSERT INTO User (Email, Password, Fullname, Gender, Address, Phone, CreatedBy, Avatar) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            ps = conn.prepareStatement(query);
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getFullname());
            ps.setString(4, user.getGender());
            ps.setString(5, user.getAddress());
            ps.setString(6, user.getPhone());
            ps.setInt(7, user.getCreatedBy());
            ps.setString(8, "https://www.svgrepo.com/show/452030/avatar-default.svg");
            int result = ps.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            closeResources();
        }
        return false;
    }

    // Read (Get User by Id)
    public User getUserById(int id) {
        String query = "SELECT * FROM User WHERE ID = ?";
        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("ID"));
                user.setEmail(rs.getString("Email"));
                user.setPassword(rs.getString("Password"));
                user.setFullname(rs.getString("Fullname"));
                user.setGender(rs.getString("Gender"));
                user.setAddress(rs.getString("Address"));
                user.setPhone(rs.getString("Phone"));
                user.setIsDeleted(rs.getBoolean("IsDeleted"));
                user.setCreatedAt(rs.getDate("CreatedAt"));
                user.setCreatedBy(rs.getInt("CreatedBy"));
                user.setAvatar(rs.getString("Avatar"));
                user.setChangeHistory(rs.getString("ChangeHistory"));
                return user;
            }
        } catch (SQLException e) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            closeResources();
        }
        return null;
    }

    // Read (Get User by Email)
    public User getUserByEmail(String email) {
        String query = "SELECT * FROM User WHERE Email = ?";
        try {
            ps = conn.prepareStatement(query);
            ps.setString(1, email);
            rs = ps.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("ID"));
                user.setEmail(rs.getString("Email"));
                user.setPassword(rs.getString("Password"));
                user.setFullname(rs.getString("Fullname"));
                user.setGender(rs.getString("Gender"));
                user.setAddress(rs.getString("Address"));
                user.setPhone(rs.getString("Phone"));
                user.setIsDeleted(rs.getBoolean("IsDeleted"));
                user.setCreatedAt(rs.getDate("CreatedAt"));
                user.setCreatedBy(rs.getInt("CreatedBy"));
                user.setAvatar(rs.getString("Avatar"));
                user.setChangeHistory(rs.getString("ChangeHistory"));
                return user;
            }
        } catch (SQLException e) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            closeResources();
        }
        return null;
    }

    // Get all users
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String query = "SELECT * FROM User";
        try {
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("ID"));
                user.setEmail(rs.getString("Email"));
                user.setPassword(rs.getString("Password"));
                user.setFullname(rs.getString("Fullname"));
                user.setGender(rs.getString("Gender"));
                user.setAddress(rs.getString("Address"));
                user.setPhone(rs.getString("Phone"));
                user.setIsDeleted(rs.getBoolean("IsDeleted"));
                user.setCreatedAt(rs.getDate("CreatedAt"));
                user.setCreatedBy(rs.getInt("CreatedBy"));
                user.setAvatar(rs.getString("Avatar"));
                user.setChangeHistory(rs.getString("ChangeHistory"));
                userList.add(user);
            }
        } catch (SQLException e) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            closeResources();
        }
        return userList;
    }

    // Get all users with pagination
    public List<User> getAllUsers(int pageNumber, int pageSize) {
        List<User> userList = new ArrayList<>();
        String query = "SELECT * FROM `User` ORDER BY ID LIMIT ? OFFSET ?";
        int offset = (pageNumber - 1) * pageSize;

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, pageSize);   // Set the maximum number of results
            ps.setInt(2, offset);      // Set the offset (starting point)

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    User user = new User();
                    user.setId(rs.getInt("ID"));
                    user.setEmail(rs.getString("Email"));
                    user.setPassword(rs.getString("Password"));
                    user.setFullname(rs.getString("Fullname"));
                    user.setGender(rs.getString("Gender"));
                    user.setAddress(rs.getString("Address"));
                    user.setPhone(rs.getString("Phone"));
                    user.setIsDeleted(rs.getBoolean("IsDeleted"));
                    user.setCreatedAt(rs.getTimestamp("CreatedAt")); // Use Timestamp if needed
                    user.setCreatedBy(rs.getInt("CreatedBy"));
                    user.setAvatar(rs.getString("Avatar"));
                    user.setChangeHistory(rs.getString("ChangeHistory"));

                    userList.add(user);
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return userList;
    }

    public List<User> getAllPagination(int pageNumber, int pageSize) {
        List<User> userList = new ArrayList<>();
        String query = "SELECT * FROM `User` ORDER BY UserID LIMIT ? OFFSET ?"; // Use backticks for the table name
        int offset = (pageNumber - 1) * pageSize;

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, pageSize);  // Limit the number of results
            ps.setInt(2, offset);     // Skip the appropriate number of rows

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    User user = new User();
                    user.setId(rs.getInt("ID")); // Check if the ID matches the correct column
                    user.setEmail(rs.getString("Email"));
                    user.setPassword(rs.getString("Password"));
                    user.setFullname(rs.getString("Fullname"));
                    user.setGender(rs.getString("Gender"));
                    user.setAddress(rs.getString("Address"));
                    user.setPhone(rs.getString("Phone"));
                    user.setIsDeleted(rs.getBoolean("IsDeleted"));
                    user.setCreatedAt(rs.getTimestamp("CreatedAt")); // Use Timestamp for complete date and time
                    user.setCreatedBy(rs.getInt("CreatedBy"));
                    user.setAvatar(rs.getString("Avatar"));
                    user.setChangeHistory(rs.getString("ChangeHistory"));
                    userList.add(user);
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, "Error retrieving users", e); // Use logging
        }
        return userList;
    }

    public List<User> getFilteredUsers(String fullName, String email, String phone, String gender, Boolean status, int pageNumber, int pageSize) {
        List<User> filteredUserList = new ArrayList<>();
        StringBuilder queryBuilder = new StringBuilder("SELECT * FROM `User` WHERE 1=1");

        // Add filter conditions dynamically
        if (fullName != null && !fullName.isEmpty()) {
            queryBuilder.append(" AND Fullname LIKE ?");
        }
        if (email != null && !email.isEmpty()) {
            queryBuilder.append(" AND Email LIKE ?");
        }
        if (phone != null && !phone.isEmpty()) {
            queryBuilder.append(" AND Phone LIKE ?");
        }
        if (gender != null && !gender.isEmpty()) {
            queryBuilder.append(" AND Gender = ?");
        }
        if (status != null) {
            queryBuilder.append(" AND IsDeleted = ?");
        }

        // Add pagination with ORDER BY, LIMIT, and OFFSET
        queryBuilder.append(" ORDER BY ID LIMIT ? OFFSET ?");

        String query = queryBuilder.toString();
        System.out.println(query);  // Debugging purposes

        int offset = (pageNumber - 1) * pageSize;

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            int paramIndex = 1;

            // Set parameters dynamically
            if (fullName != null && !fullName.isEmpty()) {
                ps.setString(paramIndex++, "%" + fullName + "%");
            }
            if (email != null && !email.isEmpty()) {
                ps.setString(paramIndex++, "%" + email + "%");
            }
            if (phone != null && !phone.isEmpty()) {
                ps.setString(paramIndex++, "%" + phone + "%");
            }
            if (gender != null && !gender.isEmpty()) {
                ps.setString(paramIndex++, gender);
            }
            if (status != null) {
                ps.setBoolean(paramIndex++, status);
            }

            // Set pagination parameters
            ps.setInt(paramIndex++, pageSize);
            ps.setInt(paramIndex, offset);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    User user = new User();
                    user.setId(rs.getInt("ID"));
                    user.setEmail(rs.getString("Email"));
                    user.setPassword(rs.getString("Password"));
                    user.setFullname(rs.getString("Fullname"));
                    user.setGender(rs.getString("Gender"));
                    user.setAddress(rs.getString("Address"));
                    user.setPhone(rs.getString("Phone"));
                    user.setIsDeleted(rs.getBoolean("IsDeleted"));
                    user.setCreatedAt(rs.getTimestamp("CreatedAt")); // Use Timestamp for complete date and time
                    user.setCreatedBy(rs.getInt("CreatedBy"));
                    user.setAvatar(rs.getString("Avatar"));
                    user.setChangeHistory(rs.getString("ChangeHistory"));
                    filteredUserList.add(user);
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, "Error retrieving filtered users", e); // Use logging
        }
        return filteredUserList;
    }

    public List<User> getFilteredUsers(String fullName, String email, String gender, Boolean status) {
        List<User> filteredUserList = new ArrayList<>();
        StringBuilder queryBuilder = new StringBuilder("SELECT * FROM User WHERE 1=1");

        // Add filter conditions dynamically
        if (fullName != null && !fullName.isEmpty()) {
            queryBuilder.append(" AND Fullname LIKE ?");
        }
        if (email != null && !email.isEmpty()) {
            queryBuilder.append(" AND Email LIKE ?");
        }
        if (gender != null && !gender.isEmpty()) {
            queryBuilder.append(" AND Gender = ?");
        }
        if (status != null) {
            queryBuilder.append(" AND IsDeleted = ?");
        }

        String query = queryBuilder.toString();
        System.out.println(query);  // Debugging purposes

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            int paramIndex = 1;

            // Set parameters for the prepared statement
            if (fullName != null && !fullName.isEmpty()) {
                ps.setString(paramIndex++, "%" + fullName + "%");
            }
            if (email != null && !email.isEmpty()) {
                ps.setString(paramIndex++, "%" + email + "%");
            }
            if (gender != null && !gender.isEmpty()) {
                ps.setString(paramIndex++, gender);
            }
            if (status != null) {
                ps.setBoolean(paramIndex++, status);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    User user = new User();
                    user.setId(rs.getInt("ID"));
                    user.setEmail(rs.getString("Email"));
                    user.setPassword(rs.getString("Password"));
                    user.setFullname(rs.getString("Fullname"));
                    user.setGender(rs.getString("Gender"));
                    user.setAddress(rs.getString("Address"));
                    user.setPhone(rs.getString("Phone"));
                    user.setIsDeleted(rs.getBoolean("IsDeleted"));
                    user.setCreatedAt(rs.getDate("CreatedAt"));
                    user.setCreatedBy(rs.getInt("CreatedBy"));
                    user.setAvatar(rs.getString("Avatar"));
                    user.setChangeHistory(rs.getString("ChangeHistory"));
                    filteredUserList.add(user);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filteredUserList;
    }

    // Update (Update User)
    public boolean updateUser(User user) {
        String query = "UPDATE User SET Email=?, Password=?, Fullname=?, Gender=?, Address=?, Phone=?, IsDeleted=?, CreatedBy=?, Avatar=?, ChangeHistory=?, Location=? WHERE ID=?";
        try {
            ps = conn.prepareStatement(query);
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getFullname());
            ps.setString(4, user.getGender());
            ps.setString(5, user.getAddress());
            ps.setString(6, user.getPhone());
            ps.setBoolean(7, user.isIsDeleted());
            ps.setInt(8, user.getCreatedBy());
            ps.setString(9, user.getAvatar());
            ps.setString(10, user.getChangeHistory());
            ps.setString(11, user.getLocation());
            ps.setInt(12, user.getId());
            int result = ps.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            closeResources();
        }
        return false;
    }

    // Delete (Delete User)
    public boolean deleteUser(int userID) {
        String query = "DELETE FROM User WHERE ID=?";
        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, userID);
            int result = ps.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            closeResources();
        }
        return false;
    }

    // Login
    public User loginUser(String email, String password) {
        String query = "SELECT * FROM drinkingorder.user WHERE Email = ? AND Password = ?";
        try {
            ps = conn.prepareStatement(query);
            ps.setString(1, email);
            ps.setString(2, password);
            rs = ps.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("ID"));
                user.setEmail(rs.getString("Email"));
                user.setPassword(rs.getString("Password"));
                user.setFullname(rs.getString("Fullname"));
                user.setGender(rs.getString("Gender"));
                user.setAddress(rs.getString("Address"));
                user.setPhone(rs.getString("Phone"));
                user.setIsDeleted(rs.getBoolean("IsDeleted"));
                user.setCreatedAt(rs.getDate("CreatedAt"));
                user.setCreatedBy(rs.getInt("CreatedBy"));
                user.setAvatar(rs.getString("Avatar"));
                user.setChangeHistory(rs.getString("ChangeHistory"));
                user.setLocation(rs.getString("Location"));
                return user;
            }
        } catch (SQLException e) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            closeResources();
        }
        return null;
    }

    private void closeResources() {

    }
}
