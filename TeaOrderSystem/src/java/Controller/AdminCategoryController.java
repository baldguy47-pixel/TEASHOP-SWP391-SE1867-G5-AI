/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.CategoryDAO;
import Model.Category;
import Model.Staff;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.security.Timestamp;
import java.util.List;

/**
 *
 * @author Legion
 */
@WebServlet(name = "AdminCategoryController", urlPatterns = {"/admin/category"})
public class AdminCategoryController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet AdminCategoryController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AdminCategoryController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int PAGE_SIZE = 10;
        CategoryDAO categoryDAO = new CategoryDAO();
        String searchQuery = request.getParameter("search") != null ? request.getParameter("search") : "";
        int page = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;

        int totalCategories = categoryDAO.getTotalCategoryCount(searchQuery);
        int offset = (page - 1) * PAGE_SIZE;
        List<Category> categories = categoryDAO.getCategories(searchQuery, offset, PAGE_SIZE);

        int totalPages = (int) Math.ceil((double) totalCategories / PAGE_SIZE);

        request.setAttribute("categories", categories);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("currentPage", page);
        request.setAttribute("searchQuery", searchQuery);

        request.getRequestDispatcher("/admin-category.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Determine action (add or update)
        String action = request.getParameter("action");
        if (action != null) {
            switch (action) {
                case "add":
                    addCategory(request, response);
                    break;
                case "update":
                    updateCategory(request, response);
                    break;
            }
        } else {
            // Handle missing action parameter
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void addCategory(HttpServletRequest request, HttpServletResponse response) throws IOException {

        Staff staff = (Staff) request.getSession().getAttribute("staff");
        CategoryDAO categoryDAO = new CategoryDAO();

        String name = request.getParameter("name");
        boolean isDeleted = request.getParameter("isDeleted").equalsIgnoreCase("true");
        int createdBy = staff.getId();

        Category newCategory = new Category();
        newCategory.setCategoryName(name);
        newCategory.setIsDeleted(isDeleted);
        newCategory.setCreatedBy(createdBy);

        boolean success = categoryDAO.addCategory(newCategory);
        if (success) {
            // Redirect to staff list page after successful addition
            response.sendRedirect("category?success");
        } else {
            response.sendRedirect("category?fail");
        }
    }

    private void updateCategory(HttpServletRequest request, HttpServletResponse response) throws IOException {
        CategoryDAO categoryDAO = new CategoryDAO();
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        boolean isDeleted = request.getParameter("isDeleted").equalsIgnoreCase("true");

        Category updatedCategory = new Category();
        updatedCategory.setID(id);
        updatedCategory.setCategoryName(name);
        updatedCategory.setIsDeleted(isDeleted);

        boolean success = categoryDAO.updateCategory(updatedCategory);
        
        if (success) {
            // Redirect to staff list page after successful addition
            response.sendRedirect("category?success");
        } else {
            response.sendRedirect("category?fail");
        }
    }

}
