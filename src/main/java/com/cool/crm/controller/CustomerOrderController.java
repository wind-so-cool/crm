package com.cool.crm.controller;

import com.cool.crm.base.BaseController;
import com.cool.crm.query.CustomerOrderQuery;
import com.cool.crm.service.CustomerOrderService;
import com.cool.crm.vo.CustomerOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @Author 许俊青
 * @Date: 2021-04-10 13:47
 */
@Controller
@RequestMapping("order")
public class CustomerOrderController extends BaseController {

    @Autowired
    private CustomerOrderService customerOrderService;

    @GetMapping("list")
    @ResponseBody
    public Map<String,Object> queryByParams(CustomerOrderQuery customerOrderQuery){
        return customerOrderService.queryByParamsForTable(customerOrderQuery);
    }

    @GetMapping("toOrderDetailsPage")
    public String toOrderDetailsPage(Integer orderId, Model model){
        CustomerOrder order=customerOrderService.selectByPrimaryKey(orderId);
        model.addAttribute("order",order);
        return "customer/customer_order_detail";
    }
}
