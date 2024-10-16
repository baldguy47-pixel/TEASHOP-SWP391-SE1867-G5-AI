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
import Model.Staff;
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
 * @author Legion
 */
@WebServlet(name = "SaleOrderController", urlPatterns = {"/sale/sale-order"})
public class SaleOrderController extends HttpServlet {

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
            out.println("<title>Servlet SaleOrderController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SaleOrderController at " + request.getContextPath() + "</h1>");
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
        orderDAO.autoCanceled();

        String startDate = request.getParameter("startDate");
        String id = request.getParameter("id");
        String customerName = request.getParameter("customerName");
        String endDate = request.getParameter("endDate");
        String salesperson = request.getParameter("salesperson");
        String orderStatus = request.getParameter("orderStatus");

        int currentPage = 1;
        int ordersPerPage = 10;

        if (request.getParameter("page") != null) {
            currentPage = Integer.parseInt(request.getParameter("page"));
        }
        
        if(startDate == null || startDate.isEmpty()) {
            startDate = "1990-01-01";
        }
        
        if(endDate == null || endDate.isEmpty()) {
            endDate = "9999-01-01";
        }
        
        Staff staff = (Staff) request.getSession().getAttribute("staff");
        
        List<Order> orders = orderDAO.getOrdersByPage(currentPage, ordersPerPage, startDate, endDate, salesperson, orderStatus, staff, id, customerName);
        List<Category> categories = new PostDAO().getUniqueCategories();
        int totalOrders = orderDAO.getTotalOrderCount(startDate, endDate, salesperson, orderStatus, staff, id, customerName);
        int totalPages = (int) Math.ceil((double) totalOrders / ordersPerPage);
        
        request.setAttribute("orders", orders);
        request.setAttribute("categories", categories);
        request.setAttribute("totalOrders", totalOrders);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("currentPage", currentPage);

        request.getRequestDispatcher("/sale-list-order.jsp").forward(request, response);
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
