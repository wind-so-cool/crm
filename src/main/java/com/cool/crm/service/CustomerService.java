package com.cool.crm.service;

import com.cool.crm.base.BaseService;
import com.cool.crm.dao.CustomerLossMapper;
import com.cool.crm.dao.CustomerMapper;
import com.cool.crm.dao.CustomerOrderMapper;
import com.cool.crm.enums.ExceptionType;
import com.cool.crm.query.CustomerQuery;
import com.cool.crm.utils.AssertUtil;
import com.cool.crm.utils.PhoneUtil;
import com.cool.crm.vo.Customer;
import com.cool.crm.vo.CustomerLoss;
import com.cool.crm.vo.CustomerOrder;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CustomerService extends BaseService<Customer,Integer> {

    @Resource
    private CustomerMapper customerMapper;

    @Resource
    private CustomerOrderMapper customerOrderMapper;

    @Resource
    private CustomerLossMapper customerLossMapper;

    @Transactional(rollbackFor = Exception.class)
    public void add(Customer customer) {
        checkParams(customer.getName(),customer.getFr(),customer.getPhone());
        Customer temp=customerMapper.selectByName(customer.getName());
        AssertUtil.isTrue(temp!=null,"客户名称已存在", ExceptionType.PARAMS);
        customer.setIsValid(1);
        customer.setCreateDate(new Date());
        customer.setUpdateDate(new Date());
        customer.setState(0);
        customer.setKhno("KH"+System.currentTimeMillis());
        AssertUtil.isTrue(customerMapper.insertSelective(customer)<1,"添加失败",ExceptionType.EXECUTION_FAIL);
    }

    private void checkParams(String name, String fr, String phone) {
        AssertUtil.isTrue(StringUtils.isBlank(name),"客户名称不能为空", ExceptionType.PARAMS);
        AssertUtil.isTrue(StringUtils.isBlank(fr),"法人不能为空",ExceptionType.PARAMS);
        AssertUtil.isTrue(!PhoneUtil.isMobile(phone),"手机号码格式不正确",ExceptionType.PARAMS);



    }
    @Transactional(rollbackFor = Exception.class)
    public void update(Customer customer) {
        Integer id=customer.getId();
        AssertUtil.isTrue(id==null,"待更新记录不存在", ExceptionType.PARAMS);
        Customer temp=customerMapper.selectByPrimaryKey(id);
        AssertUtil.isTrue(temp==null,"待更新记录不存在",ExceptionType.PARAMS);
        checkParams(customer.getName(),customer.getFr(),customer.getPhone());
        temp=customerMapper.selectByName(customer.getName());
        AssertUtil.isTrue(temp!=null&&!customer.getId().equals(temp.getId()),"客户名称已存在",ExceptionType.PARAMS);
        customer.setUpdateDate(new Date());
        AssertUtil.isTrue(customerMapper.updateByPrimaryKeySelective(customer)<1,"更新失败",ExceptionType.EXECUTION_FAIL);
    }
    @Transactional(rollbackFor = Exception.class)
    public void delete(Integer id) {
        AssertUtil.isTrue(id==null,"待删除记录不存在",ExceptionType.PARAMS);
        Customer customer=customerMapper.selectByPrimaryKey(id);
        AssertUtil.isTrue(customer==null,"待删除记录不存在",ExceptionType.PARAMS);
        customer.setUpdateDate(new Date());
        customer.setIsValid(0);
        AssertUtil.isTrue(customerMapper.updateByPrimaryKeySelective(customer)<1,"删除失败",ExceptionType.EXECUTION_FAIL);
    }
    @Transactional(rollbackFor = Exception.class)
    public void updateCustomerLossState(){
        List<Customer> customerLossList=customerMapper.queryCustomerLossList();
        if(!CollectionUtils.isEmpty(customerLossList)){
            List<CustomerLoss> customerLosses=new ArrayList<>();
            List<Integer> customerLossIds=new ArrayList<>();
            customerLossList.forEach(customer->{
                CustomerLoss customerLoss=new CustomerLoss();
                customerLoss.setCreateDate(new Date());
                customerLoss.setUpdateDate(new Date());
                customerLoss.setState(0);
                CustomerOrder customerOrder=customerOrderMapper.queryLastOrderByCusId(customer.getId());
                if(customerOrder!=null){
                    customerLoss.setLastOrderTime(customerOrder.getOrderDate());
                }
                customerLoss.setIsValid(1);
                customerLoss.setCusNo(customer.getKhno());
                customerLoss.setCusName(customer.getName());
                customerLoss.setCusManager(customer.getCusManager());
                customerLosses.add(customerLoss);
                customerLossIds.add(customer.getId());
            });
            AssertUtil.isTrue(customerLossMapper.insertBatch(customerLosses)!=customerLosses.size(),"客户流失数据转移失败",ExceptionType.EXECUTION_FAIL);
            AssertUtil.isTrue(customerMapper.updateCustomerStateByCusIds(customerLossIds)!=customerLossIds.size(),"客户流失数据转移失败",ExceptionType.EXECUTION_FAIL);
        }
    }


    public Map<String, Object> queryCusContriByParams(CustomerQuery customerQuery) {
        Map<String,Object> result = new HashMap<String,Object>();
        PageHelper.startPage(customerQuery.getPage(),customerQuery.getLimit());
        PageInfo<Map<String,Object>> pageInfo =new PageInfo<Map<String,Object>>(customerMapper.queryCusContriByParams(customerQuery));
        result.put("count",pageInfo.getTotal());
        result.put("data",pageInfo.getList());
        result.put("code",0);
        result.put("msg","");
        return result;
    }

    public Map<String, Object> countCustomerMake() {
        List<Map<String,Object>> customerList=customerMapper.countCustomerMake();
        List<String> customerLevelList=customerList.stream().map(m->m.get("level").toString()).collect(Collectors.toList());
        List<Integer> customerCountList=customerList.stream().map(m->Integer.parseInt(m.get("num").toString())).collect(Collectors.toList());
        Map<String,Object> map=new HashMap<>();
        map.put("customerLevelList",customerLevelList);
        map.put("customerCountList",customerCountList);
        return map;
    }

    public Map<String, Object> countCustomerMake2() {
        List<Map<String,Object>> customerList=customerMapper.countCustomerMake();
        List<String> customerLevelList=customerList.stream().map(m->m.get("level").toString()).collect(Collectors.toList());
        List<Map<String,Object>> nameValueList=new ArrayList<>();
        for(Map<String,Object> m:customerList){
            m.put("name",m.remove("level"));
            m.put("value",m.remove("num"));
        }
        Map<String,Object> map=new HashMap<>();
        map.put("customerLevelList",customerLevelList);
        map.put("customerList",customerList);
        return map;
    }
}
