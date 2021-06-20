package com.cool.crm.controller;

import com.cool.crm.base.BaseController;
import com.cool.crm.query.CustomerQuery;
import com.cool.crm.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @Author 许俊青
 * @Date: 2021-04-17 15:32
 */
@Controller
@RequestMapping("report")
public class CustomerReportController extends BaseController {

    @Autowired
    private CustomerService customerService;

    @RequestMapping("queryCusContriByParams")
    @ResponseBody
    public Map<String, Object> queryCusContriByParams(CustomerQuery customerQuery) {
        return customerService.queryCusContriByParams(customerQuery);
    }

    @GetMapping("{type}")
    public String toCusContriPage(@PathVariable Integer type) {
        switch (type) {
            //贡献
            case 0:

                return "report/customer_contri";
            //构成
            case 1:

                return "report/customer_make";
            //服务
            case 2:

                return "";
            //流失
            case 3:
                return "report/customer_loss";
            default:
                return "";
        }
    }

    @GetMapping("countCustomerMake")
    @ResponseBody
    public Map<String,Object> countCustomerMake(){
        return customerService.countCustomerMake();
    }

    @GetMapping("countCustomerMake2")
    @ResponseBody
    public Map<String,Object> countCustomerMake2(){
        return customerService.countCustomerMake2();
    }
}
