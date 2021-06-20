package com.cool.crm.controller;

import com.cool.crm.base.BaseController;
import com.cool.crm.base.ResultInfo;
import com.cool.crm.query.CustomerServeQuery;
import com.cool.crm.service.CustomerServeService;
import com.cool.crm.service.UserService;
import com.cool.crm.utils.LoginUserUtil;
import com.cool.crm.vo.CustomerServe;
import com.cool.crm.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @Author 许俊青
 * @Date: 2021-04-13 15:25
 */
@Controller
@RequestMapping("customer_serve")
public class CustomerServeController extends BaseController {

    @Autowired
    private CustomerServeService customerServeService;

    @Autowired
    private UserService userService;

    @GetMapping("list")
    @ResponseBody
    public Map<String, Object> queryByParams(CustomerServeQuery customerServeQuery, Integer flag, HttpServletRequest request) {
        if(flag!=null&&flag.equals(1)){
            customerServeQuery.setAssigner(LoginUserUtil.releaseUserIdFromCookie(request));
        }
        return customerServeService.queryByParamsForTable(customerServeQuery);
    }

    @GetMapping("index/{type}")
    public String index(@PathVariable int type) {
        switch (type) {
            case 1:
                return "customerServe/customer_serve";
            case 2:
                return "customerServe/customer_serve_assign";
            case 3:
                return "customerServe/customer_serve_proce";
            case 4:
                return "customerServe/customer_serve_feed_back";
            case 5:
                return "customerServe/customer_serve_archive";
            default:
                return "";
        }

    }

    @GetMapping("toAddCustomerServePage")
    public String toAddCustomerServePage(){
        return "customerServe/customer_serve_add";
    }

    @PostMapping("add")
    @ResponseBody
    public ResultInfo add(CustomerServe customerServe){
        customerServeService.add(customerServe);
        return success("服务添加成功");
    }

    @PostMapping("update")
    @ResponseBody
    public ResultInfo update(CustomerServe customerServe){
        customerServeService.update(customerServe);
        return success("服务更新成功");
    }

    @GetMapping("toAssignPage")
    public String toAssignPage(Integer id, Model model){
        model.addAttribute("customerServe",customerServeService.selectByPrimaryKey(id));
        return "customerServe/customer_serve_assign_add";
    }

    @GetMapping("queryAllCustomerMgr")
    @ResponseBody
    public List<User> queryAllCustomerMgr(){
        return userService.queryAllCustomerMgr();
    }

    @GetMapping("toProcePage")
    public String toProcePage(Integer id, Model model){
        model.addAttribute("customerServe",customerServeService.selectByPrimaryKey(id));
        return "customerServe/customer_serve_proce_add";
    }
    @GetMapping("toFeedBackPage")
    public String toFeedBackPage(Integer id, Model model){
        model.addAttribute("customerServe",customerServeService.selectByPrimaryKey(id));
        return "customerServe/customer_serve_feed_back_add";
    }
}
