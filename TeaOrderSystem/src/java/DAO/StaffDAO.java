package DAO;

import Model.Staff;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StaffDAO {

    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    public StaffDAO() {
        try {
            // Initialize the connection in the constructor
            conn = new DBContext().getConnection();
        } catch (Exception ex) {
            Logger.getLogger(StaffDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Create (Register)
    public boolean registerStaff(Staff staff) {
        String query = "INSERT INTO Staff (Email, Password, Fullname, Gender, Address, Phone, Role, CreatedBy, Avatar) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            ps = conn.prepareStatement(query);
            ps.setString(1, staff.getEmail());
            ps.setString(2, staff.getPassword());
            ps.setString(3, staff.getFullname());
            ps.setString(4, staff.getGender());
            ps.setString(5, staff.getAddress());
            ps.setString(6, staff.getPhone());
            ps.setInt(7, staff.getRole());
            ps.setInt(8, staff.getCreatedBy());
            ps.setString(9, "https://www.svgrepo.com/show/452030/avatar-default.svg");
            int result = ps.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            Logger.getLogger(StaffDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            closeResources();
        }
        return false;
    }

    // Read (Get Staff by Id)
    public Staff getStaffById(int id) {
        String query = "SELECT * FROM Staff WHERE ID = ?";
        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                Staff staff = new Staff();
                staff.setId(rs.getInt("ID"));
                staff.setEmail(rs.getString("Email"));
                staff.setPassword(rs.getString("Password"));
                staff.setFullname(rs.getString("Fullname"));
                staff.setGender(rs.getString("Gender"));
                staff.setAddress(rs.getString("Address"));
                staff.setPhone(rs.getString("Phone"));
                staff.setRole(rs.getInt("Role"));
                staff.setIsDeleted(rs.getBoolean("IsDeleted"));
                staff.setCreatedAt(rs.getDate("CreatedAt"));
                staff.setCreatedBy(rs.getInt("CreatedBy"));
                staff.setAvatar(rs.getString("Avatar"));
                return staff;
            }
        } catch (SQLException e) {
            Logger.getLogger(StaffDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            closeResources();
        }
        return null;
    }

    // Read (Get Staff by Email)
    public Staff getStaffByEmail(String email) {
        String query = "SELECT * FROM Staff WHERE Email = ?";
        try {
            ps = conn.prepareStatement(query);
            ps.setString(1, email);
            rs = ps.executeQuery();
            if (rs.next()) {
                Staff staff = new Staff();
                staff.setId(rs.getInt("ID"));
                staff.setEmail(rs.getString("Email"));
                staff.setPassword(rs.getString("Password"));
                staff.setFullname(rs.getString("Fullname"));
                staff.setGender(rs.getString("Gender"));
                staff.setAddress(rs.getString("Address"));
                staff.setPhone(rs.getString("Phone"));
                staff.setRole(rs.getInt("Role"));
                staff.setIsDeleted(rs.getBoolean("IsDeleted"));
                staff.setCreatedAt(rs.getDate("CreatedAt"));
                staff.setCreatedBy(rs.getInt("CreatedBy"));
                staff.setAvatar(rs.getString("Avatar"));
                return staff;
            }
        } catch (SQLException e) {
            Logger.getLogger(StaffDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            closeResources();
        }
        return null;
    }

    // Get all staff with pagination
    public List<Staff> getAllStaff(int pageNumber, int pageSize) {
        List<Staff> staffList = new ArrayList<>();
        String query = "SELECT * FROM Staff LIMIT ? OFFSET ?";

        // Calculate the offset
        int offset = (pageNumber - 1) * pageSize;

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, pageSize); // Set the limit
            ps.setInt(2, offset);    // Set the offset

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Staff staff = new Staff();
                    staff.setId(rs.getInt("ID"));
                    staff.setEmail(rs.getString("Email"));
                    staff.setPassword(rs.getString("Password"));
                    staff.setFullname(rs.getString("Fullname"));
                    staff.setGender(rs.getString("Gender"));
                    staff.setAddress(rs.getString("Address"));
                    staff.setPhone(rs.getString("Phone"));
                    staff.setRole(rs.getInt("Role"));
                    staff.setIsDeleted(rs.getBoolean("IsDeleted"));
                    staff.setCreatedAt(rs.getDate("CreatedAt"));
                    staff.setCreatedBy(rs.getInt("CreatedBy"));
                    staff.setAvatar(rs.getString("Avatar"));
                    staffList.add(staff);
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(StaffDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            closeResources();
        }

        return staffList;
    }

    // Get all staff with pagination
    public List<Staff> getAllStaff() {
        List<Staff> staffList = new ArrayList<>();
        String query = "SELECT * FROM Staff";
        try {
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                Staff staff = new Staff();
                staff.setId(rs.getInt("ID"));
                staff.setEmail(rs.getString("Email"));
                staff.setPassword(rs.getString("Password"));
                staff.setFullname(rs.getString("Fullname"));
                staff.setGender(rs.getString("Gender"));
                staff.setAddress(rs.getString("Address"));
                staff.setPhone(rs.getString("Phone"));
                staff.setRole(rs.getInt("Role"));
                staff.setIsDeleted(rs.getBoolean("IsDeleted"));
                staff.setCreatedAt(rs.getDate("CreatedAt"));
                staff.setCreatedBy(rs.getInt("CreatedBy"));
                staff.setAvatar(rs.getString("Avatar"));
                staffList.add(staff);
            }
        } catch (SQLException e) {
            Logger.getLogger(StaffDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            closeResources();
        }
        return staffList;
    }

    public List<Staff> getFilteredStaff(String fullName, String email, String phone, int role,
            String gender, Boolean isDeleted, int pageNumber, int pageSize) {
        List<Staff> filteredUserList = new ArrayList<>();
        StringBuilder queryBuilder = new StringBuilder(
                "SELECT * FROM Staff WHERE 1=1");

        if (fullName != null && !fullName.isEmpty()) {
            queryBuilder.append(" AND Fullname LIKE ?");
        }
        if (email != null && !email.isEmpty()) {
            queryBuilder.append(" AND Email LIKE ?");
        }
        if (phone != null && !phone.isEmpty()) {
            queryBuilder.append(" AND Phone LIKE ?");
        }
        if (role != -1) {
            queryBuilder.append(" AND Role = ?");
        }
        if (gender != null && !gender.isEmpty()) {
            queryBuilder.append(" AND Gender = ?");
        }
        if (isDeleted != null) {
            queryBuilder.append(" AND IsDeleted = ?");
        }

        // Add pagination logic
        queryBuilder.append(" LIMIT ? OFFSET ?");

        String query = queryBuilder.toString();
        System.out.println(query); // Debugging purpose

        int offset = (pageNumber - 1) * pageSize; // Calculate the offset

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            int paramIndex = 1;

            // Set dynamic parameters for the prepared statement
            if (fullName != null && !fullName.isEmpty()) {
                ps.setString(paramIndex++, "%" + fullName + "%");
            }
            if (email != null && !email.isEmpty()) {
                ps.setString(paramIndex++, "%" + email + "%");
            }
            if (phone != null && !phone.isEmpty()) {
                ps.setString(paramIndex++, "%" + phone + "%");
            }
            if (role != -1) {
                ps.setInt(paramIndex++, role);
            }
            if (gender != null && !gender.isEmpty()) {
                ps.setString(paramIndex++, gender);
            }
            if (isDeleted != null) {
                ps.setBoolean(paramIndex++, isDeleted);
            }

            // Set pagination parameters
            ps.setInt(paramIndex++, pageSize); // Set the limit
            ps.setInt(paramIndex, offset);      // Set the offset

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Staff staff = new Staff();
                    staff.setId(rs.getInt("ID"));
                    staff.setEmail(rs.getString("Email"));
                    staff.setPassword(rs.getString("Password"));
                    staff.setFullname(rs.getString("Fullname"));
                    staff.setGender(rs.getString("Gender"));
                    staff.setAddress(rs.getString("Address"));
                    staff.setPhone(rs.getString("Phone"));
                    staff.setRole(rs.getInt("Role"));
                    staff.setIsDeleted(rs.getBoolean("IsDeleted"));
                    staff.setCreatedAt(rs.getDate("CreatedAt"));
                    staff.setCreatedBy(rs.getInt("CreatedBy"));
                    staff.setAvatar(rs.getString("Avatar"));
                    filteredUserList.add(staff);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return filteredUserList;
    }

    public List<Staff> getFilteredStaff(String fullName, String email, int role, String gender, Boolean isDeleted) {
        List<Staff> filteredUserList = new ArrayList<>();
        StringBuilder queryBuilder = new StringBuilder("SELECT * FROM Staff WHERE 1=1");
        // Dynamically build query conditions
        if (fullName != null && !fullName.isEmpty()) {
            queryBuilder.append(" AND Fullname LIKE ?");
        }
        if (email != null && !email.isEmpty()) {
            queryBuilder.append(" AND Email LIKE ?");
        }
        if (role != -1) {
            queryBuilder.append(" AND Role = ?");
        }
        if (gender != null && !gender.isEmpty()) {
            queryBuilder.append(" AND Gender LIKE ?");
        }
        if (isDeleted != null) {
            queryBuilder.append(" AND IsDeleted = ?");
        }

        String query = queryBuilder.toString();
        System.out.println(query); // For debugging

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            int paramIndex = 1;

            // Set parameters for the prepared statement
            if (fullName != null && !fullName.isEmpty()) {
                ps.setString(paramIndex++, "%" + fullName + "%");
            }
            if (email != null && !email.isEmpty()) {
                ps.setString(paramIndex++, "%" + email + "%");
            }
            if (role != -1) {
                ps.setInt(paramIndex++, role);
            }
            if (gender != null && !gender.isEmpty()) {
                ps.setString(paramIndex++, "%" + gender + "%");
            }
            if (isDeleted != null) {
                ps.setBoolean(paramIndex++, isDeleted);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Staff staff = new Staff();
                    staff.setId(rs.getInt("ID"));
                    staff.setEmail(rs.getString("Email"));
                    staff.setPassword(rs.getString("Password"));
                    staff.setFullname(rs.getString("Fullname"));
                    staff.setGender(rs.getString("Gender"));
                    staff.setAddress(rs.getString("Address"));
                    staff.setPhone(rs.getString("Phone"));
                    staff.setRole(rs.getInt("Role"));
                    staff.setIsDeleted(rs.getBoolean("IsDeleted"));
                    staff.setCreatedAt(rs.getDate("CreatedAt"));
                    staff.setCreatedBy(rs.getInt("CreatedBy"));
                    staff.setAvatar(rs.getString("Avatar"));
                    filteredUserList.add(staff);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filteredUserList;
    }

    // Update (Update Staff)
    public boolean updateStaff(Staff staff) {
        String query = "UPDATE Staff SET Email=?, Password=?, Fullname=?, Gender=?, Address=?, Phone=?, Role=?, IsDeleted=?, CreatedBy=?, Avatar=? WHERE ID=?";
        try {
            ps = conn.prepareStatement(query);
            ps.setString(1, staff.getEmail());
            ps.setString(2, staff.getPassword());
            ps.setString(3, staff.getFullname());
            ps.setString(4, staff.getGender());
            ps.setString(5, staff.getAddress());
            ps.setString(6, staff.getPhone());
            ps.setInt(7, staff.getRole());
            ps.setBoolean(8, staff.isIsDeleted());
            ps.setInt(9, staff.getCreatedBy());
            ps.setString(10, staff.getAvatar());
            ps.setInt(11, staff.getId());
            int result = ps.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            Logger.getLogger(StaffDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            closeResources();
        }
        return false;
    }

    // Delete (Delete Staff)
    public boolean deleteStaff(int staffID) {
        String query = "DELETE FROM Staff WHERE ID=?";
        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, staffID);
            int result = ps.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            Logger.getLogger(StaffDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            closeResources();
        }
        return false;
    }

    // Login
    public Staff loginStaff(String email, String password) {
        String query = "SELECT * FROM Staff WHERE Email = ? AND Password = ?";
        try {
            ps = conn.prepareStatement(query);
            ps.setString(1, email);
            ps.setString(2, password);
            rs = ps.executeQuery();
            if (rs.next()) {
                Staff staff = new Staff();
                staff.setId(rs.getInt("ID"));
                staff.setEmail(rs.getString("Email"));
                staff.setPassword(rs.getString("Password"));
                staff.setFullname(rs.getString("Fullname"));
                staff.setGender(rs.getString("Gender"));
                staff.setAddress(rs.getString("Address"));
                staff.setPhone(rs.getString("Phone"));
                staff.setRole(rs.getInt("Role"));
                staff.setIsDeleted(rs.getBoolean("IsDeleted"));
                staff.setCreatedAt(rs.getDate("CreatedAt"));
                staff.setCreatedBy(rs.getInt("CreatedBy"));
                staff.setAvatar(rs.getString("Avatar"));
                return staff;
            }
        } catch (SQLException e) {
            Logger.getLogger(StaffDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            closeResources();
        }
        return null;
    }

    private void closeResources() {

    }
}
