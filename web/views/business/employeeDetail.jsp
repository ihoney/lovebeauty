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
                <td class="attr_title">手艺人名称：</td>
                <td>${employee.nickName}</td>
            </tr>
            <tr>
                <td class="attr_title">性别：</td>
                <td>${employee.sex} </td>
            </tr>
            <tr>
                <td class="attr_title">专业得分：</td>
                <td>${employee.majorScore} </td>
            </tr>
            <tr>
                <td class="attr_title">沟通得分：</td>
                <td>${employee.comScore} </td>
            </tr>
            <tr>
                <td class="attr_title">守时得分：</td>
                <td>${employee.punctualScore}</td>
            </tr>
            <tr>
                <td class="attr_title">均价(元)：</td>
                <td>${employee.avgPrice} </td>
            </tr>
            <tr>
                <td class="attr_title">在职状态：</td>
                <td>${employee.state}</td>
            </tr>
            <tr>
                <td class="attr_title">保持(天)：</td>
                <td>${employee.keepTime} </td>
            </tr>
        </table>

    </div>
    <div>
        <img src="${rootPath}/fileUpload/${employee.headImg}" class="demo_img"/>
    </div>
</div>
<div style="clear: both;"></div>
<div class="comment" style="width: 90%;">
    <span>服务范围：</span>${employee.serverScope}
</div>
<div id="comment">
    <div class="custom_comment" style="">客户评论</div>
    <table id="comment_tb">
        <c:forEach items="${comments}" var="comment">
            <tr>
                <td>
                    <div>
                        <span>客户：${comment.account}</span>
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