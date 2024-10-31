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
        <title>Tất cả sản phẩm</title>
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
        <link rel="shortcut icon" href="${pageContext.request.contextPath}/images/ico/favicon.ico">
        <link rel="apple-touch-icon-precomposed" sizes="144x144" href="${pageContext.request.contextPath}/images/ico/apple-touch-icon-144-precomposed.png">
        <link rel="apple-touch-icon-precomposed" sizes="114x114" href="${pageContext.request.contextPath}/images/ico/apple-touch-icon-114-precomposed.png">
        <link rel="apple-touch-icon-precomposed" sizes="72x72" href="${pageContext.request.contextPath}/images/ico/apple-touch-icon-72-precomposed.png">
        <link rel="apple-touch-icon-precomposed" href="${pageContext.request.contextPath}/images/ico/apple-touch-icon-57-precomposed.png">
    </head><!--/head-->

    <body>

        <jsp:include page="Header.jsp"></jsp:include>

            <form action="list-product">
                <input type="hidden" value="${searchQuery}" name="searchQuery"/>
            <section>
                <div class="container">
                    <div class="row">
                        <div class="col-sm-3">
                            <div class="left-sidebar">
                                <h2>Danh mục</h2>
                                <div class="panel-group category-products" id="accordian"><!--category-productsr-->
                                    <c:forEach items="${categories}" var="category">
                                        <div class="panel panel-default">
                                            <div class="panel-heading" style="display: flex">
                                                <input style="height: 18px; width: 18px; margin: 0"
                                                       type="checkbox" value="${category.ID}" name="category"
                                                       ${categoriesCheckBox.contains(category.ID.toString()) ? 'checked' : ''}
                                                       />
                                                <h4 class="panel-title" style="color: #696763; margin-left: 5px">${category.categoryName}</h4>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </div><!--/category-productsr-->

                                <div class="price-range"><!--price-range-->
                                    <h2>Giá tiền</h2>
                                    <div class="search_box" style="display: flex">
                                        <input name="minPrice" type="number" value="${minPrice}" style="background-image: none; margin-right: 10px; width: 125px" required/>
                                        <input name="maxPrice" type="number" value="${maxPrice}" style="background-image: none; width: 125px" required/>
                                    </div>
                                </div><!--/price-range-->

                                <div style="margin-top: 10px; display: flex; justify-content: center">
                                    <button type="submit" class="add-to-cart" style="height: 30px; width: 100%">
                                        Xác nhận
                                    </button>
                                </div>

                            </div>
                        </div>

                        <div class="col-sm-9 padding-right">
                            <div class="features_items"><!--features_items-->
                                <h2 class="title text-center">Tất cả sản phẩm</h2>
                                <div style="display: flex; justify-content: end; margin-bottom: 10px">
                                    <div>
                                        <p style="margin: 5px 5px 0 0">Sắp xếp:</p>
                                    </div>
                                    <select style="margin-right: 5px; width: 150px" name="arrangeName">
                                        <option value="ASC" ${arrangeName == 'ASC' ? 'selected' : ''}>Từ A-Z</option>
                                        <option value="DESC" ${arrangeName == 'DESC' ? 'selected' : ''}>Từ Z-A</option>
                                    </select>
                                    <select style="width: 150px" name="arrangePrice">
                                        <option value="ASC" ${arrangePrice == 'ASC' ? 'selected' : ''}>Giá tăng dần</option>
                                        <option value="DESC" ${arrangePrice == 'DESC' ? 'selected' : ''}>Giá giảm dần</option>
                                    </select>
                                </div>

                                <c:forEach items="${products}" var="product">
                                    <div class="col-sm-4">
                                        <div class="product-image-wrapper">
                                            <div class="single-products">
                                                <div class="productinfo text-center">
                                                    <img src="${product.productDetail.imageURL}" alt="" />
                                                    <h2>${String.format("%.2f", product.productDetail.price * (1 - product.productDetail.discount/100))}VND </h2>
                                                    <p>${product.productName}</p>
                                                    <a href="product-detail?id=${product.productId}" class="btn btn-default add-to-cart"><i class="fa-solid fa-eye"></i>Chi tiết</a>
                                                </div>
                                                <c:if test="${product.productDetail.discount != null && product.productDetail.discount != 0}">
                                                    <img src="images/home/sale.png" class="new" alt="" />
                                                </c:if>
                                            </div>
                                        </div>
                                    </div>
                                </c:forEach>

                         
                            </div><!--features_items-->
                            <ul class="pagination">
                                    <c:forEach begin="1" end="${endPage}" varStatus="status">
                                        <li><button type="submit" 
                                                    class="btn btn-default add-to-cart" ${status.index == page ? 'style="background-color: #FE980F"' : ''} 
                                                    name="page" value="${status.index}">${status.index}
                                            </button></li>
                                        </c:forEach>
                                    <!--<li><a href="">&raquo;</a></li>-->
                                </ul>
                        </div>
                    </div>
                </div>
            </section>
        </form>

        <jsp:include page="footer.jsp"></jsp:include>


            <script src="${pageContext.request.contextPath}/js2/jquery.js"></script>
        <script src="${pageContext.request.contextPath}/js2/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/js2/jquery.scrollUp.min.js"></script>
        <script src="${pageContext.request.contextPath}/js2/price-range.js"></script>
        <script src="${pageContext.request.contextPath}/js2/jquery.prettyPhoto.js"></script>
        <script src="${pageContext.request.contextPath}/js2/main.js"></script>

        <script>
                                                        function toggleActive(element, productDetailId, color, stock) {
                                                            // Remove 'active' class from all spans
                                                            document.querySelectorAll('.color.size').forEach(span => {
                                                                span.classList.remove('active');
                                                            });

                                                            // Add 'active' class to the clicked span
                                                            element.classList.add('active');
                                                            document.getElementById('stock').innerHTML = stock;
                                                            // Update the hidden input with the selected ProductDetail ID
                                                            document.getElementById('selectedProductDetailId').value = productDetailId;

                                                            // Update the color display
                                                            document.getElementById('selectedColor').style.backgroundColor = color.toLowerCase();
                                                        }
                                                        function addToCart(id) {
                                                            let quantity = document.getElementById('quantity').value;
                                                            console.log(quantity);
                                                            fetch('add-cart?id=' + id + '&quantity=' + quantity);
                                                            window.alert('Thêm thành công');
                                                        }
        </script>
    </body>
</html>
