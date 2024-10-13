/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Category;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Legion
 */
public class CategoryDAO extends DBContext {

    private Connection connection;

    public CategoryDAO() {
        try {
            this.connection = getConnection();
        } catch (Exception e) {
            System.out.println("Connect failed");
        }
    }

    public String getCategoryNameById(int categoryId) {
        // SQL query to retrieve category name by category ID
        String query = "SELECT Name FROM [dbo].[Category] WHERE ID = ?";
        try (PreparedStatement statement = connection.prepareStatement(query);) {
            statement.setInt(1, categoryId);

            // Execute the query
            ResultSet rs = statement.executeQuery();

            // Check if result set is not empty
            if (rs.next()) {
                // Retrieve category name from result set
                return rs.getString("Name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQLException
        }

        return null;
    }

    public List<Category> getCategories() {
        List<Category> categories = new ArrayList<>();

        String sql = "SELECT ID, Name FROM Category WHERE IsDeleted = 0;";

        try (PreparedStatement statement = connection.prepareStatement(sql); ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                Category category = new Category(
                        rs.getInt("ID"),
                        rs.getString("Name")
                );
                categories.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categories;
    }

    public List<Category> getCategories(String searchQuery, int offset, int limit) {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT [ID], [Name], [IsDeleted], [CreatedAt], [CreatedBy] FROM [dbo].[Category] "
                + "WHERE [Name] LIKE ? ORDER BY [CreatedAt] DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, "%" + searchQuery + "%");
            preparedStatement.setInt(2, offset);
            preparedStatement.setInt(3, limit);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Category category = new Category();
                category.setID(rs.getInt("ID"));
                category.setCategoryName(rs.getString("Name"));
                category.setIsDeleted(rs.getBoolean("IsDeleted"));
                category.setCreatedAt(rs.getTimestamp("CreatedAt"));
                category.setCreatedBy(rs.getInt("CreatedBy"));
                categories.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

    public int getTotalCategoryCount(String searchQuery) {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM [dbo].[Category] WHERE [Name] LIKE ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, "%" + searchQuery + "%");

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }
    
    
    public boolean addCategory(Category category) {
        String sql = "INSERT INTO [dbo].[Category] ([Name], [IsDeleted], [CreatedAt], [CreatedBy]) VALUES (?, ?, GETDATE(), ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
             
            preparedStatement.setString(1, category.getCategoryName());
            preparedStatement.setBoolean(2, category.isIsDeleted());
            preparedStatement.setInt(3, category.getCreatedBy());
            
           return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateCategory(Category category) {
        String sql = "UPDATE [dbo].[Category] SET [Name] = ?, [IsDeleted] = ? WHERE [ID] = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
             
            preparedStatement.setString(1, category.getCategoryName());
            preparedStatement.setBoolean(2, category.isIsDeleted());
            preparedStatement.setInt(3, category.getID());
            
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
