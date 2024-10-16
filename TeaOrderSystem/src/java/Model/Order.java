package Model;

import DAO.OrderDAO;
import DAO.StaffDAO;
import DAO.ToppingDAO;
import DAO.UserDAO;
import java.util.Calendar;
import java.util.Date;

public class Order {

    private int id;
    private int userId;
    private String fullname;
    private String address;
    private String phone;
    private String status;
    private boolean isDeleted;
    private Date createdAt;
    private int createdBy;
    private double totalCost;
    private String notes;
    private String paymentMethod;
     private String location;

    private User user;

    public Order() {
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Order(int id, int userId, String fullname, String address, String phone, String status, boolean isDeleted, Date createdAt, int createdBy) {
        this.id = id;
        this.userId = userId;
        this.fullname = fullname;
        this.address = address;
        this.phone = phone;
        this.status = status;
        this.isDeleted = isDeleted;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.totalCost = new OrderDAO().getTotal(id);
        
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    
    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public String getNotes() {
        return notes;
    }

    public boolean isExpired() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(createdAt);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        // Get the new date
        Date expiredDate = calendar.getTime();
        return new Date().after(expiredDate);
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getGender(String email) {
        return new UserDAO().getUserByEmail(email).getGender();
    }

    public User getUser() {
        return new UserDAO().getUserById(userId);
    }

    public Staff getSale() {
        return new StaffDAO().getStaffById(createdBy);
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

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public Staff getStaff() {
        return new StaffDAO().getStaffById(createdBy);
    }

    @Override
    public String toString() {
        return "Order{" + "id=" + id + ", userId=" + userId + ", fullname=" + fullname + ", address=" + address + ", phone=" + phone + ", status=" + status + ", isDeleted=" + isDeleted + ", createdAt=" + createdAt + ", createdBy=" + createdBy + ", totalCost=" + totalCost + ", notes=" + notes + ", user=" + user + '}';
    }

}

