package com.cool.crm.service;

import com.cool.crm.base.BaseService;
import com.cool.crm.dao.UserRoleMapper;
import com.cool.crm.vo.UserRole;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserRoleService extends BaseService<UserRole,Integer> {

    @Resource
    private UserRoleMapper userRoleMapper;
}
