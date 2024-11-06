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
            <h2>Danh sách đồ ăn kèm</h2>

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

            <button type="button" class="btn btn-primary mb-3" data-toggle="modal" data-target="#addSettingModal">Thêm món</button>

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
                <input type="text" id="nameSearch" class="form-control" style="width: auto; display: inline-block;" placeholder="Tìm kiếm">
            </div>

            <table id="settingTable" class="table table-striped">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Hình ảnh</th>
                        <th>Tên</th>
                        <th>Giá</th>
                        <th>Trạng thái</th>
                        <th>Hành động</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="topping" items="${toppings}">
                        <tr>
                            <td>${topping.id}</td>
                            <td><img src="${topping.img}" alt="alt" style="width: 50px; height: 50px; object-fit: cover"/></td>
                            <td>${topping.toppingName}</td>
                            <td>${topping.price}</td>
                            <td>
                                <c:if test="${topping.isDeleted}">
                                    Không hoạt động
                                </c:if>
                                <c:if test="${!topping.isDeleted}">
                                    Hoạt động
                                </c:if>
                            </td>
                            <td>
                                <button type="button" class="btn btn-primary btn-sm" data-toggle="modal" data-target="#editSettingModal_${topping.id}">Chỉnh sửa</button>
                            </td>
                        </tr>
                        <!-- Edit Setting Modal -->

                    </c:forEach>
                </tbody>
            </table>

        </div>

        <!--edit modal-->
        <c:forEach var="topping" items="${toppings}">
            <div class="modal fade" id="editSettingModal_${topping.id}" tabindex="-1" role="dialog" aria-labelledby="editSettingModalLabel_${topping.id}" aria-hidden="true">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="editSettingModalLabel_${setting.getID()}">Chỉnh sửa</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <!-- Edit Setting Form -->
                            <form action="topping" method="post">
                                <input type="hidden" name="action" value="update">
                                <input type="hidden" name="toppingId" value="${topping.id}">
                                <div class="form-group">
                                    <img src="${topping.img}" alt="alt" style="width: 150px; height: 150px; object-fit: cover; margin-bottom: 5px"/>
                                    <input class="inputImage" type="hidden" name="toppingImage" value="${topping.img}">
                                    <input type="file" class="form-control" onchange="inputImage(event, this)">
                                </div>
                                <div class="form-group">
                                    <label>Tên</label>
                                    <input type="text" class="form-control" name="toppingName" value="${topping.toppingName}" required>
                                </div>
                                <div class="form-group">
                                    <label>Giá</label>
                                    <input type="number" class="form-control" name="toppingPrice" value="${topping.price}" required>
                                </div>
                                <div class="form-group">
                                    <label for="status">Trạng thái</label>
                                    <select class="form-control" id="status" name="toppingStatus">
                                        <option value="false" ${!setting.isDeleted ? "selected" : ""}>Hoạt động</option>
                                        <option value="true" ${setting.isDeleted ? "selected" : ""}>Không hoạt động</option>
                                    </select> 
                                </div>
                                <button type="button" onclick="validateForm(this)" class="btn btn-primary">Lưu lại</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>

        <!-- Add Setting Modal -->
        <div class="modal fade" id="addSettingModal" tabindex="-1" role="dialog" aria-labelledby="addSettingModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="addSettingModalLabel">Thêm đồ ăn kèm</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <!-- Add Setting Form -->
                        <form action="topping" method="post">
                            <input type="hidden" name="action" value="add">
                            <div class="form-group">
                                <img src="" alt="alt" style="width: 150px; height: 150px; object-fit: cover; margin-bottom: 5px"/>
                                <input class="inputImage" type="hidden" name="toppingImage" value="">
                                <input type="file" class="form-control" onchange="inputImage(event, this)">
                            </div>
                            <div class="form-group">
                                <label for="value">Tên</label>
                                <input type="text" class="form-control" id="value" name="toppingName" required>
                            </div>
                            <div class="form-group">
                                <label for="order">Giá</label>
                                <input type="number" class="form-control" id="order" name="toppingPrice" required>
                            </div>
                            <button type="button" onclick="validateForm(this)" class="btn btn-primary">Thêm</button>
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
                                        table.columns(2).search(this.value).draw();
                                    });

                                    $('#statusFilter').on('change', function () {
                                        var selectedStatus = $(this).val();
                                        if (selectedStatus) {
                                            table.columns(4).search('^' + selectedStatus + '$', true, false).draw();
                                        } else {
                                            table.columns(4).search('').draw();
                                        }
                                    });

                                    $('#settingTable_wrapper .dataTables_filter').addClass('d-none');
                                });
        </script>

        <script>
            function inputImage(event, element) {
                const file = event.target.files[0];
                const parentElement = element.parentNode;

                // Kiểm tra kích thước tệp (dưới 1MB)
                if (file && file.size < 1024 * 1024) {
                    const reader = new FileReader();

                    reader.onload = function (e) {
                        const base64String = e.target.result;

                        // Hiển thị ảnh preview
                        parentElement.querySelector('img').src = base64String;

                        // Gán chuỗi base64 vào trường hidden
                        parentElement.querySelector('.inputImage').value = base64String;
                    };

                    reader.readAsDataURL(file);
                } else {
                    alert("Ảnh phải bé hơn 1MB.");
                }
            }

            function validateForm(button) {
                const form = button.closest('form'); // Lấy thẻ form chứa nút
                const nameField = form.querySelector('input[name="toppingName"]');
                const priceField = form.querySelector('input[name="toppingPrice"]');

                // Kiểm tra trường Tên
                if (!nameField.value.trim()) {
                    alert("Tên không được trống.");
                    nameField.focus();
                    return; // Dừng thực thi nếu có lỗi
                }

                // Kiểm tra trường Giá
                const priceValue = parseFloat(priceField.value);
                if (isNaN(priceValue) || priceValue <= 0) {
                    alert("Giá tiền phải lớn hơn 0 0.");
                    priceField.focus();
                    return; // Dừng thực thi nếu có lỗi
                }

                // Nếu tất cả hợp lệ, có thể gửi form
                form.submit(); // Gửi form
            }


        </script>

    </body>
</html>
