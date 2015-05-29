<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}" scope="application"/>
<html>
<head>
    <title>后台管理</title>
    <script type="text/javascript" src="js/jquery-1.8.3.min.js"></script>
    <script type="text/javascript" src="js/rootPath.js"></script>
    <script type="text/javascript" src="js/admin_login.js"></script>
    <link type="text/css" href="css/admin_login.css" rel="stylesheet"/>
    <script type="text/javascript">
        $(function () {
            $("#account").focus();

            $("#password").keydown(function (e) {
                e = e || event;
                if (e.keyCode == 13) {
                    $("#area_login").click();
                }
            });

            $("#area_login").click(function () {
                loginSystem();
            })
        });
    </script>
</head>
<body>
<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
    <tr>
        <td>
            <table width="962" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                    <td height="235" background="images/admin/login_03.gif">&nbsp;</td>
                </tr>
                <tr>
                    <td height="53">
                        <table width="100%" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td width="394" height="53" background="images/admin/login_05.gif">&nbsp;</td>
                                <td width="206" background="images/admin/login_06.gif">
                                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td width="20%" height="25">
                                                <div align="right">
                                                    <span class="STYLE1">账号</span>
                                                </div>
                                            </td>
                                            <td width="57%" height="25">
                                                <div align="center">
                                                    <input type="text" id="account" name="account" class="login_input"/>
                                                </div>
                                            </td>
                                            <td width="27%" height="25">&nbsp;</td>
                                        </tr>
                                        <tr>
                                            <td height="25">
                                                <div align="right">
                                                    <span class="STYLE1">密码</span>
                                                </div>
                                            </td>
                                            <td height="25">
                                                <div align="center">
                                                    <input type="password" id="password" name="password" class="login_input"/>
                                                </div>
                                            </td>
                                            <td height="25">
                                                <div align="left">
                                                    <a id="area_login">
                                                        <img src="images/admin/dl.gif" width="49" height="18" border="0">
                                                    </a>
                                                </div>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                                <td width="362" background="images/admin/login_07.gif">&nbsp;</td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td height="213" background="images/admin/login_08.gif">&nbsp;</td>
                </tr>
            </table>
        </td>
    </tr>
</table>
</body>
</html>
