
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
                    <h2>Đăng nhập</h2>
                    <form action="login" method="post">
                        <input id="email" name="email" type="email" placeholder="Email" value="${email}" required/>
                        <input id="password" name="password" type="password" placeholder="Mật khẩu" value="${password}" required/>
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

    </body>
</html>
