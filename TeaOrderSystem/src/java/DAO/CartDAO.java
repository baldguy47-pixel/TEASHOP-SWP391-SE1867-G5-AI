package DAO;

import Model.Cart;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Timestamp;
import java.util.Date;

/**
 *
 * @author Legion
 */
public class CartDAO {

    private Connection connection;
    private PreparedStatement stmt;
    private ResultSet rs;

    public CartDAO() {
        try {
            // Initialize the connection in the constructor
            connection = new DBContext().getConnection();
        } catch (Exception ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Cart> getAllCarts(int userId) {
        List<Cart> carts = new ArrayList<>();
        try {

            // SQL query to retrieve all cart items
            String query = "SELECT id, userId, productDetailId, quantity, isDeleted, createdAt, createdBy, toppingList FROM [dbo].[Cart] where userId = ?";

            // Prepare the statement
            stmt = connection.prepareStatement(query);
            stmt.setInt(1, userId);

            // Execute the query
            rs = stmt.executeQuery();

            // Process the result set
            while (rs.next()) {
                int id = rs.getInt("id");
                int productDetailId = rs.getInt("productDetailId");
                int quantity = rs.getInt("quantity");
                boolean isDeleted = rs.getBoolean("isDeleted");
                Timestamp createdAt = rs.getTimestamp("createdAt");
                int createdBy = rs.getInt("createdBy");
                String toppings = rs.getString("toppingList");

                // Create a Cart object and add it to the list
                Cart cart = new Cart(id, userId, productDetailId, quantity, isDeleted, new Date(createdAt.getTime()), createdBy);
                cart.setToppings(toppings);
                carts.add(cart);
            }
        } catch (SQLException e) {
            System.out.println("getAllCarts: " + e.getMessage());
        }
        return carts;
    }

    public List<Cart> getAllCarts(int userId, int page, int pageSize, String searchQuery, String category) {
        List<Cart> carts = new ArrayList<>();
        int offset = (page - 1) * pageSize;

        try {
            if (category == null) {
                category = "";
            }
            if (searchQuery == null) {
                searchQuery = "";
            }
            // SQL query with pagination, search, and filter
            String query = "SELECT c.id, c.userId, c.productDetailId, c.quantity, c.isDeleted, c.createdAt, c.createdBy, c.ToppingList "
                    + "FROM [dbo].[Cart] c "
                    + "JOIN [dbo].ProductDetail pd ON c.ProductDetailID = pd.ID "
                    + "JOIN [dbo].Product p ON p.ID = pd.ProductID "
                    + "JOIN [dbo].[Category] cat ON p.CategoryID = cat.ID "
                    + "WHERE c.userId = ? "
                    + "AND cat.Name LIKE '%" + category + "%'"
                    + "AND p.Name LIKE '%" + searchQuery + "%'"
                    + "ORDER BY c.createdAt DESC "
                    + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

            stmt = connection.prepareStatement(query);
            stmt.setInt(1, userId);
            stmt.setInt(2, offset);
            stmt.setInt(3, pageSize);

            rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                int productDetailId = rs.getInt("productDetailId");
                int quantity = rs.getInt("quantity");
                boolean isDeleted = rs.getBoolean("isDeleted");
                Timestamp createdAt = rs.getTimestamp("createdAt");
                int createdBy = rs.getInt("createdBy");
                String toppings = rs.getString("ToppingList");

                Cart cart = new Cart(id, userId, productDetailId, quantity, isDeleted, new Date(createdAt.getTime()), createdBy);
                cart.setToppings(toppings);
                carts.add(cart);
                
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return carts;
    }

    public int getCartCount(int userId, String searchQuery, String category) {
        int count = 0;

        try {
            if (category == null) {
                category = "";
            }
            if (searchQuery == null) {
                searchQuery = "";
            }
            String query = "SELECT COUNT(*) AS total "
                    + "FROM [dbo].[Cart] c "
                    + "JOIN [dbo].ProductDetail pd ON c.ProductDetailID = pd.ID "
                    + "JOIN [dbo].Product p ON p.ID = pd.ProductID "
                    + "JOIN [dbo].[Category] cat ON p.CategoryID = cat.ID "
                    + "WHERE c.userId = ? "
                    + "AND cat.Name LIKE '%" + category + "%'"
                    + "AND p.Name LIKE '%" + searchQuery + "%'";

            stmt = connection.prepareStatement(query);
            stmt.setInt(1, userId);

            rs = stmt.executeQuery();

            if (rs.next()) {
                count = rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public boolean updateCart(int quantity, int cartId) {
        try {
            String sql = "UPDATE Cart SET Quantity = ? WHERE ID = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, quantity);
            stmt.setInt(2, cartId);
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteCart(int cartId) {
        boolean isDeleted = false;

        try {
            String query = "DELETE FROM [dbo].[Cart] WHERE id = ?";

            stmt = connection.prepareStatement(query);
            stmt.setInt(1, cartId);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                isDeleted = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isDeleted;
    }

    public void addToCart(int userId, int productDetailId, int quantity, String toppings) {
        try {
            // Check if the item already exists in the cart
            String selectSQL = "SELECT quantity FROM Cart WHERE userId = ? AND productDetailId = ? AND isDeleted = 0 and toppingList Like ?";
            PreparedStatement selectStmt = connection.prepareStatement(selectSQL);
            selectStmt.setInt(1, userId);
            selectStmt.setInt(2, productDetailId);
            selectStmt.setString(3, toppings);
            rs = selectStmt.executeQuery();
            
            if (rs.next()) {
                // Item exists, update the quantity
                int currentQuantity = rs.getInt("quantity");
                int newQuantity = currentQuantity + quantity;
                
                String updateSQL = "UPDATE Cart SET quantity = ? WHERE userId = ? AND productDetailId = ? AND isDeleted = 0 and toppingList Like ?";
                PreparedStatement updateStmt = connection.prepareStatement(updateSQL);
                updateStmt.setInt(1, newQuantity);
                updateStmt.setInt(2, userId);
                updateStmt.setInt(3, productDetailId);
                updateStmt.setString(4, toppings);
                updateStmt.executeUpdate();
            } else {
                // Item does not exist, insert a new record
                String insertSQL = "INSERT INTO Cart (userId, productDetailId, quantity, isDeleted, createdAt, createdBy, toppingList) VALUES (?, ?, ?, 0, ?, ?, ?)";
                PreparedStatement insertStmt = connection.prepareStatement(insertSQL);
                insertStmt.setInt(1, userId);
                insertStmt.setInt(2, productDetailId);
                insertStmt.setInt(3, quantity);
                insertStmt.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
                insertStmt.setInt(5, userId);  // Assuming userId is also the createdBy
                insertStmt.setString(6, toppings);
                insertStmt.executeUpdate();
            }
        } catch (SQLException ex) {
            System.out.println("addToCart: " + ex.getMessage());
        }
    }

    public int getTotalProductCount(int userId) {
        int totalQuantity = 0;
        try {
            String query = "SELECT Count(*) AS totalQuantity FROM [Cart] WHERE [UserID] = ? AND isDeleted = 0";
            stmt = connection.prepareStatement(query);
            stmt.setInt(1, userId);
            rs = stmt.executeQuery();
            if (rs.next()) {
                totalQuantity = rs.getInt("totalQuantity");
            }
        } catch (SQLException e) {
            System.out.println("getTotalProductCount: " + e.getMessage());
        }
        return totalQuantity;
    }
    
    public void clearCart(int userId) {
        try {
            String query = "Delete from cart where userid = ?";
            stmt = connection.prepareStatement(query);
            stmt.setInt(1, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("clearCart: " + e.getMessage());
        }
    }
}