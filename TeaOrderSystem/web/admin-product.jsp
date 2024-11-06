<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Product List</title>
        <!-- Bootstrap CSS -->
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
        <!-- DataTable CSS -->
        <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.24/css/dataTables.bootstrap4.min.css">
        <!-- Font Awesome CSS for icons -->
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" rel="stylesheet">

        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/choices.js/public/assets/styles/choices.min.css">
        <script src="https://cdn.jsdelivr.net/npm/choices.js/public/assets/scripts/choices.min.js"></script>

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
        <%@ include file="admin-sidebar.jsp" %>

        <div class="mt-5 main-content">
            <h2>Danh sách sản phẩm</h2>

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



            <!-- Filter Form -->
            <form id="searchForm" action="product" method="get" class="d-flex mb-4 justify-content-center">
                <input type="hidden" name="page" id="pageInput" value="1">
                <div class="form-group mr-2 col-2">
                    <input class="form-control me-2 mb-4" type="search" name="searchQuery" placeholder="Tìm kiếm" aria-label="Search" value="${searchQuery}">
                </div>

                <div class="form-group mr-2  col-2">
                    <select class="form-control me-2 mb-4" name="categoryId">
                        <option value="">Tất cả danh mục</option>
                        <c:forEach items="${categories}" var="category">
                            <option value="${category.ID}" <c:if test="${categoryId == category.ID}">selected</c:if>>${category.categoryName}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group mr-2">
                    <button class="btn btn-outline-success" type="submit">Tìm kiếm</button>
                    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#addProductModal">
                        Thêm sản phẩm
                    </button>
                </div>
            </form>

            <!-- Product Table -->
            <table id="productTable" class="table table-striped">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Tên sản phẩm</th>
                        <th>Danh mục</th>
                        <th>Mô tả</th>
                        <th>Trạng thái</th>
                        <th>Hành động</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="product" items="${productList}">
                        <tr>
                            <td>${product.productId}</td>                         
                            <td>${product.productName}</td>
                            <td>${product.categoryName}</td>
                            <td>${product.description}</td>
                            <td>${product.isDeleted ? 'Không hoạt động' : 'Hoạt động'}</td>
                            <td>
                                <a href="product-detail?productId=${product.productId}" class="btn btn-info btn-sm">Chi tiết</a>
                                <button type="button" class="btn btn-primary btn-sm" data-toggle="modal" data-target="#editProductModal_${product.productId}">Sửa</button>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <!-- Pagination -->
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

        <!-- Edit Product Modals -->
        <c:forEach var="product" items="${productList}">
            <!-- Edit Product Modal -->
            <div class="modal fade" id="editProductModal_${product.productId}" tabindex="-1" role="dialog" aria-labelledby="editProductModalLabel_${product.productId}" aria-hidden="true">
                <!-- Modal Content -->
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <!-- Modal Header -->
                        <div class="modal-header">
                            <h5 class="modal-title" id="editProductModalLabel_${product.productId}">Edit Product</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <!-- Modal Body -->
                        <div class="modal-body">
                            <!-- Edit Product Form -->
                            <form action="product" method="post">
                                <!-- Hidden Field -->
                                <input type="hidden" name="action" value="update">
                                <input type="hidden" name="productId" value="${product.productId}">
                                <div class="form-group" style="display: none">
                                    <label for="imageUrl">Ảnh</label>
                                    <img id="image${product.productId}" class="w-100" src="${product.baseImageURL}">
                                    <input type="file" class="form-control" id="imageFile${product.productId}" accept="image/*" onchange="updateImage(${product.productId})">
                                    <input type="hidden" class="form-control" id="imageUrl${product.productId}" name="imageUrl" value="${product.baseImageURL}">
                                </div>
                                <div class="form-group">
                                    <label for="productName">Tên sản phẩm</label>
                                    <input type="text" class="form-control" id="productName" name="productName" value="${product.productName}" required>
                                </div>
                                <div class="form-group d-none">
                                    <label for="categoryName">Danh mục</label>
                                    <input type="text" class="form-control" id="categoryName" name="categoryName" value="${product.categoryName}" required>
                                </div>
                                <div class="form-group">
                                    <label for="description">Mô tả</label>
                                    <textarea class="form-control" id="description" name="description" rows="3">${product.description}</textarea>
                                </div>
                                <div class="form-group d-none">
                                    <label for="createdBy">Tạo bởi</label>
                                    <input type="text" class="form-control" id="createdBy" name="createdBy" value="${product.createdBy}" required>
                                </div>
                                <div class="form-group">
                                    <label for="isDeleted">Trang thái</label>
                                    <select class="form-control" id="isDeleted" name="isDeleted">
                                        <option value="false" ${!product.isDeleted ? 'selected' : ''}>Hoạt động</option>
                                        <option value="true" ${product.isDeleted ? 'selected' : ''}>Không hoạt động</option>
                                    </select>
                                </div>
                                <!-- Add other fields as needed -->
                                <button type="submit" class="btn btn-primary">Lưu</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>

        <!-- Add Product Modal -->
        <div class="modal fade" id="addProductModal" tabindex="-1" role="dialog" aria-labelledby="addProductModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="addProductModalLabel">Thêm mới</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <form id="addProductForm" action="product" method="post">
                        <input type="hidden" name="action" value="add">
                        <div class="modal-body">
                            <div class="form-group" style="display: none">
                                <label for="imageUrl">Ảnh</label>
                                <img id="image0" class="w-100" src="">
                                <input type="file" class="form-control" id="imageFile0" accept="image/*" onchange="updateImage(0)">
                                <input type="hidden" class="form-control" id="imageUrl0" name="imageUrl" value="">
                            </div>
                            <div class="form-group">
                                <label for="productName">Tên sản phẩm</label>
                                <input type="text" class="form-control" id="productName" name="productName" required>
                            </div>
                            <div class="form-group">
                                <label for="categoryId">Danh mục</label>
                                <select class="form-control" id="categoryId" name="categoryId" required>
                                    <c:forEach var="category" items="${categories}">
                                        <option value="${category.getID()}">${category.categoryName}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="form-group">
                                <label for="description">Mô tả</label>
                                <textarea class="form-control" id="description" name="description" rows="3"></textarea>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Đóng</button>
                            <button type="submit" class="btn btn-primary">Lưu</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>


        <!-- jQuery and Bootstrap Bundle (includes Popper) -->
        <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.bundle.min.js"></script>
        <!-- DataTables -->
        <script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.10.24/js/jquery.dataTables.js"></script>
        <script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.10.24/js/dataTables.bootstrap4.min.js"></script>


        <script>
                                    $(document).ready(function () {
                                        // Initialize DataTable
                                        $('#productTable').DataTable({
                                            "paging": false,
                                            "lengthChange": false,
                                            "searching": false,
                                            "ordering": true,
                                            "order":[[0,'desc']],
                                            "info": false,
                                            "autoWidth": false
                                        });
                                    });

                                    // Function to submit form with page number
                                    function submitFormWithPage(page) {
                                        $('#pageInput').val(page);
                                        $('#searchForm').submit();
                                    }
        </script>

        <script>
            function updateImage(sliderId) {
                let fileInput = document.getElementById(`imageFile` + sliderId);
                let image = document.getElementById(`image` + sliderId);
                let hiddenInput = document.getElementById(`imageUrl` + sliderId);
                console.log(fileInput, image, hiddenInput)

                // check file uploaded
                if (fileInput.files && fileInput.files[0]) {
                    const file = fileInput.files[0];
                    const maxSize = 1024 * 1024; // 1 MB in bytes

                    if (file.size > maxSize) {
                        alert("Ảnh chỉ có thể có dung lượng dưới 1MB");
                        fileInput.value = ''; // Clear the file input
                        return;
                    }

                    // dịch image thành url
                    const reader = new FileReader();

                    reader.onload = function (e) {
                        // Update the image src
                        image.src = e.target.result;

                        // Optionally, update the hidden input with the base64 data URL
                        hiddenInput.value = e.target.result;
                    };

                    reader.readAsDataURL(file);
                }
            }
        </script>

        <script>
            document.addEventListener('DOMContentLoaded', function () {
                let elements = document.getElementsByClassName('sizeSelect');
                Array.from(elements).forEach(element => {
                    const choices = new Choices(element, {
                        removeItemButton: true,
                        searchResultLimit: 5,
                        renderChoiceLimit: 5,
                        placeholderValue: 'Select option',
                        searchPlaceholderValue: 'Search option',
                    });
                });

                elements = document.getElementsByClassName('colorSelect');
                Array.from(elements).forEach(element => {
                    const choices = new Choices(element, {
                        removeItemButton: true,
                        searchResultLimit: 5,
                        renderChoiceLimit: 5,
                        placeholderValue: 'Select option',
                        searchPlaceholderValue: 'Search option',
                    });
                });
            });
        </script>

    </body>
</html>
