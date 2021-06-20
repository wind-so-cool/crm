package com.cool.crm.service;

import com.cool.crm.base.BaseService;
import com.cool.crm.dao.CusDevPlanMapper;
import com.cool.crm.dao.SaleChanceMapper;
import com.cool.crm.enums.ExceptionType;
import com.cool.crm.utils.AssertUtil;
import com.cool.crm.vo.CusDevPlan;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class CusDevPlanService extends BaseService<CusDevPlan,Integer> {

    @Resource
    private CusDevPlanMapper cusDevPlanMapper;

    @Resource
    private SaleChanceMapper saleChanceMapper;

    @Transactional(rollbackFor = Exception.class)
    public void addCusDevPlan(CusDevPlan cusDevPlan){
        checkCusDevPlanParams(cusDevPlan);
        cusDevPlan.setIsValid(1);
        cusDevPlan.setCreateDate(new Date());
        cusDevPlan.setUpdateDate(new Date());
        AssertUtil.isTrue(cusDevPlanMapper.insertSelective(cusDevPlan)<1,"计划项添加失败",ExceptionType.EXECUTION_FAIL);
    }

    private void checkCusDevPlanParams(CusDevPlan cusDevPlan) {
        Integer saleChanceId=cusDevPlan.getSaleChanceId();
        AssertUtil.isTrue(saleChanceId==null||saleChanceMapper.selectByPrimaryKey(saleChanceId)==null,"数据异常,请重试!", ExceptionType.PARAMS);
        AssertUtil.isTrue(StringUtils.isBlank(cusDevPlan.getPlanItem()),"计划项不能为空",ExceptionType.PARAMS);
        AssertUtil.isTrue(cusDevPlan.getPlanDate()==null,"计划时间不能为空",ExceptionType.PARAMS);

    }
    @Transactional(rollbackFor = Exception.class)
    public void updateCusDevPlan(CusDevPlan cusDevPlan){
        AssertUtil.isTrue(cusDevPlan.getId()==null||cusDevPlanMapper.selectByPrimaryKey(cusDevPlan.getId())==null,"数据异常,请重试!",ExceptionType.PARAMS);
        checkCusDevPlanParams(cusDevPlan);
        cusDevPlan.setUpdateDate(new Date());
        AssertUtil.isTrue(cusDevPlanMapper.updateByPrimaryKeySelective(cusDevPlan)<1,"计划项更新失败",ExceptionType.EXECUTION_FAIL);
    }
    @Transactional(rollbackFor = Exception.class)
    public void deleteCusDevPlan(Integer id) {
        AssertUtil.isTrue(id==null,"待删除记录不存在",ExceptionType.PARAMS);
        CusDevPlan cusDevPlan=cusDevPlanMapper.selectByPrimaryKey(id);
        AssertUtil.isTrue(cusDevPlan==null,"待删除记录不存在",ExceptionType.PARAMS);
        cusDevPlan.setIsValid(0);
        cusDevPlan.setUpdateDate(new Date());
        AssertUtil.isTrue(cusDevPlanMapper.updateByPrimaryKeySelective(cusDevPlan)<1,"计划项删除失败!",ExceptionType.EXECUTION_FAIL);
    }
}
