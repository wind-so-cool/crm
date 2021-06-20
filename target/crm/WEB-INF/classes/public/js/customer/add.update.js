layui.use(['form', 'layer'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery;

    $("#closeBtn").click(function () {
        var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
        parent.layer.close(index); //再执行关闭
    })

    form.on("submit(addOrUpdateCustomer)",function(data){
        let url=ctx+"/customer/add";
        if($("[name='id']").val()){
            url=ctx+"/customer/update";
        }
        var index = layer.msg("数据提交中,请稍后...",{
            icon:16, // 图标
            time:false, // 不关闭
            shade:0.8 // 设置遮罩的透明度
        });
        $.post(url,data.field,function(result){
            if(result.code==200){
                layer.msg(result.msg,{icon:6})
                layer.close(index);
                layer.closeAll("iframe");
                parent.location.reload();
            }else{
                layer.msg(result.msg,{icon:5})
            }
        })
        return false;
    })


});