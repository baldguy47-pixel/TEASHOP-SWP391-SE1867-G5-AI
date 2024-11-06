/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import DAO.LocationDAO;
import DAO.UserDAO;
import Model.User;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "ProfileController", urlPatterns = {"/common/profile"})
public class ProfileController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = (User) request.getSession(true).getAttribute("user");
        if(user == null){
            response.sendRedirect(request.getContextPath()+"/login");
            return;
        }
        request.setAttribute("locations", new LocationDAO().getAllLocation());
        request.getRequestDispatcher("/profile.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setAttribute("locations", new LocationDAO().getAllLocation());
        User user = (User) request.getSession(true).getAttribute("user");

        String fullname = request.getParameter("fullname");
        String gender = request.getParameter("gender");
        String address = request.getParameter("address");
        String phone = request.getParameter("phone");
        String avatar = request.getParameter("avatar");
        String location = request.getParameter("location");

        if (fullname.trim().length() == 0) {
            request.setAttribute("errorMSG", "Không được trống");
            request.getRequestDispatcher("/profile.jsp").forward(request, response);
            return;
        }
        if (!isValidPhone(phone.trim())) {
            request.setAttribute("errorMSG", "Số điện thoại không hợp lệ");
            request.getRequestDispatcher("/profile.jsp").forward(request, response);
            return;
        }
        if (!isValidAddress(address.trim())) {
            request.setAttribute("errorMSG", "Địa chỉ không hợp lệ");
            request.getRequestDispatcher("/profile.jsp").forward(request, response);
            return;
        }

        user.setFullname(fullname);
        user.setGender(gender);
        user.setAddress(address);
        user.setPhone(phone);
        user.setAvatar(avatar);
        user.setLocation(location);

        new UserDAO().updateUser(user);

        request.getSession().setAttribute("user", user);
        request.setAttribute("success", "Thành công!");
        request.getRequestDispatcher("/profile.jsp").forward(request, response);
    }

    private boolean isValidPhone(String phone) {
        // Implement your validation logic for phone number
        // For example, you can check if it contains only digits
        return phone.matches("^\\d+$");
    }

    private boolean isValidAddress(String address) {
        // Implement your validation logic for address
        // For example, you can check if it's not empty
        return !address.trim().isEmpty();
    }

}
