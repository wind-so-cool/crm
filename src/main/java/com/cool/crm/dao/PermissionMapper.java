package com.cool.crm.dao;

import com.cool.crm.base.BaseMapper;
import com.cool.crm.vo.Permission;

import java.util.List;

public interface PermissionMapper extends BaseMapper<Permission,Integer> {


    Integer countByRoleId(Integer roleId);

    Integer deleteByRoleId(Integer roleId);

    List<Integer> selectModuleIdsByRoleId(Integer roleId);


    List<String> selectAclValByUserId(Integer userId);

    int countByModuleId(Integer id);

    int deleteByModuleId(Integer id);
}