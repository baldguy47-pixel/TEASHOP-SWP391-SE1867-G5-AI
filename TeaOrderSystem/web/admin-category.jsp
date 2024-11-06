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
    </head>
    <body>
        <!-- Sidebar -->
        <%@ include file="admin-sidebar.jsp" %>

        <div class="mt-5 main-content">
            <h2>Danh sách loại sản phẩm</h2>


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

            <button type="button" class="btn btn-primary btn-sm mb-3" data-toggle="modal" data-target="#addCategoryModal">Thêm</button>

            <!--filter form-->
            <form action="category" method="get" class="form-inline mb-3">
                <div class="form-group mr-2">
                    <input type="text" class="form-control" name="search" value="${searchQuery}" placeholder="Tìm kiếm tên">
                </div>
                <button type="submit" class="btn btn-primary">Tìm kiếm</button>
            </form>


            <table id="userTable" class="table table-striped">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Tên loại sản phẩm</th>
                        <th>Tình trạng</th>
                        <th>Ngày tạo</th>
                        <th>Thao tác</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="category" items="${categories}">
                        <tr>
                            <td>${category.ID}</td>
                            <td>${category.categoryName}</td>
                            <td>${category.isDeleted ? 'Không hoạt động' : 'Hoạt động'}</td>
                            <td>${category.createdAt}</td>
                            <td>
                                <button type="button" class="btn btn-primary btn-sm" data-toggle="modal" data-target="#editCategoryModal_${category.ID}">Sửa</button>
                            </td>
                        </tr>

                    </c:forEach>
                </tbody>
            </table>

            <nav aria-label="Page navigation">
                <ul class="pagination">
                    <li class="page-item">
                        <a class="page-link" href="?page=1&search=${searchQuery}" aria-label="Previous">
                            <span aria-hidden="true">&laquo;</span>
                        </a>
                    </li>
                    <c:forEach begin="1" end="${totalPages}" step="1" var="i">
                        <li class="page-item ${currentPage == i ? 'active' : ''}">
                            <a class="page-link" href="?page=${i}&search=${searchQuery}">${i}</a>
                        </li>
                    </c:forEach>
                    <li class="page-item">
                        <a class="page-link" href="?page=${totalPages}&search=${searchQuery}" aria-label="Next">
                            <span aria-hidden="true">&raquo;</span>
                        </a>
                    </li>
                </ul>
            </nav>
        </div>

        <c:forEach var="category" items="${categories}">

            <!-- Edit User Modal -->
            <div class="modal fade" id="editCategoryModal_${category.ID}" tabindex="-1" role="dialog" aria-labelledby="editCategoryModalLabel_${category.ID}" aria-hidden="true">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="editCategoryModalLabel_${category.ID}">Chỉnh sửa</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <!-- Edit User Form -->
                            <form action="category" method="post">
                                <input type="hidden" name="action" value="update">
                                <input type="hidden" name="id" id="updateCategoryId" value="${category.ID}">
                                <div class="modal-body">
                                    <div class="form-group">
                                        <label for="categoryName">Tên</label>
                                        <input type="text" class="form-control" name="name" id="updateCategoryName" pattern="^(?!\s+$)[a-zA-Z\s]+$" title="Must enter text" value="${category.categoryName}" required>
                                    </div>
                                    <div class="form-group">
                                        <label for="isDeleted">Trạng thái</label>
                                        <select class="form-control" id="isDeleted" name="isDeleted">
                                            <option value="false" ${!category.isDeleted ? 'selected' : ''}>Hoạt động</option>
                                            <option value="true" ${category.isDeleted ? 'selected' : ''}>Không hoạt động</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="modal-footer">
                                    <button type="submit" class="btn btn-primary">
                                        Lưu</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>

        <!-- Add User Modal -->
        <div class="modal fade" id="addCategoryModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <form action="category" method="post">
                        <input type="hidden" name="action" value="add">
                        <div class="modal-header">
                            <h5 class="modal-title">Thêm mới Loại sản phẩm</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <div class="form-group">
                                <label for="categoryName">Tên</label>
                                <input type="text" class="form-control" name="name" pattern="^(?!\s+$)[a-zA-Z\s]+$" title="Must enter text" required>
                            </div>
                            <div class="form-group">
                                <label for="isDeleted">Trạng thái</label>
                                <select class="form-control" id="isDeleted" name="isDeleted">
                                    <option value="false" ${!category.isDeleted ? 'selected' : ''}>Hoạt động</option>
                                    <option value="true" ${category.isDeleted ? 'selected' : ''}>Không hoạt động</option>
                                </select>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="submit" class="btn btn-primary">Lưu</button>
                        </div>
                    </form>
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
    </body>
</html>
