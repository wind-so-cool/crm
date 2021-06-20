layui.use(['form','jquery','jquery_cookie'], function () {
    var form = layui.form,
        layer = layui.layer,
        $ = layui.jquery,
        $ = layui.jquery_cookie($);


    form.on("submit(saveBtn)",function(data){
        $.ajax({
            url:ctx+"/user/updatePwd",
            type:"post",
            data:{
                oldPwd:data.field.old_password,
                newPwd:data.field.new_password,
                repeatPwd:data.field.again_password,
            },success:function(result){
                if(result.code==200){
                    layer.msg("登录成功,系统将在三秒后退出...",function(){
                        $.removeCookie("userIdStr",{domain:"localhost",path:"/crm"});
                        $.removeCookie("trueName",{domain:"localhost",path:"/crm"});
                        $.removeCookie("username",{domain:"localhost",path:"/crm"});
                        parent.location.href=ctx+"/index";
                    })
                }else{
                    layer.msg(result.msg,{icon:5})
                }
            }
        })
    })
});