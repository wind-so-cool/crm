package com.cool.crm.service;

import com.cool.crm.base.BaseService;
import com.cool.crm.dao.CustomerMapper;
import com.cool.crm.dao.CustomerServeMapper;
import com.cool.crm.dao.UserMapper;
import com.cool.crm.enums.CustomerServeStatus;
import com.cool.crm.enums.ExceptionType;
import com.cool.crm.utils.AssertUtil;
import com.cool.crm.vo.CustomerServe;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Author 许俊青
 * @Date: 2021-04-13 15:23
 */
@Service
public class CustomerServeService extends BaseService<CustomerServe,Integer> {

    @Resource
    private CustomerServeMapper customerServeMapper;

    @Resource
    private CustomerMapper customerMapper;

    @Autowired
    private UserMapper userMapper;

    @Transactional(rollbackFor = Exception.class)
    public void add(CustomerServe customerServe) {
        AssertUtil.isTrue(StringUtils.isBlank(customerServe.getServeType()),"服务类型不能为空", ExceptionType.PARAMS);
        String customer=customerServe.getCustomer();
        AssertUtil.isTrue(StringUtils.isBlank(customer),"客户姓名不能为空",ExceptionType.PARAMS);
        AssertUtil.isTrue(customerMapper.selectByName(customer)==null,"客户不存在",ExceptionType.PARAMS);
        AssertUtil.isTrue(StringUtils.isBlank(customerServe.getServiceRequest()),"服务内容不能为空",ExceptionType.PARAMS);
        customerServe.setIsValid(1);
        customerServe.setCreateDate(new Date());
        customerServe.setUpdateDate(new Date());
        AssertUtil.isTrue(customerServeMapper.insertSelective(customerServe)<1,"服务添加失败",ExceptionType.EXECUTION_FAIL);
    }
    @Transactional(rollbackFor = Exception.class)
    public void update(CustomerServe customerServe) {
        Integer id=customerServe.getId();
        AssertUtil.isTrue(id==null||customerServeMapper.selectByPrimaryKey(id)==null,"待更新记录不存在",ExceptionType.PARAMS);
        String state=customerServe.getState();
        if(CustomerServeStatus.ASSIGNED.getState().equals(state)){
            String assigner=customerServe.getAssigner();
            AssertUtil.isTrue(StringUtils.isBlank(assigner),"分配人不能为空",ExceptionType.PARAMS);
            AssertUtil.isTrue(userMapper.selectByPrimaryKey(Integer.parseInt(assigner))==null,"分配人不存在",ExceptionType.PARAMS);
            customerServe.setAssignTime(new Date());


        }else if(CustomerServeStatus.PROCED.getState().equals(state)){
            AssertUtil.isTrue(StringUtils.isBlank(customerServe.getServiceProce()),"服务处理内容不能为空",ExceptionType.PARAMS);
            customerServe.setServiceProceTime(new Date());

        }else if(CustomerServeStatus.FEED_BACK.getState().equals(state)){
            AssertUtil.isTrue(StringUtils.isBlank(customerServe.getServiceProceResult()),"服务反馈内容不能为空",ExceptionType.PARAMS);
            AssertUtil.isTrue(StringUtils.isBlank(customerServe.getMyd()),"满意度不能为空",ExceptionType.PARAMS);
            customerServe.setState(CustomerServeStatus.ARCHIVED.getState());
        }
        customerServe.setUpdateDate(new Date());
        AssertUtil.isTrue(customerServeMapper.updateByPrimaryKeySelective(customerServe)<1,"服务更新失败",ExceptionType.EXECUTION_FAIL);
    }
}
