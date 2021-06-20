package com.cool.crm.service;

import com.cool.crm.base.BaseService;
import com.cool.crm.dao.CustomerOrderMapper;
import com.cool.crm.vo.CustomerOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author 许俊青
 * @Date: 2021-04-10 13:48
 */
@Service
public class CustomerOrderService extends BaseService<CustomerOrder,Integer> {

    @Autowired
    private CustomerOrderMapper customerOrderMapper;


}
