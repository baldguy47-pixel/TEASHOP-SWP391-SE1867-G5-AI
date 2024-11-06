/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import DAO.ProductDAO;
import Model.ProductDetail;
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
@WebServlet(name="AdminProductDetailController", urlPatterns={"/admin/product-detail"})
public class AdminProductDetailController extends HttpServlet {
   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        int productId = Integer.parseInt(request.getParameter("productId"));
        List<ProductDetail> listProductDetail = new ProductDAO().getListProductDetailsByProductIdAdmin(productId);
        String listSize = "";
        for (ProductDetail productDetail : listProductDetail) {
            listSize += productDetail.getSize();
        }
        request.setAttribute("listSize", listSize);
        request.setAttribute("productId", productId);
        request.setAttribute("listProductDetail", listProductDetail);
        request.getRequestDispatcher("../admin-product-detail.jsp").forward(request, response);
                
    } 

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        // Determine action (add or update)
        String action = request.getParameter("action");
        if (action != null) {
            switch (action) {
                case "add":
                    addProductDetail(request, response);
                    break;
                case "update":
                    updateProductDetail(request, response);
                    break;
            }
        } else {
            // Handle missing action parameter
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private void addProductDetail(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String imageUrl = request.getParameter("imageUrl");
        String size = request.getParameter("size");
        double price = Double.parseDouble(request.getParameter("price"));
        int discount = Integer.parseInt(request.getParameter("discount"));
        float importPrice = Float.parseFloat(request.getParameter("ImportPrice"));
        int productId = Integer.parseInt(request.getParameter("productId"));
        
        ProductDetail productDetail = new ProductDetail();
        productDetail.setImageURL(imageUrl);
        productDetail.setProductId(productId);
        productDetail.setSize(size);
        productDetail.setPrice(price);
        productDetail.setDiscount(discount);
        productDetail.setImportPrice(importPrice);
        productDetail.setIsDeleted(true);
        
         // Update the product detail in the database
        boolean isAdded = new ProductDAO().addProductDetail(productDetail);
        
        if (isAdded) {
            // Redirect to product list page after successful addition
            response.sendRedirect("product-detail?success&productId="+productId);
        } else {
            response.sendRedirect("product-detail?fail&productId="+productId);
        }
    }

    private void updateProductDetail(HttpServletRequest request, HttpServletResponse response) throws IOException {
         int productDetailId = Integer.parseInt(request.getParameter("ID"));
        String imageUrl = request.getParameter("imageUrl");
        String size = request.getParameter("size");
        double price = Double.parseDouble(request.getParameter("price"));
        int discount = Integer.parseInt(request.getParameter("discount"));
        float importPrice = Float.parseFloat(request.getParameter("ImportPrice"));
        boolean isDeleted = Boolean.parseBoolean(request.getParameter("isDeleted"));
        int productId = Integer.parseInt(request.getParameter("productId"));
        
        ProductDetail productDetail = new ProductDetail();
        productDetail.setProductDetailId(productDetailId);
        productDetail.setImageURL(imageUrl);
        productDetail.setSize(size);
        productDetail.setPrice(price);
        productDetail.setDiscount(discount);
        productDetail.setImportPrice(importPrice);
        productDetail.setIsDeleted(isDeleted);
        // Update the product detail in the database
        boolean isUpdated = new ProductDAO().updateProductDetail(productDetail);
        
        if (isUpdated) {
            // Redirect to product list page after successful addition
            response.sendRedirect("product-detail?success&productId="+productId);
        } else {
            response.sendRedirect("product-detail?fail&productId="+productId);
        }
        
    }

   

}
