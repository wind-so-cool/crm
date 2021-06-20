package com.cool.crm.controller;

import com.cool.crm.base.BaseController;
import com.cool.crm.query.OrderDetailsQuery;
import com.cool.crm.service.OrderDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @Author 许俊青
 * @Date: 2021-04-10 14:21
 */
@RequestMapping("order_details")
@Controller
public class OrderDetailsController extends BaseController {

    @Autowired
    private OrderDetailsService orderDetailsService;

    @GetMapping("list")
    @ResponseBody
    public Map<String,Object> queryByParams(OrderDetailsQuery orderDetailsQuery){
        return orderDetailsService.queryByParamsForTable(orderDetailsQuery);
    }


}
