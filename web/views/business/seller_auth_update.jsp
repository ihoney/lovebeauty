<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title></title>
    <script type="text/javascript" src="${rootPath}/js/jquery-1.8.3.min.js"></script>
    <script type="text/javascript" src="${rootPath}/js/jquery.form.js"></script>
    <script type="text/javascript" src="${rootPath}/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="${rootPath}/js/rootPath.js"></script>
    <link type="text/css" href="${rootPath}/css/love.form.css" rel="stylesheet"/>
    <script type="text/javascript">
        function btn_submit() {
            $('#form_custom').ajaxForm({
                beforeSubmit: validateMethod,
                success: function (data) {
                    data = JSON.parse(data);
                    if (data.reqResult) {
                        alert("update success!");
                    } else {
                        alert("update failed!");
                    }
                },
                error: function () {
                    alert("保存失败！");
                }
            });
            $("#form_custom").submit();
        }

        function validateMethod() {
            var name = $("#name").val();
            var email_reg = new RegExp("\\w+\\@\\w+\\.\\w+");

            if ($.trim(name) == "") {
                alert("名称不能为空!");
                return false;
            }

            var birthday = $("#birthday").val();
            if (birthday == "") {
                alert("出生日期不能为空！");
                return false;
            }

            var email = $.trim($("#email").val());
            if (email != "") {
                if (!email_reg.test(email)) {
                    alert("邮箱不对！");
                    return false;
                }
            } else {
                alert("邮箱不能为空！");
                return false;
            }

            var phone_reg = /^1\d{10}$/;
            var telephone = $.trim($("#telephone").val());
            if (!phone_reg.test(telephone)) {
                alert("请填写正确手机号！");
                return false;
            }

            var identify = $.trim($("#identify").val());
            var identify_reg = new RegExp("^[1-9]\\d{16}\\d$");
            if (!identify_reg.test(identify)) {
                alert("身份证号格式不对！");
                return false;
            }

            var shopName = $.trim($("#shopName").val());
            if (shopName == "") {
                alert("店铺名称不能为空！");
                return false;
            }

            var address = $.trim($("#address").val());
            if (address == "") {
                alert("店铺地址不能为空！");
                return false;
            }

            var payAccount = $.trim($("#payAccount").val());
            if (!email_reg.test(payAccount) && !phone_reg.test(payAccount)) {
                alert("支付宝账号格式错误！");
                return false;
            }

            var alipayPid = $.trim($("#alipayPid").val());
            if (alipayPid == "") {
                alert("支付宝PID不能为空！");
                return false;
            }
            var serviceScope = $.trim($("#serviceScope").val());
            if (serviceScope == "") {
                alert("服务范围不能为空！");
                return false;
            }
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
            window.parent.location.reload();
        }
    </script>
</head>
<body>
<form id="form_custom" action="${rootPath}/seller/updateAuth.do" method="post">
    <input type="hidden" name="sellerId" value="${sellerAuthInfo.sellerid}"/>
    <input type="hidden" name="id" value="${sellerAuthInfo.id}"/>
    <input type="hidden" name="headImgStr" value="${sellerAuthInfo.head_img}"/>
    <input type="hidden" name="identifyImgStr" value="${sellerAuthInfo.identify_img}"/>
    <table id="add_tab" cellspacing=0 cellpadding=5>
        <tr>
            <td class="td_att_name">店主姓名:</td>
            <td>
                <input type="text" name="name" id="name" value="${sellerAuthInfo.name}"/>
            </td>
            <td class="td_att_name">性别:</td>
            <td>
                <select id="sex" name="sex">
                    <option <c:if test="${sellerAuthInfo.sex == '男'}">selected="selected" </c:if>>男</option>
                    <option <c:if test="${sellerAuthInfo.sex == '女'}">selected="selected" </c:if>>女</option>
                </select>
            </td>
        </tr>
        <tr>
            <td class="td_att_name">出生日期:</td>
            <td>
                <input type="text" name="birthday" id="birthday" readonly="true" value="${sellerAuthInfo.birthday}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
            </td>
            <td class="td_att_name">电子邮件:</td>
            <td>
                <input type="text" name="email" id="email" value="${sellerAuthInfo.email}"/>
            </td>
        </tr>
        <tr>
            <td class="td_att_name">手机号:</td>
            <td>
                <input type="text" name="telephone" value="${sellerAuthInfo.telephone}" id="telephone"/>
            </td>
            <td class="td_att_name">店主照片:</td>
            <td>
                <input type="file" name="headImg" onchange="fileChange(this);" id="headImg"/>
            </td>
        </tr>
        <tr>
            <td class="td_att_name">身份证号:</td>
            <td>
                <input type="text" name="identify" id="identify" value="${sellerAuthInfo.identify}"/>
            </td>
            <td class="td_att_name">身份证件照片:</td>
            <td>
                <input type="file" name="identifyImg" onchange="fileChange(this);" value="${sellerAuthInfo.identify}" id="identifyImg"/>
            </td>
        </tr>
        <tr>
            <td class="td_att_name">店铺名称:</td>
            <td colspan="3">
                <input type="text" name="shopName" value="${sellerAuthInfo.shopname}" id="shopName"/>
            </td>
        </tr>
        <tr>
            <td class="td_att_name">店铺所在城市:</td>
            <td>
                <select id="cityId" name="cityId">
                    <c:forEach items="${cities}" var="city">
                        <option value="${city.id}" <c:if test="${sellerAuthInfo.cityId == city.id}">selected="selected" </c:if>>${city.name}</option>
                    </c:forEach>
                </select>
            </td>

            <td class="td_att_name">店铺地址:</td>
            <td>
                <input type="text" name="address" value="${sellerAuthInfo.address}" id="address"/>
            </td>
        </tr>

        <tr>
            <td class="td_att_name">支付宝账号:</td>
            <td>
                <input type="text" name="payAccount" value="${sellerAuthInfo.payaccount}" id="payAccount"/>
            </td>
            <td class="td_att_name">支付宝PID:</td>
            <td>
                <input type="text" name="alipayPid" id="alipayPid" value="${sellerAuthInfo.alipay_pid}"/>
            </td>
        </tr>

        <tr>
            <td class="td_att_name">支付宝公钥:</td>
            <td>
                <input readonly="true" type="text" name="alipayPublicKey" id="alipayPublicKey" value="${sellerAuthInfo.alipay_public_key}"/>
            </td>
            <td class="td_att_name">支付宝私钥:</td>
            <td>
                <input readonly="true" type="text" name="alipayPrivateKey" id="alipayPrivateKey" value="${sellerAuthInfo.alipay_private_key}"/>
            </td>
        </tr>
        <tr>

        </tr>
        <tr>
            <td class="td_att_name">服务范围:</td>
            <td colspan="3">
                <textarea style="border: none;" name="serviceScope" id="serviceScope" rows="6" cols="60">${sellerAuthInfo.servicescope}</textarea>
            </td>
        </tr>
        <tr>
            <td colspan="4" style="text-align: center">
                <input type="button" class="input_btn" onclick="btn_submit();" value="保存"/> &nbsp;&nbsp;&nbsp;
                <input type="button" class="input_btn" onclick="cancel();" value="取消"/>
            </td>
        </tr>
    </table>
</form>
</body>
</html>