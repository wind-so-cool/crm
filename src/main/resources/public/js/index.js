layui.use(['form','jquery','jquery_cookie'], function () {
    var form = layui.form,
        layer = layui.layer,
        $ = layui.jquery,
        $ = layui.jquery_cookie($);


    /**
     * 表单submit提交
     *  form.on('submit(按钮的lay-filter属性值)', function(data){
     *
     *       return false; //阻止表单跳转。
     *  });
     */
    form.on('submit(login)', function (data) {

        console.log(data.field) // 当前容器的全部表单字段，名值对形式：{name: value}

        /* 表单元素的非空校验 */

        /* 发送ajax请求，传递用户姓名与密码，执行用户登录操作 */
        $.ajax({
            url: ctx + "/user/login",
            type: "post",
            data: {
                uname: data.field.username,
                pwd: data.field.password
            }, success: function (data) {
                if (data.code == 200) {
                    layer.msg("登录成功", function () {
                        if($("#rememberMe").prop("checked")){
                            $.cookie("userIdStr", data.result.userIdStr,{expires:7});
                            $.cookie("username", data.result.username,{expires:7});
                            $.cookie("trueName", data.result.trueName,{expires:7});
                        }else{
                            $.cookie("userIdStr", data.result.userIdStr);
                            $.cookie("username", data.result.username);
                            $.cookie("trueName", data.result.trueName);
                        }

                        window.location.href = ctx + "/main";
                    })
                } else {
                    layer.msg(data.msg, {icon: 5});
                }
            }

        })
        return false;

    });
});