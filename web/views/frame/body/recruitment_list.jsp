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
            var url = "queryRecruitmentByPage.do";
            pageing(1, url, page_callback);
        }
    }

    //上一页
    function prePage() {
        if ($("#currentPage").text() != "1") {
            var curPage = parseInt($("#currentPage").text()) - 1;
            var url = "queryRecruitmentByPage.do";
            pageing(curPage, url, page_callback);
        }
    }
    //下一页
    function nextPage() {
        if ($("#currentPage").text() != $("#totalPage").text()) {
            var curPage = parseInt($("#currentPage").text()) + 1;
            var url = "queryRecruitmentByPage.do";
            pageing(curPage, url, page_callback);
        }
    }

    //最后一页
    function lastPage() {
        if ($("#currentPage").text() != $("#totalPage").text()) {
            var lastPage = $("#totalPage").text();
            var url = "queryRecruitmentByPage.do";
            pageing(lastPage, url, page_callback);
        }
    }

    //跳转到第几页
    function btnGo() {
        var go_page = $("#go_page").val();
        var totalPage = $("#totalPage").text();
        if (go_page != undefined && $.trim(go_page) != "" && totalPage != "") {
            if ((parseInt(go_page) <= parseInt(totalPage)) && parseInt(go_page) > 0) {
                var url = "queryRecruitmentByPage.do";
                pageing(go_page, url, page_callback);
            }
        }
    }

    function page_callback(data) {
        $(".frame_table tr:gt(0)").remove();
        var tabTag = $(".frame_table");
        var recruitments = data.recruitment;
        var trTag;
        var forbiddenTmp;
        for (var i = 0, j = recruitments.length; i < j; i++) {
            trTag = ' <tr class="tr_body">'
                    + '<td><input class="select_inp2" type="checkbox" recruitmentId="' + recruitments[i].id + '"/></td>'
                    + ' <td>' + i + '</td>'
                    + '<td>' + recruitments[i].name + '</td>'
                    + '<td>' + recruitments[i].telephone + '</td>'
                    + '<td>' + recruitments[i].city + '</td>'
                    + '<td>' + recruitments[i].workYear + '</td>'
                    + '<td>' + recruitments[i].openShop + '</td>'
                    + '<td>' + recruitments[i].hopeSalary + '</td>'
                    + '<td>' + recruitments[i].comFocus + '</td>'
                    + '<td>' + recruitments[i].releaseTime + '</td></tr>';
            tabTag.append($(trTag));
        }
    }
</script>

<div class="tab_header">
    <span class="fontStyle_bold cur_pos">你当前的位置：</span>[业务中心]-[招聘列表]
</div>
<div id="tb_body">
    <table class="frame_table" cellspadding=0 cellspacing=0>
        <tr class="tr_header">
            <td>
                <input class="select_inp" type="checkbox"/>
            </td>
            <td>序号</td>
            <td>姓名</td>
            <td>手机号</td>
            <td>所在城市</td>
            <td>工作年限</td>
            <td>是否有开店经验</td>
            <td>期望薪资</td>
            <td>看中公司方面</td>
            <td>发布时间</td>
        </tr>
        <c:forEach var="recruit" items="${recruitment}" varStatus="vst">
            <tr class="tr_body">
                <td><input class="select_inp2" type="checkbox" recruitmentId="${recruit.id}"/></td>
                <td>${vst.index}</td>
                <td>${recruit.name}</td>
                <td>${recruit.telephone}</td>
                <td>${recruit.city}</td>
                <td>${recruit.workYear}</td>
                <td>${recruit.openShop}</td>
                <td>${recruit.hopeSalary}</td>
                <td>${recruit.comFocus}</td>
                <td>${recruit.releaseTime}</td>
            </tr>
        </c:forEach>
    </table>
</div>

<div id="tb_body_footer">
        <span class="footer_text footer_text_align_one">
            <span>共有 ${recruitmentCount} 条记录，当前第 <span id="currentPage">${curPage}</span>/<span id="totalPage">${totalPage}</span> 页</span>
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
