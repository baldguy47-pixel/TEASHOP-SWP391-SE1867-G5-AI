
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import DAO.CategoryDAO;
import java.sql.Timestamp;

/**
 *
 * @author Legion
 */
public class Post {

    private int id;
    private int categoryId;
    private String title;
    private String content;
    private boolean isDeleted;
    private Timestamp createdAt;
    private int createdBy;
    private String authorName; 
    private String imgURL;

    // Constructor
    public Post(int id, int categoryId, String title, String content, boolean isDeleted, Timestamp createdAt, int createdBy, String authorName) {
        this.id = id;
        this.categoryId = categoryId;
        this.title = title;
        this.content = content;
        this.isDeleted = isDeleted;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.authorName = authorName;
    }

    public Post() {
    }

    public Post(int id, int categoryId, String title, String content, boolean isDeleted, Timestamp createdAt, String authorName) {
        this.id = id;
        this.categoryId = categoryId;
        this.title = title;
        this.content = content;
        this.isDeleted = isDeleted;
        this.createdAt = createdAt;
        this.authorName = authorName;
    }
    public Post(int id, int categoryId, String title, String content, boolean isDeleted, Timestamp createdAt, int createdBy) {
        this.id = id;
        this.categoryId = categoryId;
        this.title = title;
        this.content = content;
        this.isDeleted = isDeleted;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
    
    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
    
    public String getCategoryName() {
        return new CategoryDAO().getCategoryNameById(categoryId);
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }
    

    @Override
    public String toString() {
        return "Post{" + "id=" + id + ", categoryId=" + categoryId + ", title=" + title + ", content=" + content + ", isDeleted=" + isDeleted + ", createdAt=" + createdAt + ", createdBy=" + createdBy + ", authorName=" + authorName + '}';
    }

    
}

