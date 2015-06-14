<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}"/>
<link href="${rootPath}/css/frame.css" type="text/css" rel="stylesheet"/>
<link href="${rootPath}/css/frame_left.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript" src="${rootPath}/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="${rootPath}/js/frame_left.js"></script>
<div id="content">
    <div id="menu_manager">
        管理菜单
    </div>
    <div class="business_menu">
        <div class="business_title">
            业务中心
        </div>
        <div class="business_body div_show">
            <ul>
                <li><a href="${rootPath}/seller/queryAllSellers.do" target="rightFrame" class="STYLE3">商铺列表</a></li>
                <li><a href="${rootPath}/customer/queryAllCustomers.do" target="rightFrame" class="STYLE3">客户列表</a></li>
                <li><a href="${rootPath}/employee/queryAllEmployeesAdmin.do" target="rightFrame" class="STYLE3">所有技师</a></li>
                <li><a href="${rootPath}/recruitment/queryAllRecruitment.do" target="rightFrame" class="STYLE3">应聘手艺人列表</a></li>
                <li><a href="${rootPath}/privateOrder/queryPrivateOrderAdmin.do" target="rightFrame" class="STYLE3">私人订制业务</a></li>
                <li><a href="${rootPath}/admin/queryAllCities.do" target="rightFrame" class="STYLE3">开通城市管理</a></li>
                <li><a href="${rootPath}/ad/queryAllAds.do" target="rightFrame" class="STYLE3">广告管理</a></li>
            </ul>
        </div>
    </div>

    <div class="business_menu">
        <div class="business_title">
            系统管理
        </div>
        <div class="business_body">
            <ul>
            </ul>
        </div>
    </div>
</div>