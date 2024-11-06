
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import DAO.OrderDAO;
import Model.User;
import Utils.Config;
import Utils.EmailService;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Link
 */
@WebServlet(name = "HandlingPaymentResultController", urlPatterns = {"/handle-payment-result"})
public class HandlingPaymentResultController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Map fields = new HashMap();
        for (Enumeration params = request.getParameterNames(); params.hasMoreElements();) {
            String fieldName = URLEncoder.encode((String) params.nextElement(), StandardCharsets.US_ASCII.toString());
            String fieldValue = URLEncoder.encode(request.getParameter(fieldName), StandardCharsets.US_ASCII.toString());
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                fields.put(fieldName, fieldValue);
            }
        }

        String vnp_SecureHash = request.getParameter("vnp_SecureHash");
        if (fields.containsKey("vnp_SecureHashType")) {
            fields.remove("vnp_SecureHashType");
        }
        if (fields.containsKey("vnp_SecureHash")) {
            fields.remove("vnp_SecureHash");
        }
        String signValue = Config.hashAllFields(fields);
        boolean isSuccess = false;
        if (signValue.equals(vnp_SecureHash)) {
            if ("00".equals(request.getParameter("vnp_TransactionStatus"))) {
                isSuccess = true;
                new OrderDAO().updateOrder("Submitted", Config.orderID);
                EmailService.sendEmail(((User) request.getSession().getAttribute("user")).getEmail(), "Confirm Order", "We have receive your order!" + "Payment guidles: "
                        + request.getParameter("vnp_OrderInfo") + ", Amount:" + String.format("%,.0f", Double.parseDouble(request.getParameter("vnp_Amount")) / 100) + ", Transaction Code: " + request.getParameter("vnp_TransactionNo"));
            } else {
                //out.print("Failed");
                new OrderDAO().updateOrder("Wait for pay", Config.orderID);
            }

        } else {
            new OrderDAO().updateOrder("Wait for pay", Config.orderID);
            //out.print("invalid signature");
        }
        response.sendRedirect("public/cart?isSuccess=" + isSuccess);
    }

}
