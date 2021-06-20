layui.use(['table', 'treetable'], function () {
    var $ = layui.jquery;
    var table = layui.table;
    var treeTable = layui.treetable;

    // 渲染表格
    treeTable.render({
        treeColIndex: 1,
        treeSpid: -1,
        treeIdName: 'id',
        treePidName: 'parentId',
        elem: '#munu-table',
        url: ctx + '/module/list',
        toolbar: "#toolbarDemo",
        treeDefaultClose: true,
        page: true,
        cols: [[
            {type: 'numbers'},
            {field: 'moduleName', minWidth: 100, title: '菜单名称'},
            {field: 'optValue', title: '权限码'},
            {field: 'url', title: '菜单url'},
            {field: 'createDate', title: '创建时间'},
            {field: 'updateDate', title: '更新时间'},
            {
                field: 'grade', width: 80, align: 'center', templet: function (d) {
                    if (d.grade == 0) {
                        return '<span class="layui-badge layui-bg-blue">目录</span>';
                    }
                    if (d.grade == 1) {
                        return '<span class="layui-badge-rim">菜单</span>';
                    }
                    if (d.grade == 2) {
                        return '<span class="layui-badge layui-bg-gray">按钮</span>';
                    }
                }, title: '类型'
            },
            {templet: '#auth-state', width: 180, align: 'center', title: '操作'}
        ]],
        done: function () {
            layer.closeAll('loading');
        }
    });


    table.on('toolbar(munu-table)', function (data) {
        if (data.event == "expand") {
            treeTable.expandAll("#munu-table")
        } else if (data.event == "fold") {
            treeTable.foldAll("#munu-table")
        } else if (data.event == "add") {
            openAddDialog(0, -1);
        }
    })
    table.on('tool(munu-table)', function (data) {
        if (data.event == 'add') {
            if (data.data.grade == 2) {
                layer.msg("暂不支持添加四级菜单", {icon: 5});
                return;
            }
            openAddDialog(data.data.grade + 1, data.data.id);
        } else if (data.event == "edit") {
            openUpdateDialog(data.data.id);
        } else if (data.event = 'del') {
            layer.confirm("确定删除记录吗?", {icon: 3, title: "菜单管理"}, function (index) {
                layer.close(index);
                $.post(ctx + "/module/delete", {id: data.data.id}, function (result) {
                    if (result.code == 200) {
                        layer.msg(result.msg, {icon: 6},function(){
                            location.reload();
                        });

                    } else {
                        layer.msg(result.msg, {icon: 5});
                    }
                })
            })
        }
    })

    function openUpdateDialog(id) {
        let title = "菜单管理-更新菜单";
        let url = ctx + "/module/toUpdatePage?id=" + id;
        layui.layer.open({
            type: 2,
            title: title,
            area: ['500px', '620px'],
            content: url, //iframe的url,
            maxmin: true
        });

    }

    function openAddDialog(grade, parentId) {
        let title = "菜单管理-添加菜单";
        let url = ctx + "/module/toAddPage?grade=" + grade + "&parentId=" + parentId;
        layui.layer.open({
            type: 2,
            title: title,
            area: ['500px', '620px'],
            content: url, //iframe的url,
            maxmin: true
        });

    }


});