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
        }

        .attr_title {
            color: #033D61;
        }
    </style>
</head>
<body>
<div id="jubao_div" style="display: none;">
    <textarea id="yuanyin" cols="50" rows="4"></textarea>
</div>
<div id="demo_info">
    <img src="${rootPath}/fileUpload/${demo.fileEName}" class="demo_img"/>
    <span>作品名称：</span>${demo.name} <br/>
    <span>得分：</span>${demo.avgScore} <br/>
    <span> 价格：</span>${demo.price} <br/>
    <span>首次优惠价格：</span>${demo.PreferentialPrice} <br/>
    <span>可预约时间：</span>${demo.booktime} <br/>
    <span>作品简介：</span>${demo.description}
</div>
<div id="comment">
    <div class="custom_comment" style="">客户评论</div>
    <table id="comment_tb">
        <c:forEach items="${comments}" var="comment">
            <tr>
                <td>
                    <div>
                        <span>客户：${comment.account} 评分：${comment.score}</span>
                        <span style="float: right;margin-right: 10px;"><a href="javascript:void(0);" style="font-size: 12px;" onclick="jubaoCustomer(${comment.customerid})">举报买家</a></span>
                    </div>
                    <div class="comment">
                        评论：${comment.comment}
                    </div>
                </td>
            </tr>
        </c:forEach>

    </table>
</div>
</div>
</body>
</html>