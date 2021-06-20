layui.use(['table','layer',"form"],function(){
    var layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        table = layui.table;

    // 暂缓列表展示
    var  tableIns = table.render({
        elem: '#customerRepList',
        url : ctx+'/customer_rep/list?lossId='+$("input[name='id']").val(),
        cellMinWidth : 95,
        page : true,
        height : "full-125",
        limits : [10,15,20,25],
        limit : 10,
        toolbar: "#toolbarDemo",
        id : "customerRepListTable",
        cols : [[
            {type: "checkbox", fixed:"center"},
            {field: "id", title:'编号',fixed:"true"},
            {field: 'measure', title: '暂缓措施',align:"center"},
            {field: 'createDate', title: '创建时间',align:"center"},
            {field: 'updateDate', title: '更新时间',align:"center"},
            {title: '操作',fixed:"right",align:"center", minWidth:150,templet:"#customerRepListBar"}
        ]]
    });

    table.on('toolbar(customerReps)', function (data) {
        if (data.event == 'add') {
            openAddOrUpdateDialog();
        }else if(data.event=="confirm"){
            layer.confirm("确定标记当前客户为确认流失吗?",{icon:3,title:"流失管理"},function(index){
                layer.close(index);
                layer.prompt({title: '请填写流失原因', formType: 2}, function(text, index){
                    layer.close(index);
                      $.post(ctx+"/customer_loss/updateStateById",{id:$("[name='id']").val(),lossReason:text},function(result){
                       if(result.code==200){
                           layer.msg(result.msg,{icon:6});
                           parent.location.reload();
                       }else{
                           layer.msg(result.msg,{icon:5});
                       }
                   })
                });

            })
        }
    })
    table.on('tool(customerReps)', function (data) {
        if (data.event == 'edit') {
            openAddOrUpdateDialog(data.data.id);
        }else if(data.event=="del"){
            deleteCustomerReprieve(data);
        }
    })
    function deleteCustomerReprieve(data){
        layer.confirm("确定删除记录吗?",{icon:3,title:"流失管理"},function(index){
            layer.close(index);
            $.post(ctx+"/customer_rep/delete",{id:data.data.id},function(result){
                if(result.code==200){
                    layer.msg(result.msg,{icon:6});
                    tableIns.reload();
                }else{
                    layer.msg(result.msg,{icon:5});
                }
            })
        })
    }
    function openAddOrUpdateDialog(id) {
        //iframe层
        let url=ctx + '/customer_rep/toAddOrUpdatePage?lossId='+$("[name='id']").val();
        let title='<h3>流失管理-添加暂缓数据</h3>';
        if(id!=null&&id!=''){
            url+="&id="+id;
            title="<h3>流失管理-更新暂缓数据</h3>"
        }

        layui.layer.open({
            type: 2,
            title: title,
            area: ['400px', '300px'],
            content: url, //iframe的url,
            maxmin: true
        });

    }



});
