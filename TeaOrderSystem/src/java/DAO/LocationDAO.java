/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Location;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LocationDAO {

    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    public LocationDAO() {
        try {
            // Initialize the connection in the constructor
            conn = new DBContext().getConnection();
        } catch (Exception ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Location> getAllLocation() {
        String sql = "select * from drinkingorder.`Location` where isDeleted = 0";
        List<Location> list = new ArrayList<>();
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Location location = new Location();
                location.setID(rs.getInt("ID"));
                location.setLocationName(rs.getString("LocationName"));
                location.setIsDeleted(rs.getInt("IsDeleted"));
                list.add(location);
            }
        } catch (SQLException e) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return list;
    }

    public List<Location> getAllLocationWithoutDeleted() {
        String sql = "select * from drinkingorder.`Location`";
        List<Location> list = new ArrayList<>();
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Location location = new Location();
                location.setID(rs.getInt("ID"));
                location.setLocationName(rs.getString("LocationName"));
                location.setIsDeleted(rs.getInt("IsDeleted"));
                list.add(location);
            }
        } catch (SQLException e) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, e);
        }
        return list;
    }

    public boolean updateLocation(Location location) {
        boolean isSuccess = false;
        String sql = "UPDATE location SET LocationName = ?, IsDeleted = ? WHERE ID = ?";

        try {
            ps = conn.prepareStatement(sql);

            ps.setString(1, location.getLocationName());
            ps.setInt(2, location.getIsDeleted());
            ps.setInt(3, location.getID());
            isSuccess = ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace(); // In lỗi ra console để dễ dàng theo dõi
        }
        return isSuccess;
    }

    public boolean addLocation(Location location) {
        boolean isSuccess = false;
        String sql = "INSERT INTO location (LocationName, CreatedBy) VALUES (?, ?)";

        try {
            ps = conn.prepareStatement(sql);

            // Thiết lập các giá trị cho các tham số truy vấn
            ps.setString(1, location.getLocationName());
            ps.setInt(2, location.getCreatedBy());

            // Thực hiện cập nhật và kiểm tra kết quả
            isSuccess = ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace(); // In lỗi ra console để dễ dàng theo dõi
        }
        return isSuccess;
    }

    public static void main(String[] args) {
        for (Location l : new LocationDAO().getAllLocation()) {
            System.out.println(l);
        }
    }

}
