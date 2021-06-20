package com.cool.crm.controller;

import com.cool.crm.base.BaseController;
import com.cool.crm.service.PermissionService;
import com.cool.crm.service.UserService;
import com.cool.crm.utils.CookieUtil;
import com.cool.crm.utils.UserIDBase64;
import com.cool.crm.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController extends BaseController {
    @Autowired
    private UserService userService;

    @Resource
    private PermissionService permissionService;
    /**
     * 系统登录页
     * @return
     */
    @RequestMapping("index")
    public String index(){
        return "index";
    }
    // 系统界面欢迎页
    @RequestMapping("welcome")
    public String welcome(){
        return "welcome";
    }
    /**
     * 后端管理主页面
     * @return
     */
    @RequestMapping("main")
    public String main(HttpServletRequest request){
        Integer userId= UserIDBase64.decoderUserID(CookieUtil.getCookieValue(request,"userIdStr"));
        User user=userService.selectByPrimaryKey(userId);
        request.getSession().setAttribute("user",user);
        List<String> permissions=permissionService.selectAclValByUserId(userId);
        request.getSession().setAttribute("permissions",permissions);
        return "main";
    }


}
