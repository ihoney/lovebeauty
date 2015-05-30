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

<script type="text/javascript">
    $(function () {
        $(".tr_body:odd").css("background-color", "#D9EFFD");

        $(".tr_body").click(function () {
            if ($(this).find(".select_inp2").attr("checked") == undefined) {
                $(this).find(".select_inp2").attr("checked", "checked");
            } else {
                $(this).find(".select_inp2").removeAttr("checked");
            }
        })
    });

    //第一页
    function firstPage() {
        if ($("#currentPage").text() != "1") {
            var url = "queryPrivateOrderByPage.do";
            pageing(1, url, page_callback);
        }
    }

    //上一页
    function prePage() {
        if ($("#currentPage").text() != "1") {
            var curPage = parseInt($("#currentPage").text()) - 1;
            var url = "queryPrivateOrderByPage.do";
            pageing(curPage, url, page_callback);
        }
    }
    //下一页
    function nextPage() {
        if ($("#currentPage").text() != $("#totalPage").text()) {
            var curPage = parseInt($("#currentPage").text()) + 1;
            var url = "queryPrivateOrderByPage.do";
            pageing(curPage, url, page_callback);
        }
    }

    //最后一页
    function lastPage() {
        if ($("#currentPage").text() != $("#totalPage").text()) {
            var lastPage = $("#totalPage").text();
            var url = "queryPrivateOrderByPage.do";
            pageing(lastPage, url, page_callback);
        }
    }

    //跳转到第几页
    function btnGo() {
        var go_page = $("#go_page").val();
        var totalPage = $("#totalPage").text();
        if (go_page != undefined && $.trim(go_page) != "" && totalPage != "") {
            if ((parseInt(go_page) <= parseInt(totalPage)) && parseInt(go_page) > 0) {
                var url = "queryPrivateOrderByPage.do";
                pageing(go_page, url, page_callback);
            }
        }
    }

    function page_callback(data) {
        $(".frame_table tr:gt(0)").remove();
        var tabTag = $(".frame_table");
        var orders = data.orders;
        var trTag;
        for (var i = 0, j = orders.length; i < j; i++) {
            trTag = ' <tr>'
                    + '<td><input class="select_inp2" type="checkbox" orderId="' + orders[i].id + '"/></td>'
                    + ' <td>' + i + '</td>'
                    + '<td>' + orders[i].userAccount + '</td>'
                    + '<td>' + orders[i].sellerAccount + '</td>'
                    + '<td>' + orders[i].price + '</td>'
                    + '<td>' + orders[i].ordertime + '</td>';

            if (orders[i].state == 1) {
                trTag += "<td>未付款</td>";
            } else if (orders[i].state == 2) {
                trTag += "<td>交易成功</td>";
            } else if (orders[i].state == 0) {
                trTag += "<td>私人订制池</td>";
            }
            trTag += "<td>" + orders[i].successtime + "</td><td>" + orders[i].description + "</td>";
            trTag += '<td>' +
                    '<a href="' + getRootPath() + 'privateOrder/privateOrderDetail.do?orderId=' + orders[i].id + '" target="rightFrame">详情</a>';
            trTag += "</td>";
            trTag += '</tr>';
            tabTag.append($(trTag));
        }
    }
</script>

<div class="tab_header">
    <span class="fontStyle_bold cur_pos">你当前的位置：</span>[业务中心]-[系统私人订制列表]
</div>
<div id="tb_body">
    <table class="frame_table" cellspadding=0 cellspacing=0>
        <tr class="tr_header">
            <td><input class="select_inp" type="checkbox"/></td>
            <td>序号</td>
            <td>客户账号</td>
            <td>商铺账号</td>
            <td>心理价格</td>
            <td>预定时间</td>
            <td>订单状态</td>
            <td>成交时间</td>
            <td>预定描述</td>
            <td>操作</td>
        </tr>
        <c:forEach var="order" items="${orders}" varStatus="vst">
            <tr class="tr_body">
                <td><input class="select_inp2" type="checkbox" orderId="${order.id}"/></td>
                <td>${vst.index}</td>
                <td>${order.userAccount}</td>
                <td>${order.sellerAccount}</td>
                <td>${order.price}</td>
                <td>${order.ordertime}</td>
                <td>
                    <c:if test="${order.state == 1}">未付款</c:if>
                    <c:if test="${order.state == 2}">交易成功</c:if>
                    <c:if test="${order.state == 0}">私人订制池</c:if>
                </td>
                <td>${order.successtime}</td>
                <td>${order.description}</td>
                <td>
                    <a href="${rootPath}/privateOrder/privateOrderDetail.do?orderId=${order.id}" target="rightFrame">详情</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>

<div id="tb_body_footer">
        <span class="footer_text footer_text_align_one">
            <span>共有 ${orderCount} 条记录，当前第 <span id="currentPage">${curPage}</span>/<span id="totalPage">${totalPage}</span> 页</span>
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