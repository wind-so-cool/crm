layui.use(['table','layer'],function(){
       var layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        table = layui.table;


       let tableIns = table.render({
              id:"roleTable",
              elem: '#roleList'
              , height: 'full-125',
              cellMinWidth: 95
              , url: ctx + '/role/list', //数据接口
              toolbar: '#toolbarDemo'
              , page: true //开启分页
              , cols: [[ //表头
                     {type: 'checkbox', align: 'center', fixed: true},
                     {field: 'id', title: '编号', sort: true, fixed: 'left', align: "center"}
                     , {field: 'roleName', title: '角色名称', align: 'center'}
                     , {field: 'roleRemark', title: '角色备注', align: 'center'}
                     , {field: 'createDate', title: '创建时间', align: 'center'}
                     , {field: 'updateDate', title: '更新时间', align: 'center'}
                     , {fixed: 'right', align: 'center', minWidth: '150', toolbar: '#roleListBar'}

              ]]
       });
       $(".search_btn").click(function () {
              tableIns.reload({
                     where: { //设定异步数据接口的额外参数，任意设
                            roleName: $("[name='roleName']").val()
                     }
                     , page: {
                            curr: 1 //重新从第 1 页开始
                     }
              });
       })
       table.on('toolbar(roles)', function (data) {
              if (data.event == 'add') {
                     openAddOrUpdateRoleDialog();
              }else if(data.event=="grant"){
                     openGrantPage(data);
              }
       })
       function openGrantPage(data){
              let roleData=table.checkStatus(data.config.id).data;
              if(roleData.length==0){
                     layer.msg("请选择要授权的角色",{icon:5});
                     return;
              }else if(roleData.length>1){
                     layer.msg("暂不支持批量授权",{icon:5});
                     return;
              }
              let url=ctx+"/role/toGrantPage?roleId="+roleData[0].id;
              let title="<h3>角色管理-授权</h3>"
              layui.layer.open({
                     type: 2,
                     title: title,
                     area: ['600px', '600px'],
                     content: url, //iframe的url,
                     maxmin: true
              });

       }
       function openAddOrUpdateRoleDialog(id) {
              //iframe层
              let url=ctx + '/role/toAddOrUpdatePage';
              let title='<h3>角色管理-添加角色</h3>';
              if(id){
                     url+="?id="+id;
                     title="<h3>角色管理-更新角色</h3>"
              }
              layui.layer.open({
                     type: 2,
                     title: title,
                     area: ['500px', '400px'],
                     content: url, //iframe的url,
                     maxmin: true
              });

       }
       table.on('tool(roles)', function (data) {
              if (data.event == 'edit') {
                     openAddOrUpdateRoleDialog(data.data.id);
              }else if (data.event == 'del') {
                     layer.confirm("确定删除记录吗?",{icon:3,title:"角色管理"},function(index){
                            layer.close(index);
                            $.post(ctx+"/role/delete",{id:data.data.id},function(result){
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
