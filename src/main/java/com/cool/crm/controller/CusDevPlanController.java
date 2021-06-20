package com.cool.crm.controller;

import com.cool.crm.base.BaseController;
import com.cool.crm.base.ResultInfo;
import com.cool.crm.query.CusDevPlanQuery;
import com.cool.crm.service.CusDevPlanService;
import com.cool.crm.service.SaleChanceService;
import com.cool.crm.vo.CusDevPlan;
import com.cool.crm.vo.SaleChance;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("cus_dev_plan")
public class CusDevPlanController extends BaseController {

    @Resource
    private SaleChanceService saleChanceService;

    @Resource
    private CusDevPlanService cusDevPlanService;

    @RequestMapping("index")
    public String index(){
        return "cusDevPlan/cus_dev_plan";
    }

    @RequestMapping("toCusDevPlanPage")
    public String toCusDevPlanPage(Integer id, HttpServletRequest request){
        SaleChance saleChance=saleChanceService.selectByPrimaryKey(id);
        request.setAttribute("saleChance",saleChance);
        return "cusDevPlan/cus_dev_plan_data";
    }
    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> queryCusDevPlanByParams(CusDevPlanQuery cusDevPlanQuery){
        return cusDevPlanService.queryByParamsForTable(cusDevPlanQuery);
    }

    @PostMapping("add")
    @ResponseBody
    public ResultInfo addCusDevPlan(CusDevPlan cusDevPlan){
        cusDevPlanService.addCusDevPlan(cusDevPlan);
        return success("计划项添加成功!");
    }
    @GetMapping("toAddOrUpdateCusDevPlanPage")
    public String toAddOrUpdateCusDevPlanPage(HttpServletRequest request,Integer saleChanceId,Integer id){
        if(id!=null){
            CusDevPlan cusDevPlan=cusDevPlanService.selectByPrimaryKey(id);
            request.setAttribute("cusDevPlan",cusDevPlan);
        }
        request.setAttribute("sid",saleChanceId);
      return "cusDevPlan/add_update";
    }
    @PostMapping("update")
    @ResponseBody
    public ResultInfo updateCusDevPlan(CusDevPlan cusDevPlan){
        cusDevPlanService.updateCusDevPlan(cusDevPlan);
        return success("计划项更新成功!");
    }
    @PostMapping("delete")
    @ResponseBody
    public ResultInfo deleteCusDevPlan(Integer id){
        cusDevPlanService.deleteCusDevPlan(id);
        return success("计划项删除成功!");
    }
    @PostMapping("updateSaleChanceDevResult")
    @ResponseBody
    public ResultInfo updateSaleChanceDevResult(Integer saleChanceId,Integer devResult){
        saleChanceService.updateSaleChanceDevResult(saleChanceId,devResult);
        return success("开发状态更新成功!");
    }
}
