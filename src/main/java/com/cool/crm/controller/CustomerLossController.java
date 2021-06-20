package com.cool.crm.controller;

import com.cool.crm.base.BaseController;
import com.cool.crm.base.ResultInfo;
import com.cool.crm.query.CustomerLossQuery;
import com.cool.crm.service.CustomerLossService;
import com.cool.crm.utils.AssertUtil;
import com.cool.crm.vo.CustomerLoss;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @Author 许俊青
 * @Date: 2021-04-13 10:15
 */
@Controller
@RequestMapping("customer_loss")
public class CustomerLossController extends BaseController {

    @Autowired
    private CustomerLossService customerLossService;

    @GetMapping("list")
    @ResponseBody
    public Map<String,Object> queryByParams(CustomerLossQuery customerLossQuery){
        return customerLossService.queryByParamsForTable(customerLossQuery);
    }

    @GetMapping("index")
    public String index(){
        return "customerLoss/customer_loss";
    }
    @GetMapping("toCustomerLossPage")
    public String toCustomerLossPage(Integer id, Model model){
        CustomerLoss customerLoss=customerLossService.selectByPrimaryKey(id);
        model.addAttribute("customerLoss",customerLoss);
        return "customerLoss/customer_rep";
    }

    @PostMapping("updateStateById")
    @ResponseBody
    public ResultInfo updateStateById(Integer id,String lossReason){
        customerLossService.updateStateById(id,lossReason);
        return success("确认流失成功!");
    }
}
