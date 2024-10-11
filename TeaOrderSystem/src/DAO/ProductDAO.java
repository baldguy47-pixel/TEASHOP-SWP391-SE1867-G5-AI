package DAO;

import Model.Product;
import Model.ProductDetail;
import Model.Topping;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;

/**
 *
 * @author Link
 */
public class ProductDAO extends DBContext {

    private Connection connection;

    public ProductDAO() {
        try {
            this.connection = getConnection();
        } catch (Exception e) {
            System.out.println("Connect failed");
        }
    }

    
    
    public int countTotalProducts(String searchQuery, String categoryId, Double minPrice, Double maxPrice, String size) {
        String sql = "SELECT COUNT(*) FROM Product p "
                + "JOIN ProductDetail pd ON p.ID = pd.ProductID "
                + "WHERE p.IsDeleted = 0 AND pd.IsDeleted = 0";

        List<Object> params = new ArrayList<>();

        if (searchQuery != null && !searchQuery.isEmpty()) {
            sql += " AND p.Name LIKE ?";
            params.add("%" + searchQuery + "%");
        }
        if (categoryId != null && !categoryId.isEmpty()) {
            sql += " AND p.CategoryID = ?";
            params.add(categoryId);
        }
        if (minPrice != null) {
            sql += " AND pd.Price >= ?";
            params.add(minPrice);
        }
        if (maxPrice != null) {
            sql += " AND pd.Price <= ?";
            params.add(maxPrice);
        }
        if (size != null && !size.isEmpty()) {
            sql += " AND pd.Size = ?";
            params.add(size);
        }

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.out.println("countTotalProducts: " + e.getMessage());
        }

        return 0;
    }
