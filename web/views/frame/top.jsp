<%@ page import="com.lb.utils.DateUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}"/>

<link href="${rootPath}/css/frame.css" type="text/css" rel="stylesheet"/>

<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td height="70" background="${rootPath}/views/frame/images/main_05.gif">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td height="24">
                        <table width="100%" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td width="270" height="24" background="${rootPath}/views/frame/images/main_03.gif">&nbsp;</td>
                                <td width="505" background="${rootPath}/views/frame/images/main_04.gif">&nbsp;</td>
                                <td>&nbsp;</td>
                                <td width="21"><img src="${rootPath}/views/frame/images/main_07.gif" width="21" height="24"></td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td height="38">
                        <table width="100%" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td width="270" height="38" background="${rootPath}/views/frame/images/main_09.gif">&nbsp;</td>
                                <td>
                                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td width="220" valign="bottom" nowrap="nowrap">
                                                <div align="right"><span class="STYLE1">
                                                <span class="STYLE2">■</span> 今天是：<%=DateUtil.cruDateStr()%></span>
                                                </div>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                                <td width="21"><img src="${rootPath}/views/frame/images/main_11.gif" width="21" height="38"></td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td height="8" style=" line-height:8px;">
                        <table width="100%" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td width="270" background="${rootPath}/views/frame/images/main_29.gif" style=" line-height:8px;">&nbsp;</td>
                                <td width="505" background="${rootPath}/views/frame/images/main_30.gif" style=" line-height:8px;">&nbsp;</td>
                                <td style=" line-height:8px;">&nbsp;</td>
                                <td width="21" style=" line-height:8px;"><img src="${rootPath}/views/frame/images/main_31.gif" width="21" height="8"></td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td height="28" background="${rootPath}/views/frame/images/main_36.gif">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td width="177" height="28" background="${rootPath}/views/frame/images/main_32.gif">
                        <table width="100%" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td width="20%" height="22">&nbsp;</td>
                                <td width="59%" valign="bottom">
                                    <div align="center" class="STYLE1">欢迎使用！</div>
                                </td>
                                <td width="21%">&nbsp;</td>
                            </tr>
                        </table>
                    </td>
                    <td>
                    </td>
                    <td width="21"><img src="${rootPath}/views/frame/images/main_37.gif" width="21" height="28"></td>
                </tr>
            </table>
        </td>
    </tr>
</table>
