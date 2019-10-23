<div class="content-header">
    <ul class="breadcrumb">
        <li><a href="#"><i class="icon icon-home"></i></a></li>
        <li>系统管理</li>
        <li>用户管理</li>
        <li class="active">编辑用户</li>
    </ul>
</div>

<div class="content-body">
    <div class="container-fluid">
        <div class="panel">
            <div class="panel-heading">
                <div class="panel-title">编辑用户</div>
            </div>
            <div class="panel-body">
                <#include "../../common/msgTip.ftl"/>
                <form id="form">
                    <input id="objId" name="id" type="hidden" value="${obj.id?if_exists}">
                    <div class="form-group">
                        <label class="required">用户名</label>
                        <input <#if obj??>value="${obj.userName?if_exists}"</#if> type="text" class="form-control"
                               name="userName"
                               READONLY autocomplete="off"/>
                    </div>

                    <div class="form-group">
                        <label>手机号</label>
                        <input <#if obj??>value="${obj.mobile?if_exists}"</#if> type="tel" class="form-control"
                               name="mobile"
                               autocomplete="off"/>
                    </div>

                    <div class="form-group">
                        <label>微信</label>
                        <input <#if obj??>value="${obj.vchat?if_exists}"</#if> type="text" class="form-control"
                               name="vchat"
                               autocomplete="off"/>
                    </div>

                    <div class="form-group">
                        <label class="required">电子邮件</label>
                        <input <#if obj??>value="${obj.email?if_exists}"</#if> type="email" class="form-control"
                               name="email"
                               autocomplete="off"/>
                    </div>

                    <div class="form-group">
                        <label>关联渠道</label>
                        <select id="lkProject" name="subProjectList" class="form-control" multiple="multiple"></select>
                    </div>

                    <div class="form-group">
                        <label>备注</label>
                        <textarea class="form-control" name="remark"><#if obj??>${obj.remark?if_exists}</#if></textarea>
                    </div>

                    <div class="form-group">
                        <button type="button" class="btn btn-primary" onclick="edit()"><i
                                    class="icon icon-edit-sign"></i> 编辑
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<script>

    $(function () {
        var lk = $("#lkProject").select2({
            data: ${subProjectData},
            allowClear: true,
            placeholder: '请选择相关的项目'
        });
        <#if subProjectList??>
        lk.val(${subProjectList}).trigger("change");
        </#if>
    });

    function edit() {
        if ($("input[name='userName']").val() == '') {
            warmingAlert('用户名必须填写');
            return;
        }
        var email = $("input[name='email']").val();
        if (email == '') {
            warmingAlert('用户邮箱必须填写');
            return;
        } else if (!isEmail(email)) {
            warmingAlert('邮箱地址格式不正确');
            return;
        }

        loadPost("${rc.contextPath }/sys/userEdit", $("#form").serialize(), ".J_content", false);
    }
</script>