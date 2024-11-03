package controller;

import DAO.LocationDAO;
import Model.Location;
import Model.Staff;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

@WebServlet(name = "AdminLocationController", urlPatterns = {"/admin/location"})
public class AdminLocationController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LocationDAO locationDAO = new LocationDAO();
        List<Location> locations = locationDAO.getAllLocationWithoutDeleted();
        request.setAttribute("locations", locations);
        request.getRequestDispatcher("../admin-location.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Staff staff = (Staff) request.getSession(true).getAttribute("staff");

        String action = request.getParameter("action");
        LocationDAO locationDAO = new LocationDAO();

        if ("add".equals(action)) {
            // Lấy dữ liệu từ form thêm mới
            String name = request.getParameter("locationName");

            // Tạo đối tượng Location và gọi phương thức thêm mới từ DAO
            Location location = new Location();
            location.setLocationName(name);
            location.setCreatedBy(staff.getId());
            boolean isAdded = locationDAO.addLocation(location);

            // Kiểm tra kết quả và chuyển hướng
            if (isAdded) {
                request.setAttribute("notification", "Thêm địa chỉ thành công");
                List<Location> locations = locationDAO.getAllLocationWithoutDeleted();
                request.setAttribute("locations", locations);
                request.getRequestDispatcher("../admin-location.jsp").forward(request, response);
            } else {
                List<Location> locations = locationDAO.getAllLocationWithoutDeleted();
                request.setAttribute("locations", locations);
                request.setAttribute("notification", "Thêm địa điểm thất bại.");
                request.getRequestDispatcher("../admin-location.jsp").forward(request, response);
            }

        } else if ("update".equals(action)) {
            // Lấy dữ liệu từ form cập nhật
            int id = Integer.parseInt(request.getParameter("locationId"));
            String name = request.getParameter("locationName");
            int status = Integer.parseInt(request.getParameter("locationStatus"));

            // Tạo đối tượng Location và gọi phương thức cập nhật từ DAO
            Location location = new Location();
            location.setID(id);
            location.setLocationName(name);
            location.setIsDeleted(status);
            boolean isUpdated = locationDAO.updateLocation(location);

            // Kiểm tra kết quả và chuyển hướng
            if (isUpdated) {
                List<Location> locations = locationDAO.getAllLocationWithoutDeleted();
                request.setAttribute("locations", locations);
                request.setAttribute("notification", "Cập nhật địa điểm thành công.");
                request.getRequestDispatcher("../admin-location.jsp").forward(request, response);
            } else {
                List<Location> locations = locationDAO.getAllLocationWithoutDeleted();
                request.setAttribute("locations", locations);
                request.setAttribute("notification", "Cập nhật địa điểm thất bại.");
                request.getRequestDispatcher("../admin-location.jsp").forward(request, response);
            }
        }
    }

}
