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
<link type="text/css" rel="stylesheet" href="${rootPath}/css/colorbox.css"/>
<script type="text/javascript" src="${rootPath}/js/jquery.colorbox.js"></script>

<script type="text/javascript">
    $(function () {
        $(".tr_body").click(function () {
            if ($(this).find(".select_inp2").attr("checked") == undefined) {
                $(this).find(".select_inp2").attr("checked", "checked");
            } else {
                $(this).find(".select_inp2").removeAttr("checked");
            }
        })

        $(".img_class_a").colorbox({rel: 'img_class_a', photo: true});
    });

    function ad_add() {
        location.href = getRootPath() + "ad/addAdInit.do";
    }

    function ad_reuse(node) {
        var adId = $(node).attr("adId");
        var url = getRootPath() + "ad/changeAdState.do";
        $.ajax({
            url: url,
            type: "POST",
            data: {"adId": adId, "state": "启用"},
            success: function () {
                $(node).parent("td").prev("td").prev("td").text("使用中");
                $(node).attr("onclick", "ad_stop(this)").text("停用");
            }
        })
    }

    function ad_stop(node) {
        var adId = $(node).attr("adId");
        var url = getRootPath() + "ad/changeAdState.do";
        $.ajax({
            url: url,
            type: "POST",
            data: {"adId": adId, "state": "停用"},
            success: function () {
                $(node).parent("td").prev("td").prev("td").text("停用");
                $(node).attr("onclick", "ad_reuse(this)").text("启用");
            }
        })
    }


    //编辑作品
    function ad_edit(node) {
        var adId = $(node).attr("adId");
        location.href = getRootPath() + "ad/editAd.do?adId=" + adId;
    }

    //删除作品
    function ad_delete(node) {
        var adId = $(node).attr("adId");
        var url = getRootPath() + "ad/deleteAd.do?adId=" + adId + "&fileName=" + $(node).attr("fileName");
        deleteRecord(node, url);
    }

    //第一页
    function firstPage() {
        if ($("#currentPage").text() != "1") {
            var url = "queryAdByPage.do";
            pageing(1, url, page_callback);
        }
    }

    //上一页
    function prePage() {
        if ($("#currentPage").text() != "1") {
            var curPage = parseInt($("#currentPage").text()) - 1;
            var url = "queryAdByPage.do";
            pageing(curPage, url, page_callback);
        }
    }
    //下一页
    function nextPage() {
        if ($("#currentPage").text() != $("#totalPage").text()) {
            var curPage = parseInt($("#currentPage").text()) + 1;
            var url = "queryAdByPage.do";
            pageing(curPage, url, page_callback);
        }
    }

    //最后一页
    function lastPage() {
        if ($("#currentPage").text() != $("#totalPage").text()) {
            var lastPage = $("#totalPage").text();
            var url = "queryAdByPage.do";
            pageing(lastPage, url, page_callback);
        }
    }

    //跳转到第几页
    function btnGo() {
        var go_page = $("#go_page").val();
        var totalPage = $("#totalPage").text();
        if (go_page != undefined && $.trim(go_page) != "" && totalPage != "") {
            if ((parseInt(go_page) <= parseInt(totalPage)) && parseInt(go_page) > 0) {
                var url = "queryAdByPage.do";
                pageing(go_page, url, page_callback);
            }
        }
    }

    function page_callback(data) {
        $(".frame_table tr:gt(0)").remove();
        var tabTag = $(".frame_table");
        var ads = data.ads;
        var trTag;
        var stateTmp;
        for (var i = 0, j = ads.length; i < j; i++) {
            trTag = ' <tr class="tr_body">'
                    + '<td><input class="select_inp2" type="checkbox" adId="' + ads[i].id + '"/></td>'
                    + ' <td>' + i + '</td>'
                    + '<td>' + ads[i].type + '</td>'
                    + '<td>' + ads[i].url + '</td>'
                    + '<td>' + ads[i].state + '</td><td>';
            if (ads[i].state == '停用') {
                stateTmp = '<a href="javascript:void(0);" adId="' + ads[i].id + '" onclick="ad_reuse(this);">启用</a>';
            } else {
                stateTmp = '<a href="javascript:void(0);" adId="' + ads[i].id + '" onclick="ad_stop(this);">停用</a>';
            }
            trTag += '<a href="${rootPath}/fileUpload/' + ads[i].picName + '" class="img_class_a">预览</a>';
            trTag += stateTmp;
            trTag += '<a href="javascript:void(0);" adId="' + ads[i].id + '" onclick="ad_edit(this);">编辑</a>';
            trTag += '<a href="javascript:void(0);" adId="' + ads[i].id + '" fileName="' + ads[i].picName + '" onclick="ad_delete(this);">删除</a>';
            trTag += '</td></tr>';
            $(trTag).appendTo(tabTag);
        }

        $(".img_class_a").colorbox({rel: 'img_class_a', photo: true});
    }
</script>

<div class="tab_header">
    <span class="fontStyle_bold cur_pos">你当前的位置：</span>[业务中心]-[广告管理]
      <span class="btn_pos">
        <span class="btn_bg_img btn_add_img" onclick="ad_add();">添加</span>
    </span>
</div>
<div id="tb_body">
    <table class="frame_table" cellspadding=0 cellspacing=0>
        <tr class="tr_header">
            <td><input class="select_inp" type="checkbox"/></td>
            <td>序号</td>
            <td>广告类型</td>
            <td>链接地址</td>
            <td>广告状态</td>
            <td>备注</td>
            <td>操作</td>
        </tr>
        <c:forEach var="ad" items="${ads}" varStatus="vst">
            <tr class="tr_body">
                <td><input class="select_inp2" type="checkbox" adId="${ad.id}"/></td>
                <td>${vst.index}</td>
                <td>${ad.type}</td>
                <td>${ad.url}</td>
                <td>${ad.state}</td>
                <td>${ad.backup}</td>
                <td>
                    <a href="${rootPath}/fileUpload/${ad.picName}" class="img_class_a">预览</a>
                    <c:if test="${ad.state == '停用'}">
                        <a href="javascript:void(0);" adId="${ad.id}" onclick="ad_reuse(this);">启用</a>
                    </c:if>
                    <c:if test="${ad.state == '启用'}">
                        <a href="javascript:void(0);" adId="${ad.id}" onclick="ad_stop(this);">停用</a>
                    </c:if>
                    <a href="javascript:void(0);" adId="${ad.id}" onclick="ad_edit(this);">编辑</a>
                    <a href="javascript:void(0);" adId="${ad.id}" fileName="${ad.picName}" onclick="ad_delete(this);">删除</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>

<div id="tb_body_footer">
        <span class="footer_text footer_text_align_one">
            <span>共有 ${adCount} 条记录，当前第 <span id="currentPage">${curPage}</span>/<span id="totalPage">${totalPage}</span> 页</span>
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