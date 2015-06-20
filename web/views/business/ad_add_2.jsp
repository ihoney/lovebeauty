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
        $(function () {
            var cityId = $("#city").val();
            $()
        });
        function btn_submit() {
            $('#form_custom').ajaxForm({
                beforeSubmit: validateMethod,
                success: function () {
                    location.href = getRootPath() + "ad/queryAllAds.do";
                },
                error: function () {
                    alert("提交失败！");
                }
            });
            $("#form_custom").submit();
        }

        function validateMethod() {
            var fileName = $("#file").val();
            if (fileName == "") {
                alert("文件不能为空！");
                return false;
            }

            var demoId = $("#demo").val();
            if (demoId == "") {
                alert("请选择作品！");
                return false;
            }
        }

        function fileChange(node) {
            var fileName = $(node).val();
            var fileSuffix = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length);
            fileSuffix = fileSuffix.toUpperCase();
            if (fileSuffix != 'JPG' && fileSuffix != 'PNG') {
                alert("只能上传图片(JPG或PNG)！");
                $(node).val("");
            }
        }

        function cancel() {
            location.href = getRootPath() + "ad/queryAllAds.do";
        }

        //作品改变
        function changeCityAndSeller(node) {
            var tag = $(node);
            if (tag.val() == "")return;
            var selectedOption = $("#demo option:selected");
            var cityId = selectedOption.attr("cityId");
            $("#city option[value='" + cityId + "']").attr("selected", "selected");
            var sellerId = selectedOption.attr("sellerId");
            $("#seller option[value='" + sellerId + "']").attr("selected", "selected");
        }

        function changeCityAndDemo(node) {
            var tag = $(node);
            var sellerId = tag.val();
            if (sellerId == "") {
                $("#demo option:first").attr("selected", "selected");
                $("#demo option").show();
                return;
            }
            var selectedOption = $("#seller option:selected");
            var cityId = selectedOption.attr("cityId");
            $("#demo option[sellerId != " + sellerId + "]").hide();
            $("#demo option:first").show().attr("selected", "selected");
            $("#demo option[sellerId = " + sellerId + "]").show();
            $("#city option[value='" + cityId + "']").attr("selected", "selected");
        }

        function changeSellerAndDemo(node) {
            var tag = $(node);
            var cityId = tag.val();
            $("#demo option[cityId != " + cityId + "]").hide();
            $("#demo option:first").show().attr("selected", "selected");
            $("#demo option[cityId = " + cityId + "]").show();
            $("#seller option[cityId != " + cityId + "]").hide();
            $("#seller option:first").show().attr("selected", "selected");
            $("#seller option[cityId = " + cityId + "]").show();
        }
    </script>
    <style type="text/css">
        .ad_table {
            width: 550px;
            font-size: 12px;
            border-top: 1px solid #b5d6e6;
            border-left: 1px solid #b5d6e6;
        }

        .ad_table td {
            padding: 3px;
            border-right: 1px solid #b5d6e6;
            border-bottom: 1px solid #b5d6e6;
        }
    </style>
</head>
<body>
<form id="form_custom" action="${rootPath}/ad/addAd.do" enctype="multipart/form-data" method="post">
    <input type="hidden" name="type" value="内部链接"/>
    <table class="ad_table" cellspadding=0 cellspacing=0>
        <tr>
            <td class="ad_td_att_name">本站作品：</td>
            <td class="ad_td_attr_value" id="linkAddress">
                城市：<select id="city" name="cityId" onchange="changeSellerAndDemo(this)">
                <c:forEach items="${cities}" var="city">
                    <option value="${city.id}">${city.NAME}</option>
                </c:forEach>
            </select>
                &nbsp;&nbsp;&nbsp;
                商铺：<select id="seller" name="sellerId" onchange="changeCityAndDemo(this);">
                <option value="">--选择商铺--</option>
                <c:forEach items="${sellers}" var="seller">
                    <option cityId="${seller.cityId}" value="${seller.id}">${seller.shopName}</option>
                </c:forEach>
            </select>
                &nbsp;&nbsp;&nbsp;
                作品：<select id="demo" name="url" onchange="changeCityAndSeller(this);">
                <option value="">--选择作品--</option>
                <c:forEach items="${demos}" var="demo">
                    <option cityId="${demo.cityId}" sellerId="${demo.sellerId}" value="${demo.id}">${demo.NAME}</option>
                </c:forEach>
            </select>
            </td>
        </tr>
        <tr>
            <td class="td_att_name">广告图片:</td>
            <td>
                <input type="file" name="file" id="file" onchange="fileChange(this);"/>
            </td>
        </tr>
        <tr>
            <td class="ad_td_att_name">备注：</td>
            <td class="ad_td_attr_value">
                <textarea style="border: none;" style="border: none;" name="backup" rows="5" cols="55"></textarea>
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