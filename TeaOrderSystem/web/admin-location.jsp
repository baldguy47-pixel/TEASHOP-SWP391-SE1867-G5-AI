<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Setting List</title>
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
            <h2>Danh sách quận huyện</h2>

            <button type="button" class="btn btn-primary mb-3" data-toggle="modal" data-target="#addSettingModal">Thêm địa chỉ</button>

            <!-- Status Filter -->
            <div class="mb-3">
                <label for="statusFilter">Lọc theo trạng thái</label>
                <select id="statusFilter" class="form-control" style="width: auto; display: inline-block;">
                    <option value="">Tất cả</option>
                    <option value="Hoạt động">Hoạt động</option>
                    <option value="Không hoạt động">Không hoạt động</option>
                </select>
            </div>

            <!-- Name Search -->
            <div class="mb-3 mt-2">
                <label for="nameSearch">Tìm kiếm theo tên:</label>
                <input type="text" id="nameSearch" class="form-control" style="width: auto; display: inline-block;" placeholder="Enter name">
            </div>

            <table id="settingTable" class="table table-striped">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Quận/huyện</th>
                        <th>Trạng thái</th>
                        <th>Hành động</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="location" items="${locations}">
                        <tr>
                            <td>${location.getID()}</td>              
                            <td>${location.locationName}</td>
                            <td>
                                <c:if test="${location.isDeleted == 1}">
                                    Không hoạt động
                                </c:if>
                                <c:if test="${location.isDeleted == 0}">
                                    Hoạt động
                                </c:if>
                            </td>
                            <td>
                                <button type="button" class="btn btn-primary btn-sm" data-toggle="modal" data-target="#editLocationModal_${location.getID()}">Chỉnh sửa</button>
                            </td>
                        </c:forEach>
                </tbody>
            </table>

        </div>


        <!--edit location-->
        <c:forEach var="location" items="${locations}">
            <div class="modal fade" id="editLocationModal_${location.getID()}" tabindex="-1" role="dialog" aria-labelledby="editLocationModalLabel_${location.getID()}" aria-hidden="true">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="editLocationModalLabel_${location.getID()}">Chỉnh sửa</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <!-- Form chỉnh sửa -->
                            <form action="location" method="post">
                                <input type="hidden" name="action" value="update">
                                <input type="hidden" name="locationId" value="${location.getID()}">
                                <div class="form-group">
                                    <label>Tên</label>
                                    <input type="text" class="form-control" name="locationName" pattern=".*\S.*" value="${location.locationName}" required>
                                </div>
                                <div class="form-group">
                                    <label for="status_${location.getID()}">Trạng thái</label>
                                    <select class="form-control" id="status_${location.getID()}" name="locationStatus">
                                        <option value="0" ${location.isDeleted == 0 ? "selected" : ""}>Hoạt động</option>
                                        <option value="1" ${location.isDeleted == 1 ? "selected" : ""}>Không hoạt động</option>
                                    </select>
                                </div>
                                <button type="submit" class="btn btn-primary">Lưu lại</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>


        <!-- Add -->
        <div class="modal fade" id="addSettingModal" tabindex="-1" role="dialog" aria-labelledby="addSettingModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="addSettingModalLabel">Thêm địa chỉ</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <!-- Add Setting Form -->
                        <form action="location" method="post">
                            <input type="hidden" name="action" value="add">
                            <div class="form-group">
                                <label for="value">Quận/Huyện</label>
                                <input type="text" class="form-control" id="value" name="locationName" pattern=".*\S.*" required>
                            </div>
                            <button type="submit" class="btn btn-primary">Thêm</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <c:if test="${requestScope.notification != null}">
            <div class="modal fade" id="notificationModal" tabindex="-1" role="dialog" aria-labelledby="notificationModalLabel" aria-hidden="true">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="notificationModalLabel">Thông báo</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            ${requestScope.notification}
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Đóng</button>
                        </div>
                    </div>
                </div>
            </div>

            <!-- JavaScript để tự động mở modal -->
            <script>
                $(document).ready(function () {
                    $('#notificationModal').modal('show');
                });
            </script>
        </c:if>

        <!-- Bootstrap JS and jQuery -->
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        <!-- DataTable JS -->
        <script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.10.24/js/jquery.dataTables.min.js"></script>
        <script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.10.24/js/dataTables.bootstrap4.min.js"></script>

        <script>
                $(document).ready(function () {
                    let table = $('#settingTable').DataTable({
                        "paging": true,
                        "pageLength": 5,
                        "lengthChange": false,
                        "searching": true,
                        "ordering": true,
                        "order": [[0, 'desc']],
                        "info": false,
                        "autoWidth": false
                    });

                    $('#nameSearch').on('keyup', function () {
                        table.columns(1).search(this.value).draw();
                    });

                    $('#statusFilter').on('change', function () {
                        var selectedStatus = $(this).val();
                        if (selectedStatus) {
                            table.columns(2).search('^' + selectedStatus + '$', true, false).draw();
                        } else {
                            table.columns(2).search('').draw();
                        }
                    });

                    $('#settingTable_wrapper .dataTables_filter').addClass('d-none');
                });
        </script>


    </body>
</html>
