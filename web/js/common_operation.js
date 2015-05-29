/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 15-5-15
 * Time: 下午9:53
 * To change this template use File | Settings | File Templates.
 */

//删除记录
function deleteRecord(node, url) {
    if (!window.confirm("确认删除！")) {
        return;
    }
    $.ajax({
        url: url,
        type: "GET",
        dataType: "json",
        success: function (data) {
            if (data.reqResult) {
                $(node).closest("tr").remove();
            } else {
                alert("服务器出现故障，删除失败！");
            }
        }
    });
}

//审核记录
function checkPassed(node) {
    var sellerId = $(node).attr("sellerId");
    $.ajax({
        url: "checkSeller.do?sellerId=" + sellerId,
        type: "POST",
        dataType: "json",
        success: function (data) {
            if (data.reqResult) {
                $(node).parent("td").siblings(".checked").text("是");
                $(node).parent("td").siblings(".checkedTime").text(data.checkTime);
                $(node).remove();
            } else {
                alert("审核失败！");
            }
        }
    });
}

//分页

function pageing(pageNum, url, page_callback) {
    $.ajax({
        url: url,
        type: "POST",
        data: {"curPage": pageNum},
        dataType: "json",
        success: function (data) {
            $("#currentPage").text(pageNum);
            page_callback(data);
        }
    });
}