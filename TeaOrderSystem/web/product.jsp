<%@ page import="java.sql.*" %>
<html>
<head>
    <title>Manage Products</title>
</head>
<body>
    <h2>Manage Products</h2>
    <form action="product.jsp" method="POST">
        <label for="product">Product Name:</label>
        <input type="text" name="product" required><br>

        <label for="category">Category ID:</label>
        <input type="number" name="category" required><br>

        <label for="price">Price:</label>
        <input type="number" name="price" required><br>

        <input type="submit" value="Add Product">
    </form>

    <%
        if(request.getMethod().equals("POST")){
            String product = request.getParameter("product");
            int category = Integer.parseInt(request.getParameter("category"));
            double price = Double.parseDouble(request.getParameter("price"));

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/your_db", "root", "password");
                PreparedStatement ps = con.prepareStatement("INSERT INTO products (name, category_id, price) VALUES (?, ?, ?)");
                ps.setString(1, product);
                ps.setInt(2, category);
                ps.setDouble(3, price);

                int i = ps.executeUpdate();
                if(i > 0) {
                    out.println("Product Added Successfully");
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
        }

        // Hi?n th? danh sách s?n ph?m
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/my_db", "root", "password");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM products");

            while(rs.next()) {
                out.println("<p>" + rs.getString("name") + " - " + rs.getDouble("price") + "</p>");
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    %>
</body>
</html>
