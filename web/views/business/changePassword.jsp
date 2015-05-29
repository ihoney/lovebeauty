<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 15-5-24
  Time: 下午1:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>修改密码</title>
    <script type="text/javascript" src="${rootPath}/js/jquery-1.8.3.min.js"></script>
    <script type="text/javascript" src="${rootPath}/js/jquery.form.js"></script>
    <script type="text/javascript">
        function btn_submit() {
            $('#form_custom').ajaxForm({
                beforeSubmit: validateMethod,
                success: function (data) {
                    if (data.reqResult) {
                        alert("修改成功！");
                    } else {
                        alert(data.message);
                    }
                },
                error: function (data) {
                    alert(data.message);
                }
            });
            $("#form_custom").submit();
        }

        function validateMethod() {
            var oldPassword = $.trim($("#oldPassword").val());
            var newPassword = $.trim($("#newPassword").val());
            var newPassword2 = $.trim($("#newPassword2").val());
            if (oldPassword.length < 6) {
                alert("密码为6位-16字符！");
                return false;
            }

            if (newPassword.length < 6) {
                alert("密码为6位-16字符！");
                return false;
            }

            if (newPassword != newPassword2) {
                alert("两次密码不一致，请重新输入");
                return false;
            }
        }
    </script>
    <style type="text/css">
        #pwd_tb {
            border-left: 1px solid #44C8E6;
            border-top: 1px solid #44C8E6;
            color: #033D61;
        }

        #pwd_tb td {
            border-right: 1px solid #44C8E6;
            border-bottom: 1px solid #44C8E6;
        }

        .td_att_name {
            text-align: right;
            font-size: 12px;
        }

        input {
            border: 0;
        }
    </style>
</head>
<body>
<form id="form_custom" action="${rootPath}/seller/changePwd.do" method="post">
    <table id="pwd_tb" cellspacing=0 cellpadding=5>
        <tr>
            <td class="td_att_name">当前密码:</td>
            <td>
                <input id="oldPassword" placeholder="6位-16字符" type="password" name="oldPassword"/>
            </td>
        </tr>

        <tr>
            <td class="td_att_name">新密码:</td>
            <td>
                <input id="newPassword" placeholder="6位-16字符" type="password" name="newPassword"/>
            </td>
        </tr>

        <tr>
            <td class="td_att_name">确认密码:</td>
            <td>
                <input id="newPassword2" type="password"/>
            </td>
        </tr>
        <td colspan="2" style="text-align: center">
            <input type="button" class="input_btn" onclick="btn_submit();" value="提交"/> &nbsp;&nbsp;&nbsp;
            <input type="reset" class="input_btn" value="取消"/>
        </td>
    </table>
</form>
</body>
</html>