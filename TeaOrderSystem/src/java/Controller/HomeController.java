package controller;

import DAO.PostDAO;
import DAO.ProductDAO;
import DAO.SliderDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@WebServlet(name="HomeController", urlPatterns={"/home"})
public class HomeController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
        request.setAttribute("sliders", new SliderDAO().getAllSliders());
        request.setAttribute("products", new ProductDAO().homePage());
        request.setAttribute("posts", new PostDAO().homePage());
        request.getRequestDispatcher("Home.jsp").forward(request, response);
    } 
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
    }
}