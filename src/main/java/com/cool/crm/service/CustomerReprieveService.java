package com.cool.crm.service;

import com.cool.crm.base.BaseService;
import com.cool.crm.dao.CustomerLossMapper;
import com.cool.crm.dao.CustomerReprieveMapper;
import com.cool.crm.enums.ExceptionType;
import com.cool.crm.utils.AssertUtil;
import com.cool.crm.vo.CustomerReprieve;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @Author 许俊青
 * @Date: 2021-04-13 11:12
 */
@Service
public class CustomerReprieveService extends BaseService<CustomerReprieve,Integer> {

    @Autowired
    private CustomerReprieveMapper customerReprieveMapper;

    @Autowired
    private CustomerLossMapper customerLossMapper;

    @Transactional(rollbackFor = Exception.class)
    public void add(CustomerReprieve customerReprieve) {
        checkParams(customerReprieve.getLossId(),customerReprieve.getMeasure());
        customerReprieve.setIsValid(1);
        customerReprieve.setCreateDate(new Date());
        customerReprieve.setUpdateDate(new Date());
        AssertUtil.isTrue(customerReprieveMapper.insertSelective(customerReprieve)<1,"添加暂缓数据失败",ExceptionType.EXECUTION_FAIL);
    }

    private void checkParams(Integer lossId, String measure) {
        AssertUtil.isTrue(lossId==null||customerLossMapper.selectByPrimaryKey(lossId)==null,"添加暂缓的流失客户不存在", ExceptionType.PARAMS);
        AssertUtil.isTrue(StringUtils.isBlank(measure),"暂缓措施内容不能为空",ExceptionType.PARAMS);
    }

    @Transactional(rollbackFor = Exception.class)
    public void update(CustomerReprieve customerReprieve) {
        Integer id=customerReprieve.getId();
        AssertUtil.isTrue(id==null||customerReprieveMapper.selectByPrimaryKey(id)==null,"待更新记录不存在",ExceptionType.PARAMS);
        checkParams(customerReprieve.getLossId(),customerReprieve.getMeasure());
        customerReprieve.setUpdateDate(new Date());
        AssertUtil.isTrue(customerReprieveMapper.updateByPrimaryKeySelective(customerReprieve)<1,"更新暂缓数据失败",ExceptionType.EXECUTION_FAIL);
    }
    @Transactional(rollbackFor = Exception.class)
    public void delete(Integer id) {
        AssertUtil.isTrue(id==null,"待删除记录不存在",ExceptionType.PARAMS);
        CustomerReprieve customerReprieve=customerReprieveMapper.selectByPrimaryKey(id);
        AssertUtil.isTrue(customerReprieve==null,"待删除记录不存在",ExceptionType.PARAMS);
        customerReprieve.setIsValid(0);
        customerReprieve.setUpdateDate(new Date());
        AssertUtil.isTrue(customerReprieveMapper.updateByPrimaryKeySelective(customerReprieve)<1,"删除暂缓数据失败",ExceptionType.EXECUTION_FAIL);
    }
}
