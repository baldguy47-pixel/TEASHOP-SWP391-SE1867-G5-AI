package controller;

import DAO.UserDAO;
import Model.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "LoginControl", urlPatterns = {"/login"})
public class LoginControl extends HttpServlet {
    
    // Constant for serialization (Optional but good practice)
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Forward to Login.jsp (the login page)
        request.getRequestDispatcher("Login.jsp").forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Step 1: Retrieve form parameters (email and password)
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // Step 2: Instantiate UserDAO and perform login authentication
        UserDAO userDAO = new UserDAO();
        User user = userDAO.loginUser(email, password);

        // Step 3: Handle login result
        if (user != null) {
            // Login successful - Store user information in session
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            
            // Redirect to home page
            response.sendRedirect("home");
        } else {
            // Login failed - Set error message and forward to login page
            request.setAttribute("errorMessage", "Invalid email or password");
            request.getRequestDispatcher("Login.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "LoginControl handles user login, including validation and session management";
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Login Control</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Login Control Servlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }
}
