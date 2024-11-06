package Model;

import DAO.OrderDAO;
import java.util.Date;

public class Staff {

    private int id;
    private String email;
    private String password;
    private String fullname;
    private String gender;
    private String address;
    private String phone;
    private int role;
    private boolean isDeleted;
    private Date createdAt;
    private int createdBy;
    private String avatar;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    private int saleNumberOrder = -1;
    public int saleNumberOrder() {
        if (saleNumberOrder == -1) {
            saleNumberOrder = 0;
            OrderDAO dao = new OrderDAO();

            saleNumberOrder += dao.getOrdersByStatus("Submitted", id).size();
            saleNumberOrder += dao.getOrdersByStatus("Approved", id).size();
            saleNumberOrder += dao.getOrdersByStatus("Request cancel", id).size();
            saleNumberOrder += dao.getOrdersByStatus("Packaging", id).size();
            saleNumberOrder += dao.getOrdersByStatus("Delivering", id).size();
            saleNumberOrder += dao.getOrdersByStatus("Success", id).size();
        }
        
        return saleNumberOrder;
    }

    public void setRoleInt(String role) {
        if (role == "Admin") {
            this.role = 1;
        }
        if (role == "Marketing") {
            this.role = 2;
        }
        if (role == "Sale") {
            this.role = 3;
        }
        if (role == "Sale leader") {
            this.role = 4;
        }
        if (role == "User") {
            this.role = 5;
        }
    }

    public String getRoleString() {
        if (this.role == 1) {
            return "Admin";
        }
        if (this.role == 2) {
            return "Marketing";
        }
        if (this.role == 3) {
            return "Sale";
        }
        if (this.role == 4) {
            return "Sale leader";
        }
        return "User";
    }

}
