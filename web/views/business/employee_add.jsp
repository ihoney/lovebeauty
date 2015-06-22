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

            var serverScope = $.trim($("#serverScope").val());
            if (serverScope == "") {
                alert("服务范围不能为空！");
                return false;
            }
        }

        function valueChange(node) {
            var val = parseFloat($(node).val());
            if (isNaN(val)) {
                $(node).val("");
            } else {
                $(node).val(val);
            }
        }

        function fileChange(node) {
            var fileName = $(node).val();
            var fileSuffix = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length);
            fileSuffix = fileSuffix.toUpperCase();
            if (fileSuffix != 'JPG' && fileSuffix != 'PNG') {
                alert("只能上传图片！");
                $(node).val("");
            }
        }

        function cancel() {
            location.href = getRootPath() + "employee/queryAllEmployees.do";
        }
    </script>
</head>
<body>
<form id="form_custom" action="${rootPath}/employee/addEmployee.do" enctype="multipart/form-data" method="post">
    <div style="font-size: 12px; color: #ff0000;">注意：新增员工其作品次日可被预约</div>
    <table id="add_tab" cellspacing=0 cellpadding=5>
        <tr>
            <td class="td_att_name">手艺人名称:</td>
            <td>
                <input type="text" placeholder="不能为空" name="nickName" id="nickName" size="65"/>
            </td>
        </tr>
        <tr>
            <td class="td_att_name">性别:</td>
            <td>
                <select id="sex" name="sex">
                    <option>男</option>
                    <option>女</option>
                </select>
            </td>
        </tr>
        <tr>
            <td class="td_att_name">在职状态:</td>
            <td>
                <select id="state" name="state">
                    <option>在职</option>
                    <option>离职</option>
                </select>
            </td>
        </tr>

        <tr>
            <td class="td_att_name">手艺人头像(png或jpg):</td>
            <td>
                <input type="file" name="file" id="file" onchange="fileChange(this);"/>
            </td>
        </tr>
        <tr>
            <td class="td_att_name">服务范围:</td>
            <td>
                <textarea style="border: none;" name="serverScope" placeholder="不能为空" id="serverScope" rows="6" cols="60"></textarea>
            </td>
        </tr>
        <tr>
            <td colspan="2" style="text-align: center">
                <input type="button" class="input_btn" onclick="btn_submit();" value="添加"/> &nbsp;&nbsp;&nbsp;
                <input type="button" class="input_btn" onclick="cancel();" value="取消"/>
            </td>
        </tr>
    </table>
</form>
</body>
</html>