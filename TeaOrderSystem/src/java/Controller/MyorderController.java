
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import DAO.OrderDAO;
import DAO.PostDAO;
import DAO.ProductDAO;
import Model.Category;
import Model.Order;
import Model.Product;
import Model.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 * @author Link
 */
@WebServlet(name = "MyOrderController", urlPatterns = {"/customer/my-order"})
public class MyOrderController extends HttpServlet {

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
            out.println("<title>Servlet MyOrderController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet MyOrderController at " + request.getContextPath() + "</h1>");
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
        OrderDAO orderDAO = new OrderDAO();
        ProductDAO productDAO = new ProductDAO();
        orderDAO.autoCanceled();
        int currentPage = 1;
        int ordersPerPage = 10; // Set the number of orders per page

        int userId = ((User) request.getSession().getAttribute("user")).getId();
        
        System.out.println(userId);
        
        if (request.getParameter("page") != null) {
            currentPage = Integer.parseInt(request.getParameter("page"));
        }
        
        String orderDate = request.getParameter("orderDate");
        String orderTime = request.getParameter("orderTime");
        String orderStatus = request.getParameter("orderStatus");

        List<Order> orders = orderDAO.getOrdersByPage(currentPage, ordersPerPage, userId, orderDate, orderTime, orderStatus);
        List<Product> products = productDAO.getThreeLastestProducts();
        List<Category> categories = new PostDAO().getUniqueCategories();
        int totalOrders = orderDAO.getTotalOrderCount(userId, orderDate, orderTime, orderStatus);
        int totalPages = (int) Math.ceil((double) totalOrders / ordersPerPage);

        // Set order data in request attribute (security concern)
        request.setAttribute("orders", orders);
        request.setAttribute("orderDate", orderDate);
        request.setAttribute("orderTime", orderTime);
        request.setAttribute("orderStatus", orderStatus);
        request.setAttribute("products", products);
        request.setAttribute("categories", categories);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("isSuccess", request.getParameter("isSuccess"));
        request.setAttribute("type", request.getParameter("type"));
        request.getRequestDispatcher("/my-order.jsp").forward(request, response);
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
        processRequest(request, response);
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

}
