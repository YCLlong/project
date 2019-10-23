<div class="content-header">
    <ul class="breadcrumb">
        <li><a href="#"><i class="icon icon-home"></i></a></li>
        <li>系统管理</li>
        <li class="active">用户管理</li>
    </ul>
</div>
<div class="content-body">
    <div class="container-fluid">
        <div class="panel">
            <div class="panel-heading">
                <div class="panel-title">用户列表</div>
            </div>
            <div class="panel-body">
                <div class="table-tools" style="margin-bottom: 15px;">
                    <form class="form-inline" id="queryForm">
                        <input type="hidden" id="pageNo" name="pageNo">
                        <div class="form-inline">
                            <div class="form-group">
                                <label>用户名 </label>
                                <input type="text" name="name" value="${query.name?if_exists}" class="form-control ml5"
                                       autocomplete="off">
                            </div>
                            <div class="form-group">
                                <label>邮箱 </label>
                                <input type="text" name="email" value="${query.email?if_exists}"
                                       class="form-control ml5" autocomplete="off">
                            </div>
                            <div class="form-group">
                                <label>微信 </label>
                                <input type="text" name="vchat" value="${query.vchat?if_exists}"
                                       class="form-control ml5" autocomplete="off">
                            </div>
                            <div class="form-group">
                                <label>手机号 </label>
                                <input type="text" name="mobile" value="${query.mobile?if_exists}"
                                       class="form-control ml5" autocomplete="off">
                            </div>
                        </div>
                        <div class="form-inline  mt10 ml50">
                            <button type="button" class="btn btn-primary" onclick="query()"><i
                                        class="icon icon-search"></i>查询
                            </button>
                            <button type="button" class="btn btn-success ml10" onclick="add()"><i
                                        class="icon icon-plus-sign"></i> 新增
                            </button>
                        </div>
                    </form>
                </div>

                <#include "../../common/msgTip.ftl">
                <#if pageModel?? && pageModel.dataList?? && (pageModel.dataList?size > 0)>
                    <table class="table table-bordered">
                    <thead>
                    <tr>
                        <th>用户名</th>
                        <th>手机</th>
                        <th>微信</th>
                        <th>邮箱</th>
                        <th>用户类型</th>
                        <th>操作时间</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <#list  pageModel.dataList as obj>
                        <tr title="${obj.remark?if_exists}">
                        <td>${obj.userName?if_exists}</td>
                        <td>${obj.mobile?if_exists}</td>
                        <td>${obj.vchat?if_exists}</td>
                        <td>${obj.email?if_exists}</td>
                        <td><#include "../../common/accountType.ftl"/></td>
                        <td>${obj.modifyTime?string('yyyy-MM-dd hh:mm:ss')}</td>
                        <td>
                    <button class="btn btn-xs btn-primary" onclick="edit('${obj.id?if_exists}')">修改</button>
                    <button
                    class="btn btn-xs btn-danger" onclick="del('${obj.id?if_exists}','${obj.userName?if_exists}')">
                            删除</button>
                    <button class="btn btn-xs btn-success" onclick="resetPwd('${obj.id?if_exists}')">重置密码 </button>
                        </td>
                        </tr>
                    </#list>
                    </tbody>
                    </table>

                <#--分页插接件-->
                    <#include "../../common/paging.ftl">
                <#else>
                    <b>无任何数据</b>
                </#if>
            </div>
        </div>
    </div>
</div>

<script>
    function query() {
        loadPost("${rc.contextPath }/sys/userIndex", $("#queryForm").serialize(), ".J_content", true);
    }

    function add() {
        loadPost("${rc.contextPath }/sys/userAddIndex", '', ".J_content", true);
    }

    function edit(id) {
        loadPost("${rc.contextPath }/sys/userEditIndex", {id: id}, ".J_content", true);
    }

    function del(id, name) {
        if (confirm("是否要删除【" + name + "】?")) {
            loadPost("${rc.contextPath }/sys/userDelete", {id: id}, ".J_content", false);
        }
    }

    function resetPwd(id) {
        loadPost("${rc.contextPath }/sys/userResetPwd", {id: id}, ".J_content", true);
    }

</script>
