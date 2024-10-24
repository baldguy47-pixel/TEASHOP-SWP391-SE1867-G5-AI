<%@ page import="java.sql.*" %>
<html>
<head>
    <title>Register</title>
</head>
<body>
    <h2>Register an Account</h2>
    <form action="register.jsp" method="POST">
        <label for="name">Name:</label>
        <input type="text" name="name" required><br>

        <label for="email">Email:</label>
        <input type="email" name="email" required><br>

        <label for="password">Password:</label>
        <input type="password" name="password" required><br>

        <input type="submit" value="Register">
    </form>

    <%
        if(request.getMethod().equals("POST")){
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String password = request.getParameter("password");

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/your_db", "root", "password");
                PreparedStatement ps = con.prepareStatement("INSERT INTO users (name, email, password) VALUES (?, ?, ?)");
                ps.setString(1, name);
                ps.setString(2, email);
                ps.setString(3, password);

                int i = ps.executeUpdate();
                if(i > 0) {
                    out.println("Registration Successful");
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    %>
</body>
</html>
