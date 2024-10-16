package Controller;

import DAO.OrderDAO;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class OrdersController extends HttpServlet {
    private OrderDAO daoOrder;

    public void init() {
        daoOrder = new OrderDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            switch (action) {
                case "new":
                    showNewForm(request, response);
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                case "delete":
                    deleteOrder(request, response);
                    break;
                case "list":
                    listOrders(request, response);
                    break;
                default:
                    listOrders(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            if (action.equals("insert")) {
                insertOrder(request, response);
            } else if (action.equals("update")) {
                updateOrder(request, response);
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void listOrders(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        List<Order> listOrders = daoOrder.getAllOrders();
        request.setAttribute("listOrders", listOrders);
        RequestDispatcher dispatcher = request.getRequestDispatcher("order-list.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("order-form.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        Order existingOrder = daoOrder.getOrder(orderId);
        RequestDispatcher dispatcher = request.getRequestDispatcher("order-form.jsp");
        request.setAttribute("order", existingOrder);
        dispatcher.forward(request, response);
    }

    private void insertOrder(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int customerId = Integer.parseInt(request.getParameter("customerId"));
        java.sql.Date orderDate = java.sql.Date.valueOf(request.getParameter("orderDate"));
        double totalAmount = Double.parseDouble(request.getParameter("totalAmount"));
        String status = request.getParameter("status");

        Order newOrder = new Order(0, customerId, orderDate, totalAmount, status);
        daoOrder.addOrder(newOrder);
        response.sendRedirect("OrdersController?action=list");
    }

   
