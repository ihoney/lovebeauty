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
        $(".tr_body:odd").css("background-color", "#D9EFFD");

        $(".tr_body").click(function () {
            if ($(this).find(".select_inp2").attr("checked") == undefined) {
                $(this).find(".select_inp2").attr("checked", "checked");
            } else {
                $(this).find(".select_inp2").removeAttr("checked");
            }
        })
    });

    function city_add() {
        $("#add_city_div").dialog({
            modal: true,
            width: '200',
            height: '160',
            title: '添加城市',
            zIndex: 5,
            buttons: {
                "添 加": function () {
                    addCity();
                    $(this).dialog("close");
                },
                "取 消": function () {
                    $(this).dialog("close");
                }
            }
        });
    }

    function addCity() {
        var url = getRootPath() + "admin/addCity.do";
        var cityName = $("#city").val();
        $.ajax({
            url: url,
            type: "POST",
            data: {"cityName": cityName},
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
            data: {"cityId": cityId, "state": "1"},
            success: function () {
                $(node).parent("td").prev("td").text("已启用");
                $(node).parent("td").prev("td").prev("td").prev("td").text(curDateTime);
                $(node).parent("td").prev("td").prev("td").text("");
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
            data: {"cityId": cityId, "state": "0"},
            success: function () {
                $(node).parent("td").prev("td").text("已停用");
                $(node).parent("td").prev("td").prev("td").text(curDateTime);
                $(node).parent("td").prev("td").prev("td").prev("td").text("");
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
        var stoptime;
        for (var i = 0, j = cities.length; i < j; i++) {
            stoptime = cities[i].stoptime;
            if (stoptime == null) {
                stoptime = "";
            }
            trTag = ' <tr>'
                    + '<td><input class="select_inp2" type="checkbox" cityId="' + cities[i].id + '"/></td>'
                    + ' <td>' + i + '</td>'
                    + '<td>' + cities[i].name + '</td>'
                    + '<td>' + cities[i].opentime + '</td>'
                    + '<td>' + stoptime + '</td>'

            if (cities[i].state == '1') {
                trTag += '<td>已启用</td>';
            } else {
                trTag += "<td>已停用</td>";
            }
            if (cities[i].state == '0') {
                trTag += ' <td> <a href="javascript:void(0);" cityId="' + cities[i].id + '" onclick="city_reuse(this);">启用</a></td>';
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
      <span class="btn_pos">
        <span class="btn_bg_img btn_add_img" onclick="city_add();">添加</span>
    </span>
</div>
<div id="tb_body">
    <table class="frame_table" cellspadding=0 cellspacing=0>
        <tr class="tr_header">
            <td class="td_class_1"><input class="select_inp" type="checkbox"/></td>
            <td class="td_class_2">序号</td>
            <td class="td_class_3">城市</td>
            <td class="td_class_150">启用时间</td>
            <td class="td_class_150">停用时间</td>
            <td class="td_class_5">状态</td>
            <td class="td_class_150">操作</td>
        </tr>
        <c:forEach var="city" items="${cities}" varStatus="vst">
            <tr class="tr_body">
                <td><input class="select_inp2" type="checkbox" cityId="${city.id}"/></td>
                <td>${vst.index}</td>
                <td>${city.name}</td>
                <td>${city.opentime}</td>
                <td>${city.stoptime}</td>
                <td>
                    <c:if test="${city.state == 'false'}">
                        已停用
                    </c:if>
                    <c:if test="${city.state == 'true'}">
                        已启用
                    </c:if>
                </td>
                <td>
                    <c:if test="${city.state == 'false'}">
                        <a href="javascript:void(0);" cityId="${city.id}" onclick="city_reuse(this);">启用</a>
                    </c:if>
                    <c:if test="${city.state == 'true'}">
                        <a href="javascript:void(0);" cityId="${city.id}" onclick="city_stop(this);">停用</a>
                    </c:if>
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
            <button class="footer_text_two" onclick="btnGo();">&nbsp;</button>
        </span>
</div>
<div id="add_city_div">
    <div style="margin-top: 20px; margin-left: 20px;">
        <select id="city" style="width: 120px;">
            <option>北京</option>
            <option>上海</option>
            <option>天津</option>
            <option>重庆</option>
            <option>青岛</option>
        </select>
    </div>
</div>