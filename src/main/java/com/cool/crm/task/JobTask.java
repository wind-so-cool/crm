package com.cool.crm.task;

import com.cool.crm.service.CustomerService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author 许俊青
 * @Date: 2021-04-11 17:42
 */
@Component
public class JobTask{
    @Resource
    private CustomerService customerService;

    //@Scheduled(cron="0/2 * * * * ?")
    public void job(){
        System.out.println("定时任务开始执行:"+new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        customerService.updateCustomerLossState();
    }
}
