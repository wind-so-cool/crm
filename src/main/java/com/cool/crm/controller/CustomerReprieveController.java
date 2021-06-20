package com.cool.crm.controller;

import com.cool.crm.base.BaseController;
import com.cool.crm.base.ResultInfo;
import com.cool.crm.query.CustomerReprieveQuery;
import com.cool.crm.service.CustomerReprieveService;
import com.cool.crm.vo.CustomerReprieve;
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
 * @Date: 2021-04-13 11:13
 */
@Controller
@RequestMapping("customer_rep")
public class CustomerReprieveController extends BaseController {

    @Autowired
    private CustomerReprieveService customerReprieveService;

    @GetMapping("list")
    @ResponseBody
    public Map<String,Object> queryByParams(CustomerReprieveQuery customerReprieveQuery){
        return customerReprieveService.queryByParamsForTable(customerReprieveQuery);
    }

    @PostMapping("add")
    @ResponseBody
    public ResultInfo add(CustomerReprieve customerReprieve){
        customerReprieveService.add(customerReprieve);
        return success("添加暂缓数据成功");
    }

    @PostMapping("update")
    @ResponseBody
    public ResultInfo update(CustomerReprieve customerReprieve){
        customerReprieveService.update(customerReprieve);
        return success("更新暂缓数据成功");
    }

    @GetMapping("toAddOrUpdatePage")
    public String toAddOrUpdatePage(Integer lossId, Integer id, Model model){
        model.addAttribute("lossId",lossId);
        if(id!=null){
            CustomerReprieve customerRep=customerReprieveService.selectByPrimaryKey(id);
            model.addAttribute("customerRep",customerRep);
        }
        return "customerLoss/customer_rep_add_update";
    }

    @PostMapping("delete")
    @ResponseBody
    public ResultInfo delete(Integer id){
        customerReprieveService.delete(id);
        return success("删除暂缓数据成功");
    }

}
