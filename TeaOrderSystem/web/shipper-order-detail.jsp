<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Danh Sách Đơn Hàng</title>
        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <!-- DataTables CSS -->
        <link rel="stylesheet" href="https://cdn.datatables.net/1.10.21/css/jquery.dataTables.min.css">
        <!-- Font Awesome -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css">
        <style>
            body {
                background-color: #f8f9fa;
            }
            .navbar {
                margin-bottom: 20px;
                box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            }
            .table-container {
                background-color: white;
                border-radius: 8px;
                box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
                padding: 20px;
            }
            h2 {
                text-align: center;
                color: #333;
                margin-bottom: 20px;
            }
            .table th, .table td {
                text-align: center;
                vertical-align: middle;
            }
            .table tbody tr:hover {
                background-color: #f1f1f1;
            }
            .status-icon {
                font-size: 1.2em;
                color: #28a745;
            }
            .status-icon.pending {
                color: #ffc107;
            }
            .small-nav .nav-link {
                font-size: 0.9rem; /* Giảm kích thước font */
                padding: 5px 10px; /* Giảm padding */
            }
        </style>
    </head>
    <body>

        <!-- Header với logo và nút Đăng xuất -->
        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <a class="navbar-brand" href="#">
                <img src="${pageContext.request.contextPath}/Image/logo.png" alt="Logo" style="height: 40px; width: 40px; object-fit: cover; margin-right: 10px;">
            </a>
            <div class="ml-auto">
                <a href="${pageContext.request.contextPath}/logout" class="btn btn-outline-danger my-2 my-sm-0" ><i class="fas fa-sign-out-alt"></i> Đăng xuất</a>
            </div>
        </nav>

        <!-- Thanh điều hướng (thêm vào đây) -->
        <nav class="nav nav-pills nav-fill small-nav py-1">
            <a class="nav-item nav-link" href="${pageContext.request.contextPath}/shipper?page=view-all-order">Tất cả đơn hàng</a>
            <a class="nav-item nav-link" href="${pageContext.request.contextPath}/shipper?page=view-my-order">Đơn hàng của tôi</a>
            <a class="nav-item nav-link active">Chi tiết đơn hàng</a>
        </nav>


        <div class="container table-container">
            <h2>Đơn hàng</h2>
            <div class="row">
                <div class="col-sm-6">
                    <div style="width: 100%">

                        <p>Mã đơn hàng: ${order.id}</p>
                        <p>Ngày đặt: ${order.createdAt}</p>
                        <p>Trạng thái: ${order.status}</p>
                        <p>Phương thức thanh toán: ${order.paymentMethod}</p>

                        <!-- Receiver Information -->
                        <h3>Người nhận</h3>
                        <p>Họ tên: ${order.fullname}</p>
                        <p>Địa chỉ: ${order.address}</p>
                        <p>SĐT: ${order.phone}</p>
                    </div>
                </div>
            </div>
            <div class="table-responsive cart_info">                        
                <table class="table table-condensed">
                    <thead>
                        <tr class="cart_menu">
                            <td class="image">Sản phẩm</td>
                            <td class="description"></td>
                            <td class="price">Danh mục</td>
                            <td class="price">Giá</td>
                            <td class="quantity">Số lượng</td>
                            <td class="price">Ăn kèm</td>
                            <td class="total">Tổng</td>
                        </tr>
                    </thead>
                    <tbody>

                        <c:set value="0" var="totalToppingProducts"/>
                        <c:forEach var="product" items="${orderedProducts}">
                            <tr>
                                <td class="cart_product">
                                    <img src="${product.imageURL}" alt="" style="width: 75px; height: 50; object-fit: cover">
                                </td>
                                <td class="cart_description">
                                    <h4>${product.getProductName()}</h4>
                                    <p></p>
                                </td>
                                <td class="cart_price">
                                    <p>${product.getCateogryName()}</p>
                                </td>
                                <td class="cart_price">
                                    ${String.format("%.2f", Double.parseDouble(product.discount != null &&  product.discount != 0 ? (product.price * (100-product.discount)/100) : product.price))}
                                </td>
                                <td class="cart_quantity">
                                    ${product.buyQuantity}
                                </td>
                                <td class="cart_total">
                                    <c:set value="0" var="totalTopping"/>
                                    <c:forEach items="${product.listTopping}" var="t">
                                        <c:set value="${totalTopping + t.price}" var="totalTopping"/>
                                        <c:set value="${totalToppingProducts + t.price}" var="totalToppingProducts"/>
                                    </c:forEach>
                                    ${String.format("%.2f", Double.parseDouble(totalTopping))}
                                </td>
                                <td class="cart_total">
                                    <c:set value="${product.discount != null &&  product.discount != 0 ? (product.price * (100-product.discount)/100)*(product.buyQuantity) + totalTopping: product.price*product.buyQuantity + totalTopping}" var="ttprice"/>
                                    ${String.format("%.2f", Double.parseDouble(ttprice))}
                                </td>
                            </tr>
                        </c:forEach>

                    </tbody>
                </table>
            </div>
            <div>
                <strong>Tổng đơn hàng:</strong>${String.format("%.2f", Double.parseDouble(order.totalCost + totalToppingProducts))}
            </div>
        </div>

        <!-- jQuery và Bootstrap JS -->
        <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.bundle.min.js"></script>



    </body>
</html>

