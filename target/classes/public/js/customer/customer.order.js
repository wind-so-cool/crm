layui.use(['table','layer'],function(){
    var layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        table = layui.table;
    //订单列表展示
    var  tableIns = table.render({
        elem: '#customerOrderList',
        url : ctx+"/order/list?cusId="+$("input[name='id']").val(),
        cellMinWidth : 95,
        page : true,
        height : "full-125",
        limits : [10,15,20,25],
        limit : 10,
        toolbar: "#toolbarDemo",
        id : "customerOrderListTable",
        cols : [[
            {type: "checkbox", fixed:"center"},
            {field: "id", title:'编号',fixed:"true"},
            {field: 'orderNo', title: '订单编号',align:"center"},
            {field: 'orderDate', title: '下单日期',align:"center"},
            {field: 'address', title: '收货地址',align:"center"},
            {field: 'state', title: '支付状态',align:"center",templet:function (d) {
                    if(d.state==1){
                        return "已支付;"
                    }else{
                        return "未支付";
                    }
                }},
            {field: 'createDate', title: '创建时间',align:"center"},
            {field: 'updateDate', title: '更新时间',align:"center"},
            {title: '操作',fixed:"right",align:"center", minWidth:150,templet:"#customerOrderListBar"}
        ]]
    });

    table.on('tool(customerOrders)',function(data){
        if(data.event=="info"){
            let url=ctx+"/order/toOrderDetailsPage?orderId="+data.data.id;
            let title="<h3>客户管理-订单详情</h3>"
            layui.layer.open({
                type: 2,
                title: title,
                area: ['500px', '500px'],
                content: url, //iframe的url,
                maxmin: true
            });
        }
    })
   



});
