
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
        String sql = "SELECT DISTINCT p.*, c.Name as CategoryName FROM Product p "
                + "JOIN ProductDetail pd ON p.ID = pd.ProductID "
                + "JOIN Category c ON p.CategoryID = c.ID "
                + "WHERE p.IsDeleted = 0 AND pd.IsDeleted = 0 ";

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

        // Adjust pagination for MySQL
        sql += " ORDER BY p.CreatedAt DESC LIMIT ? OFFSET ?";
        params.add(pageSize);
        params.add((pageNumber - 1) * pageSize);

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

    public List<Product> getProductsByPage2(int pageNumber, int pageSize, String searchQuery, String categoryId, Double minPrice, Double maxPrice, String size) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT p.*, c.Name as CategoryName, pd.Price, pd.Size, pd.ID as PDID FROM Product p "
                + "JOIN ProductDetail pd ON p.ID = pd.ProductID "
                + "JOIN Category c ON p.CategoryID = c.ID "
                + "WHERE 1=1 ";

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

        // Adjust pagination for MySQL
        sql += " ORDER BY p.CreatedAt DESC LIMIT ? OFFSET ?";
        params.add(pageSize);
        params.add((pageNumber - 1) * pageSize);

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            // Set parameters for the prepared statement
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

                    // Fetch the product detail by its ID
                    ProductDetail productDetail = getProductDetailById(rs.getInt("PDID"));
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

        String sql = "SELECT DISTINCT\n"
                + "p.ID AS ProductID,\n"
                + "p.Name AS ProductName,\n"
                + "c.Name AS CategoryName\n"
                + "FROM Product p\n"
                + "INNER JOIN Category c ON p.CategoryID = c.ID\n"
                + "WHERE p.IsDeleted = 0\n"
                + "ORDER BY p.ID ASC\n"
                + "LIMIT ? OFFSET ?"; // Correct MySQL syntax

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, pageSize); // First parameter for LIMIT
            statement.setInt(2, offset);    // Second parameter for OFFSET

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Product product = new Product();
                product.setProductId(resultSet.getInt("ProductID"));
                product.setProductName(resultSet.getString("ProductName"));
                product.setCategoryName(resultSet.getString("CategoryName"));

                // Assuming you have a method to get product details by product ID
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

    public int countTotalProducts() {
        int totalProducts = 0;

        String sql = "SELECT COUNT(*) AS TotalProducts FROM Product WHERE IsDeleted = 0";

        try (PreparedStatement statement = connection.prepareStatement(sql); ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                totalProducts = resultSet.getInt("TotalProducts");
            }
        } catch (SQLException ex) {
            System.out.println("countTotalProducts: " + ex.getMessage());
        }

        return totalProducts;
    }

    public List<Topping> getAllToppings(int productId) {
        List<Topping> toppings = new ArrayList<>();
        String query = "SELECT ID, ToppingName, Price, IsDeleted, CreatedDate, LastUpdated, Img, ProductID FROM Topping WHERE IsDeleted = 0 and ProductID = ?";

        try (
                PreparedStatement ps = connection.prepareStatement(query);) {
            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Topping topping = new Topping();
                topping.setId(rs.getInt("ID"));
                topping.setToppingName(rs.getString("ToppingName"));
                topping.setPrice(rs.getDouble("Price"));
                topping.setDeleted(rs.getBoolean("IsDeleted"));
                topping.setCreatedDate(rs.getDate("CreatedDate").toLocalDate());
                topping.setLastUpdated(rs.getDate("LastUpdated").toLocalDate());
                topping.setImg(rs.getString("Img"));
                topping.setProductId(rs.getInt("ProductID"));
                toppings.add(topping);
            }
        } catch (SQLException e) {
            System.out.println("getAllToppings: " + e.getMessage());
        }
        return toppings;
    }

    public List<ProductDetail> getProductDetailsByProductId(int productId) {
        List<ProductDetail> productDetails = new ArrayList<>();

        String sql = "SELECT "
                + "pd.ID AS ProductDetailID, "
                + "pd.ImageURL, "
                + "pd.Size, "
                + "pd.Stock, "
                + "pd.price, "
                + "pd.discount, "
                + "pd.CreatedAt, "
                + "pd.CreatedBy "
                + "FROM ProductDetail pd "
                + "WHERE pd.ProductID = ? AND pd.IsDeleted = 0";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, productId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                ProductDetail productDetail = new ProductDetail();
                productDetail.setProductDetailId(resultSet.getInt("ProductDetailID"));
                productDetail.setImageURL(resultSet.getString("ImageURL"));
                productDetail.setSize(resultSet.getString("Size"));
                productDetail.setStock(resultSet.getInt("Stock"));
                productDetail.setPrice(resultSet.getDouble("price"));
                productDetail.setDiscount(resultSet.getInt("discount"));
                productDetail.setCreatedAt(resultSet.getTimestamp("CreatedAt"));
                productDetail.setCreatedBy(resultSet.getInt("CreatedBy"));

                productDetails.add(productDetail);
            }
        } catch (SQLException ex) {
            System.out.println("getProductDetailsByProductId: " + ex.getMessage());
        }

        return productDetails;
    }

    public Product getProductById(int productId) {
        Product product = null;

        String productSql = "SELECT "
                + "p.ID AS ProductID, "
                + "p.Name AS ProductName, "
                + "c.Name AS CategoryName, "
                + "p.CreatedAt AS ProductCreatedAt, "
                + "p.description AS description, "
                + "p.CreatedBy AS ProductCreatedBy, "
                + "p.CategoryID "
                + "FROM Product p "
                + "INNER JOIN Category c ON p.CategoryID = c.ID "
                + "WHERE p.ID = ? AND p.IsDeleted = 0";

        try (PreparedStatement productStatement = connection.prepareStatement(productSql)) {
            productStatement.setInt(1, productId);

            try (ResultSet resultSet = productStatement.executeQuery()) {
                if (resultSet.next()) {
                    product = new Product();
                    product.setProductId(resultSet.getInt("ProductID"));
                    product.setProductName(resultSet.getString("ProductName"));
                    product.setCategoryId(resultSet.getInt("CategoryID"));
                    product.setCategoryName(resultSet.getString("CategoryName"));
                    product.setCreatedAt(resultSet.getTimestamp("ProductCreatedAt"));
                    product.setCreatedBy(resultSet.getInt("ProductCreatedBy"));
                    product.setDescription(resultSet.getString("description"));
                    product.setProductDetail(getProductDetailByProductId(productId));
                }
            }
        } catch (SQLException ex) {
            System.out.println("getProductById: " + ex.getMessage());
        }

        return product;
    }

    public Product getProductByIdDcm(int productId) {
        Product product = null;

        String productSql = "SELECT "
                + "p.ID AS ProductID, "
                + "p.Name AS ProductName, "
                + "c.Name AS CategoryName, "
                + "c.ID AS CategoryID, "
                + "p.CreatedAt AS ProductCreatedAt, "
                + "p.description AS description, "
                + "p.CreatedBy AS ProductCreatedBy "
                + "FROM Product p "
                + "INNER JOIN Category c ON p.CategoryID = c.ID "
                + "WHERE p.ID = ?";

        try (PreparedStatement productStatement = connection.prepareStatement(productSql)) {
            productStatement.setInt(1, productId);

            try (ResultSet resultSet = productStatement.executeQuery()) {
                if (resultSet.next()) {
                    product = new Product();
                    product.setProductId(resultSet.getInt("ProductID"));
                    product.setProductName(resultSet.getString("ProductName"));
                    product.setCategoryId(resultSet.getInt("CategoryID"));
                    product.setCategoryName(resultSet.getString("CategoryName"));
                    product.setCreatedAt(resultSet.getTimestamp("ProductCreatedAt"));
                    product.setCreatedBy(resultSet.getInt("ProductCreatedBy"));
                    product.setDescription(resultSet.getString("description"));
                    product.setProductDetail(getProductDetailByProductId(productId));
                }
            }
        } catch (SQLException ex) {
            System.out.println("getProductById: " + ex.getMessage());
        }

        return product;
    }

    public ProductDetail getProductDetailById(int productDetailId) {
        ProductDetail productDetail = null;

        String sql = "SELECT "
                + "ID AS ProductDetailID, "
                + "ProductID, "
                + "ImageURL, "
                + "Size, "
                + "price, "
                + "discount, "
                + "Stock, "
                + "CreatedAt, "
                + "Hold, "
                + "CreatedBy, "
                + "importPrice "
                + "FROM ProductDetail "
                + "WHERE ID = ? AND IsDeleted = 0";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, productDetailId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    productDetail = new ProductDetail();
                    productDetail.setProductDetailId(resultSet.getInt("ProductDetailID"));
                    productDetail.setProductId(resultSet.getInt("ProductID"));
                    productDetail.setImageURL(resultSet.getString("ImageURL"));
                    productDetail.setSize(resultSet.getString("Size"));
                    productDetail.setStock(resultSet.getInt("Stock"));
                    productDetail.setPrice(resultSet.getDouble("price"));
                    productDetail.setDiscount(resultSet.getInt("discount"));
                    productDetail.setCreatedAt(resultSet.getTimestamp("CreatedAt"));
                    productDetail.setCreatedBy(resultSet.getInt("CreatedBy"));
                    productDetail.setHold(resultSet.getInt("Hold"));
                    productDetail.setImportPrice(resultSet.getInt("importPrice"));
                }
            }
        } catch (SQLException ex) {
            System.out.println("getProductDetailById: " + ex.getMessage());
        }

        return productDetail;
    }

    public List<Product> getProductsByPage(int pageNumber, int pageSize, String searchQuery, String categoryId) {
        List<Product> products = new ArrayList<>();
        int offset = (pageNumber - 1) * pageSize;
        String sql = "SELECT p.ID as productId, p.Name as productName, p.CategoryID, c.Name as categoryName, "
                + "p.IsDeleted, p.CreatedAt, p.CreatedBy, p.description "
                + "FROM Product p "
                + "JOIN Category c ON p.CategoryID = c.ID "
                + "WHERE p.IsDeleted = 0 AND c.IsDeleted = 0 "
                + "AND (p.Name LIKE ? OR ? IS NULL) "
                + "AND (p.CategoryID = ? OR ? IS NULL) Order by p.ID "
                + "LIMIT ? OFFSET ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(2, searchQuery != null && !searchQuery.isBlank() ? "%" + searchQuery + "%" : null);
            statement.setString(1, "%" + searchQuery + "%");
            statement.setString(4, categoryId != null && !categoryId.isBlank() ? categoryId : null);
            statement.setString(3, categoryId);
            statement.setInt(5, pageSize);
            statement.setInt(6, offset);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Product product = new Product();
                    product.setProductId(resultSet.getInt("ProductID"));
                    product.setProductName(resultSet.getString("ProductName"));
                    product.setCategoryName(resultSet.getString("CategoryName"));
                    product.setCreatedAt(resultSet.getTimestamp("CreatedAt"));
                    product.setCreatedBy(resultSet.getInt("CreatedBy"));
                    product.setDescription(resultSet.getString("description"));
                    product.setProductDetail(getProductDetailByProductId(product.getProductId()));
                    product.setProductDetail(getProductDetailByProductId(product.getProductId()));
                    product.setListProductDetail(getListProductDetailsByProductId(product.getProductId()));
                    products.add(product);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

    public List<ProductDetail> getListProductDetailsByProductId(int productId) {
        List<ProductDetail> productDetails = new ArrayList<>();
        String query = "SELECT ID, ProductID, ImageURL, Size, Color, Stock, IsDeleted, CreatedAt, CreatedBy, price, discount "
                + "FROM ProductDetail WHERE ProductID = ? and IsDeleted != 1";

        try (
                PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, productId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ProductDetail productDetail = new ProductDetail();
                    productDetail.setProductDetailId(rs.getInt("ID"));
                    productDetail.setProductId(rs.getInt("ProductID"));
                    productDetail.setImageURL(rs.getString("ImageURL"));
                    productDetail.setSize(rs.getString("Size"));
                    productDetail.setColor(rs.getString("Color"));
                    productDetail.setStock(rs.getInt("Stock"));
                    productDetail.setCreatedAt(rs.getTimestamp("CreatedAt"));
                    productDetail.setCreatedBy(rs.getInt("CreatedBy"));
                    productDetail.setPrice(rs.getDouble("price"));
                    productDetail.setDiscount(rs.getInt("discount"));
                    productDetails.add(productDetail);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productDetails;
    }

    public int countTotalProducts(String searchQuery, String categoryId) {
        int total = 0;
        String sql = "SELECT COUNT(*) FROM Product p "
                + "JOIN Category c ON p.CategoryID = c.ID "
                + "WHERE p.IsDeleted = 0 AND c.IsDeleted = 0 "
                + "AND (p.Name LIKE ? OR ? IS NULL) "
                + "AND (p.CategoryID = ? OR ? IS NULL);";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(2, searchQuery != null && !searchQuery.isBlank() ? "%" + searchQuery + "%" : null);
            statement.setString(1, "%" + searchQuery + "%");
            statement.setString(4, categoryId != null && !categoryId.isBlank() ? categoryId : null);
            statement.setString(3, categoryId);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    total = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return total;
    }

    public List<Product> getThreeLastestProducts() {
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
                + "ORDER BY p.CreatedAt ASC LIMIT 3";

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

    public void updateQuantity(int orderId, int mode) {
        String GET_PRODUCT_DETAIL_IDS_BY_ORDER_ID_SQL
                = "SELECT ProductDetailID, quantity "
                + "FROM OrderDetail "
                + "WHERE OrderID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_PRODUCT_DETAIL_IDS_BY_ORDER_ID_SQL)) {

            preparedStatement.setInt(1, orderId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    updateProductDetailQuantity(resultSet.getInt(1), resultSet.getInt(2) * mode);

                }
            }
        } catch (SQLException e) {
            System.out.println("getProductDetailIDsByOrderID: " + e.getMessage());
        }

    }

    public void updateHoldQuantity(int orderId, int mode) {
        String GET_PRODUCT_DETAIL_IDS_BY_ORDER_ID_SQL
                = "SELECT ProductDetailID, quantity "
                + "FROM OrderDetail "
                + "WHERE OrderID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_PRODUCT_DETAIL_IDS_BY_ORDER_ID_SQL)) {

            preparedStatement.setInt(1, orderId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    updateProductDetailHold(resultSet.getInt(1), resultSet.getInt(2) * mode);
                }
            }
        } catch (SQLException e) {
            System.out.println("getProductDetailIDsByOrderID: " + e.getMessage());
        }

    }

    public void updateProductDetailQuantity(int productDetailId, int quantity) {
        String UPDATE_PRODUCT_DETAIL_QUANTITY_SQL
                = "UPDATE ProductDetail "
                + "SET Stock = Stock - ? "
                + "WHERE ID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PRODUCT_DETAIL_QUANTITY_SQL)) {

            preparedStatement.setInt(1, quantity);
            preparedStatement.setInt(2, productDetailId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("updateProductDetailQuantity: " + e.getMessage());
        }
    }

    public void updateProductDetailHold(int productDetailId, int hold) {
        String UPDATE_PRODUCT_DETAIL_QUANTITY_SQL
                = "UPDATE ProductDetail "
                + "SET Hold = Hold - ? "
                + "WHERE ID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PRODUCT_DETAIL_QUANTITY_SQL)) {

            preparedStatement.setInt(1, hold);
            preparedStatement.setInt(2, productDetailId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("updateProductDetailQuantity: " + e.getMessage());
        }
    }

    public int addProduct(Product product) {
        int generatedId = -1; // Initialize to a default value if insertion fails
        String query = "INSERT INTO Product (Name, CategoryID, CreatedBy, Description, IsDeleted) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, product.getProductName());
            statement.setInt(2, product.getCategoryId());
            statement.setInt(3, product.getCreatedBy());
            statement.setString(4, product.getDescription());
            statement.setBoolean(5, product.getIsDeleted());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                // Retrieve the generated keys
                ResultSet rs = statement.getGeneratedKeys();
                if (rs.next()) {
                    generatedId = rs.getInt(1); // Assuming the generated key is an integer
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return generatedId;
    }

    public boolean updateProduct(Product product) {
        boolean success = false;
        String query = "UPDATE Product SET Name = ?, Description = ?, IsDeleted = ? "
                + "WHERE ID = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, product.getProductName());
            statement.setString(2, product.getDescription());
            statement.setInt(3, product.getIsDeleted() ? 1 : 0);
            statement.setInt(4, product.getProductId());

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                success = true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return success;
    }

    public List<Product> getFilteredProducts(String name, int categoryId, Boolean isDeleted, int pageNumber, int pageSize) {
        List<Product> productList = new ArrayList<>();
        int offset = (pageNumber - 1) * pageSize;

        String query = "SELECT p.ID as ProductID, p.Name as ProductName, p.CategoryID, p.IsDeleted, c.Name as CategoryName, "
                + "p.CreatedAt, p.CreatedBy, p.Description "
                + "FROM Product p "
                + "INNER JOIN Category c ON p.CategoryID = c.ID "
                + "WHERE (p.Name LIKE ? OR ? IS NULL) ";

        // Append condition for categoryId if it's not -1
        if (categoryId != -1) {
            query += "AND p.CategoryID = ? ";
        }

        // Append condition for isDeleted if it's not null
        if (isDeleted != null) {
            query += "AND p.IsDeleted = ? ";
        }

        query += "ORDER BY p.ID "
                + "LIMIT ?  OFFSET ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            int parameterIndex = 1;
            stmt.setString(parameterIndex++, name != null ? "%" + name + "%" : null);
            stmt.setString(parameterIndex++, name != null ? "%" + name + "%" : null);

            // Set categoryId parameter if it's not -1
            if (categoryId != -1) {
                stmt.setInt(parameterIndex++, categoryId);
            }

            // Set isDeleted parameter if it's not null
            if (isDeleted != null) {
                stmt.setBoolean(parameterIndex++, isDeleted);
            }

            stmt.setInt(parameterIndex++, pageSize);
            stmt.setInt(parameterIndex++, offset);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Product product = new Product();
                    product.setProductId(rs.getInt("ProductID"));
                    product.setProductName(rs.getString("ProductName"));
                    product.setCategoryId(rs.getInt("CategoryID"));
                    product.setCategoryName(rs.getString("CategoryName"));
                    product.setCreatedAt(rs.getTimestamp("CreatedAt"));
                    product.setCreatedBy(rs.getInt("CreatedBy"));
                    product.setDescription(rs.getString("Description"));
                    product.setIsDeleted(rs.getBoolean("IsDeleted"));

                    productList.add(product);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productList;
    }

    public int getFilteredProducts(String name, int categoryId, Boolean isDeleted) {
        int totalProducts = 0;

        String query = "SELECT COUNT(*) "
                + "FROM Product p "
                + "INNER JOIN Category c ON p.CategoryID = c.ID "
                + "WHERE (p.Name LIKE ? OR ? IS NULL) ";

        // Append condition for categoryId if it's not -1
        if (categoryId != -1) {
            query += "AND p.CategoryID = ? ";
        }

        // Append condition for isDeleted if it's not null
        if (isDeleted != null) {
            query += "AND p.IsDeleted = ? ";
        }

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            int parameterIndex = 1;
            stmt.setString(parameterIndex++, name != null ? "%" + name + "%" : null);
            stmt.setString(parameterIndex++, name != null ? "%" + name + "%" : null);

            // Set categoryId parameter if it's not -1
            if (categoryId != -1) {
                stmt.setInt(parameterIndex++, categoryId);
            }

            // Set isDeleted parameter if it's not null
            if (isDeleted != null) {
                stmt.setBoolean(parameterIndex++, isDeleted);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    totalProducts = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return totalProducts;
    }

    public boolean addProductDetail(ProductDetail productDetail) {
        boolean success = false;
        String query = "INSERT INTO ProductDetail (ProductID, ImageURL, Size, Stock, price, discount) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, productDetail.getProductId());
            statement.setString(2, productDetail.getImageURL());
            statement.setString(3, productDetail.getSize());
            statement.setInt(4, productDetail.getStock());
            statement.setDouble(5, productDetail.getPrice());
            statement.setInt(6, productDetail.getDiscount());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                success = true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return success;
    }

    public boolean updateProductDetail(ProductDetail productDetail) {
        boolean success = false;
        String query = "UPDATE ProductDetail SET ImageURL = ?, Size = ?, Stock = ?, price = ?, discount = ? "
                + "WHERE ID = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, productDetail.getImageURL());
            statement.setString(2, productDetail.getSize());
            statement.setInt(3, productDetail.getStock());
            statement.setDouble(4, productDetail.getPrice());
            statement.setInt(5, productDetail.getDiscount());
            statement.setInt(6, productDetail.getProductDetailId());

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                success = true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return success;
    }
