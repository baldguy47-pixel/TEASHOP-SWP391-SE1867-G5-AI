<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Marketing Dashboard</title>
        <!-- Bootstrap CSS -->
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
        <!-- Font Awesome CSS for icons -->
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" rel="stylesheet">

    </head>
    <body>

        <!-- Sidebar -->
        <%@ include file="tea-staff-sidebar.jsp" %>

        <!-- Main content -->
        <div class="main-content" style="margin-top: 10%; margin-bottom: 10%">
            <c:if test="${isSuccess ne null && isSuccess}">
                <div class="alert alert-success alert-dismissible fade show mt-2" role="alert" id="mess">
                    <strong>Cập nhật thành công!</strong> 
                </div>
            </c:if>
            <c:if test="${isSuccess ne null && !isSuccess}">
                <div class="alert alert-danger alert-dismissible fade show mt-2" role="alert" id="mess">
                    <strong>Cập nhật không thành công</strong>
                </div>
            </c:if>
            <!-- Order Details -->
            <h2>Chi tiết đơn hàng:</h2>
            <p>Mã đơn hàng: ${order.id}</p>
            <p>Ngày đặt: ${order.createdAt}</p>
            <p>Tổng giá: ${String.format("%.0f", order.totalCost)}VND</p>
            <p>Trạng thái ${order.status}</p>
            <p>Phương thức thanh toán: ${order.paymentMethod}</p>

            <!-- Receiver Information -->
            <h3>Thông tin khách hàng:</h3>
            <p>Họ và tên: ${order.fullname}</p>
            <p>Địa chỉ: ${order.address}</p>
            <p>Số điện thoại: ${order.phone}</p>
            <p>Giới tính: ${order.user.gender}</p>
            <p>Người bán: ${order.sale.fullname}</p>

            <!-- Ordered Products -->
            <h3>Danh sách sản phẩm</h3>
            <table class="table">
                <thead>
                    <tr>
                        <th>Hình ảnh</th>
                        <th>Tên</th>
                        <th>Thể loại</th>
                        <th>Đơn giá</th>
                        <th>Số lượng</th>
                        <th>Tổng giá</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="product" items="${orderedProducts}">
                        <tr>
                            <td><img src="${product.imageURL}" alt="..." width="100" height="100"></td>
                            <td>${product.getProductName()}</td>
                            <td>${product.getCateogryName()}</td>
                            <td>${String.format("%.0f", product.discount != null &&  product.discount != 0 ? (product.price * (100-product.discount)/100) : product.price)} VND</td>
                            <td>${product.buyQuantity}</td>
                            <td>${String.format("%.0f", product.discount != null &&  product.discount != 0 ? (product.price * (100-product.discount)/100)*(product.buyQuantity) : product.price*product.buyQuantity)} VND</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <div>
                <strong>Tổng giá:</strong> ${String.format("%.0f", order.totalCost)}VND
            </div>

        </div>
        <!-- Bootstrap JS and jQuery -->
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>


    </body>
</html>
