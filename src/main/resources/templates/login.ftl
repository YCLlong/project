<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>自适应学习系统</title>
    <link rel="stylesheet" href="${rc.contextPath}/static/css/style.css">
    <style>
        .bgpic{
            margin:0px;
            background: url(${rc.contextPath}/static/images/0.jpg) no-repeat;
            background-size:100% 100%;
            background-attachment:fixed;
        }
    </style>
</head>
<body class="bgpic">
<div class="page-wrapper flex-row align-items-center">
    <div class="container">
        <div class="row justify-content-center" style="margin-top: 100px">
            <div class="col-md-5">
                <div class="card p-4">
                    <div
                            class="text-center h4 ">
                        自适应学习系统</div>
                    <form id="form" action="${rc.contextPath}/sys/loginForm" method="post">
                        <div class="card-body py-5">
                            <div class="form-group">
                                <label class="form-control-label">用户名</label> <input
                                        type="text" class="form-control" id="userName" autocomplete = "off"
                                        name="userName" required>
                            </div>

                            <div class="form-group">
                                <label class="form-control-label">密码</label> <input
                                        type="password" class="form-control" id="password"
                                        name="password" required>
                            </div>
                            <input type="submit" class="btn btn-primary"
                                   style="width: 100%" />
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="${rc.contextPath}/static/js/jquery-1.11.1.min.js"></script>
<script src="${rc.contextPath}/static/js/bootstrap.js"></script>
<script src="${rc.contextPath}/static/js/tools.js"></script>
</body>
</html>