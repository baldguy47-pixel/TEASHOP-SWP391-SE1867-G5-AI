
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import DAO.AdminDAO;
import java.sql.Timestamp;

/**
 *
 * @author Legion
 */
public class Category {
    private int ID;
    private String categoryName;
    private boolean isDeleted;
    private Timestamp createdAt;
    private int createdBy;

    public Category() {
    }

    public Category(int ID, String categoryName) {
        this.ID = ID;
        this.categoryName = categoryName;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public boolean isIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
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
    
    public double getTotalCost() {
        return new AdminDAO().getTotalCostByCategory(ID);
    }
    
    public double getAvgFeedback() {
        return new AdminDAO().getAverageFeedbackByCategoryId(ID);
    }
    
}
