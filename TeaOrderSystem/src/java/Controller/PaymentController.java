package controller;

import DAO.CartDAO;
import DAO.OrderDAO;
import DAO.ProductDAO;
import Model.Cart;
import Model.Order;
import Model.OrderDetail;
import Model.User;
import com.google.gson.JsonObject;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import Utils.Config;
import com.google.gson.Gson;

/**
 *
 * @author Admin
 */
@WebServlet(name = "PaymentController", urlPatterns = {"/public/payment"})
public class PaymentController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse respone) throws ServletException, IOException {

        String amount_raw = request.getParameter("amount");
        double amount_d = Double.parseDouble(amount_raw);
        int amount = (int) amount_d * 100;
        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String vnp_OrderInfo = "pay pay";
        String orderType = "other";
        String vnp_TxnRef = Config.getRandomNumber(8);
        String vnp_IpAddr = Config.getIpAddress(request);
        String vnp_TmnCode = Config.vnp_TmnCode;
        Map vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version); //Phiên bản cũ là 2.0.0, 2.0.1 thay đổi sang 2.1.0
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");
        String bank_code = request.getParameter("bankcode");
        if (bank_code != null && !bank_code.isEmpty()) {
            vnp_Params.put("vnp_BankCode", bank_code);
        }
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", vnp_OrderInfo);
        vnp_Params.put("vnp_OrderType", orderType);

        String locate = request.getParameter("language");
        if (locate != null && !locate.isEmpty()) {
            vnp_Params.put("vnp_Locale", locate);
        } else {
            vnp_Params.put("vnp_Locale", "vn");
        }
        vnp_Params.put("vnp_ReturnUrl", Config.vnp_Returnurl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());

        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        //Build data to hash and querystring
        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();

        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = Config.hmacSHA512(Config.vnp_HashSecret, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = Config.vnp_PayUrl + "?" + queryUrl;

        //Section : Add new payment
        //Get user payment
        String method = request.getParameter("method");
        if (method.equalsIgnoreCase("Tranfer")) {
            respone.sendRedirect("tranfer-commit");
            return;
        }

        User user = (User) request.getSession().getAttribute("user");
        //Create bill
        String fullname = request.getParameter("fullname");
        String address = request.getParameter("address");
        String phone = request.getParameter("phone");
        String notes = request.getParameter("notes");
        String location = request.getParameter("location");

        Order order = new Order();
        order.setFullname(fullname);
        order.setAddress(location+ " - "+address);
        order.setPhone(phone);
        order.setNotes(notes);
        order.setLocation(location);
//        if (method.equalsIgnoreCase("vnpay") || method.equalsIgnoreCase("repay") || method.equalsIgnoreCase("COD")) {
//            order.setFullname(user.getFullname());
//            order.setAddress(location+ " - "+address);
//            order.setPhone(phone);
//            order.setNotes(notes);
//        }
        order.setStatus(method.equalsIgnoreCase("vnpay") ? "Wait for pay" : "Submitted");
        order.setPaymentMethod(method);
        order.setUserId(user.getId());
        int orderId = 0;
        if (request.getParameter("orderId") != null && !request.getParameter("orderId").isEmpty()) {
            orderId = Integer.parseInt(request.getParameter("orderId"));
        }

        if (method.equalsIgnoreCase("vnpay") || method.equalsIgnoreCase("COD") || method.equalsIgnoreCase("tranfer")) {
            orderId = new OrderDAO().createOrder(order);
        }

        Config.orderID = orderId;
        // Retrieve cart items from session or request (assuming a method getCartItems exists)
        List<Cart> cartItems = new CartDAO().getAllCarts(user.getId());
        // Insert Order Details
        for (Cart cartItem : cartItems) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(orderId);
            orderDetail.setCreatedBy(user.getId());
            orderDetail.setProductDetailId(cartItem.getProductDetailId());
            orderDetail.setQuantity(cartItem.getQuantity());
            orderDetail.setToppingId(cartItem.getToppings());
            new OrderDAO().createOrderDetail(orderDetail);
        }
        new CartDAO().clearCart(user.getId());

        if (method.equalsIgnoreCase("vnpay") || method.equalsIgnoreCase("repay")) {
            respone.sendRedirect(paymentUrl);
        } else {
            respone.sendRedirect("../customer/my-order");
        }

    }

}