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


            <button type="button" class="btn btn-primary" data-toggle="modal" 
                    data-target="#addProductModal" 
                    ${listProductDetail.size() == 4 ? 'disabled' : ''}>
                Thêm sản phẩm
            </button>

            <!-- Product Table -->
            <table id="productTable" class="table table-striped">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Ảnh</th>
                        <th>Kích cỡ</th>
                        <th>Giá</th>
                        <th>Giảm giá</th>
                        <th>Giá nguyên liệu</th>
                        <th>Ngày tạo</th>
                        <th>Trạng thái</th>
                        <th>Thao tác</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="productDetail" items="${listProductDetail}">
                        <tr>
                            <td>${productDetail.productDetailId}</td>
                            <td><img src="${productDetail.imageURL}" alt="Product Image" style="width: 50px; height: 50px;"></td>
                            <td>${productDetail.size}</td>
                            <td>${productDetail.price}</td>
                            <td>${productDetail.discount}%</td>
                            <td>${productDetail.importPrice}</td>
                            <td>${productDetail.createdAt}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${productDetail.getIsDeleted() == false}">
                                        Hoạt động
                                    </c:when>
                                    <c:otherwise>
                                        Không hoạt động
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <button type="button" class="btn btn-primary btn-sm" data-toggle="modal" data-target="#editProductModal_${productDetail.productDetailId}">Chỉnh sửa</button>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

        <c:forEach var="productDetail" items="${listProductDetail}">
            <!-- Edit Product Modal -->
            <div class="modal fade" id="editProductModal_${productDetail.productDetailId}" tabindex="-1" role="dialog" aria-labelledby="editProductModalLabel_${productDetail.productDetailId}" aria-hidden="true">
                <!-- Modal Content -->
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <!-- Modal Header -->
                        <div class="modal-header">
                            <h5 class="modal-title" id="editProductModalLabel_${productDetail.productDetailId}">Edit Product Detail</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <!-- Modal Body -->
                        <div class="modal-body">
                            <!-- Edit Product Form -->
                            <form action="product-detail" method="post">
                                <input type="hidden" name="action" value="update">
                                <input type="hidden" name="ID" value="${productDetail.productDetailId}">
                                <input type="hidden" name="productId" value="${productId}">

                                <div class="form-group">
                                    <label for="imageUrl">Ảnh</label>
                                    <img id="image${productDetail.productDetailId}" class="w-100" src="${productDetail.imageURL}">
                                    <input type="file" class="form-control" id="imageFile${productDetail.productDetailId}" accept="image/*" onchange="updateImage(${productDetail.productDetailId})">
                                    <input type="hidden" class="form-control" id="imageUrl${productDetail.productDetailId}" name="imageUrl" value="${productDetail.imageURL}">
                                </div>

                                <div class="form-group">
                                    <label for="Size">Kích cỡ</label>
                                    <select class=" form-control" name="size" required>
                                        <option value="S" ${productDetail.size eq 'S' ? 'selected' : listSize.contains("S") ? 'style="display: none"' : ''}>S</option>
                                        <option value="M" ${productDetail.size eq 'M' ? 'selected' : listSize.contains("M") ? 'style="display: none"' : ''}>M</option>
                                        <option value="L" ${productDetail.size eq 'L' ? 'selected' : listSize.replace("XL","").contains("L") ? 'style="display: none"' : ''}>L</option>
                                        <option value="XL" ${productDetail.size eq 'XL' ? 'selected' : listSize.contains("XL") ? 'style="display: none"' : ''}>XL</option>
                                    </select>
                                </div>

                                <div class="form-group">
                                    <label for="price">Giá</label>
                                    <input type="number" class="form-control" id="price" name="price" value="${productDetail.price}" step="0.01" required>
                                </div>

                                <div class="form-group">
                                    <label for="discount">Giảm giá (%)</label>
                                    <input type="number" class="form-control" id="discount" name="discount" value="${productDetail.discount}" step="1" min="0" max="100">
                                </div>

                                <div class="form-group">
                                    <label for="ImportPrice">Giá nguyên liệu</label>
                                    <input type="number" class="form-control" id="ImportPrice" name="ImportPrice" value="${productDetail.importPrice}" step="0.01" required>
                                </div>

                                <div class="form-group">
                                    <label for="isDeleted">Trạng thái</label>
                                    <select class="form-control" id="isDeleted" name="isDeleted">
                                        <option value="false" ${!productDetail.getIsDeleted() ? 'selected' : ''}>Hoạt động</option>
                                        <option value="true" ${productDetail.getIsDeleted() ? 'selected' : ''}>Không hoạt động</option>
                                    </select>
                                </div>

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
                        <h5 class="modal-title" id="addProductModalLabel">Tạo mới thông tin sản phẩm</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <form id="addProductForm" action="product-detail" method="post">
                        <input type="hidden" name="action" value="add">
                        <div class="modal-body">
                            <input type="hidden" name="action" value="add">
                            <input type="hidden" name="productId" value="${productId}">

                            <div class="form-group">
                                <label for="imageUrl">Image</label>
                                <img id="image0" class="w-100" src="">
                                <input type="file" class="form-control" id="imageFile0" accept="image/*" onchange="updateImage(0)" required>
                                <input type="hidden" class="form-control" id="imageUrl0" name="imageUrl" value="">
                            </div>

                            <div class="form-group">
                                <label>Kích cỡ</label>
                                <select class="form-control" name="size" required>
                                    <option value="S" ${listSize.contains("S") ? 'style="display: none"' : ''} ${!listSize.contains("S") ? 'selected' : ''}>S</option>
                                    <option value="M" ${listSize.contains("M") ? 'style="display: none"' : ''} ${!listSize.contains("M") && listSize.contains("S") ? 'selected' : ''}>M</option>
                                    <option value="L" ${listSize.replace("XL", "").contains("L") ? 'style="display: none"' : ''} ${!listSize.contains("L") && (listSize.contains("S") || listSize.contains("M")) ? 'selected' : ''}>L</option>
                                    <option value="XL" ${listSize.contains("XL") ? 'style="display: none"' : ''} ${!listSize.contains("XL") && (listSize.contains("S") || listSize.contains("M") || listSize.contains("L")) ? 'selected' : ''}>XL</option>
                                </select>
                            </div>

                            <div class="form-group">
                                <label for="price">Giá</label>
                                <input type="number" class="form-control" id="price" name="price" step="0.01" required>
                            </div>

                            <div class="form-group">
                                <label for="discount">Giảm giá (%)</label>
                                <input type="number" class="form-control" id="discount" name="discount" step="1" min="0" max="100" value="0">
                            </div>

                            <div class="form-group">
                                <label for="ImportPrice">Giá nhập</label>
                                <input type="number" class="form-control" id="ImportPrice" name="ImportPrice" step="0.01" required>
                            </div>

                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Đóng</button>
                            <button type="submit" class="btn btn-primary">Thêm</button>
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
                                            "info": false,
                                            "autoWidth": false
                                        });
                                    });
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

    </body>
</html>
