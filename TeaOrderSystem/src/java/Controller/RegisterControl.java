/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import DAO.UserDAO;
import Model.User;
import Utils.EmailService;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Random;

@WebServlet(name = "RegisterControl", urlPatterns = {"/register"})
public class RegisterControl extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("Register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Retrieve parameters from the request
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String retypePassword = request.getParameter("retypePassword");

        // Perform additional validation checks
        String validationError = validateRegistrationInput(fullName, email, password, retypePassword, "", "");

        if (validationError != null) {
            // Validation failed
            request.setAttribute("errorMessage", validationError);
            request.setAttribute("fullName", fullName);
            request.setAttribute("email", email);
            request.setAttribute("password", password);
            request.setAttribute("retypePassword", retypePassword);
            request.getRequestDispatcher("Register.jsp").forward(request, response);
            return;
        }

        UserDAO userDAO = new UserDAO();

        // Check if the email is already registered
        if (userDAO.getUserByEmail(email) != null) {
            // Email already exists
            // You may set an error message and forward back to the registration page
            request.setAttribute("errorMessage", "Tài khoản này đã tồn tại");
            request.setAttribute("fullName", fullName);
            request.setAttribute("email", email);
            request.setAttribute("password", password);
            request.setAttribute("retypePassword", retypePassword);
            request.getRequestDispatcher("Register.jsp").forward(request, response);
        } else {
            // Check if the password and retype password match
            if (!password.equals(retypePassword)) {
                // Passwords do not match
                request.setAttribute("errorMessage", "Mật khẩu không khớp");
                request.setAttribute("fullName", fullName);
                request.setAttribute("email", email);
                request.setAttribute("password", password);
                request.setAttribute("retypePassword", retypePassword);
                request.getRequestDispatcher("Register.jsp").forward(request, response);
                return;
            }

            // Email is not registered, and passwords match, proceed with registration
//            boolean registrationSuccessful = userDAO.registerUser(fullName, email, password, role);
            // Verify OTP
            String otp = generateRandomSixDigit() + "";
            String verifyLink = "http://" + request.getServerName() + ":" + request.getServerPort()
                    + request.getContextPath() + "/verify?otp=" + otp + "&email=" + email;

            // Send mail verify
            EmailService.sendEmail(email, "Verify email", "Click here to verify: " + verifyLink);

            System.out.println(otp);
            // Set register info session
            User user = new User();
            user.setEmail(email);
            user.setPassword(password);
            user.setFullname(fullName);
            user.setGender("Female");
            user.setAddress("");
            user.setPhone("");
            user.setIsDeleted(false);

            request.getSession().setAttribute("verify_" + email, user);
            request.getSession().setAttribute("verify_otp_" + email, otp);

            // Registration successful
            request.setAttribute("errorMessage", "Đã gửi thư xác minh tới email của bạn!");
            request.getRequestDispatcher("Register.jsp").forward(request, response);
        }

    }

    // Additional validation method
    private String validateRegistrationInput(String fullName, String email, String password, String retypePassword, String phone, String address) {

        //validate full name
        if (!isValidFullName(fullName)) {
            return "Tên cần ít nhất 1 chữ cái";
        }
        // Validate email
        if (!isValidEmail(email)) {
            return "Địa chỉ email không hợp lệ";
        }

        // Validate password
        if (!isValidPassword(password)) {
            return "Mật khẩu cần có ít nhất 8 chữ số";
        }

        // Validate retype password
        if (!isValidRetypePassword(password, retypePassword)) {
            return "Mật khẩu không khớp";
        }

        return null; // No validation error
    }

// Additional validation methods for phone and address
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

// Updated validation methods
    private boolean isValidFullName(String fullName) {
        // Implement your validation logic for full name format
        // For example, you can check if it contains only letters
        return fullName.trim().length() > 1;
    }

    private boolean isValidPassword(String password) {
        // Implement your simplified validation logic for password length
        String passwordPattern = "^\\S{8,}$";
        return password.matches(passwordPattern);
    }

    private boolean isValidEmail(String email) {
        // Implement your validation logic for email format and length
        // For example, you can use a regular expression for a basic email format check
        return email.matches("^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$");
    }

    private boolean isValidRetypePassword(String password, String retypePassword) {
        // Implement your validation logic for retype password
        // For example, you can check if it matches the original password
        return password.equals(retypePassword);
    }

    public int generateRandomSixDigit() {
        Random random = new Random();
        return 100000 + random.nextInt(900000);
    }

}
