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
        String query = "INSERT INTO [User] (Email, Password, Fullname, Gender, Address, Phone, CreatedBy, Avatar) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
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
        String query = "SELECT * FROM [User] WHERE ID = ?";
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
        String query = "SELECT * FROM [User] WHERE Email = ?";
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
        String query = "SELECT * FROM [User]";
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
        String query = "SELECT * FROM (SELECT ROW_NUMBER() OVER (ORDER BY ID) AS RowNum, * FROM [User]) AS SubQuery WHERE RowNum BETWEEN ? AND ?";
        int startIndex = (pageNumber - 1) * pageSize + 1;
        int endIndex = pageNumber * pageSize;
        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, startIndex);
            ps.setInt(2, endIndex);
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
    
    public List<User> getAllPagination(int pageNumber, int pageSize) {
        List<User> userList = new ArrayList<>();
        String query = "SELECT * FROM (SELECT ROW_NUMBER() OVER (ORDER BY UserID) AS RowNum, * FROM User) AS SubQuery WHERE RowNum BETWEEN ? AND ?";
        int startIndex = (pageNumber - 1) * pageSize + 1;
        int endIndex = pageNumber * pageSize;
        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, startIndex);
            ps.setInt(2, endIndex);
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
            e.printStackTrace();
        }
        return userList;
    }
    
    public List<User> getFilteredUsers(String fullName, String email, String phone, String gender, Boolean status, int pageNumber, int pageSize) {
        List<User> filteredUserList = new ArrayList<>();
        String query = "SELECT * FROM (SELECT *, ROW_NUMBER() OVER (ORDER BY ID) AS RowNum FROM [User] WHERE 1=1";
        // Add filter conditions
        if (fullName != null && !fullName.isEmpty()) {
            query += " AND Fullname LIKE '%" + fullName + "%'";
        }
        if (email != null && !email.isEmpty()) {
            query += " AND Email LIKE '%" + email + "%'";
        }
        if (phone != null && !phone.isEmpty()) {
            query += " AND Phone LIKE '%" + phone + "%'";
        }
        if (gender != null && !gender.isEmpty()) {
            query += " AND Gender = '" + gender + "'";
        }
        if (status != null) {
            query += " AND IsDeleted = '" + status.toString() + "'";
        }
        // Add pagination
        query += ") AS SubQuery WHERE RowNum BETWEEN ? AND ?";
        System.out.println(query);
        int startIndex = (pageNumber - 1) * pageSize + 1;
        int endIndex = pageNumber * pageSize;
        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, startIndex);
            ps.setInt(2, endIndex);
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
                filteredUserList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filteredUserList;
    }
    
    public List<User> getFilteredUsers(String fullName, String email, String gender, Boolean status) {
        List<User> filteredUserList = new ArrayList<>();
        String query = "SELECT * FROM (SELECT *, ROW_NUMBER() OVER (ORDER BY ID) AS RowNum FROM [User] WHERE 1=1";
        // Add filter conditions
        if (fullName != null && !fullName.isEmpty()) {
            query += " AND Fullname LIKE '%" + fullName + "%'";
        }
        if (email != null && !email.isEmpty()) {
            query += " AND Email LIKE '%" + email + "%'";
        }
        if (gender != null && !gender.isEmpty()) {
            query += " AND Gender = '" + gender + "'";
        }
        if (status != null) {
            query += " AND IsDeleted = '" + status.toString() + "'";
        }
        // Add pagination
        query += ") AS SubQuery";
        try {
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                User staff = new User();
                staff.setId(rs.getInt("ID"));
                staff.setEmail(rs.getString("Email"));
                staff.setPassword(rs.getString("Password"));
                staff.setFullname(rs.getString("Fullname"));
                staff.setGender(rs.getString("Gender"));
                staff.setAddress(rs.getString("Address"));
                staff.setPhone(rs.getString("Phone"));
                staff.setIsDeleted(rs.getBoolean("IsDeleted"));
                staff.setCreatedAt(rs.getDate("CreatedAt"));
                staff.setCreatedBy(rs.getInt("CreatedBy"));
                staff.setAvatar(rs.getString("Avatar"));
                staff.setChangeHistory(rs.getString("ChangeHistory"));
                filteredUserList.add(staff);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filteredUserList;
    }

    // Update (Update User)
   public boolean updateUser(User user) {
        String query = "UPDATE [User] SET Email=?, Password=?, Fullname=?, Gender=?, Address=?, Phone=?, IsDeleted=?, CreatedBy=?, Avatar=?, ChangeHistory=?, Location=? WHERE ID=?";
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
        String query = "DELETE FROM [User] WHERE ID=?";
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
        String query = "SELECT * FROM [user] WHERE Email = ? AND Password = ?";
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
