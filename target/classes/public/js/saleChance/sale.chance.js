layui.use(['table', 'layer'], function () {
    var layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        table = layui.table;


    let tableIns = table.render({
        id:"saleChanceTable",
        elem: '#saleChanceList'
        , height: 'full-125',
        cellMinWidth: 95
        , url: ctx + '/sale_chance/list', //数据接口
        toolbar: '#toolbarDemo'
        , page: true //开启分页
        , cols: [[ //表头
            {type: 'checkbox', align: 'center', fixed: true},
            {field: 'id', title: '编号', sort: true, fixed: 'left', align: "center"}
            , {field: 'customerName', title: '客户名称', align: 'center'}
            , {field: 'chanceSource', title: '机会来源', align: 'center'}
            , {field: 'cgjl', title: '成功几率', align: 'center'}
            , {field: 'overview', title: '概要', align: 'center'}
            , {field: 'linkMan', title: '联系人', align: 'center'}
            , {field: 'linkPhone', title: '手机号', align: 'center'}
            , {field: 'description', title: '描述', align: 'center'}
            , {field: 'createMan', title: '创建人', align: 'center'}
            , {field: 'createDate', title: '创建时间', align: 'center'}
            , {field: 'updateDate', title: '更新时间', align: 'center'}
            , {field: 'uname', title: '分配人', align: 'center'}
            , {field: 'assignTime', title: '分配时间', align: 'center'}
            , {
                field: 'state', title: '分配状态', align: 'center', templet: function (d) {
                    return formatState(d.state);
                }
            }
            , {
                field: 'devResult', title: '开发结果', align: 'center', templet: function (d) {
                    return formatDevResult(d.devResult);
                }
            }
            , {fixed: 'right', align: 'center', minWidth: '150', toolbar: '#saleChanceListBar'}

        ]]
    });

    function formatState(state) {
        if (state == 0) {
            return "<font color='#9932cc'>未分配</font>"
        } else if (state == 1) {
            return "<font color='green'>已分配</font>"
        } else {
            return "<font color='red'>未知</font>"

        }
    }

    function formatDevResult(devResult) {
        if (devResult == 0) {
            return "<font color='#9932cc'>未开发</font>"
        } else if (devResult == 1) {
            return "<font color='orange'>开发中</font>"
        } else if (devResult == 2) {
            return "<font color='green'>开发成功</font>"
        } else if (devResult == 3) {
            return "<font color='red'>开发失败</font>"
        } else {
            return "<font color='blue'>未知</font>"
        }
    }

    $(".search_btn").click(function () {
        tableIns.reload({
            where: { //设定异步数据接口的额外参数，任意设
                customerName: $("[name=customerName]").val()
                , createMan: $("[name=createMan]").val()
                , state: $("[name=state]").val()
            }
            , page: {
                curr: 1 //重新从第 1 页开始
            }
        });
    })
    table.on('toolbar(saleChances)', function (data) {
        if (data.event == 'add') {
            openSaleChanceDialog();
        } else if (data.event == 'del') {
            deleteSaleChance(data);
        }
    })
    function deleteSaleChance(data){
        let saleChanceData=table.checkStatus("saleChanceTable").data;
        if(saleChanceData.length==0){
            layer.msg("请选择要删除的数据",{icon:5});
            return;
        }
        layer.confirm("确定删除选中记录吗?",{icon:3,title:"营销机会管理"},function(index){
            layer.close(index);
            let ids="";
            for(let i=0;i<saleChanceData.length;i++){
                ids+="ids="+saleChanceData[i].id;
                if(i!=saleChanceData.length-1){
                    ids+="&";
                }
            }
            $.post(ctx+"/sale_chance/delete",ids,function(result){
                if(result.code==200){
                    layer.msg(result.msg,{icon:6});
                    tableIns.reload();
                }else{
                    layer.msg(result.msg,{icon:5});
                }
            })
        })
    }
    function openSaleChanceDialog(id) {
        //iframe层
        let url=ctx + '/sale_chance/toSaleChancePage';
        let title='<h3>营销机会管理-添加营销机会</h3>';
        if(id!=null){
            url+="?id="+id;
            title='<h3>营销机会管理-更新营销机会</h3>';
        }
        layui.layer.open({
            type: 2,
            title: title,
            area: ['500px', '620px'],
            content: url, //iframe的url,
            maxmin: true
        });

    }

    table.on('tool(saleChances)', function (data) {
        if (data.event == 'edit') {
            openSaleChanceDialog(data.data.id);
        } else if (data.event = 'del') {
            layer.confirm("确定删除记录吗?",{icon:3,title:"营销机会管理"},function(index){
                layer.close(index);
                $.post(ctx+"/sale_chance/delete",{ids:data.data.id},function(result){
                    if(result.code==200){
                        layer.msg(result.msg,{icon:6});
                        tableIns.reload();
                    }else{
                        layer.msg(result.msg,{icon:5});
                    }
                })
            })

        }
    })
});
