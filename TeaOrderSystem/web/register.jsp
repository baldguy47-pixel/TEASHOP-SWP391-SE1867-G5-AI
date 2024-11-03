<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">
        <title>Login</title>
        <link href="css2/bootstrap.min.css" rel="stylesheet">
        <script
            src="https://kit.fontawesome.com/8e2244e830.js"
            crossorigin="anonymous"
        ></script>
        <link href="css2/prettyPhoto.css" rel="stylesheet">
        <link href="css2/price-range.css" rel="stylesheet">
        <link href="css2/animate.css" rel="stylesheet">
        <link href="css2/main.css" rel="stylesheet">
        <link href="css2/responsive.css" rel="stylesheet">
        <!--[if lt IE 9]>
        <script src="js/html5shiv.js"></script>
        <script src="js/respond.min.js"></script>
        <![endif]-->       
        <link rel="shortcut icon" href="images/ico/favicon.ico">
        <link rel="apple-touch-icon-precomposed" sizes="144x144" href="images/ico/apple-touch-icon-144-precomposed.png">
        <link rel="apple-touch-icon-precomposed" sizes="114x114" href="images/ico/apple-touch-icon-114-precomposed.png">
        <link rel="apple-touch-icon-precomposed" sizes="72x72" href="images/ico/apple-touch-icon-72-precomposed.png">
        <link rel="apple-touch-icon-precomposed" href="images/ico/apple-touch-icon-57-precomposed.png">
    </head><!--/head-->

    <body>

        <jsp:include page="Header.jsp"></jsp:include>

            <section id="form" style="margin-top: 60px"><!--form-->
                <div class="container" style="display: flex; justify-content: center; flex-direction: column; align-items: center">
                <c:if test="${errorMessage != null}">
                    <div style="color: red">${errorMessage}</div>
                </c:if>
                <div class="login-form" style="width: 500px;"><!--login form-->
                    <h2>Đăng ký</h2>
                    <form id="registerForm" action="register" method="post">
                        <input id="fullName" name="fullName" type="text" placeholder="Tên đầy đủ" value="${fullName}" required/>
                        <p id="error-fullName" style="color: red; display: none;"></p>

                        <input id="email" name="email" type="email" placeholder="Email" value="${email}" required/>
                        <p id="error-email" style="color: red; display: none;"></p>

                        <input id="password" name="password" type="password" placeholder="Mật khẩu" value="${password}" required/>
                        <p id="error-password" style="color: red; display: none;"></p>

                        <input id="retypePassword" name="retypePassword" type="password" placeholder="Nhập lại mật khẩu" value="${retypePassword}" required/>
                        <p id="error-retypePassword" style="color: red; display: none;"></p>

                        <span>
                            <a href="reset-password">Quên mật khẩu?</a>
                        </span>
                        <button type="submit" class="btn btn-default">Xác nhận</button>
                    </form>
                </div><!--/login form-->

            </div>
        </section><!--/form-->



        <jsp:include page="footer.jsp"></jsp:include>


        <script src="js2/jquery.js"></script>
        <script src="js2/bootstrap.min.js"></script>
        <script src="js2/jquery.scrollUp.min.js"></script>
        <script src="js2/price-range.js"></script>
        <script src="js2/jquery.prettyPhoto.js"></script>
        <script src="js2/main.js"></script>
        <script>
            // Đối tượng chứa tất cả các trường trong form, bao gồm:
            // element - phần tử input,
            // error - phần tử <p> để hiển thị lỗi,
            // touched - đánh dấu đã được người dùng "chạm vào" để xử lý hiển thị lỗi hợp lý.
            const formElements = {
                fullName: {
                    element: document.getElementById('fullName'),
                    error: document.getElementById('error-fullName'),
                    touched: false
                },
                email: {
                    element: document.getElementById('email'),
                    error: document.getElementById('error-email'),
                    touched: false
                },
                password: {
                    element: document.getElementById('password'),
                    error: document.getElementById('error-password'),
                    touched: false
                },
                retypePassword: {
                    element: document.getElementById('retypePassword'),
                    error: document.getElementById('error-retypePassword'),
                    touched: false
                }
            };

            // Hàm validateField kiểm tra từng trường nhập liệu theo từng yêu cầu cụ thể
            // fieldName: tên của trường cần kiểm tra, như "fullName", "email", "password", v.v.
            function validateField(fieldName) {
                const field = formElements[fieldName]; // Lấy đối tượng trường từ formElements
                const value = field.element.value.trim(); // Lấy giá trị của trường và loại bỏ khoảng trắng ở đầu/cuối
                let message = ''; // Biến lưu thông báo lỗi, mặc định là chuỗi rỗng

                // Kiểm tra trường hợp cụ thể dựa trên tên trường
                if (fieldName === 'fullName') {
                    // Nếu trường tên rỗng, đặt thông báo lỗi
                    if (value.length === 0) {
                        message = 'Tên phải có ít nhất 1 ký tự.';
                    }
                } else if (fieldName === 'email') {
                    // Kiểm tra email hợp lệ theo biểu thức chính quy (regex)
                    if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(value)) {
                        message = 'Email không hợp lệ.';
                    }
                } else if (fieldName === 'password') {
                    // Kiểm tra độ dài và không chứa khoảng trắng
                    if (value.length < 8) {
                        message = 'Mật khẩu phải có ít nhất 8 ký tự.';
                    } else if (/\s/.test(value)) {
                        message = 'Mật khẩu không được chứa khoảng trắng.';
                    }
                } else if (fieldName === 'retypePassword') {
                    // Kiểm tra mật khẩu nhập lại khớp với mật khẩu gốc
                    if (value !== formElements.password.element.value) {
                        message = 'Mật khẩu không khớp!';
                    }
                }

                // Hiển thị hoặc ẩn thông báo lỗi nếu trường đã được "touched" (chạm vào)
                if (field.touched) {
                    field.error.textContent = message; // Đặt thông báo lỗi vào phần tử <p>
                    field.error.style.display = message ? 'block' : 'none'; // Hiển thị nếu có lỗi, ẩn nếu không có
                }
            }

            // Thiết lập sự kiện cho tất cả các trường trong form
            Object.keys(formElements).forEach(fieldName => {
                const field = formElements[fieldName];

                // Đánh dấu trường đã được chạm vào khi sự kiện blur xảy ra
                field.element.addEventListener('blur', () => {
                    field.touched = true; // Đánh dấu là đã chạm vào
                    validateField(fieldName); // Gọi hàm kiểm tra lỗi cho trường này
                });

                // Kiểm tra lỗi theo thời gian thực khi người dùng nhập liệu
                field.element.addEventListener('input', () => validateField(fieldName));
            });

            // Kiểm tra tất cả các trường trước khi gửi form
            document.getElementById('registerForm').addEventListener('submit', function (event) {
                let isValid = true; // Giả định form hợp lệ từ đầu

                // Lặp qua tất cả các trường để đánh dấu đã chạm vào và kiểm tra lỗi
                Object.keys(formElements).forEach(fieldName => {
                    formElements[fieldName].touched = true; // Đánh dấu tất cả là đã chạm vào
                    validateField(fieldName); // Kiểm tra lỗi cho từng trường

                    // Nếu có lỗi trong bất kỳ trường nào, đặt isValid thành false
                    if (formElements[fieldName].error.textContent) {
                        isValid = false;
                    }
                });

                // Ngăn gửi form nếu có bất kỳ lỗi nào
                if (!isValid) {
                    event.preventDefault(); // Ngăn không cho gửi form nếu không hợp lệ
                }
            });
        </script>
    </body>
</html>
