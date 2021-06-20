layui.use(['table','layer'],function(){
    var layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        table = layui.table;

    let tableIns = table.render({
        id:"cusDevPlanDataTable",
        elem: '#cusDevPlanList'
        , height: 'full-125',
        cellMinWidth: 95
        , url: ctx + '/cus_dev_plan/list?saleChanceId='+$("[name='id']").val(), //数据接口
        toolbar: '#toolbarDemo'
        , page: true //开启分页
        , cols: [[ //表头
            {type: 'checkbox', align: 'center', fixed: true},
            {field: 'id', title: '编号', sort: true, fixed: 'left', align: "center"}
            , {field: 'planItem', title: '计划项', align: 'center'}
            , {field: 'planDate', title: '计划时间', align: 'center'}
            , {field: 'exeAffect', title: '执行效果', align: 'center'}
            , {field: 'createDate', title: '创建时间', align: 'center'}
            , {field: 'updateDate', title: '更新时间', align: 'center'}
            , {fixed: 'right', align: 'center', minWidth: '150', toolbar: '#cusDevPlanListBar'}

        ]]
    });

    table.on('toolbar(cusDevPlans)', function (data) {
        if (data.event == 'add') {
            openAddOrUpdateCusDevPlanDialog();
        } else if (data.event == 'success') {
            updateSaleChanceDevResult(2);
        }else if (data.event == 'failed') {
            updateSaleChanceDevResult(3);
        }
    })
    function updateSaleChanceDevResult(devResult){
        layer.confirm("您确认操作吗?",{icon:3,title:'营销机会管理'},function(index){
            $.post(ctx+"/cus_dev_plan/updateSaleChanceDevResult",{saleChanceId:$("[name='id']").val(),devResult:devResult},function(result){
                if(result.code==200){
                    layer.msg(result.msg,{icon:6});
                    parent.location.reload();
                }else{
                    layer.msg(result.msg,{icon:5})
                }
            })
        })
    }
    table.on("tool(cusDevPlans)",function(data){
        if(data.event=="edit"){
            openAddOrUpdateCusDevPlanDialog(data.data.id);
        }else if(data.event=='del'){
            deleteCusDevPlan(data.data.id);
        }
    })
    function deleteCusDevPlan(id){
        layer.confirm("你确认删除吗?",{icon:3,title:"计划项数据管理"},function(index){
            $.post(ctx+"/cus_dev_plan/delete",{id:id},function(result){
                if(result.code==200){
                    layer.msg(result.msg,{icon:6});
                    tableIns.reload();
                }else{
                    layer.msg(result.msg,{icon:5});
                }
            })
        })
    }
    function openAddOrUpdateCusDevPlanDialog(id){
        let title="计划项管理-添加计划项";
        let url=ctx+"/cus_dev_plan/toAddOrUpdateCusDevPlanPage?saleChanceId="+$("[name='id']").val();
        if(id!=null&&id!=''){
            title="计划项管理-更新计划项";
            url+="&id="+id;
        }
        layui.layer.open({
            type: 2,
            title: title,
            area: ['500px', '500px'],
            content: url, //iframe的url,
            maxmin: true
        });
    }


});
