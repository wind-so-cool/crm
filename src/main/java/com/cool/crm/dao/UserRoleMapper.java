package com.cool.crm.dao;

import com.cool.crm.base.BaseMapper;
import com.cool.crm.vo.UserRole;

public interface UserRoleMapper extends BaseMapper<UserRole,Integer> {

    Integer countByUserId(Integer userId);

    Integer deleteByUserId(Integer userId);
}