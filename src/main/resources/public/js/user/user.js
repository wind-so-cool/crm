layui.use(['table','layer'],function(){
    var layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        table = layui.table;

    let tableIns = table.render({
        id:"userTable",
        elem: '#userList'
        , height: 'full-125',
        cellMinWidth: 95
        , url: ctx + '/user/list', //数据接口
        toolbar: '#toolbarDemo'
        , page: true //开启分页
        , cols: [[ //表头
            {type: 'checkbox', align: 'center', fixed: true},
            {field: 'id', title: '编号', sort: true, fixed: 'left', align: "center"}
            , {field: 'userName', title: '用户名', align: 'center'}
            , {field: 'trueName', title: '真实姓名', align: 'center'}
            , {field: 'email', title: '邮箱', align: 'center'}
            , {field: 'phone', title: '手机号码', align: 'center'}
            , {field: 'createDate', title: '创建时间', align: 'center'}
            , {field: 'updateDate', title: '更新时间', align: 'center'}
            , {fixed: 'right', align: 'center', minWidth: '150', toolbar: '#userListBar'}

        ]]
    });

    $(".search_btn").click(function () {
        tableIns.reload({
            where: { //设定异步数据接口的额外参数，任意设
                userName: $("[name=userName]").val()
                , email: $("[name=email]").val()
                , phone: $("[name=phone]").val()
            }
            , page: {
                curr: 1 //重新从第 1 页开始
            }
        });
    })
    table.on('toolbar(users)', function (data) {
        if (data.event == 'add') {
            openAddOrUpdateUserDialog();
        } else if (data.event == 'del') {
            deleteUser(data);
        }
    })
    function deleteUser(data){
        let userData=table.checkStatus("userTable").data;
        if(userData.length==0){
            layer.msg("请选择要删除的数据",{icon:5});
            return;
        }
        layer.confirm("确定删除选中记录吗?",{icon:3,title:"用户管理"},function(index){
            layer.close(index);
            let ids="";
            for(let i=0;i<userData.length;i++){
                ids+="ids="+userData[i].id;
                if(i!=userData.length-1){
                    ids+="&";
                }
            }
            $.post(ctx+"/user/delete",ids,function(result){
                if(result.code==200){
                    layer.msg(result.msg,{icon:6});
                    tableIns.reload();
                }else{
                    layer.msg(result.msg,{icon:5});
                }
            })
        })
    }
    table.on('tool(users)', function (data) {
        if (data.event == 'edit') {
            openAddOrUpdateUserDialog(data.data.id);
        } else if (data.event = 'del') {
            layer.confirm("确定删除记录吗?",{icon:3,title:"用户管理"},function(index){
                layer.close(index);
                $.post(ctx+"/user/delete",{ids:data.data.id},function(result){
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
    function openAddOrUpdateUserDialog(id) {
        //iframe层
        let url=ctx + '/user/toAddOrUpdateUserPage';
        let title='<h3>用户管理-添加用户</h3>';
        if(id!=null&&id!=''){
            url+="?id="+id;
            title="<h3>用户管理-更新用户</h3>"
        }

        layui.layer.open({
            type: 2,
            title: title,
            area: ['650px', '600px'],
            content: url, //iframe的url,
            maxmin: true
        });

    }
});