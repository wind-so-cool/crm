package com.cool.crm.controller;

import com.cool.crm.annotation.RequiredPermission;
import com.cool.crm.base.BaseController;
import com.cool.crm.base.ResultInfo;
import com.cool.crm.enums.StateStatus;
import com.cool.crm.query.SaleChanceQuery;
import com.cool.crm.service.SaleChanceService;
import com.cool.crm.service.UserService;
import com.cool.crm.utils.CookieUtil;
import com.cool.crm.utils.LoginUserUtil;
import com.cool.crm.vo.SaleChance;
import com.cool.crm.vo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("sale_chance")
public class SaleChanceController extends BaseController {

    @Resource
    private SaleChanceService saleChanceService;

    @Resource
    private UserService userService;

    @RequiredPermission(code="101001")
    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> querySaleChanceByParams(SaleChanceQuery saleChanceQuery,Integer flag,HttpServletRequest request){
        if(flag!=null&&flag.equals(1)){
            saleChanceQuery.setState(StateStatus.STATED.getType());
            Integer userId= LoginUserUtil.releaseUserIdFromCookie(request);
            saleChanceQuery.setAssignMan(String.valueOf(userId));

        }
        return saleChanceService.queryByParamsForTable(saleChanceQuery);
    }
    @RequiredPermission(code="1010")
    @RequestMapping("index")
    public String index(){
        return "saleChance/sale_chance";
    }

    @RequiredPermission(code="101002")
    @PostMapping("add")
    @ResponseBody
    public ResultInfo addSaleChance(SaleChance saleChance, HttpServletRequest request){
        String username= CookieUtil.getCookieValue(request,"username");
        saleChance.setCreateMan(username);
        saleChanceService.addSaleChance(saleChance);
        return success("添加营销机会成功");
    }

    @RequestMapping("toSaleChancePage")
    public String toSaleChancePage(Integer id,HttpServletRequest request){
        List<User> userList=userService.queryAllSales();
        request.setAttribute("userList",userList);
        if(id!=null){
            SaleChance saleChance=saleChanceService.selectByPrimaryKey(id);
            request.setAttribute("saleChance",saleChance);
        }
        return "saleChance/add_update";
    }


    @GetMapping("queryAllSales")
    @ResponseBody
    public List<User> queryAllSales(){
        return userService.queryAllSales();
    }

    @RequiredPermission(code="101004")
    @PostMapping("update")
    @ResponseBody
    public ResultInfo updateSaleChance(SaleChance saleChance){
        saleChanceService.updateSaleChance(saleChance);
        return success("更新营销机会成功");
    }
    @RequiredPermission(code="101003")
    @PostMapping("delete")
    @ResponseBody
    public ResultInfo deleteSaleChance(Integer[] ids){
        saleChanceService.deleteSaleChance(ids);
        return success("删除营销机会成功");
    }
}
