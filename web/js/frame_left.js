/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 15-4-22
 * Time: 下午7:28
 * To change this template use File | Settings | File Templates.
 */

$(function () {
    $(".business_menu1").click(function () {
        var node = $(this);
        node.find(".business_body").slideDown(10);
        node.siblings("div").each(function () {
            $(this).find(".business_body").slideUp(10);
        })
    });
})
