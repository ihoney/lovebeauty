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
<script type="text/javascript" src="${rootPath}/js/DateFormat.js"></script>

<script type="text/javascript">
    $(function () {
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
            var url = "queryOrderByPage.do";
            pageing(1, url, page_callback);
        }
    }

    //上一页
    function prePage() {
        if ($("#currentPage").text() != "1") {
            var curPage = parseInt($("#currentPage").text()) - 1;
            var url = "queryOrderByPage.do";
            pageing(curPage, url, page_callback);
        }
    }
    //下一页
    function nextPage() {
        if ($("#currentPage").text() != $("#totalPage").text()) {
            var curPage = parseInt($("#currentPage").text()) + 1;
            var url = "queryOrderByPage.do";
            pageing(curPage, url, page_callback);
        }
    }

    //最后一页
    function lastPage() {
        if ($("#currentPage").text() != $("#totalPage").text()) {
            var lastPage = $("#totalPage").text();
            var url = "queryOrderByPage.do";
            pageing(lastPage, url, page_callback);
        }
    }

    //跳转到第几页
    function btnGo() {
        var go_page = $("#go_page").val();
        var totalPage = $("#totalPage").text();
        if (go_page != undefined && $.trim(go_page) != "" && totalPage != "") {
            if ((parseInt(go_page) <= parseInt(totalPage)) && parseInt(go_page) > 0) {
                var url = "queryOrderByPage.do";
                pageing(go_page, url, page_callback);
            }
        }
    }

    function order_sure(node) {
        var order_id = $(node).attr("orderId");
        var url = getRootPath() + "order/orderSure.do";
        $.ajax({
            url: url,
            type: "POST",
            data: {"orderId": order_id},
            dataType: "json",
            success: function (data) {
                $(node).parent("td").prev("td").prev("td").text(curDateTime);
                $(node).parent("td").prev("td").text("交易成功");
                $(node).parent("td").empty();
            }
        });
    }

    function order_abort(node) {
        var order_id = $(node).attr("orderId");
        var url = getRootPath() + "order/abortOrder.do";
        $.ajax({
            url: url,
            type: "POST",
            data: {"orderId": order_id},
            dataType: "json",
            success: function (data) {
                $(node).parent("td").prev("td").text("订单取消");
                $(node).parent("td").empty();
            }
        });
    }

    function page_callback(data) {
        $(".frame_table tr:gt(0)").remove();
        var tabTag = $(".frame_table");
        var orders = data.orders;
        var trTag;
        var orderState;
        for (var i = 0, j = orders.length; i < j; i++) {
            trTag = ' <tr class="tr_body">'
                    + '<td><input class="select_inp2" type="checkbox" orderId="' + orders[i].id + '"/></td>'
                    + ' <td>' + i + '</td>'
                    + '<td>' + orders[i].id + '</td>'
                    + '<td>' + orders[i].account + '</td>'
                    + '<td><a href="${rootPath}/demo/getDemoDetail.do?demoId=' + orders[i].demoId + '">' + orders[i].NAME + '</a></td>'
                    + '<td>' + orders[i].price + '</td>'
                    + '<td>' + orders[i].bookTime + '</td>'
                    + '<td>' + orders[i].ordertime + '</td>'
                    + '<td>' + orders[i].paytime + '</td>'
                    + '<td>' + orders[i].state + '</td>'
                    + '<td>' + orders[i].serverAddress + '</td>';
            if (orders[i].state == '未付款') {
                trTag = trTag + '<td><a href="javascript:void(0);" orderId="' + orders[i].id + '" onclick="order_sure(this);">确认</a>'
                        + '  <a href="javascript:void(0);" orderId="' + orders[i].id + '" onclick="order_abort(this);">取消订单</a>' + ' </td> ';
            }
            trTag += '</tr>';
            $(trTag).appendTo($(tabTag));
        }
    }
</script>

<div class="tab_header">
    <span class="fontStyle_bold cur_pos">你当前的位置：</span>[业务中心]-[订单列表]
</div>
<div id="tb_body">
    <table class="frame_table" cellspadding=0 cellspacing=0>
        <tr class="tr_header">
            <td><input class="select_inp" type="checkbox"/></td>
            <td>序号</td>
            <td>订单号</td>
            <td>客户账号</td>
            <td>作品</td>
            <td>订单价格</td>
            <td>预约服务时间</td>
            <td>下单时间</td>
            <td>付款时间</td>
            <td>订单状态</td>
            <td>服务地址</td>
            <td>操作</td>
        </tr>
        <c:forEach var="order" items="${orders}" varStatus="vst">
            <tr class="tr_body">
                <td><input class="select_inp2" type="checkbox" orderId="${order.id}"/></td>
                <td>${vst.index}</td>
                <td>${order.id}</td>
                <td>${order.account}</td>
                <td>
                    <a href="${rootPath}/demo/getDemoDetail.do?demoId=${order.demoId}">${order.NAME}</a>
                </td>
                <td>${order.price}</td>
                <td>${order.bookTime}</td>
                <td>${order.ordertime}</td>
                <td>${order.paytime}</td>
                <td>${order.state}</td>
                <td>${order.serverAddress}</td>
                <td>
                    <c:if test="${order.state == '未付款'}">
                        <a href="javascript:void(0);" orderId="${order.id}" onclick="order_sure(this);">确认</a>
                        <a href="javascript:void(0);" orderId="${order.id}" onclick="order_abort(this);">取消订单</a>
                    </c:if>
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
            <button class="footer_text_two" onclick="btnGo();">跳转</button>
        </span>
</div>