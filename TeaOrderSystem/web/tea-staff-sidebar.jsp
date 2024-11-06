<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!-- Custom CSS -->
<style>
    /* Sidebar style */
    .sidebar {
        position: fixed;
        top: 0;
        left: 0;
        height: 100%;
        width: 11%;
        background-color: #343a40;
        padding-top: 56px;
        z-index: 1;
        transition: width 0.3s;
    }

    .sidebar ul {
        list-style-type: none;
        padding: 0;
        margin: 0;
    }

    .sidebar li {
        padding: 10px;
        text-align: center;
        border-bottom: 1px solid rgba(255, 255, 255, 0.1);
    }

    .sidebar a {
        color: #f8f9fa;
        text-decoration: none;
    }

    .sidebar a:hover {
        color: #007bff;
    }

    /* Main content style */
    .main-content {
        padding-left: 11%;
        margin-left: 5%;
        margin-right: 5%;
    }
    .sidebar {
        display: flex;
        flex-direction: column;
        height: 100vh;
    }

    .sidebar ul.logout {
        margin-top: auto;
    }

    .chart-container {
        padding-left: 15%;
        padding-right: 15%;
    }
</style>

<!-- Sidebar -->
<nav class="sidebar">
    <ul>
        <li><a href="teastaff-order"><i class="fas fa-shopping-cart mr-2"></i>Đơn hàng</a></li>
        <li><a href="customer"><i class="fas fa-users mr-2"></i>Khách hàng</a></li>
        <li><a href="list-post"><i class="fas fa-chart-line mr-2"></i>Bài viết</a></li>
    </ul>
    <ul class="logout">
        <li class="text-light"><i class="fas fa-users mr-2"></i>${sessionScope.staff.fullname}</li>
        <li><a href="../logout"><i class="fas fa-sign-out-alt mr-2"></i>Đăng xuất</a></li>
    </ul>
</nav>
