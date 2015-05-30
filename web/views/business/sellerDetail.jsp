<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>商家详情</title>
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
        }
    </style>
</head>
<body>

<div id="demo_info">
    <div style="width: 30%; float: left;">
        <span>账号：</span>${seller.account} <br/>
        <span>密码：</span>${seller.password} <br/>
        <span>名称：</span>${seller.name} <br/>
        <span>性别：</span>${seller.sex} <br/>
        <span>身份证号：</span>${seller.identify} <br/>
        <span>出生日期：</span>${seller.birthday} <br/>
        <span>电子邮件：</span>${seller.email} <br/>
        <span>店铺名称：</span>${seller.shopname} <br/>
        <span>店铺地址：</span>${seller.address} <br/>
        <span>支付宝账号：</span>${seller.payaccount} <br/>
        <span>手机号：</span>${seller.telephone} <br/>
        <span>支付宝秘钥：</span>${seller.alipay_key} <br/>
        <span>支付宝PID：</span>${seller.alipay_pid} <br/>
    </div>
    <div>
        <img src="${rootPath}/fileUpload/${seller.head_img}" class="seller_head_img"/>
        <img src="${rootPath}/fileUpload/${seller.identify_img}" class="seller_head_img"/>
    </div>
</div>
</div>
<div style="clear: both"></div>
<div class="comment" style="text-align: left">服务范围：${seller.servicescope} </div>
</body>
</html>