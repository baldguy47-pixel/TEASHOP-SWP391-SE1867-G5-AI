package Model;

import DAO.OrderDAO;
import DAO.ProductDAO;
import java.util.Date;

public class OrderDetail {
    private int id;
    private int orderId;
    private int productDetailId;
    private boolean isDeleted;
    private Date createdAt;
    private int createdBy;
    private int quantity;
    private String toppingId;

    public String getToppingId() {
        return toppingId;
    }

    public void setToppingId(String toppingId) {
        this.toppingId = toppingId;
    }
    
    
    
    private ProductDetail productDetail;

    public ProductDetail getProductDetail() {
        return productDetail;
    }

    public void setProductDetail(ProductDetail productDetail) {
        this.productDetail = productDetail;
    }
    
    

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getProductDetailId() {
        return productDetailId;
    }

    public void setProductDetailId(int productDetailId) {
        this.productDetailId = productDetailId;
    }

    public boolean isIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public Order getOrder() {
        return new OrderDAO().getOrderById(orderId);
    }
    
    public ProductDetail getDetail() {
        return new ProductDAO().getProductDetailById(productDetailId);
    }

}