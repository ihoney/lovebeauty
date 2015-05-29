$(function () {
    $("#userAccount").focus();

    $("#password").keydown(function (e) {
        e = e || event;
        if (e.keyCode == 13) {
            $("#btn_login").click();
        }
    });

    $("#passwd2").keydown(function (e) {
        e = e || event;
        if (e.keyCode == 13) {
            $("#regBtn").click();
        }
    });

    $('#regBtn').click(function () {
        if ($('#regUserAccount').val() == "") {
            $('#regUserAccount').focus().css({
                border: "1px solid red",
                boxShadow: "0 0 2px red"
            });
            $('#userCue').html("<span class='login_error_info'>×用户名不能为空</span>");
            return false;
        }

        if ($('#regUserAccount').val().length < 4 || $('#regUserAccount').val().length > 16) {
            $('#regUserAccount').focus().css({
                border: "1px solid red",
                boxShadow: "0 0 2px red"
            });
            $('#userCue').html("<span class='login_error_info'>×账号为4-16字符</span>");
            return false;
        }

        if ($('#regPassword').val().length < 6 || $('#regPassword').val().length >16) {
            $('#regPassword').focus();
            $('#userCue').html("<span class='login_error_info'>×密码为6位-16字符</span>");
            return false;
        }
        if ($('#passwd2').val() != $('#regPassword').val()) {
            $('#passwd2').focus();
            $('#userCue').html("<span class='login_error_info'>×两次密码不一致！</span>");
            return false;
        }

        $.ajax({
            url: getRootPath()+"seller/register.do",
            type: "POST",
            data: {"account": $("#regUserAccount").val(), "password": $("#regPassword").val()},
            dataType: "json",
            success: function (data) {
                if (data.reqResult) {
                    $("#regUserAccount").val("");
                    $("#regPassword").val("");
                    $("#passwd2").val("");
                    $('#switch_qlogin').click();
                } else {
                    $('#userCue').html("<span class='login_error_info'>data.message</span>");
                }
            }
        });
    });

    $("#btn_login").click(function () {
        if ($('#userAccount').val() == "") {
            $('#userAccount').focus().css({
                border: "1px solid red",
                boxShadow: "0 0 2px red"
            });
            alert("用户名不能为空!");
            return false;
        }

        if ($('#password').val().length < 6) {
            $('#password').focus();
            alert("密码不能小于6位-16字符!");
            return false;
        }

        $.ajax({
            url: "seller/checkLogin.do",
            type: "POST",
            data: {"account": $("#userAccount").val(), "password": $("#password").val()},
            dataType: "json",
            success: function (data) {
                if (data.reqResult) {
                    location.href = "frame/main.do";
                } else {
                    alert("用户名或密码错误！");
                }
            }
        });
    })

    $('#switch_qlogin').click(function () {
        $('#switch_login').removeClass("switch_btn_focus").addClass('switch_btn');
        $('#switch_qlogin').removeClass("switch_btn").addClass('switch_btn_focus');
        $('#switch_bottom').animate({left: '0px', width: '70px'});
        $('#qlogin').css('display', 'none');
        $('#web_qr_login').css('display', 'block');
    });
    $('#switch_login').click(function () {
        $('#switch_login').removeClass("switch_btn").addClass('switch_btn_focus');
        $('#switch_qlogin').removeClass("switch_btn_focus").addClass('switch_btn');
        $('#switch_bottom').animate({left: '154px', width: '70px'});
        $('#qlogin').css('display', 'block');
        $('#web_qr_login').css('display', 'none');
    });
});

function quitSys() {
    $.ajax({
        url: "seller/quitSys.do",
        type: "GET",
        dataType: "json",
        success: function (data) {
            window.parent.location.href = 'index.jsp';
        }
    });
}