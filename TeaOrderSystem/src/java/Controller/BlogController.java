
package controller;

import DAO.PostDAO;
import Model.Category;
import Model.Post;
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
 * @author Linkkk
 */
@WebServlet(name="BlogDetailController", urlPatterns={"/public/post-detail"})
public class BlogDetailController extends HttpServlet {
   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet BlogDetailController</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet BlogDetailController at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        int postId = Integer.parseInt(request.getParameter("id"));
        Post post = new PostDAO().getPostById(postId);
        
         PostDAO postDAO = new PostDAO();
         List<Category> categories = postDAO.getUniqueCategories();
         List<Post> latestPosts = postDAO.getLatestPosts();

        request.setAttribute("post", post);
        request.setAttribute("categories", categories);
        request.setAttribute("latestPosts", latestPosts);
        request.getRequestDispatcher("/blog-detail.jsp").forward(request, response);
    } 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}