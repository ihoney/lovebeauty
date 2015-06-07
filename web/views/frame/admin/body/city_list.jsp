<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}"/>

<script type="text/javascript" src="${rootPath}/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="${rootPath}/js/rootPath.js"></script>
<script type="text/javascript" src="${rootPath}/js/common_operation.js"></script>
<script type="text/javascript" src="${rootPath}/js/jquery-ui-1.10.2.custom.js"></script>
<script type="text/javascript" src="${rootPath}/js/DateFormat.js"></script>
<link href="${rootPath}/css/frame_table.css" type="text/css" rel="stylesheet"/>
<link href="${rootPath}/css/jquery-ui-1.10.2.custom.css" type="text/css" rel="stylesheet"/>
<link href="${rootPath}/css/ui_skin.css" type="text/css" rel="stylesheet"/>
<link href="${rootPath}/css/love.form.css" type="text/css" rel="stylesheet"/>

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

function change_city_serviceScope(node) {
    var cityId = $(node).attr("cityId");
    var serviceScope = $(node).parent("td").prev("td").text();
    $("#up_serverScope").val(serviceScope);
    $("#chg_city_div").dialog({
        modal: true,
        width: '480',
        height: '230',
        resizable: false,
        title: '修改城市服务范围',
        zIndex: 5,
        buttons: {
            "保 存": function () {
                chgCityServiceScope(cityId);
                $(this).dialog("close");
            },
            "取 消": function () {
                $(this).dialog("close");
            }
        }
    });
}

function chgCityServiceScope(cityId) {
    var url = getRootPath() + "admin/updateCityServiceScope.do";
    var serviceScope = $.trim($("#up_serviceScope").val());
    if (serviceScope == "") {
        alert("服务范围不能为空！");
        return;
    }
    $.ajax({
        url: url,
        type: "POST",
        data: {"cityId": cityId, "serviceScope": serviceScope},
        success: function (data) {
            location.reload();
        },
        error: function () {
            alert("error");
        }
    })
}

function city_reuse(node) {
    var cityId = $(node).attr("cityId");
    var url = getRootPath() + "admin/changeCityState.do";
    $.ajax({
        url: url,
        type: "POST",
        data: {"cityId": cityId, "state": "开通"},
        success: function () {
            $(node).parent("td").prev("td").prev("td").text("开通");
            $(node).parent("td").prev("td").prev("td").prev("td").prev("td").text(curDateTime);
            $(node).parent("td").prev("td").prev("td").prev("td").text("");
            $(node).attr("onclick", "city_stop(this);").text("停用");
        }
    })
}

function city_stop(node) {
    var cityId = $(node).attr("cityId");
    var url = getRootPath() + "admin/changeCityState.do";
    $.ajax({
        url: url,
        type: "POST",
        data: {"cityId": cityId, "state": "停用"},
        success: function () {
            $(node).parent("td").prev("td").prev("td").text("停用");
            $(node).parent("td").prev("td").prev("td").prev("td").text(curDateTime);
            $(node).parent("td").prev("td").prev("td").prev("td").prev("td").text("");
            $(node).attr("onclick", "city_reuse(this);").text("启用");
        }
    })
}

//第一页
function firstPage() {
    if ($("#currentPage").text() != "1") {
        var url = "queryCityByPage.do";
        pageing(1, url, page_callback);
    }
}

//上一页
function prePage() {
    if ($("#currentPage").text() != "1") {
        var curPage = parseInt($("#currentPage").text()) - 1;
        var url = "queryCityByPage.do";
        pageing(curPage, url, page_callback);
    }
}
//下一页
function nextPage() {
    if ($("#currentPage").text() != $("#totalPage").text()) {
        var curPage = parseInt($("#currentPage").text()) + 1;
        var url = "queryCityByPage.do";
        pageing(curPage, url, page_callback);
    }
}

//最后一页
function lastPage() {
    if ($("#currentPage").text() != $("#totalPage").text()) {
        var lastPage = $("#totalPage").text();
        var url = "queryCityByPage.do";
        pageing(lastPage, url, page_callback);
    }
}

//跳转到第几页
function btnGo() {
    var go_page = $("#go_page").val();
    var totalPage = $("#totalPage").text();
    if (go_page != undefined && $.trim(go_page) != "" && totalPage != "") {
        if ((parseInt(go_page) <= parseInt(totalPage)) && parseInt(go_page) > 0) {
            var url = "queryCityByPage.do";
            pageing(go_page, url, page_callback);
        }
    }
}

function page_callback(data) {
    $(".frame_table tr:gt(0)").remove();
    var tabTag = $(".frame_table");
    var cities = data.cities;
    var trTag;
    for (var i = 0, j = cities.length; i < j; i++) {
        trTag = ' <tr class="tr_header">'
                + '<td><input class="select_inp2" type="checkbox" cityId="' + cities[i].id + '"/></td>'
                + ' <td>' + i + '</td>'
                + '<td>' + cities[i].name + '</td>'
                + '<td>' + cities[i].opentime + '</td>'
                + '<td>' + cities[i].stoptime + '</td>'
                + '<td>' + cities[i].state + '</td>'
                + '<td>' + cities[i].serviceScope + '</td>';
        if (cities[i].state == '停用') {
            trTag += ' <td> <a href="javascript:void(0);" cityId="' + cities[i].id + '" onclick="city_reuse(this);">开通</a></td>';
        } else {
            trTag += '<td>  <a href="javascript:void(0);" cityId="' + cities[i].id + '" onclick="city_stop(this);">停用</a></td>'
        }
        trTag += '</tr>';
        $(trTag).appendTo(tabTag);
    }
}
</script>
<div class="tab_header">
    <span class="fontStyle_bold cur_pos">你当前的位置：</span>[业务中心]-[开通城市列表]
</div>
<div id="tb_body">
    <table class="frame_table" cellspadding=0 cellspacing=0>
        <tr class="tr_header">
            <td><input class="select_inp" type="checkbox"/></td>
            <td>序号</td>
            <td>城市</td>
            <td>开通时间</td>
            <td>停用时间</td>
            <td>状态</td>
            <td>服务范围</td>
            <td>操作</td>
        </tr>
        <c:forEach var="city" items="${cities}" varStatus="vst">
            <tr class="tr_body">
                <td><input class="select_inp2" type="checkbox" cityId="${city.id}"/></td>
                <td>${vst.index}</td>
                <td>${city.name}</td>
                <td>${city.opentime}</td>
                <td>${city.stoptime}</td>
                <td>${city.state}</td>
                <td>${city.serviceScope}</td>
                <td>
                    <c:if test="${city.state == '停用'}">
                        <a href="javascript:void(0);" cityId="${city.id}" onclick="city_reuse(this);">启用</a>
                    </c:if>
                    <c:if test="${city.state == '开通'}">
                        <a href="javascript:void(0);" cityId="${city.id}" onclick="city_stop(this);">停用</a>
                    </c:if>

                    <a href="javascript:void(0);" cityId="${city.id}" onclick="change_city_serviceScope(this);">编辑</a>

                </td>
            </tr>
        </c:forEach>
    </table>
</div>

<div id="tb_body_footer">
        <span class="footer_text footer_text_align_one">
            <span>共有 ${cityCount} 条记录，当前第 <span id="currentPage">${curPage}</span>/<span id="totalPage">${totalPage}</span> 页</span>
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

<div id="chg_city_div" style="display: none">
    <div style="margin-top: 5px; margin-left: 5px;">
        <table class="city_tab">
            <tr>
                <td class="city_td_attr_title">服务范围：</td>
                <td><textarea id="up_serviceScope" rows="6" cols="40" style="border: none;"></textarea></td>
            </tr>
        </table>
    </div>
</div>