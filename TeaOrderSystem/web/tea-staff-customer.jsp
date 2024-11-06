<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>User List</title>
        <!-- Bootstrap CSS -->
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
        <!-- DataTable CSS -->
        <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.24/css/dataTables.bootstrap4.min.css">
        <!-- Font Awesome CSS for icons -->
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" rel="stylesheet">

        <style>
            .modal-lg {
                max-width: 80%;
            }

            .table th, .table td {
                vertical-align: middle;
            }

        </style>
    </head>
    <body>
        <!-- Sidebar -->
        <%@ include file="tea-staff-sidebar.jsp" %>

        <div class="mt-5 main-content">
            <h2>Danh sách người dùng</h2>


            <c:if test="${param.success ne null}">
                <div class="alert alert-success" role="alert">
                    Thành công!
                </div>
            </c:if>
            <c:if test="${param.fail ne null}">
                <div class="alert alert-danger" role="alert">
                    Thất bại!
                </div>
            </c:if>

            <!--filter form-->
            <form id="searchForm" action="customer" method="get" class="form-inline mb-3">
                <div class="form-group mr-2">
                    <input type="text" class="form-control" name="fullName" placeholder="Họ và tên" value="${fullName}">
                </div>
                <div class="form-group mr-2">
                    <input type="text" class="form-control" name="email" placeholder="Email" value="${email}">
                </div>
                <div class="form-group mr-2">
                    <input type="text" class="form-control" name="phone" placeholder="Số điện thoại" value="${phone}">
                </div>
                <div class="form-group mr-2">
                    <select class="form-control" name="gender">
                        <option value="">Giới tính</option>
                        <option value="Male" ${gender eq 'Male' ? 'selected' : ''}>Nam</option>
                        <option value="Female" ${gender eq 'Female' ? 'selected' : ''}>Nữ</option>
                    </select>
                </div>
                <div class="form-group mr-2">
                    <select class="form-control" name="status">
                        <option value="">Trạng thái</option>
                        <option value="true" ${statusString eq 'true' ? 'selected' : ''}>Không hoạt động</option>
                        <option value="false" ${statusString eq 'false' ? 'selected' : ''}>Hoạt động</option>
                    </select>
                </div>
                <input type="hidden" name="page" id="pageInput" value="1">
                <button type="submit" class="btn btn-primary">Tìm kiếm</button>
            </form>


            <table id="userTable" class="table table-striped">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Họ và tên</th>
                        <th>Email</th>
                        <th>Giới tính</th>
                        <th>Địa chỉ</th>
                        <th>Số điện thoại</th>
                        <th>Trạng Thái</th>
                        <th>Hành động</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="user" items="${userList}">
                        <tr>
                            <td>${user.id}</td>
                            <td>${user.fullname}</td>
                            <td>${user.email}</td>
                            <td>${user.gender}</td>
                            <td>${user.address}</td>
                            <td>${user.phone}</td>
                            <td>${user.isDeleted ? 'Không hoạt động' : 'Hoạt động'}</td>
                            <td>
                                <button type="button" class="btn btn-info btn-sm" data-toggle="modal" data-target="#userInfoModal_${user.id}">Thông tin</button>
                                <button type="button" class="btn btn-primary btn-sm" data-toggle="modal" data-target="#editUserModal_${user.id}">Chỉnh sửa</button>
                            </td>
                        </tr>

                    </c:forEach>
                </tbody>
            </table>

            <nav aria-label="Page navigation">
                <ul class="pagination">
                    <li class="page-item">
                        <button class="page-link" onclick="submitFormWithPage(1)" aria-label="Previous">
                            <span aria-hidden="true">&laquo;</span>
                        </button>
                    </li>
                    <c:forEach begin="1" end="${totalPages}" step="1" var="i">
                        <li class="page-item ${currentPage == i ? 'active' : ''}">
                            <button class="page-link" onclick="submitFormWithPage(${i})">${i}</button>
                        </li>
                    </c:forEach>
                    <li class="page-item">
                        <button class="page-link" onclick="submitFormWithPage(${totalPages})" aria-label="Next">
                            <span aria-hidden="true">&raquo;</span>
                        </button>
                    </li>
                </ul>
            </nav>
        </div>

        <c:forEach var="user" items="${userList}">

            <!-- Edit User Modal -->
            <div class="modal fade" id="editUserModal_${user.id}" tabindex="-1" role="dialog" aria-labelledby="editUserModalLabel_${user.id}" aria-hidden="true">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="editUserModalLabel_${user.id}">Edit User</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <!-- Edit User Form -->
                            <form action="customer" method="post">
                                <input type="hidden" name="action" value="update">
                                <input type="hidden" name="userId" value="${user.id}">
                                <div class="form-group">
                                    <label for="fullName">Họ và tên</label>
                                    <input type="text" class="form-control" id="fullName" name="fullName" value="${user.fullname}">
                                </div>
                                <div class="form-group">
                                    <label for="email">Email</label>
                                    <input type="email" class="form-control" id="email" name="email" value="${user.getEmail()}">
                                </div>
                                <div class="form-group">
                                    <label for="gender">Giới tính</label>
                                    <select class="form-control" id="gender" name="gender">
                                        <option value="true" ${user.gender eq 'Male' ? "selected" : ""}>Nam</option>
                                        <option value="false" ${user.gender eq 'Female' ? "selected" : ""}>Nữ</option>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label for="address">Địa chỉ</label>
                                    <input type="text" class="form-control" id="address" name="address" value="${user.getAddress()}">
                                </div>
                                <div class="form-group">
                                    <label for="phone">SĐT</label>
                                    <input type="text" class="form-control" id="phone" name="phone" value="${user.getPhone()}">
                                </div>
                                <div class="form-group">
                                    <label for="status">Trạng thái</label>
                                    <select class="form-control" id="status" name="status">
                                        <option value="false" ${!user.isDeleted ? "selected" : ""}>Hoạt động</option>
                                        <option value="true" ${user.isDeleted ? "selected" : ""}>Không hoạt động</option>
                                    </select>
                                </div>
                                <!-- Add other fields as needed -->
                                <button type="submit" class="btn btn-primary">Lưu thay đổi</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>

            <!-- User Info Modal -->
            <div class="modal fade" id="userInfoModal_${user.id}" tabindex="-1" role="dialog" aria-labelledby="userInfoModalLabel_${user.id}" aria-hidden="true">
                <div class="modal-dialog modal-lg" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="userInfoModalLabel_${user.id}">Người dùng</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <div class="d-flex flex-column align-items-center">
                                <img src="${user.avatar}" class="img-thumbnail mb-3" alt="Profile Image" style="width: 150px; height: 150px;">
                            </div>
                            <table class="table table-bordered">
                                <tbody>
                                    <tr>
                                        <th>ID</th>
                                        <td>${user.id}</td>
                                    </tr>
                                    <tr>
                                        <th>Họ và tên</th>
                                        <td>${user.fullname}</td>
                                    </tr>
                                    <tr>
                                        <th>Email</th>
                                        <td>${user.getEmail()}</td>
                                    </tr>
                                    <tr>
                                        <th>Giới tính</th>
                                        <td>${user.gender}</td>
                                    </tr>
                                    <tr>
                                        <th>Địa chỉ</th>
                                        <td>${user.getAddress()}</td>
                                    </tr>
                                    <tr>
                                        <th>SĐT</th>
                                        <td>${user.getPhone()}</td>
                                    </tr>
                                    <tr>
                                        <th>Trạng thái</th>
                                        <td>${user.isDeleted ? 'Không hoạt động' : 'Hoạt động'}</td>
                                    </tr>
                                </tbody>
                            </table>
                            <div>
                                <strong>Lịch sử thay đổi</strong>
                                <table class="table">
                                    <thead>
                                        <tr>
                                            <th>Ngày cập nhật</th>
                                            <th>Email</th>
                                            <th>Họ và tên</th>
                                            <th>Giới tính</th>
                                            <th>Địa chỉ</th>
                                            <th>SĐT</th>
                                            <th>Người cập nhật</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        ${user.changeHistory eq null ? '<tr><td>No data</td></tr>' : user.changeHistory}
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>



        </c:forEach>

        <!-- Add User Modal -->
        <div class="modal fade" id="addUserModal" tabindex="-1" role="dialog" aria-labelledby="addUserModalLabel" aria-hidden="true">
            <!-- Modal Content -->
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <!-- Modal Header -->
                    <div class="modal-header">
                        <h5 class="modal-title" id="addUserModalLabel">Thêm người dùng</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <!-- Modal Body -->
                    <div class="modal-body">
                        <!-- Add User Form -->
                        <form action="customer" method="post">
                            <!-- Hidden Field -->
                            <input type="hidden" name="action" value="add">
                            <!-- Form Inputs -->
                            <div class="form-group">
                                <label for="fullName">Họ và tên</label>
                                <input type="text" class="form-control" id="fullName" name="fullName" required>
                            </div>
                            <div class="form-group">
                                <label for="email">Email</label>
                                <input type="email" class="form-control" id="email" name="email" required>
                            </div>
                            <div class="form-group">
                                <label for="password">Mật khẩu</label>
                                <input type="password" class="form-control" id="password" name="password" required>
                            </div>
                            <div class="form-group">
                                <label for="gender">Giới tính</label>
                                <select class="form-control" id="gender" name="gender">
                                    <option value="true">Nam</option>
                                    <option value="false">Nữ</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label for="address">Địa chỉ</label>
                                <input type="text" class="form-control" id="address" name="address">
                            </div>
                            <div class="form-group">
                                <label for="phone">SĐT</label>
                                <input type="text" class="form-control" id="phone" name="phone">
                            </div>
                            <button type="submit" class="btn btn-primary">Lưu</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>


        <!-- Bootstrap JS and jQuery -->
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        <!-- DataTable JS -->
        <script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.10.24/js/jquery.dataTables.min.js"></script>
        <script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.10.24/js/dataTables.bootstrap4.min.js"></script>

        <script>
                            $(document).ready(function () {
                                $('#userTable').DataTable({
                                    "paging": false,
                                    "lengthChange": false,
                                    "searching": false,
                                    "ordering": true,
                                    "info": false,
                                    "autoWidth": false
                                });
                            });
        </script>

        <script>
            function submitFormWithPage(page) {
                document.getElementById('pageInput').value = page;
                document.getElementById('searchForm').submit();
            }
        </script>

    </body>
</html>
