package controller;

import dao.CategoryDAO;
import model.Category;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CategoryController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String categoryName = request.getParameter("categoryName");
        
        Category category = new Category(categoryName);
        CategoryDAO categoryDAO = new CategoryDAO();
        
        if (categoryDAO.addCategory(category)) {
            response.sendRedirect("manageCategory.jsp");
        } else {
            response.sendRedirect("error.jsp");
        }
    }
}
