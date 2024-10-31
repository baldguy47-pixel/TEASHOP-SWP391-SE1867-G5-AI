<%@ page import="java.util.List" %>
<%@ page import="model.Category" %>
<%@ page import="dao.CategoryDAO" %>
<html>
<head>
    <title>Qu?n lý danh m?c</title>
</head>
<body>
    <h2>Manage Categories</h2>
    <form action="CategoryController" method="post">
        <input type="text" name="categoryName" placeholder="Enter category name" required>
        <input type="submit" value="Add Category">
    </form>

    <h3>Existing Categories</h3>
    <ul>
        <%
            CategoryDAO categoryDAO = new CategoryDAO();
            List<Category> categories = categoryDAO.getAllCategories();
            for (Category category : categories) {
        %>
            <li><%= category.getName() %> - <a href="editCategory.jsp?id=<%= category.getId() %>">Edit</a> | <a href="deleteCategory?id=<%= category.getId() %>">Delete</a></li>
        <% } %>
    </ul>
</body>
</html>
