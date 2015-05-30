<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}"/>

<script type="text/javascript" src="${rootPath}/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="${rootPath}/js/rootPath.js"></script>
<script type="text/javascript" src="${rootPath}/js/frame_table.js"></script>
<script type="text/javascript" src="${rootPath}/js/paging.js"></script>
<script type="text/javascript" src="${rootPath}/js/jquery-ui-1.10.2.custom.js"></script>
<link href="${rootPath}/css/frame_table.css" type="text/css" rel="stylesheet"/>
<link href="${rootPath}/css/jquery-ui-1.10.2.custom.css" type="text/css" rel="stylesheet"/>
<link href="${rootPath}/css/ui_skin.css" type="text/css" rel="stylesheet"/>

<link type="text/css" rel="stylesheet" href="${rootPath}/css/colorbox.css"/>
<script type="text/javascript" src="${rootPath}/js/jquery.colorbox.js"></script>


<script type="text/javascript">
    $(function () {
        $(".select_inp").click(function () {
            if ($(this).attr("checked") == "checked") {
                $(":checkbox").attr("checked", $(this).attr("checked"));
            } else {
                $(":checkbox").removeAttr("checked");
            }
        });

        $(".btn_edit_img").click(function () {
            if ($(".select_inp2:checked").size() == 0) {
                alert("请选中要编辑的记录！");
                return;
            } else if ($(".select_inp2:checked").size() > 1) {
                alert("只能编辑一条记录！");
                return;
            }
        });
        $(".btn_del_img").click(function () {
            if ($(".select_inp2:checked").size() == 0) {
                alert("请选中要删除的记录！");
                return;
            }
        });

        $(".btn_add_img").click(function () {
            location.href = getRootPath() + "demo/addInit.do";
        });

        $("#firstPage").click(function () {
            if ($("#currentPage").text() != "1") {
                var url = "queryDemoByPage.do";
                pageing(1, url, page_callback);
            }
        });

        $("#prePage").click(function () {
            if ($("#currentPage").text() != "1") {
                var curPage = parseInt($("#currentPage").text()) - 1;
                var url = "queryDemoByPage.do";
                pageing(curPage, url, page_callback);
            }
        });
        $("#nextPage").click(function () {
            if ($("#currentPage").text() != $("#totalPage").text()) {
                var curPage = parseInt($("#currentPage").text()) + 1;
                var url = "queryDemoByPage.do";
                pageing(curPage, url, page_callback);
            }

        });
        $("#lastPage").click(function () {
            if ($("#currentPage").text() != $("#totalPage").text()) {
                var lastPage = $("#totalPage").text();
                var url = "queryDemoByPage.do";
                pageing(lastPage, url, page_callback);
            }
        });

        $(".btn_go").click(function () {
            var go_page = $("#go_page").val();
            var totalPage = $("#totalPage").text();
            if (go_page != undefined && $.trim(go_page) != "" && totalPage != "") {
                if ((parseInt(go_page) <= parseInt(totalPage)) && parseInt(go_page) > 0) {
                    var url = "queryDemoByPage.do";
                    pageing(go_page, url, page_callback);
                }
            }
        });

        $(".tr_body:odd").css("background-color", "#D9EFFD");

        $(".tr_body").click(function () {
            if ($(this).find(".select_inp2").attr("checked") == undefined) {
                $(this).find(".select_inp2").attr("checked", "checked");
            } else {
                $(this).find(".select_inp2").removeAttr("checked");
            }
        })

        $(".img_class_a").colorbox({rel: 'img_class_a', photo: true});
    });

    function page_callback(data) {
        $("#tb_body").empty();
        var tabTag = $("#tb_body");
        var demos = data.demos;
        var demoTmp;

        var divTag;
        for (var i = 0, j = demos.length; i < j; i++) {
            demoTmp = demos[i];
            divTag = '<div class="demo_cell" demo_id="' + demoTmp.id + '"> '
                    + '<div class="demo_img">'
                    + '<a href="${rootPath}/fileUpload/' + demoTmp.fileEName + '" class="img_class_a">'
                    + '<img class="img_class" src="${rootPath}/fileUpload/' + demoTmp.fileEName + '"/>   '
                    + '</a>'
                    + '</div>'
                    + '<div class="desc_ps">'
                    + '<div>'
                    + '<span class="attr_value">'
                    + demoTmp.demoType
                    + '：' + demoTmp.name
                    + '</span>'
                    + '</div>'
                    + '<div class="school_attr">'
                    + '<span class="attr_title">价格</span> <span class="attr_value">' + demoTmp.price + '</span>'
                    + '</div>'
                    + '<div class="school_attr">'
                    + '<span class="attr_title">优惠价格</span> <span class="attr_value">' + demoTmp.PreferentialPrice + '</span>'
                    + '</div>'
                    + '</div>'
                    + '</div>';
            tabTag.append($(divTag));
        }
        $(".img_class_a").colorbox({rel: 'img_class_a', photo: true});
    }
</script>

<style type="text/css">

    .demo_cell {
        width: 15%;
        height: 275px;
        border: 1px solid #E8E8E8;
        float: left;
        margin-right: 10px;
        margin-top: 10px;
        cursor: pointer;
    }

    .demo_cell:hover {
        border: 1px solid #9ECDDF;
    }

    .demo_img {
        height: 200px;
        margin: 5px auto;
    }

    .img_class {
        width: 100%;
        height: 100%;
    }

    .attr_title {
        color: #516A88;
        font-size: 13px;
        font-weight: bold;
    }

    .attr_value {
        color: #516A88;
        font-size: 12px;
    }

    .school_attr {
        margin-top: 5px;
    }

    .desc_ps {
        margin-top: 3px;
        border-top: 1px solid #E8E8E8;
        padding-top: 3px;
        padding-left: 10px;
    }
</style>

<div class="tab_header">
    <span class="fontStyle_bold cur_pos">你当前的位置：</span>[业务中心]-[作品列表]
</div>
<div id="tb_body">
    <c:forEach items="${demos}" var="demo">
        <div class="demo_cell" demo_id="${demo.id}">
            <div class="demo_img">
                <a href="${rootPath}/fileUpload/${demo.fileEName}" class="img_class_a">
                    <img class="img_class" src="${rootPath}/fileUpload/${demo.fileEName}"/>
                </a>
            </div>
            <div class="desc_ps">
                <div>
                    <span class="attr_value">${demo.demoType}</span>
                </div>
                <div class="school_attr">
                    <span class="attr_title">价格</span> <span class="attr_value">${demo.price}</span>
                </div>

                <div class="school_attr">
                    <span class="attr_title">优惠价格</span> <span class="attr_value">${demo.PreferentialPrice}</span>
                </div>
            </div>
        </div>
    </c:forEach>
</div>

<div id="tb_body_footer">
        <span class="footer_text footer_text_align_one">
            <span>共有 ${demoCount} 条记录，当前第 <span id="currentPage">${curPage}</span>/<span id="totalPage">${totalPage}</span> 页</span>
        </span>
        <span class="footer_text_align_two">
            <button class="footer_text_two" id="firstPage">首页</button>
            <button class="footer_text_two" id="prePage">上一页</button>
            <button class="footer_text_two" id="nextPage">下一页</button>
            <button class="footer_text_two" id="lastPage">尾页</button>
            转到第<input id="go_page" type="text" size="8"/>页
            <button class="footer_text_two btn_go">跳转</button>
        </span>
</div>