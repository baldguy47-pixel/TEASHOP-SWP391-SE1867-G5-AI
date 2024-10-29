package DAO;

import Model.Topping;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Legion
 */
public class ToppingDAO extends DBContext {

    private Connection connection;

    public ToppingDAO() {
        try {
            this.connection = getConnection();
        } catch (Exception e) {
            System.out.println("Connect failed");
        }
    }

    public List<Topping> getToppingsByList(String toppings) {
        // Split the comma-separated topping names into an array
        String[] toppingIDs = toppings.split(",");

        // Initialize the list to hold Topping objects
        List<Topping> toppingsList = new ArrayList<>();

        // Create the SQL query with placeholders for the topping names
        StringBuilder sqlQuery = new StringBuilder("SELECT * FROM Topping WHERE ID IN (");

        // Create placeholders for each topping name
        for (int i = 0; i < toppingIDs.length; i++) {
            sqlQuery.append("?");
            if (i < toppingIDs.length - 1) {
                sqlQuery.append(", ");
            }
        }
        sqlQuery.append(")");

        // Establish the database connection
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery.toString())) {
            
            // Set the values for the placeholders in the query
            for (int i = 0; i < toppingIDs.length; i++) {
                preparedStatement.setString(i + 1, toppingIDs[i].trim());
            }

            // Execute the query
            ResultSet resultSet = preparedStatement.executeQuery();
            // Process the results
            while (resultSet.next()) {
                
                Topping topping = new Topping();
                topping.setId(resultSet.getInt("id"));
                topping.setToppingName(resultSet.getString("toppingName"));
                topping.setPrice(resultSet.getDouble("price"));
                topping.setDeleted(resultSet.getBoolean("isDeleted"));
                topping.setCreatedDate(resultSet.getDate("createdDate").toLocalDate());
                topping.setLastUpdated(resultSet.getDate("lastUpdated").toLocalDate());
                topping.setImg(resultSet.getString("img"));
                topping.setProductId(resultSet.getInt("productId"));
                
                // Add the topping to the list
                toppingsList.add(topping);
            }
        } catch (SQLException e) {
            System.out.println("getToppingsByList: " + e.getMessage());
        }
        

        // Return the list of toppings
        return toppingsList;
    }
    
        public List<Topping> getAllToppingsWithDeleted(){
        String sql = "select * from Topping";
        List<Topping> toppings = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int id = rs.getInt("ID");
                String name = rs.getString("ToppingName");
                double price = rs.getDouble("Price");
                boolean isDeleted = rs.getBoolean("isDeleted");
                LocalDate createdAt = rs.getDate("createdDate").toLocalDate();
                LocalDate updatedAt = rs.getDate("lastUpdated").toLocalDate();
                String img = rs.getString("img");
                toppings.add(new Topping(id, name, price, isDeleted, createdAt, updatedAt, img, 1));
            }
        } catch (Exception e) {
        }
        return toppings;
    }
    
    public void updateTopping(Topping topping){
        String sql = "UPDATE Topping "
                + "SET img=?, "
                + "ToppingName=?, "
                + "Price=?, "
                + "isDeleted=?, "
                + "lastUpdated=? WHERE ID=?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, topping.getImg());
            ps.setString(2, topping.getToppingName());
            ps.setDouble(3, topping.getPrice());
            ps.setBoolean(4, topping.isIsDeleted());
            ps.setDate(5, Date.valueOf(LocalDate.now()));
            ps.setInt(6, topping.getId());
            ps.executeUpdate();
        } catch (Exception e) {
        }
    }
    
    public void addTopping(Topping topping){
        String sql = "Insert into Topping (img, ToppingName, Price, isDeleted) "
                + "Values (?,?,?,?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, topping.getImg());
            ps.setString(2, topping.getToppingName());
            ps.setDouble(3, topping.getPrice());
            ps.setBoolean(4, topping.isIsDeleted());
            ps.executeUpdate();
        } catch (Exception e) {
        }
    }

}