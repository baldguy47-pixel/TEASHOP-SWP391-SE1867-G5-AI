package dao;

import model.Category;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import util.Database;

public class CategoryDAO {
    public boolean addCategory(Category category) {
        try (Connection conn = Database.getConnection()) {
            String query = "INSERT INTO categories (name) VALUES (?)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, category.getName());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        try (Connection conn = Database.getConnection()) {
            String query = "SELECT * FROM categories";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                Category category = new Category(rs.getString("name"));
                category.setId(rs.getInt("id"));
                categories.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }
}
