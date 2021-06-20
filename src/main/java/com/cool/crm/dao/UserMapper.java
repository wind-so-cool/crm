package com.cool.crm.dao;

import com.cool.crm.base.BaseMapper;
import com.cool.crm.vo.User;

import java.util.List;

public interface UserMapper extends BaseMapper<User,Integer> {

    User queryUserByName(String username);

    List<User> queryAllSales();

    List<User> queryAllCustomerMgr();
}