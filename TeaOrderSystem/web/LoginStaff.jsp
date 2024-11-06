<%-- 
    Document   : Login
    Created on : Jan 16, 2024, 8:56:18 PM
    Author     : anhdu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Login Form</title>
        <!-- Include Bootstrap CSS -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <style>
            body {
                background-color: #212529;
            }

            .login-container {
                max-width: 400px;
                margin: 100px auto;
                background-color: #fff;
                padding: 20px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                border-radius: 8px;
            }

            .login-container h2 {
                text-align: center;
                color: #007bff;
            }

            .login-form {
                margin-top: 20px;
            }

            .login-form .form-group {
                margin-bottom: 20px;
            }

            .login-form label {
                font-weight: 600;
            }

            .login-form input[type="text"],
            .login-form input[type="password"] {
                width: 100%;
                padding: 10px;
                border: 1px solid #ced4da;
                border-radius: 4px;
            }

            .login-form button {
                width: 100%;
                padding: 10px;
                color: #fff;
                border: none;
                border-radius: 4px;
                cursor: pointer;
            }

            .login-form button:hover {
            }

            .forgot-password,
            .register {
                text-align: center;
                margin-top: 10px;
            }

            .forgot-password a,
            .register a {
                color: #007bff;
                text-decoration: none;
            }

            .forgot-password a:hover,
            .register a:hover {
                text-decoration: underline;
            }

            .error-message {
                color: red;
                text-align: center;
                margin-top: 10px;
            }
        </style>
    </head>
    <body>

        <div class="login-container">
            <h2 class="text-warning">Đăng nhập nhân viên</h2>

            <!-- Display error message if any -->
            <% if (request.getAttribute("errorMessage") != null) { %>
            <div class="error-message">
                <%= request.getAttribute("errorMessage") %>
            </div>
            <% } %>

            <form class="login-form" action="login-staff" method="post">
                <div class="form-group">
                    <label for="email">Email</label>
                    <input type="text" id="email" name="email" required oninput="validateEmail()">
                    <div id="emailError" class="error-message"></div>
                </div>
                <div class="form-group">
                    <label for="password">Mật khẩu</label>
                    <input type="password" id="password" name="password" required oninput="validatePassword()">
                    <div id="passwordError" class="error-message"></div>
                </div>
                <button type="submit" class="btn btn-warning">Đăng nhập</button>
            </form>
<!--            <div class="forgot-password">
                <a href="reset-password">Forgot Password?</a>
            </div>
            <div class="register">
                <p>Don't have an account? <a href="register">Register</a></p>
            </div>-->
        </div>

        <!-- Include Bootstrap JS and Popper.js -->
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

        <script>
            function validateEmail() {
                var emailInput = document.getElementById('email');
                var emailError = document.getElementById('emailError');
                var emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

                if (!emailRegex.test(emailInput.value)) {
                    emailError.textContent = 'Địa chỉ email chưa đúng';
                } else {
                    emailError.textContent = '';
                }
            }

            function validatePassword() {
                var passwordInput = document.getElementById('password');
                var passwordError = document.getElementById('passwordError');

                if (passwordInput.value.trim() === '') {
                    passwordError.textContent = 'Mật khẩu không được trống';
                } else if (passwordInput.value.length < 8) {
                    passwordError.textContent = 'Mật khẩu ít nhất 8 chữ số';
                } else {
                    passwordError.textContent = '';
                }
            }
        </script>

    </body>
</html>
