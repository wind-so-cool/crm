layui.use(['table','layer',"form"],function(){
    var layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        table = layui.table;
    //客户流失列表展示
    var  tableIns = table.render({
        elem: '#customerLossList',
        url : ctx+'/customer_loss/list',
        cellMinWidth : 95,
        page : true,
        height : "full-125",
        limits : [10,15,20,25],
        limit : 10,
        toolbar: "#toolbarDemo",
        id : "customerLossListTable",
        cols : [[
            {type: "checkbox", fixed:"center"},
            {field: "id", title:'编号',fixed:"true"},
            {field: 'cusNo', title: '客户编号',align:"center"},
            {field: 'cusName', title: '客户名称',align:"center"},
            {field: 'cusManager', title: '客户经理',align:"center"},
            {field: 'lastOrderTime', title: '最后下单时间',align:"center"},
            {field: 'lossReason', title: '流失原因',align:"center"},
            {field: 'confirmLossTime', title: '确认流失时间',align:"center"},
            {field: 'createDate', title: '创建时间',align:"center"},
            {field: 'updateDate', title: '更新时间',align:"center"},
            {title: '操作',fixed:"right",align:"center", minWidth:150,templet:"#op"}
        ]]
    });

    $(".search_btn").click(function () {
        tableIns.reload({
            where: { //设定异步数据接口的额外参数，任意设
                cusNo: $("[name='cusNo']").val(),
                cusName: $("[name='cusName']").val(),
                state: $("[name='state']").val()
            }
            , page: {
                curr: 1 //重新从第 1 页开始
            }
        });
    })
    table.on("tool(customerLosses)",function(data){
        if(data.event=="add"){
            openCustomerLossDialog("<h3>流失管理-暂缓措施维护</h3>",data.data.id);

        }else if(data.event=="info"){
            openCustomerLossDialog("<h3>流失管理-暂缓数据查看</h3>",data.data.id);

        }
    })
    function openCustomerLossDialog(title,id){
        layui.layer.open({
            type: 2,
            title: title,
            area: ['600px', '600px'],
            content: ctx+"/customer_loss/toCustomerLossPage?id="+id, //iframe的url,
            maxmin: true
        });
    }

});
