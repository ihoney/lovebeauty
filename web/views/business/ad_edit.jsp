<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title></title>
    <script type="text/javascript" src="${rootPath}/js/jquery-1.8.3.min.js"></script>
    <script type="text/javascript" src="${rootPath}/js/jquery.form.js"></script>
    <script type="text/javascript" src="${rootPath}/js/rootPath.js"></script>
    <link type="text/css" href="${rootPath}/css/love.form.css" rel="stylesheet"/>
    <script type="text/javascript">
        function btn_submit() {
            $('#form_custom').ajaxForm({
                beforeSubmit: validateMethod,
                success: function () {
                    location.href = getRootPath() + "ad/queryAllAds.do";
                },
                error: function () {
                    alert("提交失败！");
                }
            });
            $("#form_custom").submit();
        }

        function validateMethod() {

        }

        function fileChange(node) {
            var fileName = $(node).val();
            var fileSuffix = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length);
            if (fileSuffix != 'jpg' && fileSuffix != 'gif' && fileSuffix != 'png' && fileSuffix != 'jpeg') {
                alert("只能上传图片！");
                $(node).val("");
            }
        }

        function cancel() {
            location.href = getRootPath() + "ad/queryAllAds.do";
        }
    </script>
    <style type="text/css">
        .ad_table {
            font-size: 12px;
            border-top: 1px solid #b5d6e6;
            border-left: 1px solid #b5d6e6;
        }

        .ad_table td {
            padding: 3px;
            border-right: 1px solid #b5d6e6;
            border-bottom: 1px solid #b5d6e6;
        }
    </style>
</head>
<body>
<form id="form_custom" action="${rootPath}/ad/updateAd.do" enctype="multipart/form-data" method="post">
    <input type="hidden" name="id" value="${ad.id}"/>
    <table id="add_tab" cellspacing=0 cellpadding=5>
        <tr>
            <td class="ad_td_att_name">广告类型：</td>
            <td class="ad_td_attr_value">
                ${ad.type}
            </td>
        </tr>
        <tr>
            <td class="ad_td_att_name">链接地址：</td>
            <td class="ad_td_attr_value">
                <c:if test="${ad.type =='外部链接'}">
                    <input name="url" value="${ad.url}" type="text"/>
                </c:if>

                <c:if test="${ad.type =='内部链接'}">
                    '<select name="url" style="border: none; width: 100px;">
                    <c:forEach items="${demos}" var="demo">
                        <option value="${demo.id}" <c:if test="${demo.id == ad.url}">selected="selected" </c:if>>${demo.name}</option>
                    </c:forEach>
                    </select>
                </c:if>
            </td>
        </tr>
        <tr>
            <td class="td_att_name">广告图片:</td>
            <td>
                <input type="hidden" name="picName" value="${ad.picName}"/>
                <input type="file" name="file" id="file" onchange="fileChange(this);"/>
            </td>
        </tr>
        <tr>
            <td class="ad_td_att_name">备注：</td>
            <td class="ad_td_attr_value">
                <textarea style="border: none;" style="border: none;" name="backup" rows="5" cols="35">${ad.backup}</textarea>
            </td>
        </tr>
        <tr>
            <td colspan="2" style="text-align: center">
                <input type="button" class="input_btn" onclick="btn_submit();" value="保存"/> &nbsp;&nbsp;&nbsp;
                <input type="button" class="input_btn" onclick="cancel()" value="取消"/>
            </td>
        </tr>
    </table>
</form>
</body>
</html>