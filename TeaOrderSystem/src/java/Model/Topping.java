/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Linh
 */
import java.time.LocalDate;

public class Topping {
    private int id;

    public boolean isIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
    private String toppingName;
    private double price;
    private boolean isDeleted;
    private LocalDate createdDate;
    private LocalDate lastUpdated;
    private String img;
    private int productId;

    public Topping() {
    }
    
    

    // Constructor
    public Topping(int id, String toppingName, double price, boolean isDeleted, LocalDate createdDate, LocalDate lastUpdated, String img, int productId) {
        this.id = id;
        this.toppingName = toppingName;
        this.price = price;
        this.isDeleted = isDeleted;
        this.createdDate = createdDate;
        this.lastUpdated = lastUpdated;
        this.img = img;
        this.productId = productId;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getToppingName() {
        return toppingName;
    }

    public void setToppingName(String toppingName) {
        this.toppingName = toppingName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDate getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDate lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    // toString method for easier debugging
    @Override
    public String toString() {
        return "Topping [id=" + id + ", toppingName=" + toppingName + ", price=" + price + 
               ", isDeleted=" + isDeleted + ", createdDate=" + createdDate + 
               ", lastUpdated=" + lastUpdated + ", img=" + img + ", productId=" + productId + "]";
    }
}