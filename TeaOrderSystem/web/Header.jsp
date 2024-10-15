<%-- 
    Document   : Header
    Created on : Sep 15, 2024, 2:12:19 PM
    Author     : MSI
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<header id="header">

    <div class="header-middle"><!--header-middle-->
        <div class="container">
            <div class="row">
                <div class="col-sm-4">
                    <div class="logo pull-left">
                        <a href="${pageContext.request.contextPath}/home"><img src="${pageContext.request.contextPath}/Image/logo.png" alt="" /></a>
                    </div>
                </div>
                <div class="col-sm-8">
                    <div class="shop-menu pull-right">
                        <ul class="nav navbar-nav">

                            <li><a href="${pageContext.request.contextPath}/public/cart"><i class="fa fa-shopping-cart"></i>Giỏ hàng</a></li>
                                <c:if test="${sessionScope.user != null}">
                                <li><a href="${pageContext.request.contextPath}/common/profile"><i class="fa fa-user"></i>${sessionScope.user.fullname}</a></li>
                                <li><a href="${pageContext.request.contextPath}/customer/my-order"><i class="fa fa-calendar"></i>Order</a></li>
                                </c:if>
                                <c:if test="${sessionScope.user == null}">
                                <li><a href="${pageContext.request.contextPath}/login"><i class="fa fa-lock"></i> Đăng nhập</a></li>
                                <li><a href="${pageContext.request.contextPath}/register"><i class="fa fa-lock"></i> Đăng ký</a></li>
                                </c:if>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div><!--/header-middle-->

    <div class="header-bottom"><!--header-bottom-->
        <div class="container">
            <div class="row">
                <div class="col-sm-9">
                    <div class="navbar-header">
                        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                            <span class="sr-only">Toggle navigation</span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                        </button>
                    </div>
                    <div class="mainmenu pull-left">
                        <ul class="nav navbar-nav collapse navbar-collapse">
                            <li><a href="${pageContext.request.contextPath}/home">Trang chủ</a></li>
                            <li><a href="${pageContext.request.contextPath}/public/list-product">Sản phẩm</a></li> 
                            <li class="dropdown"><a href="#">Bài viết</a></li> 
                        </ul>
                    </div>
                </div>
                <div class="col-sm-3">
                    <div class="search_box pull-right">
                        <form action="${pageContext.request.contextPath}/public/list-product">
                            <input style="background-image: none" type="text" placeholder="Tìm kiếm ... " value="${searchQuery}" name="searchQuery"/>
                            <button type="submit" style="height: 35px; color: #B2B2B2; border: none"><i class="fa-solid fa-magnifying-glass"></i></button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div><!--/header-bottom-->
</header><!--/header-->