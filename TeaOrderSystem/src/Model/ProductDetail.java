package Model;

import DAO.CategoryDAO;
import DAO.OrderDAO;
import DAO.ProductDAO;
import DAO.ToppingDAO;
import java.sql.*;
import java.util.List;

/**
 *
 * @author Link
 */
public class ProductDetail {
    private int productDetailId;
    private int productId;
    private String imageURL;
    private String size;
    private String color;
    private int stock;
    private double price;
    private int discount;
    private Timestamp createdAt;
    private int createdBy;
    private int orderDetailId;
    private int hold;
    private float importPrice;
    private String categoryName;
    private String productName;
    private boolean isDeleted;
    private String description;
    private String listToppingIDs;
    
    private List<Topping> listTopping;

    public String getListToppingIDs() {
        return listToppingIDs;
    }

    public void setListToppingIDs(String listToppingIDs) {
        this.listToppingIDs = listToppingIDs;
    }
    
    

    public List<Topping> getListTopping() {
        return new ToppingDAO().getToppingsByList(listToppingIDs);
    }

    public void setListTopping(List<Topping> listTopping) {
        this.listTopping = listTopping;
    }
    
    
    

    public boolean getIsDeleted() {
        return new ProductDAO().getProductById(productId).getIsDeleted();
    }

    public String getDescription() {
        return new ProductDAO().getProductById(productId).getDescription();
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
    
   

    public void setProductName(String productName) {
        this.productName = productName;
    }
    
    
    public String getProductName() {
        return new ProductDAO().getProductById(productId).getProductName();
    }
    
    

    public String getCategoryName() {
        return new ProductDAO().getProductById(productId).getCategoryName();
    }

    
    
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    
    

    public void setOrderDetailId(int orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public int getHold() {
        return hold;
    }

    public void setHold(int hold) {
        this.hold = hold;
    }

    public float getImportPrice() {
        return importPrice;
    }

    public void setImportPrice(float importPrice) {
        this.importPrice = importPrice;
    }
    
    public int getOrderDetailId() {
        return orderDetailId;
    }
    
        
    public boolean isFeedbacked() {
        return new OrderDAO().isFeedbacked(orderDetailId);
    }
    
    private int buyQuantity;
    
    public int getProductId() {
        return productId;
    }

    public int getBuyQuantity() {
        return buyQuantity;
    }

    public void setBuyQuantity(int buyQuantity) {
        this.buyQuantity = buyQuantity;
    }

    // Getters and setters
    public void setProductId(int productId) {    
        this.productId = productId;
    }

    public int getProductDetailId() {
        return productDetailId;
    }

    public void setProductDetailId(int productDetailId) {
        this.productDetailId = productDetailId;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }
    
    
    public String getCateogryName() {
        return new ProductDAO().getProductById(productId).getCategoryName();
    }
    
    public Product getProduct() {
        return new ProductDAO().getProductByIdDcm(productId);
    }

    @Override
    public String toString() {
        return "ProductDetail{" + "productDetailId=" + productDetailId + ", imageURL=" + imageURL + ", size=" + size + ", color=" + color + ", stock=" + stock + ", price=" + price + ", discount=" + discount + ", createdAt=" + createdAt + ", createdBy=" + createdBy + '}';
    }
    
    
}