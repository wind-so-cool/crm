layui.use(['table','layer',"form"],function(){
    var layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        table = layui.table,
        form = layui.form;

    //客户列表展示
    var  tableIns = table.render({
        elem: '#customerList',
        url : ctx+'/customer/list',
        cellMinWidth : 95,
        page : true,
        height : "full-125",
        limits : [10,15,20,25],
        limit : 10,
        toolbar: "#toolbarDemo",
        id : "customerListTable",
        cols : [[
            {type: "checkbox", fixed:"center"},
            {field: "id", title:'编号',fixed:"true"},
            {field: 'name', title: '客户名',align:"center"},
            {field: 'fr', title: '法人',  align:'center'},
            {field: 'khno', title: '客户编号', align:'center'},
            {field: 'area', title: '地区', align:'center'},
            {field: 'cusManager', title: '客户经理',  align:'center'},
            {field: 'myd', title: '满意度', align:'center'},
            {field: 'level', title: '客户级别', align:'center'},
            {field: 'xyd', title: '信用度', align:'center'},
            {field: 'address', title: '详细地址', align:'center'},
            {field: 'postCode', title: '邮编', align:'center'},
            {field: 'phone', title: '电话', align:'center'},
            {field: 'webSite', title: '网站', align:'center'},
            {field: 'fax', title: '传真', align:'center'},
            {field: 'zczj', title: '注册资金', align:'center'},
            {field: 'yyzzzch', title: '营业执照', align:'center'},
            {field: 'khyh', title: '开户行', align:'center'},
            {field: 'khzh', title: '开户账号', align:'center'},
            {field: 'gsdjh', title: '国税', align:'center'},
            {field: 'dsdjh', title: '地税', align:'center'},
            {field: 'createDate', title: '创建时间', align:'center'},
            {field: 'updateDate', title: '更新时间', align:'center'},
            {title: '操作', templet:'#customerListBar',fixed:"right",align:"center", minWidth:150}
        ]]
    });
    $(".search_btn").click(function () {
        tableIns.reload({
            where: { //设定异步数据接口的额外参数，任意设
                customerName: $("[name=name]").val()
                , customerNo: $("[name=khno]").val()
                , level: $("[name=level]").val()
            }
            , page: {
                curr: 1 //重新从第 1 页开始
            }
        });
    })

    table.on('toolbar(customers)', function (data) {
        if (data.event == 'add') {
            openAddOrUpdateCustomerDialog();
        }else if(data.event="order"){
            openCustomerOrderDialog(data);
        }
    })
    function openCustomerOrderDialog(data){
        let da=table.checkStatus(data.config.id).data;
        if(da.length==0){
            layer.msg("请选择要查看订单的客户");
            return;
        }
        if(da.length>1){
            layer.msg("暂不支持批量查看");
            return;
        }
        let title="<h3>客户管理-订单查看</h3>";
        let url=ctx+"/customer/toCustomerOrderPage?cusId="+da[0].id;
        layui.layer.open({
            type: 2,
            title: title,
            area: ['700px', '650px'],
            content: url, //iframe的url,
            maxmin: true
        });
    }
    table.on('tool(customers)', function (data) {
        if (data.event == 'edit') {
            openAddOrUpdateCustomerDialog(data.data.id);
        } else if (data.event = 'del') {
            layer.confirm("确定删除记录吗?",{icon:3,title:"客户信息管理"},function(index){
                layer.close(index);
                $.post(ctx+"/customer/delete",{id:data.data.id},function(result){
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
    function openAddOrUpdateCustomerDialog(id) {
        //iframe层
        let url=ctx + '/customer/toAddOrUpdateCustomerPage';
        let title='<h3>客户管理-添加客户</h3>';
        if(id){
            url+="?id="+id;
            title='<h3>客户管理-更新客户</h3>';
        }
        layui.layer.open({
            type: 2,
            title: title,
            area: ['600px', '600px'],
            content: url, //iframe的url,
            maxmin: true
        });

    }
});
