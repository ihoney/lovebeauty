<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>私人订制详情</title>
    <script type="text/javascript" src="${rootPath}/js/jquery-1.8.3.min.js"></script>
    <script type="text/javascript" src="${rootPath}/js/rootPath.js"></script>
    <link type="text/css" rel="stylesheet" href="${rootPath}/css/privateOrder.css"/>
    <script type="text/javascript" src="${rootPath}/js/jquery-ui-1.10.2.custom.js"></script>
    <link href="${rootPath}/css/jquery-ui-1.10.2.custom.css" type="text/css" rel="stylesheet"/>
    <link href="${rootPath}/css/ui_skin.css" type="text/css" rel="stylesheet"/>

    <script type="text/javascript">
        function imgLoadError(node) {
            $(node).attr("src", "${rootPath}/images/default.jpg");
        }
    </script>
</head>
<body>
<div id="privateOrder_info">
    <div style="float: left; ">
        <img onerror="imgLoadError(this);" src="${rootPath}/fileUpload/${privateOrder.reqPicName}" class="demo_img"/>
    </div>
    <div style="width: 300px; height: 300px;">
        <table style="width: 100%;">
            <tr>
                <td class="td_attr_title">客户：</td>
                <td class="td_attr_value"> ${privateOrder.account}</td>
            </tr>
            <tr>
                <td class="td_attr_title">理想价格(元)：</td>
                <td class="td_attr_value"> ${privateOrder.price}</td>
            </tr>
            <tr>
                <td class="td_attr_title">订单时间：</td>
                <td> ${privateOrder.ordertime}</td>
            </tr>
            <tr>
                <td class="td_attr_title">需求描述：</td>
                <td> ${privateOrder.description} </td>
            </tr>
        </table>
    </div>
</div>
</body>
</html>