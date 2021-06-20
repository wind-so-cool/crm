$(function(){
    loadModuleData();
})
let ztreeObj;
function loadModuleData(){
    var setting = {
        // 使用复选框
        check: {
            enable: true
        },
        // 使用简单的JSON数据
        data: {
            simpleData: {
                enable: true
            }
        },
        callback: {
            // onCheck函数：当 checkbox/radio 被选中或取消选中时触发的函数
            onCheck: zTreeOnCheck
        }
    }
    $.get(ctx+"/module/queryAllModules?roleId="+$("[name='roleId']").val(),function(data){
        ztreeObj = $.fn.zTree.init($("#test1"), setting, data);
    })
}
function zTreeOnCheck(event, treeId, treeNode) {
    let nodes=ztreeObj.getCheckedNodes(true);
    let mIds="";
    let roleId=$("[name='roleId']").val();
    if(nodes.length>0){

        for(let i=0;i<nodes.length;i++){
            mIds+="mIds="+nodes[i].id;
            if(i<nodes.length-1){
                mIds+='&';
            }
        }


    }
    $.post(ctx+"/role/grant","roleId="+roleId+"&"+mIds,function(data){
        layui.use('layer',function(){
            let layer=layui.layer;
            if(data.code==200){
                layer.msg(data.msg,{icon:6})
            }else{
                layer.msg(data.msg,{icon:5})
            }
        })

    })
}
