layui.use(['table','layer'],function(){
    var layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        table = layui.table;

    let tableIns = table.render({
        id:"cusDevPlanTable",
        elem: '#saleChanceList'
        , height: 'full-125',
        cellMinWidth: 95
        , url: ctx + '/sale_chance/list?flag=1', //数据接口
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
            , {
                field: 'devResult', title: '开发结果', align: 'center', templet: function (d) {
                    return formatDevResult(d.devResult);
                }
            }
            , {fixed: 'right', align: 'center', minWidth: '150', toolbar: '#op'}

        ]]
    });



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
                , createMan: $("[name=createMan]").val(),
                devResult:$("#devResult").val()
            }
            , page: {
                curr: 1 //重新从第 1 页开始
            }
        });
    })
    table.on('tool(saleChances)', function (data) {
        if (data.event == 'dev') {
            openCusDevPlanDialog("计划项数据开发",data.data.id);
        } else if (data.event == 'info') {
            openCusDevPlanDialog("计划项数据维护",data.data.id);

        }
    })
    function openCusDevPlanDialog(title,id){

        layui.layer.open({
            type: 2,
            title: title,
            area: ['750px', '550px'],
            content: ctx+"/cus_dev_plan/toCusDevPlanPage?id="+id, //iframe的url,
            maxmin: true
        });

    }
});
