<%-- 
    Document   : cart
    Created on : Jun 4, 2024, 7:18:47 PM
    Author     : Legion
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

    <head>
        <title>Shopping Cart</title>
        <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet">
        <style>
            #product:hover, #product *:hover {
                background-color: #e6e6e6;
            }
        </style>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
    </head>

    <body>
        <jsp:include page="Header.jsp"></jsp:include>
            <!-- Header-->
            <header class="py-5" style="background-image: linear-gradient(rgba(0, 0, 0, 0.3), rgba(0, 0, 0, 0.3)), url(https://w0.peakpx.com/wallpaper/752/914/HD-wallpaper-sabito-s-haori-kimetsu-no-yaiba-pattern.jpg);">
                <div class="container px-4 px-lg-5 my-5">
                    <div class="text-center text-white">
                        <h1 class="display-4 fw-bolder">Shop in style</h1>
                        <p class="lead fw-normal text-white-50 mb-0">With this shop home page template</p>
                    </div>
                </div>
            </header>

            <div class="col-md-12 d-flex justify-content-center p-3">

                <div id="sidebar" class="col-md-2 p-3" style="border: 1px solid rgb(144, 141, 141); height: 100vh;">
                    <form method="get" action="cart" class="mr-0">
                        <div id="product-search">
                            <h3>Search Products</h3>
                            <input type="text" id="search-box" name="searchQuery" placeholder="Search for products..." class="form-control" value="${param.searchQuery}">
                    </div>
                    <div class="form-group">
                        <label for="category">Category:</label>
                        <select id="category" name="category" class="form-control">
                            <option value="">All</option>
                            <c:forEach var="cat" items="${categories}">
                                <option value="${cat.categoryName}" ${param.category == cat.categoryName ? 'selected' : ''}>${cat.categoryName}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <button type="submit" class="btn btn-primary">Filter</button>
                </form>
                <div id="latest-products">
                    <h3>Latest Products</h3>
                    <c:forEach items="${products}" var="p">
                        <a id="product" href="product-detail?id=${p.productId}" style="text-decoration: none; color: black;">
                            <table class="p-2">
                                <tr>
                                    <td><img src="${p.productDetail.imageURL}" style="width:50px; height: 50px" alt="..." /></td>
                                    <td><h5 class="fw-bolder">${p.productName}</h5></td>
                                    <td>
                                        <div class="d-flex justify-content-center small text-warning mb-2">
                                            <div class="bi-star-fill"></div>
                                            <div class="bi-star-fill"></div>
                                            <div class="bi-star-fill"></div>
                                            <div class="bi-star-fill"></div>
                                            <div class="bi-star-fill"></div>
                                        </div>
                                    </td>
                                    <td>
                                        <c:if test="${p.productDetail.discount != null || p.productDetail.discount != 0}">
                                            <span class="text-muted text-decoration-line-through">$${p.productDetail.price}</span>
                                            $${p.productDetail.price * (100.0- p.productDetail.discount)/100}
                                        </c:if>

                                        <c:if test="${p.productDetail.discount == null || p.productDetail.discount == 0}">
                                            $${p.productDetail.price}
                                        </c:if>

                                    </td>
                                </tr>
                            </table>
                        </a>

                    </c:forEach>
                </div>
                <div id="static-contacts" style="margin-top: 15px">
                    <h3>Contact Us</h3>
                    <p>Email: contact@example.com</p>
                    <p>Phone: 123-456-7890</p>
                    <p>Address: 123 Main St, Anytown, USA</p>
                </div>
            </div>
            <div class="col-md-10">

                <h2 style="display: none">Shopping Cart</h2>
                <table class="table" style="display: none">
                    <thead>
                        <tr>
                            <th>Image</th>
                            <th>Product Name</th>
                            <th>Size</th>
                            <th>Color</th>
                            <th>Price</th>
                            <th style="text-align: center">Quantity</th>
                            <th>Total Cost</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:set value="0" var="total"/>
                        <c:forEach var="item" items="${cartItems}">
                            <tr>
                                <td><img src="${item.productDetail.imageURL}" width="50" height="50" alt="alt"/></td>
                                <td>${item.productDetail.getProductName()}</td>
                                <td>${item.productDetail.size}</td>
                                <td>${item.productDetail.color}</td>
                                <td>
                                    <c:if test="${item.productDetail.discount != null || item.productDetail.discount != 0}">
                                        $${item.productDetail.price * (100.0- p.productDetail.discount)/100}
                                        <c:set value="${total + item.productDetail.price * (100.0- item.productDetail.discount)/100}" var="total"/>
                                    </c:if>
                                    <c:if test="${item.productDetail.discount == null || item.productDetail.discount == 0}">
                                        $${item.productDetail.price}
                                        <c:set value="${total + item.productDetail.price}" var="total"/>
                                    </c:if>
                                </td>
                                <td style="text-align: center">
                                    <p>${item.quantity}</p>
                                </td>
                                <td>
                                    <c:if test="${item.productDetail.discount != null || item.productDetail.discount != 0}">
                                        $${item.quantity * (item.productDetail.price * (100.0- item.productDetail.discount)/100)}
                                    </c:if>
                                    <c:if test="${item.productDetail.discount == null || item.productDetail.discount == 0}">
                                        $${item.quantity * (item.productDetail.price)}
                                    </c:if>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <div style="display: none">
                    <c:if test="${totalPages > 1}">
                        <nav aria-label="Page navigation">
                            <ul class="pagination">
                                <c:forEach begin="1" end="${totalPages}" var="pageNum">
                                    <li class="page-item ${pageNum == currentPage ? 'active' : ''}">
                                        <a class="page-link" href="cart?page=${pageNum}&searchQuery=${param.searchQuery}&category=${param.category}">${pageNum}</a>
                                    </li>
                                </c:forEach>
                            </ul>
                        </nav>
                    </c:if>
                </div>
                <div style="display: none">
                    Total Order Price: $<span id="total-price">
                        <c:set var="totalPrice" value="0" />
                        <c:forEach var="item" items="${cartItemsFull}">
                            <c:if test="${item.productDetail.discount != null || item.productDetail.discount != 0}">
                                <c:set var="totalPrice" value="${totalPrice + item.quantity * (item.productDetail.price * (100.0- item.productDetail.discount)/100)}" />
                            </c:if>
                            <c:if test="${item.productDetail.discount == null || item.productDetail.discount == 0}">
                                <c:set var="totalPrice" value="${totalPrice + item.quantity * (item.productDetail.price)}" />
                            </c:if>

                        </c:forEach>
                        ${totalPrice}
                    </span>
                </div>
                <br>
                <h2 style="display: none">Cart Contact</h2>
                <form action="../public/payment" method="post">
                    <table class="table table-borderless">
                        <input class="form-control" id="amount" name="amount" type="hidden" readonly value="${totalPrice}" />
                        <input type="hidden" Checked="True" id="bankCode" name="bankcode" value="NCB">
                        <input type="hidden" id="language" Checked="True" name="language" value="vn">
                        <tr>
                            <td>
                                <input style="display: none" type="radio" name="method" value="Tranfer" checked="">
                                <div> 
                                    <ul style="list-style-type: none">
                                        <li><strong>STK: </strong> 01239817231123</li>
                                        <li> <strong>BANK: </strong>MB Bank - HA NOI</li>
                                        <li><strong>OWNER: </strong>Sloth shop</li>
                                    </ul>
                                </div> 
                            </td> 
                            <td>
                                <img src="https://eko.in/assets/img/bill-payment/QR.png" alt="alt"/>
                            </td>
                            
                        </tr>
                        <tr>
                            <td>Full Name:</td>
                            <td><input type="text" class="form-control" name="fullname" value="${sessionScope.user.fullname}"></td> 
                        </tr>
                        <tr>
                            <td>Gender:</td>
                            <td><input type="text" class="form-control" name="gender" value="${sessionScope.user.gender}"></td> 
                        </tr>
                        <tr>
                            <td>Email:</td>
                            <td><input type="text" class="form-control" name="email" value="${sessionScope.user.email}"></td> 
                        </tr>
                        <tr>
                            <td>Phone:</td>
                            <td><input type="text" class="form-control" name="phone" value="${sessionScope.user.phone}"></td> 
                        </tr>
                        <tr>
                            <td>Address:</td>
                            <td><input type="text" class="form-control"  name="address" value="${sessionScope.user.address}"></td> 
                        </tr>
                        <tr>
                            <td>Notes:</td>
                            <td><input type="text" class="form-control" name="notes" value=""></td> 
                        </tr>

                        <tr>
                            <td colspan="2">
                                <a href="cart" class="btn btn-secondary">Change</a>
                                <button type="submit" class="btn btn-primary">Submit</button>
                            </td>
                        </tr>

                    </table>


                </form>

            </div>

        </div>

    </body>

</html>


</body>

</html>
