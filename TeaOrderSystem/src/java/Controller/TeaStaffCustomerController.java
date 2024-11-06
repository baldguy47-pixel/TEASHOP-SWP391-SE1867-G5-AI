/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import DAO.UserDAO;
import Model.Staff;
import Model.User;
import Utils.EmailService;
import Utils.SessionUserInfo;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 * @author anhdu
 */
@WebServlet(name = "TeaStaffCustomerController", urlPatterns = {"/teastaff/customer"})
public class TeaStaffCustomerController extends HttpServlet {

    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        userDAO = new UserDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Pagination parameters
        int pageNumber = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
        int pageSize = 5;

        // Filter parameters
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String gender = request.getParameter("gender");
        String statusString = request.getParameter("status");
        Boolean status = (statusString==null || statusString.isEmpty()) ? null : Boolean.parseBoolean(statusString);

        // Perform filtering based on the provided parameters
        List<User> filteredUserList = userDAO.getFilteredUsers(fullName, email, phone, gender, status, pageNumber, pageSize);

        // Get total number of users matching the filter criteria
        int totalUsers = userDAO.getFilteredUsers(fullName, email, gender, status).size();

        // Calculate total number of pages
        int totalPages = (int) Math.ceil((double) totalUsers / pageSize);

        // Forward the filtered user list and pagination parameters to the JSP
        request.setAttribute("userList", filteredUserList);
        request.setAttribute("currentPage", pageNumber);
        request.setAttribute("pageSize", pageSize);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("fullName", fullName);
        request.setAttribute("email", email);
        request.setAttribute("phone", phone);
        request.setAttribute("gender", gender);
        request.setAttribute("statusString", statusString);

        request.getRequestDispatcher("../tea-staff-customer.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Determine action (add or update)
        String action = request.getParameter("action");
        if (action != null) {
            switch (action) {
                case "update":
                    updateUser(request, response);
                    break;
            }
        } else {
            // Handle missing action parameter
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    

    private void updateUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve user data from request parameters
        int userId = Integer.parseInt(request.getParameter("userId"));
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        boolean gender = Boolean.parseBoolean(request.getParameter("gender"));
        String address = request.getParameter("address");
        String phone = request.getParameter("phone");
        boolean status = Boolean.parseBoolean(request.getParameter("status"));
        
        Staff staff = SessionUserInfo.getStaffSession(request);

        // Create a User object with the updated data
        User user = new UserDAO().getUserById(userId);
        user.setId(userId);
        user.setFullname(fullName);
        user.setEmail(email);
        user.setGender(gender ? "Male" : "Female");
        user.setAddress(address);
        user.setPhone(phone);
        user.setIsDeleted(status);
        user.setChangeHistory((user.getChangeHistory() == null ? "" : user.getChangeHistory()) + user.toString(staff));

        // Update the user
        boolean success = userDAO.updateUser(user);
        if (success) {
            // Redirect to user list page after successful update
            response.sendRedirect("customer?success");
        } else {
            // Handle update failure
            response.sendRedirect("customer?fail");
        }
    }

}
