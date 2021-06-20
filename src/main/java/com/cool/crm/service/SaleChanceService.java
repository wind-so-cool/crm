package com.cool.crm.service;

import com.cool.crm.base.BaseService;
import com.cool.crm.dao.SaleChanceMapper;
import com.cool.crm.enums.DevResult;
import com.cool.crm.enums.ExceptionType;
import com.cool.crm.enums.StateStatus;
import com.cool.crm.utils.AssertUtil;
import com.cool.crm.utils.PhoneUtil;
import com.cool.crm.vo.SaleChance;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class SaleChanceService extends BaseService<SaleChance,Integer> {

    @Resource
    private SaleChanceMapper saleChanceMapper;

    /*public Map<String,Object> querySaleChanceByParams(SaleChanceQuery saleChanceQuery){
        PageHelper.startPage(saleChanceQuery.getPage(),saleChanceQuery.getLimit());
        PageInfo<SaleChance> pageInfo=new PageInfo<>(saleChanceMapper.selectByParams(saleChanceQuery));
        Map<String,Object> map=new HashMap<>();
        map.put("code",0);
        map.put("msg","success");
        map.put("count",pageInfo.getTotal());
        map.put("data",pageInfo.getList());
        return map;
    }*/
    @Transactional(rollbackFor = Exception.class)
    public void addSaleChance(SaleChance saleChance){
        checkSaleChanceParams(saleChance.getCustomerName(),saleChance.getLinkMan(),saleChance.getLinkPhone());
        saleChance.setCreateDate(new Date());
        saleChance.setIsValid(1);
        saleChance.setUpdateDate(new Date());
        if(StringUtils.isBlank(saleChance.getAssignMan())){
            //未分配
            saleChance.setDevResult(DevResult.UNDEV.getStatus());
            saleChance.setState(StateStatus.UNSTATE.getType());

        }else{
            //已分配
            saleChance.setAssignTime(new Date());
            saleChance.setState(StateStatus.STATED.getType());
            saleChance.setDevResult(DevResult.DEVING.getStatus());
        }
        AssertUtil.isTrue(saleChanceMapper.insertSelective(saleChance)!=1,"添加营销机会失败", ExceptionType.EXECUTION_FAIL);
    }

    private void checkSaleChanceParams(String customerName, String linkMan, String linkPhone) {
        AssertUtil.isTrue(StringUtils.isBlank(customerName),"客户名称不能为空",ExceptionType.PARAMS);
        AssertUtil.isTrue(StringUtils.isBlank(linkMan),"联系人不能为空",ExceptionType.PARAMS);
        AssertUtil.isTrue(StringUtils.isBlank(linkPhone),"手机号码不能为空",ExceptionType.PARAMS);
        AssertUtil.isTrue(!PhoneUtil.isMobile(linkPhone),"手机号码格式不正确",ExceptionType.PARAMS);

    }

    @Transactional(rollbackFor = Exception.class)
    public void updateSaleChance(SaleChance saleChance){

        AssertUtil.isTrue(saleChance.getId()==null,"待更新记录不存在",ExceptionType.PARAMS);
        SaleChance old=saleChanceMapper.selectByPrimaryKey(saleChance.getId());
        AssertUtil.isTrue(old==null,"待更新记录不存在",ExceptionType.PARAMS);
        checkSaleChanceParams(saleChance.getCustomerName(),saleChance.getLinkMan(),saleChance.getLinkPhone());
        saleChance.setUpdateDate(new Date());
        if(StringUtils.isBlank(old.getAssignMan())){//原来不存在
            if(!StringUtils.isBlank(saleChance.getAssignMan())){//现在存在
                saleChance.setAssignTime(new Date());
                saleChance.setState(StateStatus.STATED.getType());
                saleChance.setDevResult(DevResult.DEVING.getStatus());
            }
        }else{//原来存在
            if(StringUtils.isBlank(saleChance.getAssignMan())){
                //现在不存在
                saleChance.setAssignTime(null);
                saleChance.setState(StateStatus.UNSTATE.getType());
                saleChance.setDevResult(DevResult.UNDEV.getStatus());
            }else{//现在存在
                if(!old.getAssignMan().equals(saleChance.getAssignMan())){
                    saleChance.setAssignTime(new Date());
                }else{
                    saleChance.setAssignTime(old.getAssignTime());
                }
            }

        }
        AssertUtil.isTrue(saleChanceMapper.updateByPrimaryKeySelective(saleChance)!=1,"更新失败",ExceptionType.EXECUTION_FAIL);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteSaleChance(Integer[] ids){
        AssertUtil.isTrue(ArrayUtils.isEmpty(ids),"请选择要删除的记录",ExceptionType.PARAMS);
        AssertUtil.isTrue(saleChanceMapper.deleteBatch(ids)!=ids.length,"删除失败",ExceptionType.EXECUTION_FAIL);
    }
    @Transactional(rollbackFor = Exception.class)
    public void updateSaleChanceDevResult(Integer saleChanceId,Integer devResult) {
        AssertUtil.isTrue(saleChanceId==null,"待更新记录不存在",ExceptionType.PARAMS);
        SaleChance saleChance=saleChanceMapper.selectByPrimaryKey(saleChanceId);
        AssertUtil.isTrue(saleChance==null,"待更新记录不存在",ExceptionType.PARAMS);
        saleChance.setDevResult(devResult);
        saleChance.setUpdateDate(new Date());
        AssertUtil.isTrue(saleChanceMapper.updateByPrimaryKeySelective(saleChance)<1,"开发状态更新失败!",ExceptionType.EXECUTION_FAIL);
    }
}
