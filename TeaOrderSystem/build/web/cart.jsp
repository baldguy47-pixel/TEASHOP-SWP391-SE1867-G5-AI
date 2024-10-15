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
                                <strong>Thành công!</strong> Cảm ơn bạn đã mua hàng.
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
                            <form method="get" action="cart">
                                <div class="row">
                                    <div id="product-search" class="col-sm-4">
                                        <input type="text" id="search-box" name="searchQuery" placeholder="Tìm kiếm" class="form-control" value="${param.searchQuery}">
                                    </div>
                                    <div class="form-group col-sm-5">
                                        <select id="category" name="category" class="form-control">
                                            <option value="">Danh mục</option>
                                            <c:forEach var="cat" items="${categories}">
                                                <option value="${cat.categoryName}" ${param.category == cat.categoryName ? 'selected' : ''}>${cat.categoryName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-sm-3">
                                        <button style="margin-top: 0" type="submit" class="btn btn-primary">Lọc</button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
                <div class="table-responsive cart_info">                        
                    <table class="table table-condensed">
                        <thead>
                            <tr class="cart_menu">
                                <td class="image">Sản phẩm</td>
                                <td class="description"></td>
                                <td class="price">Kích thước</td>
                                <td class="price">Giá</td>
                                <td class="quantity">Số lượng</td>
                                <td class="total">Tổng</td>
                                <td></td>
                            </tr>
                        </thead>
                        <tbody>
                            <c:set value="0" var="total"/>
                            <c:forEach var="item" items="${cartItems}">
                                <tr>
                                    <td class="cart_product">
                                        <img src="${item.productDetail.imageURL}" alt="" style="width: 150px; height: 100; object-fit: cover">
                                    </td>
                                    <td class="cart_description">
                                        <h4>${item.productDetail.getProductName()}</h4>
                                        <p>Ăn kèm: <c:set value="0" var="totalTopping"/>
                                            <c:forEach items="${item.listTopping}" var="t">
                                                <c:set value="${totalTopping + t.price}" var="totalTopping"/>
                                            </c:forEach>
                                            ${totalTopping}
                                        </p>
                                    </td>
                                    <td class="cart_price">
                                        <p>${item.productDetail.size}</p>
                                    </td>
                                    <td class="cart_price">
                                        <p style="margin: 0"><c:if test="${item.productDetail.discount != null && item.productDetail.discount != 0}">
                                                ${item.productDetail.price * (100.0- item.productDetail.discount)/100}
                                                <c:set value="${total + item.productDetail.price * (100.0- item.productDetail.discount)/100}" var="total"/>
                                            </c:if>
                                            <c:if test="${item.productDetail.discount == null || item.productDetail.discount == 0}">
                                                ${item.productDetail.price}
                                                <c:set value="${total + item.productDetail.price}" var="total"/>
                                            </c:if>
                                        </p>
                                    </td>
                                    <td class="cart_quantity">
                                        <div class="cart_quantity_button">
                                            <form action="update-cart" method="post" style="display:inline;">
                                                <input type="hidden" name="cartId" value="${item.id}">
                                                <input type="number"  name="quantity" value="${item.quantity}" min="1" max="${item.productDetail.stock}"
                                                       class="form-control" style="width: 80px; display:inline;">
                                                <button type="submit" class="btn btn-sm btn-primary" style="margin-top: 0">Cập nhật</button>
                                            </form>
                                        </div>
                                    </td>
                                    <td class="cart_total">
                                        <p class="cart_total_price" style="margin: 0">
                                            <c:if test="${item.productDetail.discount != null && item.productDetail.discount != 0}">
                                                $${String.format('%.2f', item.quantity * (item.productDetail.price * (100.0- item.productDetail.discount)/100) + totalTopping)}
                                            </c:if>
                                            <c:if test="${item.productDetail.discount == null || item.productDetail.discount == 0}">
                                                $${String.format('%.2f', item.quantity * (item.productDetail.price) + totalTopping)}
                                            </c:if>
                                        </p>
                                    </td>
                                    <td class="cart_delete">
                                        <form action="delete-cart" method="post" style="display:inline;">
                                            <input type="hidden" name="cartId" value="${item.id}">
                                            <button type="submit" class="btn btn-sm btn-danger">Xóa</button>
                                        </form>
                                        <button class="btn-info btn-sm" onclick="openModal(${item.id})">Thông tin</button>

                                    </td>
                                </tr>

                                <!-- Modal Structure -->


                            </c:forEach>

                        </tbody>
                    </table>
                </div>

                <c:forEach var="item" items="${cartItems}">
                    <div id="customModal_${item.id}" class="modal-overlay">
                        <div class="modal-dialog">
                            <div class="modal-header">
                                <h5>Thông tin sản phẩm</h5>
                                <span class="close-button" onclick="closeModal(${item.id})">&times;</span>
                            </div>
                            <div class="modal-body">
                                <div class="image-container">
                                    <img src="${item.productDetail.imageURL}" alt="Product Image" class="product-image">
                                    <strong>Ảnh sản phẩm</strong>
                                </div>

                                <table class="product-info">
                                    <tr>
                                        <th>Tên sản phẩm</th>
                                        <td>${item.productDetail.productName}</td>
                                    </tr>
                                    <tr>
                                        <th>Kích thước</th>
                                        <td>${item.productDetail.size}</td>
                                    </tr>
                                    <tr>
                                        <th>Giá</th>
                                        <td>${item.productDetail.price}</td>
                                    </tr>
                                    <tr>
                                        <th>Số lượng</th>
                                        <td>${item.quantity}</td>
                                    </tr>
                                    <tr>
                                        <th>Ăn kèm</th>
                                        <td>
                                            <c:forEach var="t" items="${item.listTopping}">
                                                <p>${t.toppingName}: ${t.price}VNĐ</p>
                                            </c:forEach>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th>Mô tả</th>
                                        <td>${item.productDetail.description}</td>
                                    </tr>
                                </table>
                            </div>
                        </div>
                    </div>
                </c:forEach>

                <c:if test="${totalPages > 1}">
                    <ul class="pagination" style="padding-left: 0;">
                        <c:forEach begin="1" end="${totalPages}" var="pageNum">
                            <li class="page-item ${pageNum == currentPage ? 'active' : ''}">
                                <a href="cart?page=${pageNum}&searchQuery=${param.searchQuery}&category=${param.category}">${pageNum}</a>
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
                <div class="row">
                    <div class="col-sm-6">
                        <div class="total_area">
                            <ul>
                                <li>Giá sản phẩm <span>
                                        <c:set var="totalPrice" value="0"/>
                                        <c:forEach var="item" items="${cartItemsFull}">
                                            <c:set value="0" var="totalTopping"/>
                                            <c:forEach items="${item.listTopping}" var="t">
                                                <c:set value="${totalTopping + t.price}" var="totalTopping"/>
                                            </c:forEach>
                                            <c:if test="${item.productDetail.discount != null && item.productDetail.discount != 0}">
                                                <c:set var="totalPrice" value="${totalPrice + totalTopping + item.quantity * (item.productDetail.price * (100.0- item.productDetail.discount)/100)}" />
                                            </c:if>
                                            <c:if test="${item.productDetail.discount == null || item.productDetail.discount == 0}">
                                                <c:set var="totalPrice" value="${totalPrice + totalTopping + item.quantity * (item.productDetail.price)}" />
                                            </c:if>

                                        </c:forEach>
                                        ${String.format("%.2f", Double.parseDouble(totalPrice))}
                                    </span></li>
                                <li>Shipping <span>Miễn phí</span></li>
                                <li>Tổng <span>
                                        <c:set var="totalPrice" value="0"/>
                                        <c:forEach var="item" items="${cartItemsFull}">
                                            <c:set value="0" var="totalTopping"/>
                                            <c:forEach items="${item.listTopping}" var="t">
                                                <c:set value="${totalTopping + t.price}" var="totalTopping"/>
                                            </c:forEach>
                                            <c:if test="${item.productDetail.discount != null && item.productDetail.discount != 0}">
                                                <c:set var="totalPrice" value="${totalPrice + totalTopping + item.quantity * (item.productDetail.price * (100.0- item.productDetail.discount)/100)}" />
                                            </c:if>
                                            <c:if test="${item.productDetail.discount == null || item.productDetail.discount == 0}">
                                                <c:set var="totalPrice" value="${totalPrice + totalTopping + item.quantity * (item.productDetail.price)}" />
                                            </c:if>

                                        </c:forEach>
                                        ${String.format("%.2f", Double.parseDouble(totalPrice))}
                                    </span></li>
                            </ul>
                            <a class="btn btn-default update" href="list-product">Tiếp tục mua sắm</a>

                            <c:if test="${cartItemsFull.size() ne 0}">
                                <a href="cart-contact" class="btn btn-default check_out">Thanh toán</a>
                            </c:if>
                        </div>
                    </div>
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
                                                <h2><c:if test="${p.productDetail.discount != null && p.productDetail.discount != 0}">
                                                        <span class="text-muted text-decoration-line-through">$${p.productDetail.price}</span>
                                                        ${p.productDetail.price * (100.0- p.productDetail.discount)/100}
                                                    </c:if>

                                                    <c:if test="${p.productDetail.discount == null || p.productDetail.discount == 0}">
                                                        ${p.productDetail.price}
                                                    </c:if></h2>
                                                <p>${p.productName}</p>
                                                <a href="product-detail?id=${p.productId}" class="btn btn-default add-to-cart"><i class="fa fa-shopping-cart"></i>Xem chi tiết</a>
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
        <script>
                                    // Open the modal with the corresponding product ID
                                    function openModal(productId) {
                                        var modal = document.getElementById('customModal_' + productId);
                                        modal.style.display = 'flex';
                                    }

                                    // Close the modal with the corresponding product ID
                                    function closeModal(productId) {
                                        var modal = document.getElementById('customModal_' + productId);
                                        modal.style.display = 'none';
                                    }

                                    // Optional: Close modal when clicking outside of it
                                    window.addEventListener('click', function (event) {
                                        var modals = document.querySelectorAll('.modal-overlay');
                                        modals.forEach(function (modal) {
                                            if (event.target == modal) {
                                                modal.style.display = 'none';
                                            }
                                        });
                                    });

        </script>

    </body>
</html>


