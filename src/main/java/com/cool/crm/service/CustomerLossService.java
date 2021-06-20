package com.cool.crm.service;

import com.cool.crm.base.BaseService;
import com.cool.crm.dao.CustomerLossMapper;
import com.cool.crm.enums.ExceptionType;
import com.cool.crm.utils.AssertUtil;
import com.cool.crm.vo.CustomerLoss;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @Author 许俊青
 * @Date: 2021-04-13 10:16
 */
@Service
public class CustomerLossService extends BaseService<CustomerLoss,Integer> {

    @Autowired
    private CustomerLossMapper customerLossMapper;


    @Transactional(rollbackFor = Exception.class)
    public void updateStateById(Integer id, String lossReason) {
        AssertUtil.isTrue(id==null,"待确认流失的客户不存在", ExceptionType.PARAMS);
        CustomerLoss customerLoss=customerLossMapper.selectByPrimaryKey(id);
        AssertUtil.isTrue(customerLoss==null,"待确认流失的客户不存在",ExceptionType.PARAMS);
        customerLoss.setState(1);
        customerLoss.setLossReason(lossReason);
        customerLoss.setConfirmLossTime(new Date());
        customerLoss.setUpdateDate(new Date());
        AssertUtil.isTrue(customerLossMapper.updateByPrimaryKeySelective(customerLoss)<1,"确认流失失败",ExceptionType.EXECUTION_FAIL);
    }
}
