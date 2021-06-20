package com.cool.crm.dao;

import com.cool.crm.base.BaseMapper;
import com.cool.crm.query.CustomerQuery;
import com.cool.crm.vo.Customer;

import java.util.List;
import java.util.Map;

public interface CustomerMapper extends BaseMapper<Customer,Integer> {

     Customer selectByName(String name);

    List<Customer> queryCustomerLossList();

    int updateCustomerStateByCusIds(List<Integer> customerLossIds);

    List<Map<String, Object>> queryCusContriByParams(CustomerQuery customerQuery);

    List<Map<String, Object>> countCustomerMake();
}