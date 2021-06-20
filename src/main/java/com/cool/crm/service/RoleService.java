package com.cool.crm.service;

import com.cool.crm.base.BaseService;
import com.cool.crm.dao.ModuleMapper;
import com.cool.crm.dao.PermissionMapper;
import com.cool.crm.dao.RoleMapper;
import com.cool.crm.enums.ExceptionType;
import com.cool.crm.utils.AssertUtil;
import com.cool.crm.vo.Permission;
import com.cool.crm.vo.Role;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class RoleService extends BaseService<Role,Integer> {
    @Autowired
    private RoleMapper roleMapper;

    @Resource
    private PermissionMapper permissionMapper;

    @Resource
    private ModuleMapper moduleMapper;

    public List<Map<String,Object>> queryAllRoles(Integer userId){
        return roleMapper.queryAllRoles(userId);
    }

    @Transactional(rollbackFor = Exception.class)
    public void add(Role role) {
        AssertUtil.isTrue(StringUtils.isBlank(role.getRoleName()),"角色名称不能为空", ExceptionType.PARAMS);
        Role temp=roleMapper.selectByRoleName(role.getRoleName());
        AssertUtil.isTrue(temp!=null,"角色名称已存在",ExceptionType.PARAMS);
        role.setIsValid(1);
        role.setCreateDate(new Date());
        role.setUpdateDate(new Date());
        AssertUtil.isTrue(roleMapper.insertSelective(role)<1,"角色添加失败",ExceptionType.EXECUTION_FAIL);
    }

    @Transactional(rollbackFor = Exception.class)
    public void update(Role role) {
        AssertUtil.isTrue(role.getId()==null,"待更新记录不存在",ExceptionType.PARAMS);
        Role temp=roleMapper.selectByPrimaryKey(role.getId());
        AssertUtil.isTrue(temp==null,"待更新记录不存在",ExceptionType.PARAMS);
        temp=roleMapper.selectByRoleName(role.getRoleName());
        AssertUtil.isTrue(temp!=null&&!temp.getId().equals(role.getId()),"角色名已存在",ExceptionType.PARAMS);
        role.setUpdateDate(new Date());
        AssertUtil.isTrue(roleMapper.updateByPrimaryKeySelective(role)<1,"更新失败",ExceptionType.EXECUTION_FAIL);
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(Integer id) {
        AssertUtil.isTrue(id==null,"待删除记录不存在",ExceptionType.PARAMS);
        Role role=roleMapper.selectByPrimaryKey(id);
        AssertUtil.isTrue(role==null,"待删除记录不存在",ExceptionType.PARAMS);
        role.setIsValid(0);
        role.setUpdateDate(new Date());
        AssertUtil.isTrue(roleMapper.updateByPrimaryKeySelective(role)<1,"删除失败",ExceptionType.EXECUTION_FAIL);
    }

    @Transactional(rollbackFor = Exception.class)
    public void grant(Integer roleId, Integer[] mIds) {
        Integer count=permissionMapper.countByRoleId(roleId);
        if(count>0){
            AssertUtil.isTrue(!permissionMapper.deleteByRoleId(roleId).equals(count),"授权失败",ExceptionType.EXECUTION_FAIL);
        }
        if(ArrayUtils.isNotEmpty(mIds)){
            List<Permission> permissionList=new ArrayList<>();
            for(Integer mId:mIds){
                Permission permission=new Permission();
                permission.setAclValue(moduleMapper.selectByPrimaryKey(mId).getOptValue());
                permission.setCreateDate(new Date());
                permission.setUpdateDate(new Date());
                permission.setModuleId(mId);
                permission.setRoleId(roleId);
                permissionList.add(permission);
            }
            AssertUtil.isTrue(permissionMapper.insertBatch(permissionList)!=permissionList.size(),"授权失败",ExceptionType.EXECUTION_FAIL);
        }


    }
}
