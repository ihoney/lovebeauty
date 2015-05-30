<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>FlexiGrid</title>
    <link rel="stylesheet" href="${rootPath}/css/flexigrid/flexigrid.css" type="text/css"/>
    <link rel="stylesheet" href="${rootPath}/css/flexigrid/flexigrid_self.css" type="text/css"/>
    <link href="${rootPath}/css/frame_table.css" type="text/css" rel="stylesheet"/>

    <script type="text/javascript" src="${rootPath}/js/jquery-1.8.3.min.js"></script>
    <script type="text/javascript" src="${rootPath}/js/flexigrid.js"></script>

    <script type="text/javascript">
        $(document).ready(function () {
            var maiheight = document.documentElement.clientHeight;
            var w = $("#ptable").width() - 3;
            var h = maiheight - 130;
            var grid = $("#flex1").flexigrid({
                width: w,
                height: h,
                url: '${rootPath}/seller/flexLoadData.do',
                dataType: 'json',
                colModel: [
                    {display: '编号', name: 'id', width: 50, sortable: true, align: 'center', hide: false},
                    {display: '工作名称', name: 'job_name', width: 120, sortable: true, align: 'left'},
                    {display: '工作地址', name: 'work_address', width: 120, sortable: true, align: 'left'},
                    {display: '工资', name: 'salary', width: 120, sortable: true, align: 'left', process: formatMoney},
                    {display: '日期', name: 'date', width: 120, sortable: true, align: 'left'},
                    {display: '语言', name: 'language', width: 120, sortable: true, align: 'left'}
                ],

                searchitems: [
                    {display: '编号', name: 'id', isdefault: true},
                    {display: '工作名称', name: 'job_name'},
                    {display: '工作地址', name: 'work_address'},
                    {display: '语言', name: 'language'}
                ],
                errormsg: '发生异常',
                sortname: "id",
                sortorder: "desc",
                usepager: true,
                pagestat: '显示记录从{from}到{to}，总数 {total} 条',
                useRp: true,
                rp: 10,
                rpOptions: [10, 15, 20, 30, 40, 100], //可选择设定的每页结果数
                nomsg: '没有符合条件的记录存在',
                minColToggle: 1, //允许显示的最小列数
                showTableToggleBtn: false,
                autoload: true, //自动加载，即第一次发起ajax请求
                resizable: false, //table是否可伸缩
                procmsg: '加载中, 请稍等 ...',
                hideOnSubmit: true, //是否在回调时显示遮盖
                blockOpacity: 0.5,//透明度设置
                rowbinddata: true,
                showcheckbox: true
            });

            function toolbar(com, grid) {
                if (com == '删除') {
                    $("#action").val("delete");
                    if ($('.trSelected', grid).length == 0) {
                        alert("请选择要删除的数据");
                    } else {
                        if (confirm('是否删除这 ' + $('.trSelected', grid).length + ' 条记录吗?')) {
                            var ids = "";
                            for (var i = 0; i < $('.trSelected', grid).length; i++) {
                                ids += "," + $('.trSelected', grid).find("td:first").eq(i).text();//获取id
                            }
                            ids = ids.substring(1);
                            $.ajax({
                                        type: "POST",
                                        url: "${rootPath}/seller/flexLoadData.do?action=" + $("#action").val(),
                                        data: "id=" + ids,
                                        dataType: "text",
                                        success: function (msg) {
                                            if (msg == "success") {
                                                $("#flex1").flexReload();
                                            } else {
                                                alert("有错误发生,msg=" + msg);
                                            }
                                        },
                                        error: function (msg) {
                                            alert(msg);
                                        }
                                    }
                            )
                        }
                    }
                }

                else if (com == '添加') {
                    $("#action").val("add");
                    window.location.href = "${rootPath}/seller/flexLoadData.do?action=" + $("#action").val();
                } else if (com == '修改') {
                    $("#action").val("modify");
                    if ($(".trSelected").length == 1) {
                        window.location.href = "${rootPath}/seller/flexLoadData.do?action=" + $("#action").val() + "&id=" + $('.trSelected', grid).find("td").eq(0).text();
                    } else if ($(".trSelected").length > 1) {
                        alert("请选择一个修改,不能同时修改多个");
                    } else if ($(".trSelected").length == 0) {
                        alert("请选择一个您要修改的记录")
                    }
                }
            }

            function formatMoney(value, pid) {
                return "￥" + parseFloat(value).toFixed(2);
            }
        })
        function reloadTmp() {
            $("#flex1").flexReload();
        }
    </script>
</head>
<body>
<div class="tab_header">
    <span class="fontStyle_bold cur_pos">你当前的位置：</span>[业务中心]-[作品列表]

    <span class="btn_pos">
        <span class="btn_bg_img_2 btn_list_mode_img">网格模式</span>
        <span class="btn_bg_img btn_add_img">添加</span>
    </span>

</div>

<div id="ptable" style="margin:1px">
    <table id="flex1" style="display:none"></table>
</div>
<input id="action" type="hidden" name="action"/>
</body>
</html>