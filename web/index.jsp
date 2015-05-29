<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}" scope="application"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>爱尚美拉</title>
    <meta name="keywords" content="爱尚美拉"/>
    <meta name="description" content="爱尚美拉"/>
    <script type="text/javascript" src="js/jquery-1.8.3.min.js"></script>
    <script type="text/javascript" src="js/rootPath.js"></script>
    <script type="text/javascript" src="js/login.js"></script>
    <link href="css/login.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<h1>爱尚美拉<sup>V2015</sup></h1>

<div class="login" style="margin-top:50px;">

    <div class="header">
        <div class="switch" id="switch">
            <a class="switch_btn_focus" id="switch_qlogin" href="javascript:void(0);" tabindex="7">快速登录</a>
            <a class="switch_btn" id="switch_login" href="javascript:void(0);" tabindex="8">快速注册</a>

            <div class="switch_bottom" id="switch_bottom" style="position: absolute; width: 64px; left: 0px;"></div>
        </div>
    </div>
    <div class="web_qr_login" id="web_qr_login" style="display: block; height: 235px;">
        <!--登录-->
        <div class="web_login" id="web_login">
            <div class="login-box">
                <div class="login_form">
                    <form action="seller/checkLogin.do" name="loginform" accept-charset="utf-8" id="login_form" class="loginForm" method="post">
                        <input type="hidden" name="did" value="0"/>
                        <input type="hidden" name="to" value="log"/>

                        <div class="uinArea" id="uinArea">
                            <label class="input-tips" for="userAccount">帐号：</label>

                            <div class="inputOuter" id="uArea">
                                <input type="text" id="userAccount" name="account" class="inputstyle"/>
                            </div>
                        </div>
                        <div class="pwdArea" id="pwdArea">
                            <label class="input-tips" for="password">密码：</label>

                            <div class="inputOuter" id="pArea">
                                <input type="password" id="password" name="password" class="inputstyle"/>
                            </div>
                        </div>
                        <div style="padding-left:50px;margin-top:20px;">
                            <input type="button" id="btn_login" value="登 录" style="width:150px;" class="button_blue"/>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <!--登录end-->
    </div>

    <!--注册-->
    <div class="qlogin" id="qlogin" style="display: none; ">

        <div class="web_login">
            <form name="form2" id="regUser" accept-charset="utf-8" action="seller/register.do" method="post">
                <input type="hidden" name="to" value="reg"/>
                <input type="hidden" name="did" value="0"/>
                <ul class="reg_form" id="reg-ul">
                    <div id="userCue" class="cue">快速注册请注意格式</div>
                    <li>
                        <label for="regUserAccount" class="input-tips2">账号：</label>

                        <div class="inputOuter2">
                            <input type="text" id="regUserAccount" placeholder="账号为4-16字符" name="account" maxlength="16" class="inputstyle2"/>
                        </div>
                    </li>
                    <li>
                        <label for="regPassword" class="input-tips2">密码：</label>

                        <div class="inputOuter2">
                            <input type="password" id="regPassword" placeholder="密码为6位-16字符" name="password" maxlength="16" class="inputstyle2"/>
                        </div>
                    </li>
                    <li>
                        <label for="passwd2" class="input-tips2">确认密码：</label>

                        <div class="inputOuter2">
                            <input type="password" id="passwd2" name="" maxlength="16" class="inputstyle2"/>
                        </div>
                    </li>
                    <div class="inputArea">
                        <input type="button" id="regBtn" style="margin-top:10px;margin-left:85px;" class="button_blue" value="同意协议并注册"/>
                        <a href="#" class="zcxy" target="_blank">注册协议</a>
                    </div>
                    </li>
                    <div class="cl"></div>
                </ul>
            </form>
        </div>
    </div>
    <!--注册end-->
</div>
<div class="jianyi">*推荐使用ie8或以上版本ie浏览器或Chrome内核浏览器访问本站</div>
</body>
</html>