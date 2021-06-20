package com.cool.crm.dao;

import com.cool.crm.base.BaseMapper;
import com.cool.crm.model.TreeModel;
import com.cool.crm.vo.Module;

import java.util.List;

public interface ModuleMapper extends BaseMapper<Module,Integer> {

    List<TreeModel> queryAllModules();

    List<Module> queryModuleList();

    Module queryByModuleName(String moduleName);

    Module queryByUrl(String url);

    Module queryByOptValue(String optValue);

    int countByParentId(Integer id);
}