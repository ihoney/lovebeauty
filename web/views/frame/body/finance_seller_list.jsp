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
            var url = "queryCycleFinanceByPage.do";
            pageing(1, url, page_callback);
        }
    }

    //上一页
    function prePage() {
        if ($("#currentPage").text() != "1") {
            var curPage = parseInt($("#currentPage").text()) - 1;
            var url = "queryCycleFinanceByPage.do";
            pageing(curPage, url, page_callback);
        }
    }
    //下一页
    function nextPage() {
        if ($("#currentPage").text() != $("#totalPage").text()) {
            var curPage = parseInt($("#currentPage").text()) + 1;
            var url = "queryCycleFinanceByPage.do";
            pageing(curPage, url, page_callback);
        }
    }

    //最后一页
    function lastPage() {
        if ($("#currentPage").text() != $("#totalPage").text()) {
            var lastPage = $("#totalPage").text();
            var url = "queryCycleFinanceByPage.do";
            pageing(lastPage, url, page_callback);
        }
    }

    //跳转到第几页
    function btnGo() {
        var go_page = $("#go_page").val();
        var totalPage = $("#totalPage").text();
        if (go_page != undefined && $.trim(go_page) != "" && totalPage != "") {
            if ((parseInt(go_page) <= parseInt(totalPage)) && parseInt(go_page) > 0) {
                var url = "queryCycleFinanceByPage.do";
                pageing(go_page, url, page_callback);
            }
        }
    }

    function applyTransfer(node) {
        var financeRecordId = $(node).attr("financeRecordId");
		var tradeCycle = $(node).attr("tradeCycle");
		var now = new Date();
		var year = now.getFullYear();
		var month = now.getMonth() + 1;
		var dateStr = year + "" + month;
		if(dateStr == tradeCycle){
			alert("只能提取之前月份的款项！");
			return;
		}
		
        $.ajax({
            url: getRootPath() + "finance/applyTransfer.do?financeRecordId=" + financeRecordId + "&code=1",
            type: "GET",
            success: function (data) {
                $(node).closest("td").prev("td").text("审核中");
                $(node).remove();
            }
        })
    }

    function page_callback(data) {
        $(".frame_table tr:gt(0)").remove();
        var tabTag = $(".frame_table");
        var financeRecords = data.financeRecords;
        var trTag;
        var financeRecordState;
        var status;
        for (var i = 0, j = financeRecords.length; i < j; i++) {
            if (financeRecords[i].transferStatus == 0) {
                status = "未转账";
            } else if (financeRecords[i].transferStatus == 1) {
                status = "审核中";
            } else {
                status = "转账成功";
            }
            trTag = ' <tr class="tr_body">'
                    + '<td><input class="select_inp2" type="checkbox" financeRecordId="' + financeRecords[i].id + '"/></td>'
                    + ' <td>' + i + '</td>'
                    + '<td>' + financeRecords[i].tradeCycle + '</td>'
                    + '<td>' + financeRecords[i].orderCount + '</td>'
                    + '<td>' + financeRecords[i].orderPrice + '</td>'
                    + '<td>' + status + '</td><td>';
            if (financeRecords[i].transferStatus == 0) {
                trTag += '<a href="javascript:void(0);" financeRecordId="' + financeRecords[i].id + '" onclick="applyTransfer(this);">申请转账</a>';
            }
            trTag += '</td></tr>';
            $(trTag).appendTo($(tabTag));
        }
    }
</script>

<div class="tab_header">
    <span class="fontStyle_bold cur_pos">你当前的位置：</span>[业务中心]-[财务列表]
</div>
<div id="tb_body">
    <table class="frame_table" cellspadding=0 cellspacing=0>
        <tr class="tr_header">
            <td><input class="select_inp" type="checkbox"/></td>
            <td>序号</td>
            <td>交易月份</td>
            <td>订单数量</td>
            <td>交易额度</td>
            <td>交易状态</td>
            <td>操作</td>
        </tr>
        <c:forEach var="financeRecord" items="${financeRecords}" varStatus="vst">
            <tr class="tr_body">
                <td><input class="select_inp2" type="checkbox" financeRecordId="${financeRecord.id}"/></td>
                <td>${vst.index}</td>
                <td>${financeRecord.tradeCycle}</td>
                <td>${financeRecord.orderCount}</td>
                <td>${financeRecord.orderPrice}</td>
                <td>
                    <c:if test="${financeRecord.transferStatus == 0 }"><span style="color:green;">未转账</span></c:if>
                    <c:if test="${financeRecord.transferStatus == 1 }"><span style="color:red;">审核中</span></c:if>
                    <c:if test="${financeRecord.transferStatus == 2 }">转账成功</c:if>
                </td>
                <td>
                    <c:if test="${financeRecord.transferStatus == 0}">
                        <a href="javascript:void(0);" tradeCycle="${financeRecord.tradeCycle}" financeRecordId="${financeRecord.id}" onclick="applyTransfer(this);">申请转账</a>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>

<div id="tb_body_footer">
        <span class="footer_text footer_text_align_one">
            <span>共有 ${financeCount} 条记录，当前第 <span id="currentPage">${curPage}</span>/<span id="totalPage">${totalPage}</span> 页</span>
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