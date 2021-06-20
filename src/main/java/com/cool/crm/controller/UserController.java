package com.cool.crm.controller;

import com.cool.crm.base.BaseController;
import com.cool.crm.base.ResultInfo;
import com.cool.crm.model.UserModel;
import com.cool.crm.query.UserQuery;
import com.cool.crm.service.UserService;
import com.cool.crm.utils.LoginUserUtil;
import com.cool.crm.vo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("user")
public class UserController extends BaseController {

    @Resource
    private UserService userService;
    @PostMapping("login")
    @ResponseBody
    public ResultInfo userLogin(String uname,String pwd){
        ResultInfo resultInfo=new ResultInfo();
        UserModel userModel= userService.userLogin(uname,pwd);
        resultInfo.setResult(userModel);
       /* try{

        }catch(ParamsException p){
            resultInfo.setCode(p.getCode());
            resultInfo.setMsg(p.getMsg());
            p.printStackTrace();
        }catch(Exception e){
            resultInfo.setCode(500);
            resultInfo.setMsg("登录失败");
            e.printStackTrace();
        }*/
        return resultInfo;
    }

    @PostMapping("updatePwd")
    @ResponseBody
    public ResultInfo updatePassword(HttpServletRequest request,String oldPwd, String newPwd,String repeatPwd){
        ResultInfo resultInfo=new ResultInfo();
        int userId= LoginUserUtil.releaseUserIdFromCookie(request);
        userService.updatePassword(userId,oldPwd,newPwd,repeatPwd);
       /* try{

        }catch(ParamsException p){
            resultInfo.setCode(p.getCode());
            resultInfo.setMsg(p.getMsg());
            p.printStackTrace();
        }catch(Exception e){
            resultInfo.setCode(500);
            resultInfo.setMsg("修改密码失败");
            e.printStackTrace();
        }*/
        return resultInfo;
    }

    @GetMapping("toPasswordPage")
    public String toPasswordPage(){
        return "user/password";
    }

    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> queryUserByParams(UserQuery userQuery){
        return userService.queryByParamsForTable(userQuery);
    }

    @GetMapping("index")
    public String index(){
        return "user/user";
    }

    @PostMapping("add")
    @ResponseBody
    public ResultInfo addUser(User user){
        userService.addUser(user);
        return success("用户添加成功");
    }

    @GetMapping("toAddOrUpdateUserPage")
    public String toAddOrUpdateUserPage(Integer id,HttpServletRequest request){
        if(id!=null){
            User user=userService.selectByPrimaryKey(id);
            request.setAttribute("userInfo",user);
        }
        return "user/add_update";
    }

    @PostMapping("update")
    @ResponseBody
    public ResultInfo updateUser(User user){
        userService.updateUser(user);
        return success("用户更新成功");
    }

    @PostMapping("delete")
    @ResponseBody
    public ResultInfo deleteUser(Integer[] ids){
        userService.deleteUser(ids);
        return success("删除用户成功");
    }

}
