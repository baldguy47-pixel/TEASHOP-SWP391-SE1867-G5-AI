
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">
        <title>Login</title>
        <link href="${pageContext.request.contextPath}/css2/bootstrap.min.css" rel="stylesheet">
        <script
            src="https://kit.fontawesome.com/8e2244e830.js"
            crossorigin="anonymous"
        ></script>
        <link href="${pageContext.request.contextPath}/css2/prettyPhoto.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/css2/price-range.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/css2/animate.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/css2/main.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/css2/responsive.css" rel="stylesheet">
        <!--[if lt IE 9]>
        <script src="js/html5shiv.js"></script>
        <script src="js/respond.min.js"></script>
        <![endif]-->       
        <link rel="shortcut icon" href="images/ico/favicon.ico">
        <link rel="apple-touch-icon-precomposed" sizes="144x144" href="${pageContext.request.contextPath}/images/ico/apple-touch-icon-144-precomposed.png">
        <link rel="apple-touch-icon-precomposed" sizes="114x114" href="${pageContext.request.contextPath}/images/ico/apple-touch-icon-114-precomposed.png">
        <link rel="apple-touch-icon-precomposed" sizes="72x72" href="${pageContext.request.contextPath}/images/ico/apple-touch-icon-72-precomposed.png">
        <link rel="apple-touch-icon-precomposed" href="${pageContext.request.contextPath}/images/ico/apple-touch-icon-57-precomposed.png">
    </head><!--/head-->

    <body>

        <jsp:include page="Header.jsp"></jsp:include>

            <section style="background-color: #eee;">
                <div class="alert alert-success" style="display: ${success ne null ? 'block' : 'none'}" role="alert">
                ${success}
            </div>
            <div class="alert alert-danger" style="display: ${errorMSG ne null ? 'block': 'none'}"  role="alert">
                ${errorMSG}
            </div>
            <form id="profileForm" onsubmit="return validateForm()" action="profile" method="post">
                <div class="container py-5">

                    <div class="row">
                        <div class="col-lg-4" style="margin-top: 100px">
                            <div class="card mb-4">
                                <div class="card-body text-center">
                                    <img id="image0" src="${user.avatar}" alt="avatar"
                                         class="rounded-circle img-fluid" style="width: 150px; border: 1px solid black">
                                    <h5 class="my-3">${sessionScope.user.fullname}</h5>
                                    <div class="d-flex justify-content-center mb-2">
                                        <input type="file" class="form-control" id="imageFile0" accept="image/*" onchange="updateImage(0)">
                                        <input type="hidden" class="form-control" id="imageUrl0" name="avatar" value="${user.avatar}">
                                    </div>
                                </div>
                            </div>

                        </div>
                        <div class="col-lg-8">
                            <div class="card mb-4">
                                <div class="card-body">
                                    <hr>
                                    <div class="row">
                                        <div class="col-sm-3">
                                            <p class="mb-0">Họ và tên</p>
                                        </div>
                                        <div class="col-sm-9">
                                            <div class="form-outline" data-mdb-input-init>
                                                <input type="text" name="fullname" class="form-control" value="${sessionScope.user.fullname}" pattern=".*\S.*" required title="Vui lòng không để trống hoặc nhập toàn khoảng trắng"/>
                                            </div>
                                        </div>
                                    </div>
                                    <hr>
                                    <div class="row">
                                        <div class="col-sm-3">
                                            <p class="mb-0">Email</p>
                                        </div>
                                        <div class="col-sm-9">
                                            <div class="form-outline" data-mdb-input-init>
                                                <input type="text" class="form-control" value="${sessionScope.user.email}" disabled/>
                                            </div>
                                        </div>
                                    </div>
                                    <hr>
                                    <div class="row">
                                        <div class="col-sm-3">
                                            <p class="mb-0">Số điện thoại</p>
                                        </div>
                                        <div class="col-sm-9">
                                            <div class="form-outline" data-mdb-input-init>
                                                <input type="text" name="phone" class="form-control" value="${sessionScope.user.phone}" pattern="\d{10}" required title="Vui lòng nhập số điện thoại gồm 10 chữ số"/>
                                            </div>
                                        </div>
                                    </div>
                                    <hr>
                                    <div class="row">
                                        <div class="col-sm-3">
                                            <p class="mb-0">Giới tính</p>
                                        </div>
                                        <div class="col-sm-9">
                                            <select style="padding: 1.25rem 0rem 1.25rem 1.25rem;
                                                    border: 1px solid rgba(65, 64, 62, 0.20);
                                                    border-radius: 0.25rem;
                                                    background-color: #fff" name="gender">
                                                <option value="Male" ${sessionScope.user.gender eq 'Male' ? 'selected' : ''}>Nam</option>
                                                <option value="Female" ${sessionScope.user.gender eq 'Female' ? 'selected' : ''}>Nữ</option>
                                            </select>
                                        </div>
                                    </div>
                                    <hr>
                                    <div class="row">
                                        <div class="col-sm-3">
                                            <p class="mb-0">Quận huyện</p>
                                        </div>
                                        <div class="col-sm-9">
                                            <div class="form-outline" data-mdb-input-init>
                                                <select style="padding: 1.25rem 0rem 1.25rem 1.25rem;
                                                        border: 1px solid rgba(65, 64, 62, 0.20);
                                                        border-radius: 0.25rem;
                                                        background-color: #fff" name="location" required>
                                                    <c:forEach items="${locations}" var="location">
                                                        <option value="${location.locationName}" ${sessionScope.user.location eq location.locationName ? 'selected' : ''}>${location.locationName}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                    <hr>
                                    <div class="row">
                                        <div class="col-sm-3">
                                            <p class="mb-0">Địa chỉ</p>
                                        </div>
                                        <div class="col-sm-9">
                                            <div class="form-outline" data-mdb-input-init>
                                                <input type="text" name="address" class="form-control" value="${sessionScope.user.address}" pattern=".*\S.*" required title="Vui lòng không để trống hoặc nhập toàn khoảng trắng"/>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="d-flex justify-content-center mb-2">
                                <button  type="submit" class="btn btn-primary">Xác nhận</button>
                                <a  href="change-pass" class="btn btn-primary ms-3">Đổi mật khẩu</a>
                                <a  href="${pageContext.request.contextPath}/logout" class="btn btn-primary ms-3">Đăng xuất</a>
                            </div>
                            <hr>
                        </div>
                    </div>
                </div>
            </form>
        </section>   



        <jsp:include page="footer.jsp"></jsp:include>


            <script src="${pageContext.request.contextPath}/js2/jquery.js"></script>
        <script src="${pageContext.request.contextPath}/js2/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/js2/jquery.scrollUp.min.js"></script>
        <script src="${pageContext.request.contextPath}/js2/price-range.js"></script>
        <script src="${pageContext.request.contextPath}/js2/jquery.prettyPhoto.js"></script>
        <script src="${pageContext.request.contextPath}/js2/main.js"></script>
        <script type="text/javascript">

                                            function updateImage(sliderId) {
                                                let fileInput = document.getElementById(`imageFile` + sliderId);
                                                let image = document.getElementById(`image` + sliderId);
                                                let hiddenInput = document.getElementById(`imageUrl` + sliderId);
                                                console.log(fileInput, image, hiddenInput)

                                                // check file uploaded
                                                if (fileInput.files && fileInput.files[0]) {
                                                    const file = fileInput.files[0];
                                                    const maxSize = 1 * 1024 * 1024; // 2 MB in bytes

                                                    if (file.size > maxSize) {
                                                        alert("Ảnh chỉ có thể dùng bé hơn 1 MB.");
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
