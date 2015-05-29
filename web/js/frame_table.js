/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 15-4-23
 * Time: 下午1:13
 * To change this template use File | Settings | File Templates.
 */

function checkPassed(node) {
    var sellerId = $(node).attr("sellerId");
    $.ajax({
        url: "checkSeller.do?sellerId=" + sellerId,
        type: "POST",
        dataType: "json",
        success: function (data) {
            if (data.reqResult) {
                alert("审核失败！");
            } else {
                $(node).parent("td").siblings(".checked").text("是");
                $(node).parent("td").siblings(".checkedTime").text(new Date().toLocaleString());
            }
        }
    });
}