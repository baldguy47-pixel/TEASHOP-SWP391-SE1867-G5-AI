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
        <div class="main-content text-center" style="margin-top: 2%">
            <h2>Tất cả hóa đơn</h2>
            <c:if test="${isSuccess ne null && isSuccess}">
                <div class="alert alert-success alert-dismissible fade show mt-2" role="alert" id="mess">
                    <strong>Thành công!</strong> 
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close" onclick="document.getElementById('mess').style.display = 'none'"></button>
                </div>
            </c:if>
            <c:if test="${isSuccess ne null && !isSuccess}">
                <div class="alert alert-danger alert-dismissible fade show mt-2" role="alert" id="mess">
                    <strong>Thất bại!</strong> 
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
            </c:if>
            <form method="get" action="teastaff-order" class="mb-3 " style="width: 100%">
                <div class="row d-flex ${sessionScope.staff.role eq 4 ? 'justify-content-center' : 'justify-content-between' }" >
                    <div class="col-md-2">
                        <label for="startDate" class="form-label">Từ ngày: </label>
                        <input type="date" id="startDate" name="startDate" class="form-control" value="${param.startDate}">
                    </div>
                    <div class="col-md-2">
                        <label for="endDate" class="form-label">Đến ngày: </label>
                        <input type="date" id="endDate" name="endDate" class="form-control" value="${param.endDate}">
                    </div>
                    <c:if test="${sessionScope.staff.role eq 4}">
                        <div class="col-md-2 mr-2">
                            <label for="salesperson" class="form-label">Người bán</label>
                            <input  type="text" id="salesperson" name="salesperson" class="form-control" value="${param.salesperson}">
                        </div>
                    </c:if>
                    <div class="${sessionScope.staff.role eq 4 ? 'col-md-1' : 'col-md-2' }">
                        <label for="salesperson" class="form-label">ID: </label>
                        <input type="text" id="salesperson" name="id"   class="form-control" value="${param.id}">
                    </div>
                    <div class="col-md-2">
                        <label for="salesperson" class="form-label">Tên khách hàng: </label>
                        <input type="text" id="salesperson" name="customerName" class="form-control" width="100%" value="${param.customerName}">
                    </div>
                    <div class="col-md-2">
                        <label for="orderStatus" class="form-label">Trạng thái</label>
                        <select id="orderStatus" name="orderStatus" class="form-control">
                            <option value="" ${param.orderStatus == null ? 'selected' : ''}>Tất cả</option>
                            <option value="Submitted" ${param.orderStatus eq"Submitted" ? "selected" : ""}>Đã thanh toán</option>
                            <option value="Shipped" ${param.orderStatus eq"Shipped" ? "selected" : ""}>Đã giao</option>
                            <option value="Wait for pay" ${param.orderStatus eq"Wait for pay" ? "selected" : ""}>Chưa thanh toán</option>
                            <option value="Delivering" ${param.orderStatus eq"Delivering" ? "selected" : ""}>Đang giao</option>
                        </select>
                    </div>
                    <div class="d-flex flex-column">
                        <label for="orderStatus" style="visibility: hidden" class="form-label">O</label>
                        <button type="submit" class="btn btn-primary">Lọc</button>
                    </div>
                </div>
            </form>
            <table class="table">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Ngày đặt</th>
                        <th>Người mua</th>
                        <th>Người bán</th>
                        <th>Địa chỉ</th>
                        <th>SĐT</th>
                        <th>Tổng giá</th>
                        <th>Thanh toán</th>
                        <th>Trạng thái</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="item" items="${orders}">
                        <tr>
                            <td><a href="order-detail?orderId=${item.id}">${item.id}</a></td>
                            <td>${item.createdAt}</td>
                            <td>${item.fullname}</td>
                            <td>${item.staff.fullname}</td>
                            <td>${item.address}</td>
                            <td>${item.phone}</td>
                            <td>${String.format("%.0f", item.totalCost)}VND</td>
                            <td>${item.paymentMethod}</td>
                            <td>
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
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <!-- Pagination Controls -->
            <nav aria-label="Page navigation">
                <ul class="pagination justify-content-center">
                    <c:if test="${currentPage > 1}">
                        <li class="page-item">
                            <a class="page-link" href="?page=${currentPage - 1}&startDate=${param.startDate}&endDate=${param.endDate}&salesperson=${param.salesperson}&orderStatus=${param.orderStatus}" aria-label="Previous">
                                <span aria-hidden="true">&laquo;</span>
                            </a>
                        </li>
                    </c:if>
                    <c:forEach var="i" begin="1" end="${totalPages}">
                        <li class="page-item ${i == currentPage ? 'active' : ''}">
                            <a class="page-link" href="?page=${i}&startDate=${param.startDate}&endDate=${param.endDate}&salesperson=${param.salesperson}&orderStatus=${param.orderStatus}">${i}</a>
                        </li>
                    </c:forEach>
                    <c:if test="${currentPage < totalPages}">
                        <li class="page-item">
                            <a class="page-link" href="?page=${currentPage + 1}&startDate=${param.startDate}&endDate=${param.endDate}&salesperson=${param.salesperson}&orderStatus=${param.orderStatus}" aria-label="Next">
                                <span aria-hidden="true">&raquo;</span>
                            </a>
                        </li>
                    </c:if>
                </ul>
            </nav>
        </div>
        <!-- Bootstrap JS and jQuery -->
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>


    </body>
</html>
