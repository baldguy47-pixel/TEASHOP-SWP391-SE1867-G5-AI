package Controller;

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
        String minPriceParam = request.getParameter("minPrice");
        String maxPriceParam = request.getParameter("maxPrice");
        String size = request.getParameter("size");

        int pageNumber = pageParam == null ? 1 : Integer.parseInt(pageParam);
        int pageSize = 10;

        Double minPrice = minPriceParam == null || minPriceParam.isEmpty() ? null : Double.parseDouble(minPriceParam);
        Double maxPrice = maxPriceParam == null || maxPriceParam.isEmpty() ? null : Double.parseDouble(maxPriceParam);

        List<Product> products = new ProductDAO().getProductsByPage2(pageNumber, pageSize, searchQuery, categoryId, minPrice, maxPrice, size);
        int total = new ProductDAO().countTotalProducts(searchQuery, categoryId, minPrice, maxPrice, size);

        int endPage = total % pageSize == 0 ? total / pageSize : total / pageSize + 1;
        List<Category> categories = new CategoryDAO().getCategories();
        List<String> sizes = new ProductDAO().getAvailableSizes();

        // Forward the filtered product list and pagination parameters to the JSP
        request.setAttribute("productList", products);
        request.setAttribute("currentPage", pageNumber);
        request.setAttribute("pageSize", pageSize);
        request.setAttribute("categories", categories);
        request.setAttribute("totalPages", endPage);
        request.setAttribute("sizes", sizes);
        request.setAttribute("searchQuery", searchQuery);
        request.setAttribute("categoryId", categoryId);
        request.setAttribute("minPrice", minPrice);
        request.setAttribute("maxPrice", maxPrice);
        request.setAttribute("selectedSize", size);
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
        double price = Double.parseDouble(request.getParameter("price"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        // Create a new Product object
        Product newProduct = new Product();
        newProduct.setProductName(productName);
        newProduct.setCategoryId(categoryId);
        newProduct.setDescription(description);
        newProduct.setCreatedBy(1);
        newProduct.setIsDeleted(Boolean.FALSE);

        // Add the product to the database
        int productId = productDAO.addProduct(newProduct);

        ProductDetail productDetail = new ProductDetail();
        productDetail.setProductId(productId);
        productDetail.setImageURL(imageUrl);
        productDetail.setPrice(price);
        productDetail.setStock(quantity);

        String[] sizes = request.getParameterValues("size");

        if (sizes != null) {
            for (String size : sizes) {
                productDetail.setSize(size);
                new ProductDAO().addProductDetail(productDetail);
            }
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
        double price = Double.parseDouble(request.getParameter("price"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        String size = request.getParameter("size");
        // Create a Product object with the updated data
        Product product = new Product();
        product.setProductId(productId);
        product.setProductName(productName);
        product.setCategoryName(categoryName);
        product.setDescription(description);
        product.setIsDeleted(isDeleted);

        ProductDetail productDetail = new ProductDAO().getProductDetailByProductId(productId);
        productDetail.setImageURL(imageUrl);
        productDetail.setPrice(price);
        productDetail.setSize(size);
        productDetail.setStock(quantity);
        new ProductDAO().updateProductDetail(productDetail);

        // Update the product in the database
        boolean success = productDAO.updateProduct(product);

        if (success) {
            // Redirect to product list page after successful update
            response.sendRedirect("product?success");
        } else {
            // Handle update failure
            response.sendRedirect("product?fail");
        }
    }

}
