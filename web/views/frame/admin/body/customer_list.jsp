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

    //删除作品
    function customer_delete(node) {
        var customerId = $(node).attr("customerId");
        var url = getRootPath() + "customer/deleteCustomer.do?customerId=" + customerId;
        deleteRecord(node, url);
    }

    function customer_forbidden(node) {
        if (!window.confirm("确认禁用！")) {
            return;
        }
        var customerId = $(node).attr("customerId");
        var url = getRootPath() + "customer/forbiddenCustomer.do?customerId=" + customerId;
        $.ajax({
            url: url,
            type: "GET",
            dataType: "json",
            success: function (data) {
                if (data.reqResult) {
                    alert("服务器出现故障！");
                } else {
                    $(node).parent("td").siblings(".forbidden_cls").text("是");
                }
            }
        });
    }

    function customer_delete(node) {
        var customerId = $(node).attr("customerId");
        var url = getRootPath() + "customer/deleteCustomer.do?customerId=" + customerId;
        deleteRecord(node, url);
    }

    //第一页
    function firstPage() {
        if ($("#currentPage").text() != "1") {
            var url = "queryCustomerByPage.do";
            pageing(1, url, page_callback);
        }
    }

    //上一页
    function prePage() {
        if ($("#currentPage").text() != "1") {
            var curPage = parseInt($("#currentPage").text()) - 1;
            var url = "queryCustomerByPage.do";
            pageing(curPage, url, page_callback);
        }
    }
    //下一页
    function nextPage() {
        if ($("#currentPage").text() != $("#totalPage").text()) {
            var curPage = parseInt($("#currentPage").text()) + 1;
            var url = "queryCustomerByPage.do";
            pageing(curPage, url, page_callback);
        }
    }

    //最后一页
    function lastPage() {
        if ($("#currentPage").text() != $("#totalPage").text()) {
            var lastPage = $("#totalPage").text();
            var url = "queryCustomerByPage.do";
            pageing(lastPage, url, page_callback);
        }
    }

    //跳转到第几页
    function btnGo() {
        var go_page = $("#go_page").val();
        var totalPage = $("#totalPage").text();
        if (go_page != undefined && $.trim(go_page) != "" && totalPage != "") {
            if ((parseInt(go_page) <= parseInt(totalPage)) && parseInt(go_page) > 0) {
                var url = "queryCustomerByPage.do";
                pageing(go_page, url, page_callback);
            }
        }
    }

    function page_callback(data) {
        $(".frame_table tr:gt(0)").remove();
        var tabTag = $(".frame_table");
        var customers = data.customers;
        var trTag;
        for (var i = 0, j = customers.length; i < j; i++) {
            trTag = ' <tr>'
                    + '<td><input class="select_inp2" type="checkbox" customerId="' + customers[i].id + '"/></td>'
                    + ' <td>' + i + '</td>'
                    + '<td>' + customers[i].account + '</td>'
                    + '<td>' + customers[i].regip + '</td>'
                    + '<td>' + customers[i].regtime + '</td>'
                    + '<td>' + customers[i].loginip + '</td>'
                    + '<td>' + customers[i].logintime + '</td>'
                    + '<td>' + customers[i].city + '</td>';
            if (customers[i].forbidden == 'true') {
                trTag += '<td>是</td>';
            } else {
                trTag += '<td>否</td>';
            }
            trTag += '<td>' + customers[i].jubao + '</td>'
                    + '<td>'
                    + ' <a href="javascript:void(0);">预览</a>'
                    + '  <a href="javascript:void(0);" customerId="' + customers[i].id + '" onclick="customer_forbidden(this);">编辑</a>'
                    + '  <a href="javascript:void(0);" customerId="' + customers[i].id + '" onclick="customer_delete(this);">删除</a>'
                    + ' </td>          '
                    + '</tr>';
            tabTag.append($(trTag));
        }
    }
</script>

<div class="tab_header">
    <span class="fontStyle_bold cur_pos">你当前的位置：</span>[业务中心]-[客户列表]
</div>
<div id="tb_body">
    <table class="frame_table" cellspadding=0 cellspacing=0>
        <tr class="tr_header">
            <td class="td_class_1">
                <input class="select_inp" type="checkbox"/>
            </td>
            <td class="td_class_2">序号</td>
            <td class="td_class_3">账号</td>
            <td class="td_class_5">注册IP</td>
            <td class="td_class_6">注册时间</td>
            <td class="td_class_5">登录IP</td>
            <td class="td_class_6">登录时间</td>
            <td class="td_class_6">所在城市</td>
            <td class="td_class_6">禁用</td>
            <td class="td_class_7">举报次数</td>
            <td class="td_class_150">操作</td>
        </tr>
        <c:forEach var="customer" items="${customers}" varStatus="vst">
            <tr class="tr_body">
                <td><input class="select_inp2" type="checkbox" customerId="${customer.id}"/></td>
                <td>${vst.index}</td>
                <td>${customer.account}</td>
                <td>${customer.regip}</td>
                <td>${customer.regtime}</td>
                <td>${customer.loginip}</td>
                <td>${customer.logintime}</td>
                <td>${customer.city}</td>
                <td class="forbidden_cls">
                    <c:if test="${customer.forbidden == 'true'}">是</c:if>
                    <c:if test="${customer.forbidden == 'false'}">否</c:if>
                </td>
                <td>${customer.jubao}</td>
                <td>
                    <a href="javascript:void(0);" customerId="${customer.id}" onclick="customer_forbidden(this);">禁用</a>
                    <a href="javascript:void(0);" customerId="${customer.id}" onclick="customer_delete(this);">删除</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>

<div id="tb_body_footer">
        <span class="footer_text footer_text_align_one">
            <span>共有 ${sellerCount} 条记录，当前第 <span id="currentPage">${curPage}</span>/<span id="totalPage">${totalPage}</span> 页</span>
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
