package com.cool.crm.dao;

import com.cool.crm.base.BaseMapper;
import com.cool.crm.vo.Role;

import java.util.List;
import java.util.Map;

public interface RoleMapper extends BaseMapper<Role,Integer>{

    List<Map<String,Object>> queryAllRoles(Integer userId);

    Role selectByRoleName(String roleName);
}