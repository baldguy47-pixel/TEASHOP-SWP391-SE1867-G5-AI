package controller;

import DAO.CategoryDAO;
import DAO.ProductDAO;
import Model.Category;
import Model.Product;
import Model.ProductDetail;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "AdminProductController", urlPatterns = {"/admin/product"})
public class AdminProductController extends HttpServlet {

    private ProductDAO productDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        productDAO = new ProductDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Pagination parameters
        // Pagination parameters
        String pageParam = request.getParameter("page");
        String searchQuery = request.getParameter("searchQuery");
        String categoryId = request.getParameter("categoryId");

        int pageNumber = pageParam == null ? 1 : Integer.parseInt(pageParam);
        int pageSize = 10;


        List<Product> products = new ProductDAO().getProductsByPage2(pageNumber, pageSize, searchQuery, categoryId);
        int total = new ProductDAO().countTotalProducts(searchQuery, categoryId);
        System.out.println(total);

        int endPage = total % pageSize == 0 ? total / pageSize : total / pageSize + 1;
        List<Category> categories = new CategoryDAO().getCategories();

        // Forward the filtered product list and pagination parameters to the JSP
        request.setAttribute("productList", products);
        request.setAttribute("currentPage", pageNumber);
        request.setAttribute("pageSize", pageSize);
        request.setAttribute("categories", categories);
        request.setAttribute("totalPages", endPage);
        request.setAttribute("searchQuery", searchQuery);
        request.setAttribute("categoryId", categoryId);
        request.setAttribute("categories", new CategoryDAO().getCategories());

        request.getRequestDispatcher("../admin-product.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Determine action (add or update)
        String action = request.getParameter("action");
        if (action != null) {
            switch (action) {
                case "add":
                    addProduct(request, response);
                    break;
                case "update":
                    updateProduct(request, response);
                    break;
            }
        } else {
            // Handle missing action parameter
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private void addProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve product data from request parameters
        String productName = request.getParameter("productName");
        int categoryId = Integer.parseInt(request.getParameter("categoryId"));
        String description = request.getParameter("description");
        String imageUrl = request.getParameter("imageUrl");

        // Create a new Product object
        Product newProduct = new Product();
        newProduct.setProductName(productName);
        newProduct.setCategoryId(categoryId);
        newProduct.setDescription(description);
        newProduct.setCreatedBy(1);
        newProduct.setBaseImageURL(imageUrl);
        newProduct.setIsDeleted(Boolean.FALSE);

        int productId = -1;
        if(!productDAO.checkExistedProductName(productName.trim())) {
            /// Add the product to the database
            productId = productDAO.addProduct(newProduct);
        }
        

        if (productId != -1) {
            // Redirect to product list page after successful addition
            response.sendRedirect("product?success");
        } else {
            response.sendRedirect("product?fail");
        }
    }

    private void updateProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve product data from request parameters
        int productId = Integer.parseInt(request.getParameter("productId"));
        String productName = request.getParameter("productName");
        String categoryName = request.getParameter("categoryName");
        String description = request.getParameter("description");
        boolean isDeleted = Boolean.parseBoolean(request.getParameter("isDeleted"));
        String imageUrl = request.getParameter("imageUrl");
        // Create a Product object with the updated data
        Product product = new Product();
        product.setProductId(productId);
        product.setProductName(productName);
        product.setCategoryName(categoryName);
        product.setDescription(description);
        product.setIsDeleted(isDeleted);
        product.setBaseImageURL(imageUrl);
         boolean success = false;
         
        if(!productDAO.checkExistedProductName(productName.trim())) {
            // Update the product in the database
            success= productDAO.updateProduct(product);
        }
        
        

        if (success) {
            // Redirect to product list page after successful update
            response.sendRedirect("product?success");
        } else {
            // Handle update failure
            response.sendRedirect("product?fail");
        }
    }

}
