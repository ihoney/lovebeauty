/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 15-4-23
 * Time: 下午6:17
 * To change this template use File | Settings | File Templates.
 */

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
