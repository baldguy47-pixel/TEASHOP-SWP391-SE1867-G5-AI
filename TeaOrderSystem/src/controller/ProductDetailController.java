
 package controller;

 import DAO.FeedbackDAO;
 import DAO.ProductDAO;
 import Model.Feedback;
 import Model.Product;
 import Model.ProductDetail;
 import Model.Topping;
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
  * @author link
  */
 @WebServlet(name="ProductDetailController", urlPatterns={"/public/product-detail"})
 public class ProductDetailController extends HttpServlet {
    
 
     private static final int PAGE_SIZE = 10;
     @Override
     protected void doGet(HttpServletRequest request, HttpServletResponse response)
     throws ServletException, IOException {
 
         int id = Integer.parseInt(request.getParameter("id"));
         Product product = new ProductDAO().getProductById(id);
         if(request.getParameter("pdid") != null) {
             int pdid = Integer.parseInt(request.getParameter("pdid"));
             product.setProductDetail(new ProductDAO().getProductDetailById(pdid));
         }
         
         
         //List<Feedback> feedbackList = new FeedbackDAO().getFeedbackByProductDetailID(product.getProductDetail().getProductDetailId(), offset, PAGE_SIZE);
         
        
         List<Topping> toppings = new ProductDAO().getAllToppings(id);
         request.setAttribute("toppings", toppings);
 
         List<ProductDetail> listDetails = new ProductDAO().getProductDetailsByProductId(id);
         request.setAttribute("product", product);
         request.setAttribute("listDetails", listDetails);
         request.getRequestDispatcher("/product-detail.jsp").forward(request, response);
     } 
 
     
     @Override
     protected void doPost(HttpServletRequest request, HttpServletResponse response)
     throws ServletException, IOException {
         
     }
 
 
 }