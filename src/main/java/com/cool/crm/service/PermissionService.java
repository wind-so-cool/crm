package com.cool.crm.service;

import com.cool.crm.base.BaseService;
import com.cool.crm.dao.PermissionMapper;
import com.cool.crm.vo.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionService extends BaseService<Permission,Integer> {

    @Autowired
    private PermissionMapper permissionMapper;

    public List<String> selectAclValByUserId(Integer userId) {
        return permissionMapper.selectAclValByUserId(userId);
    }
}