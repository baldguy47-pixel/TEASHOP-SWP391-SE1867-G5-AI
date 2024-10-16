/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.UserDAO;
import Model.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "ChangePasswordControl", urlPatterns = {"/common/change-pass"})
public class ChangePasswordControl extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("../change-pass.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // lấy thông tin người dùng
        User user = (User) request.getSession().getAttribute("user");

        String oldPassword = request.getParameter("oldpassword");
        String password = request.getParameter("password");
        String retypepassword = request.getParameter("retypepassword");

        if (password.equals(retypepassword) && user.getPassword().equals(oldPassword)) {

            user.setPassword(password);
            new UserDAO().updateUser(user);

            request.setAttribute("errorMessage", "Đổi mật khẩu thành công!");

        } else {
            request.setAttribute("password", password);
            request.setAttribute("oldPassword", oldPassword);
            request.setAttribute("retypepassword", retypepassword);
            
            if(!user.getPassword().equals(oldPassword)){
            request.setAttribute("errorMessage", "Mật khẩu cũ không đúng");
            }
            else if (!isValidPassword(password)) {
                request.setAttribute("errorMessage", "Mật khẩu cần ít nhất 8 chữ số");
            }
            else if(!password.equals(retypepassword)){
                request.setAttribute("errorMessage", "Mật khẩu không khớp");
            }
        }

        request.getRequestDispatcher("../change-pass.jsp").forward(request, response);

    }

    private boolean isValidPassword(String password) {
        // Implement your simplified validation logic for password length
        String passwordPattern = "^\\S{8,}$";
        return password.matches(passwordPattern);
    }

}