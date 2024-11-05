/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
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
 * @author Legion
 */
@WebServlet(name = "TeastaffListPostController", urlPatterns = {"/teastaff/list-post"})
public class TeastaffListPostController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int PAGE_SIZE = 10;
        int page = 1;
        String pageStr = request.getParameter("page");
        if (pageStr != null && !pageStr.isEmpty()) {
            page = Integer.parseInt(pageStr);
        }

        // Get filtering and sorting parameters
        String category = request.getParameter("category");
        String author = request.getParameter("author");
        String status = request.getParameter("status");
        String search = request.getParameter("search");
        String sortBy = request.getParameter("sortBy");
        String sortOrder = request.getParameter("sortOrder");

        // Fetch posts for the requested page with filters and sorting
        PostDAO postDAO = new PostDAO();
        List<Post> posts = postDAO.getPosts(page, PAGE_SIZE, category, author, status, search, sortBy, sortOrder, "Yes");

        // Get the total number of posts for pagination
        int totalPosts = postDAO.getTotalPosts(category, author, status, search);
        int totalPages = (int) Math.ceil((double) totalPosts / PAGE_SIZE);

        // Fetch filter options
        List<Category> categories = postDAO.getUniqueCategories();
        List<String> authors = postDAO.getUniqueAuthors();

        // Set attributes for the JSP
        request.setAttribute("posts", posts);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("categories", categories);
        request.setAttribute("authors", authors);
        request.setAttribute("category", category);
        request.setAttribute("author", author);
        request.setAttribute("status", status);
        request.setAttribute("search", search);
        request.setAttribute("sortBy", sortBy);
        request.setAttribute("sortOrder", sortOrder);
        request.setAttribute("isSuccess", request.getParameter("isSuccess"));

        request.getRequestDispatcher("/tea-staff-post.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Determine action (add or update)
        String action = request.getParameter("action");
        if (action != null) {
            switch (action) {
                case "add":
                    addPost(request, response);
                    break;
                case "update":
                    updatePost(request, response);
                    break;
                case "delete":
                    deletePost(request, response);
                    break;
            }
        } else {
            // Handle missing action parameter
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private void addPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Get the form data
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        String category = request.getParameter("category");
        String imgURL = request.getParameter("imgURL");
        // Assuming you have a way to get the current user ID
        //int createdBy = ((User) request.getSession().getAttribute("user")).getId();
        int createdBy = 1; //need delete
        boolean isSuccess = new PostDAO().createPost(title, content, category, createdBy, imgURL);
        response.sendRedirect("list-post?isSuccess=" + isSuccess);
    }

    private void updatePost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int postId = Integer.parseInt(request.getParameter("postId"));
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        int categoryId = Integer.parseInt(request.getParameter("category"));
        String imgURL = request.getParameter("imgURL");
        boolean isSuccess = new PostDAO().updatePost(postId, title, content, categoryId, imgURL);
        response.sendRedirect("list-post?isSuccess=" + isSuccess);
    }

    private void deletePost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int postId = Integer.parseInt(request.getParameter("postId"));
        int isDeleted = Integer.parseInt(request.getParameter("isDeleted"));
        boolean isSuccess = new PostDAO().updatePost(postId, isDeleted);
        response.sendRedirect("list-post?isSuccess=" + isSuccess);
    }

}
