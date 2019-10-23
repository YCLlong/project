<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>自适应学习系统</title>
    <link rel="stylesheet" href="${rc.contextPath}/static/css/style.css">
    <script src="${rc.contextPath}/static/js/base.js"></script>
</head>
<body>
<div class="container-scroller">
    <nav class="navbar default-layout-navbar col-lg-12 col-12 p-0 fixed-top d-flex flex-row">
        <div class="text-center navbar-brand-wrapper d-flex align-items-center justify-content-center">
            自适应学习后台管理系统
        </div>
        <div class="navbar-menu-wrapper d-flex align-items-stretch">
            <ul class="navbar-nav navbar-nav-right">
                <li class="nav-item nav-profile dropdown">
                    <a class="nav-link dropdown-toggle" id="profileDropdown" href="#" data-toggle="dropdown"
                       aria-expanded="false">
                        <div class="nav-profile-img">
                            <img src="${rc.contextPath}/static/images/face.jpg" alt="image">
                            <span class="availability-status online"></span>
                        </div>
                        <div class="nav-profile-text">
                            <p class="mb-1 text-black">欢迎您，${userName?if_exists}</p>
                        </div>
                    </a>
                    <div class="dropdown-menu navbar-dropdown" aria-labelledby="profileDropdown">
                        <a class="dropdown-item" href="javascript:exitSys()">
                            <i class="mdi mdi-logout mr-2 text-primary"></i>
                            退出系统
                        </a>
                    </div>
                </li>
            </ul>
            <button class="navbar-toggler navbar-toggler-right d-lg-none align-self-center" type="button"
                    data-toggle="offcanvas">
                <span class="mdi mdi-menu"></span>
            </button>
        </div>
    </nav>
    <div class="container-fluid page-body-wrapper">
        <!-- 菜单 -->
        <nav class="sidebar sidebar-offcanvas" id="sidebar">
            <ul class="nav">
                <li class="nav-item">
                    <a class="nav-link" href="javascript:menu0();">
                        <span class="menu-title">词库管理</span>
                        <i class="mdi mdi-home menu-icon"></i>
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="javascript:menu1();">
                        <span class="menu-title">用户管理</span>
                        <i class="menu-arrow"></i>
                        <i class="mdi mdi-crosshairs-gps menu-icon"></i>
                    </a>
                </li>
            </ul>
        </nav>
        <!-- 主体部分 -->
        <div class="main-panel">
            <div class="content-wrapper">
                <div class="card">
                    <div class="card-body" id="myContent">
                        <!-- 内容 -->
                    </div>
                </div>
            </div>
        </div>
    </div>
    <footer class="footer">
        <div class="d-sm-flex justify-content-center justify-content-sm-between">
					<span class="text-muted text-center text-sm-left d-block d-sm-inline-block">Copyright © 2019 自适应学习系统. All rights
						reserved. </span>
            <span class="float-none float-sm-right d-block mt-1 mt-sm-0 text-center">Hand-crafted & made with
						<i class="mdi mdi-heart text-danger"></i>张志勤 from 安徽文达信息学院</span>
        </div>
    </footer>
</div>
<script src="${rc.contextPath}/static/js/jquery-1.11.1.min.js"></script>
<script src="${rc.contextPath}/static/js/bootstrap.js"></script>
<script src="${rc.contextPath}/static/js/tools.js"></script>
<script>
    $(function () {
        menu1();
    });

    //词库管理
    function menu0() {
        loadPost("${rc.contextPath}/static/word/index.htm", '', $("#myContent"));
    }

    //用户管理
    function menu1() {
        loadPost("${rc.contextPath}/sys/userIndex", '', $("#myContent"));
    }

    function exitSys() {
        location.href = "${rc.contextPath}/static/user/logOut.htm";
    }
</script>
</body>
</html>
