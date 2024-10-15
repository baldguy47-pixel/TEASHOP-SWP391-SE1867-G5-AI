package Model;

import DAO.CartDAO;
import DAO.ProductDAO;
import DAO.ToppingDAO;
import java.util.Date;
import java.util.List;

public class Cart {
    private int id;
    private int userId;
    private int productDetailId;
    private int quantity;
    private boolean isDeleted;
    private Date createdAt;
    private int createdBy;
    private ProductDetail productDetail;
    private String toppings;
    private List<Topping> listTopping;

    public List<Topping> getListTopping() {
        return new ToppingDAO().getToppingsByList(toppings);
    }

    public void setListTopping(List<Topping> listTopping) {
        this.listTopping = listTopping;
    }

    public String getToppings() {
        return toppings;
    }

    public void setToppings(String toppings) {
        this.toppings = toppings;
    }
    
    
    
    

    public Cart() {
    }
    
    

    // Constructor
    public Cart(int id, int userId, int productDetailId, int quantity, boolean isDeleted, Date createdAt, int createdBy) {
        this.id = id;
        this.userId = userId;
        this.productDetailId = productDetailId;
        this.quantity = quantity;
        this.isDeleted = isDeleted;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.productDetail = new ProductDAO().getProductDetailById(productDetailId);
    }
    
    
    

    public ProductDetail getProductDetail() {
        return productDetail;
    }

    public void setProductDetail(ProductDetail productDetail) {
        this.productDetail = productDetail;
    }
    
    public int getTotal(int userId) {
        return new CartDAO().getCartCount(userId, "", "");
    }
    
    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getProductDetailId() {
        return productDetailId;
    }

    public void setProductDetailId(int productDetailId) {
        this.productDetailId = productDetailId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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

    

}
