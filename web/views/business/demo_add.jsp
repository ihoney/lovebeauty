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
                    location.href = getRootPath() + "demo/queryAllDemos.do?showType=1";
                },
                error: function () {
                    alert("提交失败！");
                }
            });
            $("#form_custom").submit();
        }

        function validateMethod() {
            var price = parseFloat($("#price").val());
            if (isNaN(price)) {
                $("#price").val(0)
            } else {
                $("#price").val(price)
            }

            var preferentialPrice = parseFloat($("#preferentialPrice").val());
            if (isNaN(preferentialPrice)) {
                $("#preferentialPrice").val(0)
            } else {
                $("#preferentialPrice").val(preferentialPrice)
            }

            var name = $.trim($("#name").val());
            if (name == "") {
                alert("名称不能为空！");
                return false;
            }

            var fileName = $("#file").val();
            if (fileName == "") {
                alert("文件不能为空！");
                return false;
            }
            var description = $.trim($("#description").val());
            if (description == "") {
                alert("描述不能为空！");
                return false;
            }
        }

        function valueChange(node) {
            var val = parseFloat($(node).val());
            if (isNaN(val)) {
                $(node).val(0)
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
<form id="form_custom" action="${rootPath}/demo/addDemo.do" enctype="multipart/form-data" method="post">
    <table id="add_tab" cellspacing=0 cellpadding=5>
        <tr>
            <td class="td_att_name">名称:</td>
            <td>
                <input type="text" name="name" id="name" size="65"/>
            </td>
        </tr>
        <tr>
            <td class="td_att_name">作品类型:</td>
            <td>
                <select id="demoType" name="demoType">
                    <option value="0">美甲</option>
                    <option value="1">美睫</option>
                    <option value="2">美容</option>
                    <option value="3">美足</option>
                    <option value="4">化妆造型</option>
                </select>
            </td>
        </tr>
        <tr>
            <td class="td_att_name">价格:</td>

            <td>
                <input type="text" name="price" onchange="valueChange(this);" id="price" size="65"/>
            </td>
        </tr>

        <tr>
            <td class="td_att_name">首次优惠价格:</td>
            <td>
                <input type="text" name="preferentialPrice" onchange="valueChange(this);" id="preferentialPrice" size="65"/>
            </td>
        </tr>
        <tr>
            <td class="td_att_name">
                可预约时间:
            </td>
            <td>
                <input type="text" name="bookTime" id="bookTime" size="65"/>
            </td>
        </tr>
        <tr>
            <td class="td_att_name">文件:</td>
            <td>
                <input type="file" name="file" id="file" onchange="fileChange(this);"/>
            </td>
        </tr>
        <tr>
            <td class="td_att_name">作品描述:</td>
            <td>
                <textarea name="description" id="description" rows="6" cols="60"></textarea>
            </td>
        </tr>
        <tr>
            <td colspan="2" style="text-align: center">
                <input type="button" class="input_btn" onclick="btn_submit();" value="添加"/> &nbsp;&nbsp;&nbsp;
                <input type="reset" class="input_btn" value="取消"/>
            </td>
        </tr>
    </table>
</form>
</body>
</html>