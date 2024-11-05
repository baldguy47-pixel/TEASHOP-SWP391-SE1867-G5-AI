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
            <a class="nav-item nav-link active" href="${pageContext.request.contextPath}/shipper?page=view-my-order">Đơn hàng của tôi</a>
            <a class="nav-item nav-link">Chi tiết đơn hàng</a>
        </nav>


        <div class="container table-container">
            <h2>Danh Sách Đơn Hàng</h2>
            <div class="table-responsive">
                <table id="userTable" class="table table-striped table-bordered">
                    <thead class="thead-dark">
                        <tr>
                            <th>STT</th>
                            <th>Tên Khách Hàng</th>
                            <th>Số Điện Thoại</th>
                            <th>Địa Chỉ</th>
                            <th>Hành động</th>
                        </tr>
                    </thead>
                    <tbody>               
                        <c:forEach var="order" items="${orders}">
                            <tr>
                                <td>${order.id}</td>
                                <td>${order.fullname}</td>
                                <td>${order.phone}</td>
                                <td>${order.address}</td>
                                <td>
                                    <button class="btn btn-info btn-sm" onclick="showOrderDetails(${order.id})">Chi Tiết</button>
                                    <c:if test="${order.status.trim() ne 'Shipped'}">
                                        <form action="shipper" method="post" style="display: inline">
                                            <input type="hidden" name="orderId" value="${order.id}"/>
                                            <button class="btn btn-success btn-sm" name="action" value="completeOrder">Đã Giao</button>
                                        </form>
                                    </c:if>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>

        <!-- jQuery và Bootstrap JS -->
        <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.bundle.min.js"></script>
        <!-- DataTables JS -->
        <script src="https://cdn.datatables.net/1.10.21/js/jquery.dataTables.min.js"></script>
        <script>
                                        $(document).ready(function () {
                                            $('#userTable').DataTable({
                                                "paging": true,
                                                "lengthChange": true,
                                                "searching": true,
                                                "ordering": true,
                                                "info": true,
                                                "autoWidth": false,
                                                "language": {
                                                    "emptyTable": "Không có dữ liệu",
                                                    "info": "Hiện _START_ đến _END_ trong tổng số _TOTAL_ mục",
                                                    "infoEmpty": "Hiện 0 đến 0 trong tổng số 0 mục",
                                                    "infoFiltered": "(lọc từ _MAX_ mục)",
                                                    "lengthMenu": "Hiển thị _MENU_ mục",
                                                    "search": "Tìm kiếm:",
                                                    "paginate": {
                                                        "first": "Đầu",
                                                        "last": "Cuối",
                                                        "next": "Tiếp",
                                                        "previous": "Trước"
                                                    }
                                                }
                                            });
                                        });
        </script>
        <script>
            function showOrderDetails(id) {
                window.location.href = '?page=order-detail&orderId=' + id;
            }
        </script>
    </body>
</html>

