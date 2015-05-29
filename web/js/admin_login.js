function loginSystem() {
    var account = $("#account").val();
    if (!validateAccount(account)) {
        alert("账户不能为空！");
        return;
    }
    var password = $("#password").val();
    if (!validatePassword(password)) {
        alert("密码不能为空！");
        return;
    }

    $.ajax({
        url: getRootPath() + "admin/checkLogin.do",
        type: "POST",
        data: {"account": account, "password": password},
        dataType: "json",
        success: function (data) {
            if (data.reqResult) {
                window.location.href = getRootPath() + "frame/adminMain.do";
            } else {
                alert("账户或密码错误！");
            }
        }
    });
}

function validateAccount(account) {
    var reg = /\w+/;
    if (!reg.test(account)) {
        return false;
    }
    return true;
}

function validatePassword(password) {
    if ($.trim(password) == "") {
        return false;
    }
    return true;
}

function quitSys() {
    $.ajax({
        url: getRootPath() + "admin/quitSys.do",
        type: "GET",
        dataType: "json",
        success: function (data) {
            window.parent.location.href = getRootPath() + "index.jsp";
        }
    });
}