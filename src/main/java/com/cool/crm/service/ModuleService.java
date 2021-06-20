package com.cool.crm.service;

import com.cool.crm.base.BaseService;
import com.cool.crm.dao.ModuleMapper;
import com.cool.crm.dao.PermissionMapper;
import com.cool.crm.enums.ExceptionType;
import com.cool.crm.model.TreeModel;
import com.cool.crm.utils.AssertUtil;
import com.cool.crm.vo.Module;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ModuleService extends BaseService<Module,Integer> {

    @Resource
    private PermissionMapper permissionMapper;

    @Resource
    private ModuleMapper moduleMapper;

    public List<TreeModel> queryAllModules(Integer roleId) {
        List<TreeModel> moduleList=moduleMapper.queryAllModules();
        List<Integer> moduleIds=permissionMapper.selectModuleIdsByRoleId(roleId);
        moduleList.forEach(m->{
            if(moduleIds.contains(m.getId())){
                m.setChecked(true);
            }
        });
        return moduleList;
    }

    public Map<String, Object> queryModuleList() {
        Map<String,Object> result = new HashMap<String,Object>();
        List<Module> moduleList=moduleMapper.queryModuleList();
        result.put("count",moduleList.size());
        result.put("data",moduleList);
        result.put("code",0);
        result.put("msg","");
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    public void add(Module module){
        Integer grade=module.getGrade();
        AssertUtil.isTrue(grade==null,"层级不能为空",ExceptionType.PARAMS);
        AssertUtil.isTrue(!(grade.equals(0)||grade.equals(1)||grade.equals(2)),"层级必须为有效值(0-2)",ExceptionType.PARAMS);
        String moduleName=module.getModuleName();
        AssertUtil.isTrue(StringUtils.isBlank(moduleName),"模块名称不能为空", ExceptionType.PARAMS);
        AssertUtil.isTrue(!(null==moduleMapper.queryByModuleName(moduleName)),"模块名称已存在",ExceptionType.PARAMS);
        if(grade.equals(1)){
            String url=module.getUrl();
            AssertUtil.isTrue(StringUtils.isBlank(url),"url不能为空",ExceptionType.PARAMS);
            AssertUtil.isTrue(!(null==moduleMapper.queryByUrl(url)),"url重复",ExceptionType.PARAMS);
        }else{
            module.setUrl(null);
        }
        Integer parentId=module.getParentId();
        if(grade.equals(0)){
            module.setParentId(-1);
        }else{
            AssertUtil.isTrue(null==parentId,"父级菜单不能为空",ExceptionType.PARAMS);
            AssertUtil.isTrue(null==moduleMapper.selectByPrimaryKey(parentId),"请指定正确的父级菜单",ExceptionType.PARAMS);

        }
        String optValue=module.getOptValue();
        AssertUtil.isTrue(StringUtils.isBlank(optValue),"权限码不能为空",ExceptionType.PARAMS);
        AssertUtil.isTrue(!(null==moduleMapper.queryByOptValue(optValue)),"权限码不能重复",ExceptionType.PARAMS);

        module.setIsValid((byte) 1);
        module.setCreateDate(new Date());
        module.setUpdateDate(new Date());

        AssertUtil.isTrue(moduleMapper.insertSelective(module)<1,"菜单添加失败",ExceptionType.EXECUTION_FAIL);

    }

    @Transactional(rollbackFor = Exception.class)
    public void update(Module module) {
        Integer id=module.getId();
        AssertUtil.isTrue(id==null,"待更新记录不存在",ExceptionType.PARAMS);
        Module temp=moduleMapper.selectByPrimaryKey(id);
        AssertUtil.isTrue(temp==null,"待更新记录不存在",ExceptionType.PARAMS);

        Integer grade=module.getGrade();
        AssertUtil.isTrue(grade==null,"层级不能为空",ExceptionType.PARAMS);
        AssertUtil.isTrue(!(grade.equals(0)||grade.equals(1)||grade.equals(2)),"层级必须为有效值(0-2)",ExceptionType.PARAMS);
        String moduleName=module.getModuleName();
        AssertUtil.isTrue(StringUtils.isBlank(moduleName),"模块名称不能为空", ExceptionType.PARAMS);
        temp=moduleMapper.queryByModuleName(moduleName);
        AssertUtil.isTrue(!(null==temp)&&!temp.getId().equals(module.getId()),"模块名称已存在",ExceptionType.PARAMS);
        if(grade.equals(1)){
            String url=module.getUrl();
            AssertUtil.isTrue(StringUtils.isBlank(url),"url不能为空",ExceptionType.PARAMS);
            temp=moduleMapper.queryByUrl(url);
            AssertUtil.isTrue(!(null==temp)&&!temp.getId().equals(module.getId()),"url重复",ExceptionType.PARAMS);
        }else{
            module.setUrl(null);
        }

        String optValue=module.getOptValue();
        AssertUtil.isTrue(StringUtils.isBlank(optValue),"权限码不能为空",ExceptionType.PARAMS);
        temp=moduleMapper.queryByOptValue(optValue);
        AssertUtil.isTrue(!(null==temp)&&!temp.getId().equals(id),"权限码不能重复",ExceptionType.PARAMS);

        module.setUpdateDate(new Date());
        AssertUtil.isTrue(moduleMapper.updateByPrimaryKeySelective(module)<1,"菜单更新失败",ExceptionType.EXECUTION_FAIL);
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(Integer id) {
        AssertUtil.isTrue(id==null,"待删除记录不存在",ExceptionType.PARAMS);
        Module module=moduleMapper.selectByPrimaryKey(id);
        AssertUtil.isTrue(module==null,"待删除记录不存在",ExceptionType.PARAMS);
        int countSon=moduleMapper.countByParentId(id);
        AssertUtil.isTrue(countSon>0,"菜单存在子项无法删除",ExceptionType.PARAMS);
        int countPermisson=permissionMapper.countByModuleId(id);
        if(countPermisson>0){
            AssertUtil.isTrue(permissionMapper.deleteByModuleId(id)!=countPermisson,"删除失败!",ExceptionType.EXECUTION_FAIL);
        }
        module.setIsValid((byte) 0);
        module.setUpdateDate(new Date());
        AssertUtil.isTrue(moduleMapper.updateByPrimaryKeySelective(module)<1,"删除失败",ExceptionType.EXECUTION_FAIL);
    }
}
