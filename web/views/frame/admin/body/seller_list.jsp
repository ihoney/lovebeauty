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

    function seller_check(node) {
        $.ajax({
            url: getRootPath() + 'seller/getValidateInfo.do',
            type: 'get',
            async: false,
            data: {"sellerId": $(node).attr("sellerId")},
            success: function (data) {
                var validateInfo = data.validateInfo;
                $("#name").text(validateInfo.name);
                $("#sex").text(validateInfo.sex);
                $("#address").text(validateInfo.address);
                $("#alipay_key").text(validateInfo.alipay_key);
                $("#alipay_pid").text(validateInfo.alipay_pid);
                $("#birthday").text(validateInfo.birthday);
                $("#email").text(validateInfo.email);
                $("#head_img").attr("src", getRootPath() + "fileUpload/" + validateInfo.head_img);
                $("#email").text(validateInfo.email);
                $("#identify").text(validateInfo.identify);
                $("#identify_img").attr("src", getRootPath() + "fileUpload/" + validateInfo.identify_img);
                $("#payaccount").text(validateInfo.payaccount);
                $("#servicescope").text(validateInfo.servicescope);
                $("#shopname").text(validateInfo.shopname);
                $("#telephone").text(validateInfo.telephone);
            }
        });

        $("#checkSeller_div").dialog({
            modal: true,
            width: '700',
            height: '460',
            title: '审核商铺',
            zIndex: 5,
            buttons: {
                "确 定": function () {
                    checkPassed(node);
                    $(this).dialog("close");
                },
                "取 消": function () {
                    $(this).dialog("close");
                }
            }
        });
    }

    function seller_forbidden(node) {
        if (!window.confirm("确认禁用！")) {
            return;
        }
        var sellerId = $(node).attr("sellerId");
        var url = getRootPath() + "seller/forbiddenSeller.do?sellerId=" + sellerId;
        $.ajax({
            url: url,
            type: "GET",
            dataType: "json",
            success: function (data) {
                if (data.reqResult) {
                    $(node).parent("td").siblings(".forbidden_cls").text("是");
                    $(node).attr("onclick", "seller_reUse(this)").text("启用");
                } else {
                    alert("服务器出现故障！");
                }
            }
        });
    }

    function seller_reUse(node) {
        if (!window.confirm("确认启用！")) {
            return;
        }
        var sellerId = $(node).attr("sellerId");
        var url = getRootPath() + "seller/reUseSeller.do?sellerId=" + sellerId;
        $.ajax({
            url: url,
            type: "GET",
            dataType: "json",
            success: function (data) {
                if (data.reqResult) {
                    $(node).parent("td").siblings(".forbidden_cls").text("否");
                    $(node).attr("onclick", "seller_forbidden(this)").text("禁用");
                } else {
                    alert("服务器出现故障！");
                }
            }
        });
    }

    function seller_delete(node) {
        var sellerId = $(node).attr("sellerId");
        var url = getRootPath() + "seller/deleteSeller.do?sellerId=" + sellerId;
        deleteRecord(node, url);
    }

    //第一页
    function firstPage() {
        if ($("#currentPage").text() != "1") {
            var url = "querySellerByPage.do";
            pageing(1, url, page_callback);
        }
    }

    //上一页
    function prePage() {
        if ($("#currentPage").text() != "1") {
            var curPage = parseInt($("#currentPage").text()) - 1;
            var url = "querySellerByPage.do";
            pageing(curPage, url, page_callback);
        }
    }
    //下一页
    function nextPage() {
        if ($("#currentPage").text() != $("#totalPage").text()) {
            var curPage = parseInt($("#currentPage").text()) + 1;
            var url = "querySellerByPage.do";
            pageing(curPage, url, page_callback);
        }
    }

    //最后一页
    function lastPage() {
        if ($("#currentPage").text() != $("#totalPage").text()) {
            var lastPage = $("#totalPage").text();
            var url = "querySellerByPage.do";
            pageing(lastPage, url, page_callback);
        }
    }

    //跳转到第几页
    function btnGo() {
        var go_page = $("#go_page").val();
        var totalPage = $("#totalPage").text();
        if (go_page != undefined && $.trim(go_page) != "" && totalPage != "") {
            if ((parseInt(go_page) <= parseInt(totalPage)) && parseInt(go_page) > 0) {
                var url = "querySellerByPage.do";
                pageing(go_page, url, page_callback);
            }
        }
    }

    function page_callback(data) {
        $(".frame_table tr:gt(0)").remove();
        var tabTag = $(".frame_table");
        var sellers = data.sellers;
        var trTag;
        var checkTmp;
        var forbiddenTmp;
        for (var i = 0, j = sellers.length; i < j; i++) {
            trTag = ' <tr class="tr_body">'
                    + '<td><input class="select_inp2" type="checkbox" sellerId="' + sellers[i].id + '"/></td>'
                    + ' <td>' + i + '</td>'
                    + '<td>' + sellers[i].account + '</td>'
                    + '<td>' + sellers[i].regip + '</td>'
                    + '<td>' + sellers[i].regtime + '</td>'
                    + '<td>' + sellers[i].loginip + '</td>'
                    + '<td>' + sellers[i].logintime + '</td>'
                    + '<td>' + sellers[i].checked + '</td>'
                    + '<td>' + sellers[i].checkedtime + '</td>'
                    + '<td class="forbidden_cls">' + sellers[i].forbidden + '</td>'
                    + '<td>' + sellers[i].jubao + '</td>'
                    + '<td>';
            if (sellers[i].checked == '否') {
                trTag += '  <a href="javascript:void(0);" sellerId="' + sellers[i].id + '" onclick="seller_check(this);">审核</a>';
            }
            if (sellers[i].forbidden == '否') {
                forbiddenTmp = '<a href="javascript:void(0);" sellerId="' + sellers[i].id + '" onclick="seller_forbidden(this);">禁用</a>';
            } else {
                forbiddenTmp = '<a href="javascript:void(0);" sellerId="' + sellers[i].id + '" onclick="seller_reUse(this);">启用</a>';
            }
            trTag += forbiddenTmp;
            trTag += '<a href="javascript:void(0);" sellerId="' + sellers[i].id + '" onclick="seller_delete(this);">删除</a>';
            trTag += '<a href="${rootPath}/seller/getSellerDetail.do?sellerId=' + sellers[i].id + '">详情</a>'
                    + ' </td>'
                    + '</tr>';

            $(trTag).appendTo(tabTag);
        }
    }
</script>

<div class="tab_header">
    <span class="fontStyle_bold cur_pos">你当前的位置：</span>[业务中心]-[商铺列表]
</div>
<div id="tb_body">
    <table class="frame_table" cellspadding=0 cellspacing=0>
        <tr class="tr_header">
            <td><input class="select_inp" type="checkbox"/></td>
            <td>序号</td>
            <td>账号</td>
            <td>注册IP</td>
            <td>注册时间</td>
            <td>登录IP</td>
            <td>登录时间</td>
            <td>是否审核</td>
            <td>审核时间</td>
            <td>禁用</td>
            <td>举报次数</td>
            <td>操作</td>
        </tr>
        <c:forEach var="seller" items="${sellers}" varStatus="vst">
            <tr class="tr_body">
                <td><input class="select_inp2" type="checkbox" sellerId="${seller.id}"/></td>
                <td>${vst.index}</td>
                <td>${seller.account}</td>
                <td>${seller.regIp}</td>
                <td>${seller.regTime}</td>
                <td>${seller.loginIp}</td>
                <td>${seller.loginTime}</td>
                <td class="checked">${seller.checked}</td>
                <td class="checkedTime">${seller.checkedTime}</td>
                <td class="forbidden_cls">${seller.forbidden}</td>
                <td>${seller.jubao}</td>
                <td>
                    <c:if test="${seller.checked == '否'}">
                        <a href="javascript:void(0);" sellerId="${seller.id}" onclick="seller_check(this);">审核</a>
                    </c:if>
                    <c:if test="${seller.forbidden == '否'}">
                        <a href="javascript:void(0);" sellerId="${seller.id}" onclick="seller_forbidden(this);">禁用</a>
                    </c:if>
                    <c:if test="${seller.forbidden == '是'}">
                        <a href="javascript:void(0);" sellerId="${seller.id}" onclick="seller_reUse(this);">启用</a>
                    </c:if>

                    <a href="${rootPath}/seller/getSellerDetail.do?sellerId=${seller.id}">详情</a>
                    <a href="javascript:void(0);" sellerId="${seller.id}" onclick="seller_delete(this);">删除</a>
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
            <button class="footer_text_two" onclick="btnGo();">跳转</button>
        </span>
</div>
<div id="checkSeller_div">
    <div style="border: 1px;margin-right: 30px;">
        <table>
            <tr>
                <td class="td_att_name">名称：</td>
                <td><span id="name"></span></td>
                <td class="td_att_name">性别：</td>
                <td><span id="sex"></span></td>
                <td rowspan="6">
                    <img id="head_img" src="" width="120" height="150"/>
                </td>
            </tr>
            <tr>
                <td class="td_att_name">出生日期：</td>
                <td><span id="birthday"></span></td>
                <td class="td_att_name">电子邮件：</td>
                <td><span id="email"></span></td>
            </tr>
            <tr>
                <td class="td_att_name">手机号：</td>
                <td><span id="telephone"></span></td>
                <td class="td_att_name">身份证号：</td>
                <td><span id="identify"></span></td>
            </tr>
            <tr>
                <td class="td_att_name">店铺名称：</td>
                <td><span id="shopname"></span></td>
                <td class="td_att_name">店铺地址：</td>
                <td><span id="address"></span></td>
            </tr>
            <tr>
                <td class="td_att_name">支付宝账号：</td>
                <td><span id="payaccount"></span></td>
                <td class="td_att_name">支付宝秘钥：</td>
                <td><span id="alipay_key"></span></td>
            </tr>
            <tr>
                <td class="td_att_name">支付宝PID：</td>
                <td colspan="4"><span id="alipay_pid"></span></td>

            </tr>
            <tr>
                <td class="td_att_name" style="vertical-align: text-top">服务范围：</td>
                <td colspan="2" style="vertical-align: text-top"><span id="servicescope"></span></td>
                <td colspan="3">
                    <img id="identify_img" src="" width="300" height="200"/>
                </td>
            </tr>
        </table>
    </div>
</div>