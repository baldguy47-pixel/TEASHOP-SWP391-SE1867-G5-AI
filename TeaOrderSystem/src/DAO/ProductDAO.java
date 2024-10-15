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
    
    public List<String> getAvailableSizes() {
        List<String> sizes = new ArrayList<>();
        String sql = "SELECT DISTINCT Size FROM ProductDetail WHERE IsDeleted = 0";

        try (PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                sizes.add(rs.getString("Size"));
            }
        } catch (SQLException e) {
            System.out.println("getAvailableSizes: " + e.getMessage());
        }

        return sizes;
    }

    public List<Product> getProductsByPage(int pageNumber, int pageSize, String searchQuery, String categoryId, Double minPrice, Double maxPrice, String size) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT Distinct  p.*, c.Name as CategoryName FROM Product p "
                + "                 JOIN ProductDetail pd ON p.ID = pd.ProductID "
                + "				 Join Category c on p.CategoryID = c.ID"
                + "                 WHERE p.IsDeleted = 0 AND pd.IsDeleted = 0 ";

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

        sql += " ORDER BY p.CreatedAt DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        params.add((pageNumber - 1) * pageSize);
        params.add(pageSize);

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Product product = new Product();
                    product.setProductId(rs.getInt("ID"));
                    product.setProductName(rs.getString("Name"));
                    product.setCategoryId(rs.getInt("CategoryID"));
                    product.setCategoryName(rs.getString("CategoryName"));
                    product.setCreatedAt(rs.getTimestamp("CreatedAt"));
                    product.setCreatedBy(rs.getInt("CreatedBy"));
                    product.setDescription(rs.getString("Description"));
                    product.setIsDeleted(rs.getBoolean("IsDeleted"));

                    ProductDetail productDetail = getProductDetailByProductId(product.getProductId());
                    product.setProductDetail(productDetail);
                    products.add(product);
                }
            }
        } catch (SQLException e) {
            System.out.println("getProductsByPage: " + e.getMessage());
        }

        return products;
    }
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();

        String sql = "SELECT "
                + "p.ID AS ProductID, "
                + "p.Name AS ProductName, "
                + "c.Name AS CategoryName, "
                + "pd.ID AS ProductDetailID, "
                + "pd.ImageURL, "
                + "pd.Size, "
                + " "
                + "pd.Stock, "
                + "pd.price AS price, "
                + "pd.discount AS discount, "
                + "pd.CreatedAt AS ProductDetailCreatedAt, "
                + "pd.CreatedBy AS ProductDetailCreatedBy "
                + "FROM Product p "
                + "INNER JOIN Category c ON p.CategoryID = c.ID "
                + "INNER JOIN ProductDetail pd ON p.ID = pd.ProductID "
                + "WHERE p.IsDeleted = 0 AND pd.IsDeleted = 0 "
                + "ORDER BY p.ID ASC";

        try (PreparedStatement statement = connection.prepareStatement(sql); ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Product product = new Product();
                product.setProductId(resultSet.getInt("ProductID"));
                product.setProductName(resultSet.getString("ProductName"));
                product.setCategoryName(resultSet.getString("CategoryName"));

                ProductDetail productDetail = new ProductDetail();
                productDetail.setProductDetailId(resultSet.getInt("ProductDetailID"));
                productDetail.setImageURL(resultSet.getString("ImageURL"));
                productDetail.setSize(resultSet.getString("Size"));

                productDetail.setStock(resultSet.getInt("Stock"));
                productDetail.setCreatedAt(resultSet.getTimestamp("ProductDetailCreatedAt"));
                productDetail.setCreatedBy(resultSet.getInt("ProductDetailCreatedBy"));
                productDetail.setPrice(resultSet.getDouble("price"));
                productDetail.setDiscount(resultSet.getInt("discount"));

                product.setProductDetail(productDetail);

                products.add(product);
            }
        } catch (SQLException ex) {
            System.out.println("getAllProducts: " + ex.getMessage());
        }

        return products;
    }

    public List<Product> getProductsByPage(int pageNumber, int pageSize) {
        List<Product> products = new ArrayList<>();
        int offset = (pageNumber - 1) * pageSize;

        String sql = "SELECT distinct\n"
                + "p.ID AS ProductID,\n"
                + "p.Name AS ProductName,\n"
                + "c.Name AS CategoryName\n"
                + "FROM Product p\n"
                + "INNER JOIN Category c ON p.CategoryID = c.ID\n"
                + "WHERE p.IsDeleted = 0\n"
                + "ORDER BY p.ID ASC\n"
                + "LIMIT ? OFFSET ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(2, offset);
            statement.setInt(1, pageSize);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Product product = new Product();
                product.setProductId(resultSet.getInt("ProductID"));
                product.setProductName(resultSet.getString("ProductName"));
                product.setCategoryName(resultSet.getString("CategoryName"));

                ProductDetail productDetail = getProductDetailByProductId(product.getProductId());
                product.setProductDetail(productDetail);
                products.add(product);
            }
        } catch (SQLException ex) {
            System.out.println("getProductsByPage: " + ex.getMessage());
        }

        return products;
    }

    public ProductDetail getProductDetailByProductId(int productId) {

        String sql = "SELECT "
                + "pd.ID AS ProductDetailID, "
                + "pd.ImageURL, "
                + "pd.Size, "
                + "pd.Stock, "
                + "pd.price, "
                + "pd.discount, "
                + "pd.CreatedAt, "
                + "pd.Hold, "
                + "pd.ImportPrice, "
                + "pd.CreatedBy "
                + "FROM ProductDetail pd "
                + "WHERE pd.ProductID = ? AND pd.IsDeleted = 0";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, productId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                ProductDetail productDetail = new ProductDetail();
                productDetail.setProductDetailId(resultSet.getInt("ProductDetailID"));
                productDetail.setImageURL(resultSet.getString("ImageURL"));
                productDetail.setSize(resultSet.getString("Size"));
                productDetail.setStock(resultSet.getInt("Stock"));
                productDetail.setPrice(resultSet.getDouble("price"));
                productDetail.setDiscount(resultSet.getInt("discount"));
                productDetail.setCreatedAt(resultSet.getTimestamp("CreatedAt"));
                productDetail.setCreatedBy(resultSet.getInt("CreatedBy"));
                productDetail.setHold(resultSet.getInt("Hold"));
                productDetail.setImportPrice(resultSet.getFloat("ImportPrice"));
                return productDetail;
            }
        } catch (SQLException ex) {
            System.out.println("getProductDetailsByProductId: " + ex.getMessage());
        }

        return new ProductDetail();
    }
