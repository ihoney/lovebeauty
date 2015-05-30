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

        function cancel() {
            location.href = getRootPath() + "demo/queryAllDemos.do?showType=1";
        }
    </script>
</head>
<body>
<form id="form_custom" action="${rootPath}/demo/updateDemo.do" enctype="multipart/form-data" method="post">
    <input type="hidden" name="id" value="${demo.id}"/>
    <input type="hidden" name="sellerId" value="${demo.sellerid}"/>
    <table id="add_tab" cellspacing=0 cellpadding=5>
        <tr>
            <td class="td_att_name">名称:</td>
            <td>
                <input type="text" name="name" id="name" size="65" value="${demo.name}"/>
            </td>
        </tr>

        <tr>
            <td class="td_att_name">作品师:</td>
            <td>
                <select id="empId" name="empId" style="width: 120px;">
                    <c:forEach items="${employees}" var="employee">
                        <option value="${employee.id}" <c:if test="${employee.id == demo.employeeId}">selected="true"</c:if>>${employee.nickName}</option>
                    </c:forEach>
                </select>
            </td>
        </tr>
        <tr>
            <td class="td_att_name">作品类型:</td>
            <td>
                <select id="demoType" name="demoType">
                    <option <c:if test="${demo.demoType == '美甲'}">selected="true"</c:if>>美甲</option>
                    <option <c:if test="${demo.demoType == '美睫'}">selected="true"</c:if>>美睫</option>
                    <option <c:if test="${demo.demoType == '美容'}">selected="true"</c:if>>美容</option>
                    <option <c:if test="${demo.demoType == '美足'}">selected="true"</c:if>>美足</option>
                    <option <c:if test="${demo.demoType == '化妆造型'}">selected="true"</c:if>>化妆造型</option>
                </select>
            </td>
        </tr>
        <tr>
            <td class="td_att_name">价格:</td>

            <td>
                <input type="text" name="price" onchange="valueChange(this);" value="${demo.price}" id="price" size="65"/>
            </td>
        </tr>

        <tr>
            <td class="td_att_name">首次优惠价格:</td>
            <td>
                <input type="text" name="preferentialPrice" onchange="valueChange(this);" value="${demo.PreferentialPrice}" id="preferentialPrice" size="65"/>
            </td>
        </tr>
        <tr>
            <td class="td_att_name">
                可预约时间:
            </td>
            <td>
                <input type="text" name="bookTime" value="${demo.booktime}" id="bookTime" size="65"/>
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
                <textarea style="border: none;" name="description" id="description" rows="6" cols="60">${demo.description}</textarea>
            </td>
        </tr>
        <tr>
            <td colspan="2" style="text-align: center">
                <input type="button" class="input_btn" onclick="btn_submit();" value="保存"/> &nbsp;&nbsp;&nbsp;
                <input type="button" class="input_btn" onclick="cancel()" value="取消"/>
            </td>
        </tr>
    </table>
</form>
</body>
</html>