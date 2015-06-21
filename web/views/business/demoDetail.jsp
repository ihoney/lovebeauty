<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>作品详情</title>
    <script type="text/javascript" src="${rootPath}/js/jquery-1.8.3.min.js"></script>
    <script type="text/javascript" src="${rootPath}/js/rootPath.js"></script>
    <link type="text/css" rel="stylesheet" href="${rootPath}/css/demo.css"/>
    <script type="text/javascript" src="${rootPath}/js/jquery-ui-1.10.2.custom.js"></script>
    <link href="${rootPath}/css/jquery-ui-1.10.2.custom.css" type="text/css" rel="stylesheet"/>
    <link href="${rootPath}/css/ui_skin.css" type="text/css" rel="stylesheet"/>
    <link href="${rootPath}/css/bg.css" type="text/css" rel="stylesheet"/>


    <script type="text/javascript">
        function jubaoCustomer(customerId) {
            $("#jubao_div").dialog({
                modal: true,
                width: '500',
                height: '200',
                title: '填写举报原因',
                buttons: {
                    "确认举报": function () {
                        if ($.trim($("#yuanyin").val()) == '') {
                            alert("原因不能为空！");
                            return;
                        }
                        $.ajax({
                            url: getRootPath() + 'customer/jbCustomer.do',
                            type: 'POST',
                            data: {"customerId": customerId, "reason": $.trim($("#yuanyin").val())},
                            success: function (data) {
                                $("#yuanyin").val("");
                                $("#jubao_div").dialog("close");
                            }
                        })
                    },
                    "取 消": function () {
                        $("#yuanyin").val("");
                        $("#jubao_div").dialog("close");
                    }
                }
            });
        }

        function imgLoadError(node) {
            $(node).attr("src", "${rootPath}/images/default.jpg");
        }
    </script>
    <style type="text/css">
        .comment {
            word-wrap: break-word;
            word-break: break-all;
            border-bottom: 1px solid #1873AA;
            margin-top: 10px;
        }

        .attr_title {
            text-align: right;
        }

        .dd_tb {
            border-left: 1px solid #44c8e6;
            border-top: 1px solid #44c8e6;
            font-size: 12px;
        }

        .dd_tb td {
            width: 120px;
            padding: 2px;
            border-right: 1px solid #44c8e6;
            border-bottom: 1px solid #44c8e6;
        }
    </style>
</head>
<body>

<div id="demo_info">
    <div style="float: left; margin-right: 10px;">
        <table class="dd_tb" cellspacing=0 cellpadding=0>
            <tr>
                <td class="attr_title">作品名称：</td>
                <td>${demo.name}</td>
            </tr>
            <tr>
                <td class="attr_title">手艺人：</td>
                <td><a href="${rootPath}/employee/getEmployeeDetail.do?employeeId=${demo.employeeId}">${demo.nickName}</a></td>
            </tr>
            <tr>
                <td class="attr_title">作品类型：</td>
                <td>${demo.demoType} </td>
            </tr>
            <tr>
                <td class="attr_title">作品得分：</td>
                <td>${demo.avgScore} </td>
            </tr>
            <tr>
                <td class="attr_title">价格(元)：</td>
                <td>${demo.price}</td>
            </tr>
            <tr>
                <td class="attr_title">首次优惠价格(元)：</td>
                <td>${demo.PreferentialPrice} </td>
            </tr>
            <tr>
                <td class="attr_title">耗时(分钟)：</td>
                <td>${demo.timeConsuming}</td>
            </tr>
            <tr>
                <td class="attr_title">保持(天)：</td>
                <td>${demo.keepTime} </td>
            </tr>
            <tr>
                <td class="attr_title">可预约时间(点)：</td>
                <td>${demo.booktime} </td>
            </tr>
        </table>

    </div>
    <div>
        <img src="${rootPath}/fileUpload/${demo.fileEName}" class="demo_img"/>
    </div>
</div>
<div style="clear: both;"></div>
<div class="comment" style="width: 90%;">
    <span>作品简介：</span>${demo.description}
</div>
</div>

<div id="jubao_div" style="display: none;">
    <textarea style="border: none;" id="yuanyin" cols="50" rows="4"></textarea>
</div>
</body>
</html>