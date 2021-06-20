package com.cool.crm.controller;

import com.cool.crm.base.BaseController;
import com.cool.crm.base.ResultInfo;
import com.cool.crm.query.CustomerQuery;
import com.cool.crm.service.CustomerService;
import com.cool.crm.vo.Customer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

@Controller
@RequestMapping("customer")
public class CustomerController extends BaseController {

    @Resource
    private CustomerService customerService;

    @GetMapping("list")
    @ResponseBody
    public Map<String, Object> queryByParams(CustomerQuery customerQuery) {
        return customerService.queryByParamsForTable(customerQuery);

    }

    @GetMapping("index")
    public String index() {
        return "customer/customer";
    }

    @PostMapping("add")
    @ResponseBody
    public ResultInfo add(Customer customer) {
        customerService.add(customer);
        return success("添加成功");
    }

    @GetMapping("toAddOrUpdateCustomerPage")
    public String toAddOrUpdateCustomerPage(Integer id, Model model) {
        if(id!=null){
            Customer customer=customerService.selectByPrimaryKey(id);
            model.addAttribute("customer",customer);
        }
        return "customer/add_update";
    }

    @PostMapping("update")
    @ResponseBody
    public ResultInfo update(Customer customer) {
        customerService.update(customer);
        return success("更新成功!");
    }
    @PostMapping("delete")
    @ResponseBody
    public ResultInfo delete(Integer id) {
        customerService.delete(id);
        return success("删除成功!");
    }
    @GetMapping("toCustomerOrderPage")
    public String toCustomerOrderPage(Integer cusId,Model model) {
        Customer customer=customerService.selectByPrimaryKey(cusId);
        model.addAttribute("customer",customer);
        return "customer/customer_order";
    }

}
