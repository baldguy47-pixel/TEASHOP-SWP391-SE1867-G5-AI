
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">
        <title>Login</title>
        <link href="${pageContext.request.contextPath}/css2/bootstrap.min.css" rel="stylesheet">
        <script
            src="https://kit.fontawesome.com/8e2244e830.js"
            crossorigin="anonymous"
        ></script>
        <link href="${pageContext.request.contextPath}/css2/prettyPhoto.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/css2/price-range.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/css2/animate.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/css2/main.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/css2/responsive.css" rel="stylesheet">
        <!--[if lt IE 9]>
        <script src="js/html5shiv.js"></script>
        <script src="js/respond.min.js"></script>
        <![endif]-->       
        <link rel="shortcut icon" href="images/ico/favicon.ico">
        <link rel="apple-touch-icon-precomposed" sizes="144x144" href="${pageContext.request.contextPath}/images/ico/apple-touch-icon-144-precomposed.png">
        <link rel="apple-touch-icon-precomposed" sizes="114x114" href="${pageContext.request.contextPath}/images/ico/apple-touch-icon-114-precomposed.png">
        <link rel="apple-touch-icon-precomposed" sizes="72x72" href="${pageContext.request.contextPath}/images/ico/apple-touch-icon-72-precomposed.png">
        <link rel="apple-touch-icon-precomposed" href="${pageContext.request.contextPath}/images/ico/apple-touch-icon-57-precomposed.png">
        <style>
            /* Modal Overlay */
            .modal-overlay {
                display: none; /* Hidden by default */
                position: fixed;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                background-color: rgba(0, 0, 0, 0.5); /* Semi-transparent background */
                justify-content: center;
                align-items: center;
                z-index: 1000;
            }

            /* Modal Content */
            .modal-dialog {
                background-color: white;
                border-radius: 8px;
                padding: 20px;
                width: 50%;
                max-width: 600px;
                position: relative;
            }

            /* Modal Header */
            .modal-header {
                display: flex;
                justify-content: space-between;
                align-items: center;
                border-bottom: 1px solid #ddd;
                padding-bottom: 10px;
            }

            /* Close Button */
            .close-button {
                cursor: pointer;
                font-size: 20px;
                color: black;
            }

            /* Modal Body */
            .modal-body {
                margin-top: 20px;
                display: flex;
            }

            .image-container {
                text-align: center;
                margin-right: 20px;
            }

            .product-image {
                width: 100%;
                height: auto;
                max-width: 200px;
            }

            /* Product Info Table */
            .product-info {
                width: 100%;
                border-collapse: collapse;
            }

            .product-info th, .product-info td {
                padding: 8px;
                text-align: left;
            }

            .product-info th {
                width: 30%;
                background-color: #f9f9f9;
                font-weight: bold;
            }



            .total_area {
                max-width: 600px; /* Đặt chiều rộng tối đa cho biểu mẫu */
                padding: 20px; /* Thêm khoảng cách bên trong */
                border: 1px solid #ccc; /* Đường viền xung quanh biểu mẫu */
                border-radius: 10px; /* Bo góc cho đường viền */
                background-color: #f9f9f9; /* Màu nền cho biểu mẫu */
                box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1); /* Hiệu ứng bóng */
            }

            .form-group {
                margin-bottom: 15px; /* Khoảng cách giữa các phần tử */
                display: flex; /* Sử dụng Flexbox */
                flex-direction: column; /* Đặt chiều dọc */
            }

            .form-group label {
                display: block; /* Hiển thị label dưới dạng block */
                font-weight: bold; /* Đậm cho label */
                margin-bottom: 5px; /* Khoảng cách giữa label và input */
            }

            .form-control {
                width: 100%; /* Chiều rộng 100% cho input */
                max-width: 400px; /* Chiều rộng tối đa cho input */
                padding: 10px; /* Khoảng cách bên trong */
                border: 1px solid #ccc; /* Đường viền cho input */
                border-radius: 5px; /* Bo góc cho input */
                box-sizing: border-box; /* Đảm bảo padding không ảnh hưởng đến chiều rộng */
            }

            .bank-info {
                margin-top: 10px; /* Khoảng cách phía trên cho thông tin ngân hàng */
                padding: 10px; /* Khoảng cách bên trong */
                background-color: #eef; /* Màu nền cho thông tin ngân hàng */
                border: 1px solid #aac; /* Đường viền cho thông tin ngân hàng */
                border-radius: 5px; /* Bo góc cho thông tin ngân hàng */
            }

            .button-group {
                display: flex; /* Sử dụng Flexbox cho nhóm nút */
                justify-content: space-between; /* Khoảng cách đều giữa các nút */
                margin-top: 20px; /* Khoảng cách phía trên cho nhóm nút */
            }

            .success-message {
                background-color: #4CAF50; /* Màu xanh lá cho thành công */
                color: white;
                padding: 15px;
                position: relative;
                border-radius: 5px;
                font-family: Arial, sans-serif;
                display: flex;
                justify-content: space-between; /* Đưa nội dung và nút đóng sang hai bên */
                align-items: center; /* Canh giữa theo chiều dọc */
                margin: 10px 0; /* Khoảng cách trên dưới */
                box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1); /* Tạo hiệu ứng đổ bóng */
            }

            .success-message strong {
                font-size: 16px; /* Kích thước chữ của tiêu đề */
            }

            .btn-close {
                background: none;
                border: none;
                color: white;
                font-size: 20px; /* Kích thước của biểu tượng đóng */
                cursor: pointer;
                padding: 0 10px;
                transition: color 0.3s ease; /* Hiệu ứng khi hover */
            }

            .btn-close:hover {
                color: #d4d4d4; /* Màu khi hover */
            }

            .error-message {
                background-color: #f8d7da; /* Màu nền đỏ nhạt */
                color: #721c24; /* Màu chữ đỏ đậm */
                padding: 15px;
                position: relative;
                border-radius: 5px;
                font-family: Arial, sans-serif;
                display: flex;
                justify-content: space-between; /* Đưa nội dung và nút đóng sang hai bên */
                align-items: center; /* Căn giữa theo chiều dọc */
                margin: 10px 0;
                border: 1px solid #f5c6cb; /* Viền màu đỏ nhạt */
                box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            }

            .error-message strong {
                font-size: 16px;
            }

            .btn-close {
                background: none;
                border: none;
                color: #721c24;
                font-size: 20px;
                cursor: pointer;
                padding: 0 10px;
                transition: color 0.3s ease;
            }

            .btn-close:hover {
                color: #d4d4d4;
            }


        </style>
    </head><!--/head-->

    <body>

        <jsp:include page="Header.jsp"></jsp:include>

            <section id="cart_items">
                <div class="container">
                    <div class="breadcrumbs">
                        <ol class="breadcrumb">
                            <li><a href="${pageContext.request.contextPath}/home">Trang chủ</a></li>
                        <li class="active">Giỏ hàng</li>
                    </ol>
                </div>
                <div class="row">
                    <div class="col-sm-6">
                        <h3>Giỏ hàng</h3>
                        <c:if test="${isSuccess ne null && isSuccess}">
                            <div class="success-message" id="mess">
                                <strong>Thành công!</strong>
                                <button type="button" class="btn-close" onclick="document.getElementById('mess').style.display = 'none'">&times;</button>
                            </div>

                        </c:if>
                        <c:if test="${isSuccess ne null && !isSuccess}">
                            <div class="alert alert-danger alert-dismissible fade show mt-2" role="alert" id="mess">
                                <strong>Thất bại!</strong> Bạn hãy kiểm tra lại kết nối.
                                <button type="button" class="btn-close"  onclick="document.getElementById('mess').style.display = 'none'"></button>
                            </div>
                        </c:if>
                    </div>
                    <div class="col-sm-6">
                        <div style="width: 100%">
                            <form method="GET" action="my-order" class="form-inline mb-3">
                                <div class="form-group mx-sm-3 mb-2">
                                    <label for="orderDate" class="sr-only">Ngày:</label>
                                    <input type="date" id="orderDate" name="orderDate" class="form-control" value="${orderDate}" placeholder="Order Date">
                                </div>
                                <div class="form-group mx-sm-3 mb-2">
                                    <label for="orderTime" class="sr-only">Thời gian:</label>
                                    <input type="time" id="orderTime" name="orderTime" class="form-control" value="${orderTime}" placeholder="Order Time">
                                </div>
                                <div class="form-group mx-sm-3 mb-2">
                                    <label for="orderStatus" class="sr-only">Trạng thái:</label>
                                    <select id="orderStatus" name="orderStatus" class="form-control">
                                        <option value="">Tất cả</option>
                                        <option value="Submitted" ${orderStatus eq"Submitted" ? "selected" : ""}>Đã thanh toán</option>
                                        <option value="Shipped" ${orderStatus eq"Shipped" ? "selected" : ""}>Đã giao</option>
                                        <option value="Wait for pay" ${orderStatus eq"Wait for pay" ? "selected" : ""}>Chưa thanh toán</option>
                                        <option value="Delivering" ${orderStatus eq"Delivering" ? "selected" : ""}>Đang giao</option>
                                        
                                        <option value="Canceled" ${orderStatus eq "Canceled" ? "selected" : ""}>Đã hủy</option>
                                    </select>
                                </div>
                                <button type="submit" class="btn btn-primary mb-2" style="margin-top: 0">Lọc</button>
                            </form>
                        </div>
                    </div>
                </div>
                <div class="table-responsive cart_info">                        
                    <table class="table table-condensed">
                        <thead>
                            <tr class="cart_menu">
                                <td class="price">Mã đơn hàng</td>
                                <td class="price">Thời gian</td>
                                <td class="price">Địa chỉ</td>
                                <td class="price">Số Điện thoại</td>
                                <td class="price">Giá</td>
                                <td class="quantity">Trạng thái</td>
                                <td class="total">Thanh toán</td>
                                <td></td>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="item" items="${orders}">
                                <tr>
                                    <td class="cart_product">
                                        <a href="order-detail?orderId=${item.id}">${item.id}</a>
                                    </td>
                                    <td class="cart_price">
                                        <fmt:formatDate value="${item.createdAt}" pattern="dd/MM/yyyy HH:mm:ss"/>
                                    </td>
                                    <td class="cart_price">
                                        ${item.address}
                                    </td>
                                    <td class="cart_price">
                                        ${item.phone}
                                    </td>
                                    <td class="cart_quantity">
                                        ${String.format("%.0f", item.totalCost)}VND
                                    </td>
                                    <td class="cart_quantity">
                                        <c:choose>
                                            <c:when test="${item.status.trim() eq 'Submitted'}">
                                                Đã thanh toán
                                            </c:when>
                                            <c:when test="${item.status.trim() eq 'Shipped'}">
                                                Đã giao
                                            </c:when>
                                            <c:when test="${item.status.trim() eq 'Wait for pay'}">
                                                Chưa thanh toán
                                            </c:when>
                                            <c:when test="${item.status.trim() eq 'Delivering'}">
                                                Đang giao
                                            </c:when>
                                            <c:when test="${item.status.trim() eq 'Canceled'}">
                                                Đã hủy
                                            </c:when>
                                            <c:otherwise>
                                                ${item.status}
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td class="cart_total">
                                        ${item.paymentMethod}
                                    </td>
                                    <td class="cart_delete">
                                        <c:if test="${item.status eq 'Success'}">
                                            <a href="confirm-order?orderId=${item.id}" class="btn btn-primary">Đóng</a>
                                        </c:if>

                                        <c:if test="${item.status eq 'Wait for pay' && !item.isExpired()}">
                                            <a href="../public/payment?orderId=${item.id}&method=repay&amount=${item.totalCost}" class="btn btn-primary">Thanh toán</a>
                                        </c:if>
                                    </td>
                                </tr>

                                <!-- Modal Structure -->


                            </c:forEach>

                        </tbody>
                    </table>
                </div>



                <c:if test="${totalPages > 1}">
                    <ul class="pagination" style="padding-left: 0;">
                        <c:forEach var="i" begin="1" end="${totalPages}">
                            <li class="page-item ${i == currentPage ? 'active' : ''}">
                                <a class="page-link" href="?page=${i}&orderDate=${orderDate}&orderTime=${orderTime}&orderStatus=${orderStatus}">${i}</a>
                            </li>
                        </c:forEach>
                    </ul> 
                </c:if>
            </div>
        </section> <!--/#cart_items-->



        <section id="do_action">
            <div class="container">
                <div class="heading">

                </div>

            </div>
            <div class="recommended_items"><!--recommended_items-->
                <h2 class="title text-center">Sản phẩm mới</h2>

                <div id="recommended-item-carousel">
                    <div class="container">
                        <div class="item">	

                            <c:forEach items="${products}" var="p">

                                <div class="col-sm-4">
                                    <div class="product-image-wrapper">
                                        <div class="single-products">
                                            <div class="productinfo text-center">
                                                <img src="${p.productDetail.imageURL}" alt="" />
                                                <h2>${String.format("%.0f", p.productDetail.price)}VND</h2>
                                                <p>${p.productName}</p>
                                                <a href="${pageContext.request.contextPath}/public/product-detail?id=${p.productId}" class="btn btn-default add-to-cart"><i class="fa fa-shopping-cart"></i>Xem chi tiết</a>
                                            </div>

                                        </div>
                                    </div>
                                </div>

                            </c:forEach>
                        </div>
                    </div>			
                </div>
            </div><!--/recommended_items-->
        </section><!--/#do_action-->


        <jsp:include page="footer.jsp"></jsp:include>


            <script src="${pageContext.request.contextPath}/js2/jquery.js"></script>
        <script src="${pageContext.request.contextPath}/js2/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/js2/jquery.scrollUp.min.js"></script>
        <script src="${pageContext.request.contextPath}/js2/price-range.js"></script>
        <script src="${pageContext.request.contextPath}/js2/jquery.prettyPhoto.js"></script>
        <script src="${pageContext.request.contextPath}/js2/main.js"></script>


    </body>
</html>
