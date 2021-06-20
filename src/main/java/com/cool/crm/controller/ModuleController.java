package com.cool.crm.controller;

import com.cool.crm.base.BaseController;
import com.cool.crm.base.ResultInfo;
import com.cool.crm.model.TreeModel;
import com.cool.crm.service.ModuleService;
import com.cool.crm.vo.Module;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("module")
public class ModuleController extends BaseController {

    @Resource
    private ModuleService moduleService;

    @GetMapping("queryAllModules")
    @ResponseBody
    public List<TreeModel> queryAllModules(Integer roleId){
        return moduleService.queryAllModules(roleId);
    }

    @GetMapping("list")
    @ResponseBody
    public Map<String,Object> queryModuleList(){
        return moduleService.queryModuleList();
    }

    @GetMapping("index")
    public String index(){
        return "module/module";
    }

    @PostMapping("add")
    @ResponseBody
    public ResultInfo add(Module module){
        moduleService.add(module);
        return success("资源添加成功");
    }

    @GetMapping("toAddPage")
    public String toAddPage(Integer grade, Integer parentId, HttpServletRequest request){
        request.setAttribute("grade",grade);
        request.setAttribute("parentId",parentId);
        return "module/add";
    }

    @PostMapping("update")
    @ResponseBody
    public ResultInfo update(Module module){
        moduleService.update(module);
        return success("资源修改成功");
    }
    @GetMapping("toUpdatePage")
    public String toUpdatePage(Integer id,HttpServletRequest request){
        Module module=moduleService.selectByPrimaryKey(id);
        request.setAttribute("module",module);
        return "module/update";
    }
    @PostMapping("delete")
    @ResponseBody
    public ResultInfo delete(Integer id){
        moduleService.delete(id);
        return success("删除菜单成功");
    }
}
