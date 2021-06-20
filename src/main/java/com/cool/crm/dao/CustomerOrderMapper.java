package com.cool.crm.dao;

import com.cool.crm.base.BaseMapper;
import com.cool.crm.vo.CustomerOrder;

public interface CustomerOrderMapper extends BaseMapper<CustomerOrder,Integer> {

    CustomerOrder queryLastOrderByCusId(Integer id);
}