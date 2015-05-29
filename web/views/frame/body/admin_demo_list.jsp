<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}"/>

<script type="text/javascript" src="${rootPath}/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="${rootPath}/js/rootPath.js"></script>
<script type="text/javascript" src="${rootPath}/js/common_operation.js"></script>
<script type="text/javascript" src="${rootPath}/js/jquery-ui-1.10.2.custom.js"></script>

<link href="${rootPath}/css/frame_table.css" type="text/css" rel="stylesheet"/>
<link href="${rootPath}/css/jquery-ui-1.10.2.custom.css" type="text/css" rel="stylesheet"/>
<link href="${rootPath}/css/ui_skin.css" type="text/css" rel="stylesheet"/>

<link type="text/css" rel="stylesheet" href="${rootPath}/css/colorbox.css"/>
<script type="text/javascript" src="${rootPath}/js/jquery.colorbox.js"></script>

<script type="text/javascript">
    $(function () {
        $(".tr_body:odd").css("background-color", "#D9EFFD");

        $(".img_class_a").colorbox({rel: 'img_class_a', photo: true});
    });

    //第一页
    function firstPage() {
        if ($("#currentPage").text() != "1") {
            var url = "queryDemoByPage.do";
            pageing(1, url, page_callback);
        }
    }

    //上一页
    function prePage() {
        if ($("#currentPage").text() != "1") {
            var curPage = parseInt($("#currentPage").text()) - 1;
            var url = "queryDemoByPage.do";
            pageing(curPage, url, page_callback);
        }
    }
    //下一页
    function nextPage() {
        if ($("#currentPage").text() != $("#totalPage").text()) {
            var curPage = parseInt($("#currentPage").text()) + 1;
            var url = "queryDemoByPage.do";
            pageing(curPage, url, page_callback);
        }
    }

    //最后一页
    function lastPage() {
        if ($("#currentPage").text() != $("#totalPage").text()) {
            var lastPage = $("#totalPage").text();
            var url = "queryDemoByPage.do";
            pageing(lastPage, url, page_callback);
        }
    }

    //跳转到第几页
    function btnGo() {
        var go_page = $("#go_page").val();
        var totalPage = $("#totalPage").text();
        if (go_page != undefined && $.trim(go_page) != "" && totalPage != "") {
            if ((parseInt(go_page) <= parseInt(totalPage)) && parseInt(go_page) > 0) {
                var url = "queryDemoByPage.do";
                pageing(go_page, url, page_callback);
            }
        }
    }

    function page_callback(data) {
        $(".frame_table tr:gt(0)").remove();
        var tabTag = $(".frame_table");
        var demos = data.demos;
        var trTag;
        for (var i = 0, j = demos.length; i < j; i++) {
            trTag = ' <tr>'
                    + '<td><input class="select_inp2" type="checkbox" demoId="' + demos[i].id + '"/></td>'
                    + ' <td>' + i + '</td>'
                    + '<td>' + demos[i].sellerName + '</td>'
                    + '<td><a href="${rootPath}/demo/getDemoDetail.do?demoId=' + demos[i].id + '">' + demos[i].NAME + '</a></td>';
            if (demos[i].demoType == 0) {
                trTag += '<td>美甲</td>';
            } else if (demos[i].demoType == 1) {
                trTag += '<td>美睫</td>';
            } else if (demos[i].demoType == 2) {
                trTag += '<td>美容</td>';
            } else if (demos[i].demoType == 3) {
                trTag += '<td>美足</td>';
            } else if (demos[i].demoType == 4) {
                trTag += '<td>化妆造型</td>';
            }
            trTag += '<td>' + demos[i].price + '</td>'
                    + '<td>' + demos[i].PreferentialPrice + '</td>'
                    + '<td>' + demos[i].booktime + '</td>'
                    + '<td>'
                    + ' <a href="${rootPath}/fileUpload/' + demos[i].fileEName + '" class="img_class_a">预览</a>'
                    + ' </td>'
                    + '</tr>';
            tabTag.append($(trTag));
        }
    }
</script>

<div class="tab_header">
    <span class="fontStyle_bold cur_pos">你当前的位置：</span>[业务中心]-[订单列表]
</div>
<div id="tb_body">
    <table class="frame_table" cellspadding=0 cellspacing=0>
        <tr class="tr_header">
            <td class="td_class_1"><input class="select_inp" type="checkbox"/></td>
            <td class="td_class_2">序号</td>
            <td class="td_class_80">商家</td>
            <td class="td_class_6">作品名称</td>
            <td class="td_class_80">作品类型</td>
            <td class="td_class_4">价格</td>
            <td class="td_class_6">首次优惠价格</td>
            <td class="td_class_6">可预约时间</td>
            <td class="td_class_150">操作</td>
        </tr>
        <c:forEach var="demo" items="${demos}" varStatus="vst">
            <tr class="tr_body">
                <td><input class="select_inp2" type="checkbox" demoId="${demo.id}"/></td>
                <td>${vst.index}</td>
                <td>${demo.sellerName}</td>
                <td>
                    <a href="${rootPath}/demo/getDemoDetail.do?demoId=${demo.id}">${demo.NAME}</a>
                <td>
                    <c:if test="${demo.demoType == 0}">美甲</c:if>
                    <c:if test="${demo.demoType == 1}">美睫</c:if>
                    <c:if test="${demo.demoType == 2}">美容</c:if>
                    <c:if test="${demo.demoType == 3}">美足</c:if>
                    <c:if test="${demo.demoType == 4}">化妆造型</c:if>
                </td>
                <td>${demo.price}</td>
                <td>${demo.PreferentialPrice}</td>
                <td>${demo.booktime}</td>
                <td>
                    <a href="${rootPath}/fileUpload/${demo.fileEName}" class="img_class_a">预览</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>

<div id="tb_body_footer">
        <span class="footer_text footer_text_align_one">
            <span>共有 ${demoCount} 条记录，当前第 <span id="currentPage">${curPage}</span>/<span id="totalPage">${totalPage}</span> 页</span>
        </span>
        <span class="footer_text_align_two">
            <button class="footer_text_two" onclick="firstPage();">首页</button>
            <button class="footer_text_two" onclick="prePage();">上一页</button>
            <button class="footer_text_two" onclick="nextPage();">下一页</button>
            <button class="footer_text_two" onclick="lastPage();">尾页</button>
            转到第<input id="go_page" type="text" size="8"/>页
            <button class="footer_text_two" onclick="btnGo();">&nbsp;</button>
        </span>
</div>