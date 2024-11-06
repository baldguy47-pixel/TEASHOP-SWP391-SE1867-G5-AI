<%-- 
    Document   : list-post
    Created on : May 20, 2024, 4:20:58 PM
    Author     : Legion
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Post</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
        <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
        <link rel="stylesheet" href="../css/list-post.css">
        <!-- Font Awesome CSS for icons -->
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" rel="stylesheet">

        <!-- Include Quill.js CSS -->
        <link href="https://cdn.quilljs.com/1.3.6/quill.snow.css" rel="stylesheet">
    </head>

    <body>
        <%@ include file="tea-staff-sidebar.jsp" %>

        <div class="mt-5 main-content">
            <c:if test="${isSuccess ne null && isSuccess}">
                <div class="alert alert-success alert-dismissible fade show mt-2" role="alert" id="mess">
                    <strong>Thành công!</strong> 
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
            </c:if>
            <c:if test="${isSuccess ne null && !isSuccess}">
                <div class="alert alert-danger alert-dismissible fade show mt-2" role="alert" id="mess">
                    <strong>Thất bại!</strong> 
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
            </c:if>
            <div class="card-header">
                Post detail
            </div>

            <form method="post" action="list-post">
                <div class="modal-header">
                    <input type="hidden" name="action" value="update">
                    <h5 class="modal-title" id="addPostModalLabel">Xem bài viết</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div class="form-group">
                        <label for="postId">ID:</label>
                        <input type="text" class="form-control" id="postId" name="postId" readonly style="background-color: #e6e6e6">
                    </div>
                    <div class="form-group">
                        <label for="postTitleEdit">Tiêu đề</label>
                        <input type="text" class="form-control" id="postTitleEdit" name="title" required>
                    </div>
                    <div class="form-group">
                        <label for="postContentEdit">Nội dung</label>
                        <!--<textarea class="form-control" id="postContentEdit" name="content" rows="5" required></textarea>-->
                        <label for="editContent" class="form-label">Nội dung:</label>
                        <div id="postContentEdit" style="height: 900px;"></div>
                        <input type="hidden" id="editContent" name="content">
                    </div>
                    <div class="form-group">
                        <label for="createdAt">Ngày tạo: </label>
                        <input type="text" class="form-control" id="createdAt" name="createdAt" readonly style="background-color: #e6e6e6">
                    </div>
                    <div class="form-group">
                        <label for="createdBy">Người tạo: </label>
                        <input type="text" class="form-control" id="createdBy" name="createdBy" readonly style="background-color: #e6e6e6">
                    </div>
                    <div class="form-group">
                        <label for="postImgURLUpdate">Thumbnail:</label><br>
                        <img id="image1" class="w-50" src="">
                        <input type="file" class="form-control w-50" id="imageFile1" accept="image/*" onchange="updateImage(1)">
                        <input type="hidden" class="form-control" id="imageUrl1" name="imgURL" value="">
                    </div>
                    <div class="form-group">
                        <label for="postCategoryEdit">Thể loại</label>
                        <select class="form-control" id="postCategoryEdit" name="category" required>
                            <c:forEach var="cat" items="${categories}">
                                <option class="cateOption" value="${cat.ID}">${cat.categoryName}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="modal-footer">
                    <a class="btn btn-secondary" href="list-post">Đóng</a>
                    <button type="submit" class="btn btn-primary">Lưu lại</button>
                </div>
            </form>

        </div>




        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4"
        crossorigin="anonymous"></script>

        <!-- Quill.js library -->
        <script src="https://cdn.quilljs.com/1.3.6/quill.js"></script>

        <script>
                                    async function view(id) {
                                        try {
                                            const response = await fetch('post-detail?id=' + id);
                                            const data = await response.json();

                                            data.forEach(post => {
                                                document.getElementById('postId').value = post.id;
                                                document.getElementById('postTitleEdit').value = post.title;
                                                document.getElementById('postContentEdit').innerHTML = post.content;
                                                document.getElementById('createdAt').value = post.createdAt;
                                                document.getElementById('createdBy').value = post.createdBy;
                                                document.getElementById('image1').src = post.imgURL;
                                                document.getElementById('imageUrl1').value = post.imgURL;

                                                let listCategory = document.getElementsByClassName('cateOption');
                                                for (let i = 0; i < listCategory.length; i++) {
                                                    if (listCategory[i].value === post.categoryId) {
                                                        listCategory[i].selected = true;
                                                    }
                                                }
                                            });

                                            // Initialize Quill after setting the post content
                                            var quill = new Quill('#postContentEdit', {
                                                theme: 'snow',
                                                modules: {
                                                    toolbar: [
                                                        [{'header': [1, 2, 3, false]}],
                                                        ['bold', 'italic', 'underline', 'strike'],
                                                        [{'align': []}],
                                                        [{'list': 'ordered'}, {'list': 'bullet'}],
                                                        ['link', 'image'],
                                                        ['clean']
                                                    ]
                                                }
                                            });

                                            // Save Quill content to hidden input on form submit
                                            document.querySelector('form').onsubmit = function () {
                                                var html = quill.root.innerHTML;
                                                document.querySelector('#editContent').value = html;
                                            };

                                        } catch (error) {
                                            console.error('Error fetching post data:', error);
                                        }
                                    }

                                    // Ensure to call the view function when the page is loaded or when necessary
                                    document.addEventListener('DOMContentLoaded', function () {
                                        const postId = ${id}; // Ensure this variable is correctly passed from your server-side logic
                                        view(postId);
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
                    const maxSize = 2 * 1024 * 1024; // 2 MB in bytes

                    if (file.size > maxSize) {
                        alert("File chỉ tối đa 2 MB");
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
