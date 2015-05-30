<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title></title>
    <script type="text/javascript" src="${rootPath}/js/jquery-1.8.3.min.js"></script>
    <script type="text/javascript" src="${rootPath}/js/jquery.form.js"></script>
    <script type="text/javascript" src="${rootPath}/js/rootPath.js"></script>
    <link type="text/css" href="${rootPath}/css/love.form.css" rel="stylesheet"/>
    <script type="text/javascript">
        function btn_submit() {
            $('#form_custom').ajaxForm({
                beforeSubmit: validateMethod,
                success: function () {
                    location.href = getRootPath() + "employee/queryAllEmployees.do";
                },
                error: function () {
                    alert("提交失败！");
                }
            });
            $("#form_custom").submit();
        }

        function validateMethod() {
            var nickName = $.trim($("#nickName").val());
            if (nickName == "") {
                alert("名称不能为空!");
                return false;
            }

            var majorScore = parseFloat($("#majorScore").val());
            if (isNaN(majorScore)) {
                $("#majorScore").val("");
                return false;
            } else {
                if (majorScore < 0 || majorScore > 5) {
                    $("#majorScore").val("");
                    return false;
                }
            }

            var comScore = parseFloat($("#comScore").val());
            if (isNaN(comScore)) {
                $("#comScore").val("");
                return false;
            } else {
                if (comScore < 0 || comScore > 5) {
                    $("#comScore").val("");
                    return false;
                }
            }

            var punctualScore = parseFloat($("#punctualScore").val());
            if (isNaN(punctualScore)) {
                $("#punctualScore").val("");
                return false;
            } else {
                if (punctualScore < 0 || punctualScore > 5) {
                    $("#punctualScore").val("");
                    return false;
                }
            }

            var avgPrice = parseFloat($("#avgPrice").val());
            if (isNaN(avgPrice)) {
                $("#avgPrice").val("");
                return false;
            } else {
                if (avgPrice < 0) {
                    $("#avgPrice").val("");
                    return false;
                }
            }

            var serverScope = $.trim($("#serverScope").val());
            if (serverScope == "") {
                alert("服务范围不能为空！");
                return false;
            }
        }

        function valueChange(node) {
            var val = parseFloat($(node).val());
            if (isNaN(val)) {
                $(node).val("")
            } else {
                $(node).val(val)
            }
        }

        function fileChange(node) {
            var fileName = $(node).val();
            var fileSuffix = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length);
            if (fileSuffix != 'jpg' && fileSuffix != 'gif' && fileSuffix != 'png' && fileSuffix != 'jpeg') {
                alert("只能上传图片！");
                $(node).val("");
            }
        }
    </script>
</head>
<body>
<form id="form_custom" action="${rootPath}/employee/updateEmployee.do" enctype="multipart/form-data" method="post">
    <input type="hidden" name="id" value="${employee.id}"/>
    <input type="hidden" name="sellerId" value="${employee.sellerId}"/>
    <table id="add_tab" cellspacing=0 cellpadding=5>
        <tr>
            <td class="td_att_name">名称:</td>
            <td>
                <input type="text" placeholder="不能为空" name="nickName" id="nickName" value="${employee.nickName}" size="65"/>
            </td>
        </tr>
        <tr>
            <td class="td_att_name">性别:</td>
            <td>
                <select id="sex" name="sex">
                    <option <c:if test="${employee.sex == '男'}">selected="true"</c:if>>男</option>
                    <option <c:if test="${employee.sex == '女'}">selected="true"</c:if>>女</option>
                </select>
            </td>
        </tr>
        <tr>
            <td class="td_att_name">在职状态:</td>
            <td>
                <select id="state" name="state">
                    <option <c:if test="${employee.state == '在职'}">selected="true"</c:if>>在职</option>
                    <option <c:if test="${employee.state == '离职'}">selected="true"</c:if>>离职</option>
                </select>
            </td>
        </tr>
        <tr>
            <td class="td_att_name">专业得分:</td>
            <td>
                <input value="${employee.majorScore}" type="text" placeholder="范围为0-5分，不得小于0" name="majorScore" onchange="valueChange(this);" id="majorScore" size="65"/>
            </td>
        </tr>

        <tr>
            <td class="td_att_name">沟通得分:</td>
            <td>
                <input value="${employee.comScore}" type="text" placeholder="范围为0-5分，不得小于0" name="comScore" onchange="valueChange(this);" id="comScore" size="65"/>
            </td>
        </tr>

        <tr>
            <td class="td_att_name">守时得分:</td>
            <td>
                <input value="${employee.punctualScore}" type="text" placeholder="范围为0-5分，不得小于0" name="punctualScore" onchange="valueChange(this);" id="punctualScore" size="65"/>
            </td>
        </tr>

        <tr>
            <td class="td_att_name">均价:</td>
            <td>
                <input value="${employee.avgPrice}" type="text" name="avgPrice" placeholder="不得小于0" onchange="valueChange(this);" id="avgPrice" size="65"/>
            </td>
        </tr>


        <tr>
            <td class="td_att_name">头像:</td>
            <td>
                <input type="hidden" name="headImg" id="headImg" value="${employee.headImg}"/>
                <input type="file" name="file" id="file" onchange="fileChange(this);"/>
            </td>
        </tr>
        <tr>
            <td class="td_att_name">服务范围:</td>
            <td>
                <textarea name="serverScope" placeholder="不能为空" id="serverScope" rows="6" cols="60">${employee.serverScope}</textarea>
            </td>
        </tr>
        <tr>
            <td colspan="2" style="text-align: center">
                <input type="button" class="input_btn" onclick="btn_submit();" value="保存"/> &nbsp;&nbsp;&nbsp;
                <input type="reset" class="input_btn" value="取消"/>
            </td>
        </tr>
    </table>
</form>
</body>
</html>