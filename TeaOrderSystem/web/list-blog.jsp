
<%-- 
    Document   : list-blog
    Created on : Jun 4, 2024, 9:33:35 PM
    Author     : Legion
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">
        <title>Tất cả blog</title>
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
    </head>
    <body>

        <jsp:include page="Header.jsp"></jsp:include>

            <form action="list-blog">
                <section>
                    <div class="container">
                        <div class="row">
                            <div class="col-sm-3">
                                <div class="left-sidebar">
                                    <div id="product-search mb-5">
                                        <h3>Tìm kiểm bài viết</h3>
                                        <input type="text" id="search-box" name="searchQuery" placeholder="bài viết..." class="form-control" value="${param.searchQuery}"/>
                                </div>
                                <div class="form-group" style="margin-top: 20px">
                                    <label for="category">Thể loại: </label>
                                    <select id="category" name="category" class="form-control">
                                        <option value="">Tất cả</option>
                                        <c:forEach var="cat" items="${categories}">
                                            <option value="${cat.categoryName}" ${param.category == cat.categoryName ? 'selected' : ''}>${cat.categoryName}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <button type="submit" class="btn btn-primary">Lọc</button>
                            </div>
                        </div>

                        <div class="col-sm-9 padding-right">
                            <table class="table">
                                <tbody id="table-content-body">
                                    <c:forEach var="post" items="${posts}">
                                        <tr>
                                            <td><img src="${post.imgURL}" alt="alt" width="200px" height="200px"/></td>
                                            <td>${post.title}</td>
                                            <td>${fn:substring(post.content, 0, 50)}...</td>
                                            <td>
                                                <a class="btn btn-primary" href="post-detail?id=${post.id}">Chi tiết</a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>

                            <ul class="pagination">
                                <c:if test="${currentPage > 1}">
                                    <li class="page-item"><a class="page-link" href="list-blog?page=${currentPage - 1}&category=${param.category}&search=${param.search}">Lùi</a></li>
                                    </c:if>
                                    <c:forEach var="i" begin="1" end="${totalPages}">
                                    <li class="page-item ${currentPage == i ? 'active' : ''}">
                                        <a class="page-link" href="list-blog?page=${i}&category=${param.category}&search=${param.search}">${i}</a>
                                    </li>
                                </c:forEach>
                                <c:if test="${currentPage < totalPages}">
                                    <li class="page-item"><a class="page-link" href="list-blog?page=${currentPage + 1}&category=${param.category}&search=${param.search}">Tiến</a></li>
                                    </c:if>
                            </ul>

                        </div><!--features_items-->

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
    </body>
</html>
