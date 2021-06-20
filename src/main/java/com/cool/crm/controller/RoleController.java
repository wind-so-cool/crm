package com.cool.crm.controller;

import com.cool.crm.base.BaseController;
import com.cool.crm.base.ResultInfo;
import com.cool.crm.query.RoleQuery;
import com.cool.crm.service.RoleService;
import com.cool.crm.vo.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("role")
public class RoleController extends BaseController {
    @Autowired
    private RoleService roleService;

    @RequestMapping("/queryAllRoles")
    @ResponseBody
    List<Map<String,Object>> queryAllRoles(Integer userId){
        return roleService.queryAllRoles(userId);
    }

    @GetMapping("list")
    @ResponseBody
    public Map<String,Object> queryRolesByParams(RoleQuery roleQuery){
        return roleService.queryByParamsForTable(roleQuery);

    }
    @GetMapping("index")
    public String index(){
        return "role/role";

    }
    @PostMapping("add")
    @ResponseBody
    public ResultInfo add(Role role){
        roleService.add(role);
        return success("角色添加成功");
    }

    @GetMapping("toAddOrUpdatePage")
    public String toAddOrUpdatePage(Integer id, HttpServletRequest request){
        if(id!=null){
            Role role=roleService.selectByPrimaryKey(id);
            request.setAttribute("role",role);
        }
        return "role/add_update";
    }

    @PostMapping("update")
    @ResponseBody
    public ResultInfo update(Role role){
        roleService.update(role);
        return success("更新成功");
    }

    @PostMapping("delete")
    @ResponseBody
    public ResultInfo delete(Integer id){
        roleService.delete(id);
        return success("删除成功");
    }

    @GetMapping("toGrantPage")
    public String toGrantPage(Integer roleId,HttpServletRequest request){
        request.setAttribute("roleId",roleId);
        return "role/grant";
    }

    @PostMapping("grant")
    @ResponseBody
    public ResultInfo grant(Integer roleId,Integer[] mIds){
        roleService.grant(roleId,mIds);
        return success("授权成功");
    }
}
