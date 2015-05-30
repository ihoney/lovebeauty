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
    </style>
</head>
<body>

<div id="demo_info">
    <img src="${rootPath}/fileUpload/${employee.headImg}" class="demo_img"/>
    <span>名称：</span>${employee.name} <br/>
    <span>性别：</span>${employee.sex} <br/>
    <span>专业得分：</span>${employee.majorScore} <br/>
    <span>沟通得分：</span>${employee.comScore} <br/>
    <span>守时得分：</span>${employee.punctualScore} <br/>
    <span>均价得分：</span>${employee.avgPrice} <br/>
    <span>在职状态：</span>${employee.state} <br/>
    <span>服务范围：</span><span class="comment">${employee.serverScope}</span> <br/>

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