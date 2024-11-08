package controller;

import DAO.OrderDAO;
import DAO.ProductDAO;
import Model.Order;
import Model.ProductDetail;
import Model.Staff;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ShipperController", urlPatterns = {"/shipper"})
public class ShipperController extends HttpServlet {

    /**
     * Xử lý yêu cầu GET từ shipper, hiển thị các trang khác nhau dựa vào
     * tham số 'page' trong request.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Lấy thông tin nhân viên từ session và kiểm tra quyền truy cập
        Staff staff = (Staff) request.getSession(true).getAttribute("staff");

        if (!isAuthorizedShipper(staff)) {
            response.sendRedirect("home"); // Chuyển hướng về trang chủ nếu không có quyền
            return;
        }

        // Xác định trang cần truy cập dựa vào tham số 'page', mặc định là 'view-all-order'
        String page = request.getParameter("page") == null ? "view-all-order" : request.getParameter("page");

        switch (page) {
            case "view-all-order":
                displayAllOrders(request, response);
                break;
            case "view-my-order":
                displayMyOrders(request, response, staff.getId());
                break;
            default:
                displayOrderDetail(request, response);
                break;
        }
    }

    /**
     * Xử lý yêu cầu POST từ shipper, như nhận đơn hàng hoặc hoàn tất đơn hàng.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("getOrder".equals(action)) {
            handleGetOrder(request, response);
        } else {
            handleCompleteOrder(request, response);
        }
    }

    /**
     * Kiểm tra nếu nhân viên có quyền shipper (role = 3).
     * 
     * @param staff đối tượng Staff
     * @return true nếu có quyền shipper, ngược lại là false
     */
    private boolean isAuthorizedShipper(Staff staff) {
        return staff != null && staff.getRole() == 3;
    }

    /**
     * Hiển thị tất cả các đơn hàng cho shipper.
     */
    private void displayAllOrders(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        OrderDAO orderDAO = new OrderDAO();
        List<Order> orders = orderDAO.shipperViewAllOrder();
        request.setAttribute("orders", orders);
        request.getRequestDispatcher("shipper-view-order.jsp").forward(request, response);
    }

    /**
     * Hiển thị các đơn hàng mà shipper hiện tại đã nhận.
     */
    private void displayMyOrders(HttpServletRequest request, HttpServletResponse response, int staffId)
            throws ServletException, IOException {
        OrderDAO orderDAO = new OrderDAO();
        List<Order> orders = orderDAO.shipperViewMyOrder(staffId);
        request.setAttribute("orders", orders);
        request.getRequestDispatcher("shipper-my-order.jsp").forward(request, response);
    }

    /**
     * Hiển thị chi tiết một đơn hàng cụ thể.
     */
    private void displayOrderDetail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        OrderDAO orderDAO = new OrderDAO();
        
        // Lấy thông tin đơn hàng và sản phẩm trong đơn hàng
        Order order = orderDAO.getOrderById(orderId);
        List<ProductDetail> orderedProducts = orderDAO.getOrderedProductsByOrderId(orderId);

        request.setAttribute("order", order);
        request.setAttribute("orderedProducts", orderedProducts);
        request.getRequestDispatcher("shipper-order-detail.jsp").forward(request, response);
    }

    /**
     * Xử lý hành động "nhận đơn hàng" từ shipper.
     */
    private void handleGetOrder(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Staff staff = (Staff) request.getSession(true).getAttribute("staff");
        int orderId = Integer.parseInt(request.getParameter("orderId"));

        OrderDAO orderDAO = new OrderDAO();
        if(orderDAO.getOrderByOrderID(orderId).getStatus().equals("Pay before")){
            orderDAO.updateShipperOrder(staff.getId(), "Delivering - Pay before", orderId);
        }
        else{
            orderDAO.updateShipperOrder(staff.getId(), "Delivering", orderId);
        }

        response.sendRedirect("shipper?page=view-all-order&isSuccess=success"); // Chuyển đến trang các đơn hàng đã nhận
    }

    /**
     * Xử lý hành động "hoàn tất đơn hàng" từ shipper.
     */
    private void handleCompleteOrder(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Staff staff = (Staff) request.getSession(true).getAttribute("staff");
        int orderId = Integer.parseInt(request.getParameter("orderId"));

        OrderDAO orderDAO = new OrderDAO();
        orderDAO.updateShipperOrder(staff.getId(), "Shipped ", orderId);

        response.sendRedirect("shipper?page=view-my-order"); // Chuyển đến trang các đơn hàng đã nhận
    }
}
