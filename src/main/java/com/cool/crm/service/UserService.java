package com.cool.crm.service;

import com.cool.crm.base.BaseService;
import com.cool.crm.dao.UserMapper;
import com.cool.crm.dao.UserRoleMapper;
import com.cool.crm.enums.ExceptionType;
import com.cool.crm.model.UserModel;
import com.cool.crm.utils.AssertUtil;
import com.cool.crm.utils.Md5Util;
import com.cool.crm.utils.PhoneUtil;
import com.cool.crm.utils.UserIDBase64;
import com.cool.crm.vo.User;
import com.cool.crm.vo.UserRole;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserService extends BaseService<User,Integer> {

    @Resource
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    public UserModel userLogin(String uname,String pwd){

        checkLoginParams(uname,pwd);
        User user=userMapper.queryUserByName(uname);
        AssertUtil.isTrue(user==null,"用户名不存在", ExceptionType.PARAMS);
        checkPwd(user.getUserPwd(),pwd);
        return buildUserInfo(user);
    }
    public UserModel buildUserInfo(User user){
        UserModel userModel=new UserModel();
        userModel.setUserIdStr(UserIDBase64.encoderUserID(user.getId()));
        userModel.setUsername(user.getUserName());
        userModel.setTrueName(user.getTrueName());
        return userModel;
    }
    private void checkPwd(String userPwd, String pwd) {
        pwd= Md5Util.encode(pwd);
        AssertUtil.isTrue(!userPwd.equals(pwd),"密码错误",ExceptionType.PARAMS);
    }

    private void checkLoginParams(String uname, String pwd) {
        AssertUtil.isTrue(StringUtils.isBlank(uname),"用户名不能为空",ExceptionType.PARAMS);
        AssertUtil.isTrue(StringUtils.isBlank(pwd),"密码不能为空",ExceptionType.PARAMS);
    }
    @Transactional(rollbackFor = Exception.class)
    public  void updatePassword(int userId, String oldPwd, String newPwd, String repeatPwd){
        User user=userMapper.selectByPrimaryKey(userId);
        AssertUtil.isTrue(user==null,"修改密码的用户不存在",ExceptionType.PARAMS);
        checkPwdParams(user,oldPwd,newPwd,repeatPwd);
        user.setUserPwd(Md5Util.encode(newPwd));
        AssertUtil.isTrue(userMapper.updateByPrimaryKeySelective(user)<1,"修改密码失败",ExceptionType.EXECUTION_FAIL);;
    }

    private void checkPwdParams(User user,String oldPwd, String newPwd, String repeatPwd) {
        AssertUtil.isTrue(StringUtils.isBlank(oldPwd),"原始密码不能为空",ExceptionType.PARAMS);
        AssertUtil.isTrue(!Md5Util.encode(oldPwd).equals(user.getUserPwd()),"原始密码错误",ExceptionType.PARAMS);
        AssertUtil.isTrue(StringUtils.isBlank(newPwd),"新密码不能为空",ExceptionType.PARAMS);
        AssertUtil.isTrue(oldPwd.equals(newPwd),"新密码不能与原始密码一样",ExceptionType.PARAMS);

        AssertUtil.isTrue(StringUtils.isBlank(repeatPwd),"确认密码不能为空",ExceptionType.PARAMS);
        AssertUtil.isTrue(!newPwd.equals(repeatPwd),"新密码与确认密码不一致",ExceptionType.PARAMS);

    }

    public List<User> queryAllSales(){
        return userMapper.queryAllSales();
    }

    @Transactional(rollbackFor = Exception.class)
    public void addUser(User user) {
        checkUserParams(user,null);
        user.setIsValid(1);
        user.setCreateDate(new Date());
        user.setUpdateDate(new Date());
        user.setUserPwd(Md5Util.encode("123456"));
        AssertUtil.isTrue(userMapper.insertSelective(user)<1,"用户添加失败!",ExceptionType.EXECUTION_FAIL);

        relationUserRole(user.getId(),user.getRoleIds());
    }

    private void relationUserRole(Integer userId, String roleIds) {
        Integer count=userRoleMapper.countByUserId(userId);
        if(count>0){
            AssertUtil.isTrue(!userRoleMapper.deleteByUserId(userId).equals(count),"用户角色分配失败",ExceptionType.EXECUTION_FAIL);
        }
        if(StringUtils.isNotBlank(roleIds)){
            List<UserRole> userRoleList=new ArrayList<>();
            String[] roleIdArr=roleIds.split(",");
            for(String roleId:roleIdArr){
                UserRole userRole=new UserRole();
                userRole.setCreateDate(new Date());
                userRole.setRoleId(Integer.parseInt(roleId));
                userRole.setUpdateDate(new Date());
                userRole.setUserId(userId);
                userRoleList.add(userRole);
            }
            AssertUtil.isTrue(userRoleMapper.insertBatch(userRoleList)!=userRoleList.size(),"用户角色分配失败",ExceptionType.EXECUTION_FAIL);
        }
    }

    private void checkUserParams(User user,Integer id) {
        AssertUtil.isTrue(StringUtils.isBlank(user.getUserName()),"用户名不能为空",ExceptionType.PARAMS);
        User temp=userMapper.queryUserByName(user.getUserName());
        AssertUtil.isTrue(temp!=null&& !temp.getId().equals(id),"用户名已存在",ExceptionType.PARAMS);
        AssertUtil.isTrue(StringUtils.isBlank(user.getTrueName()),"真实姓名不能为空",ExceptionType.PARAMS);
        AssertUtil.isTrue(StringUtils.isBlank(user.getEmail()),"邮箱不能为空",ExceptionType.PARAMS);
        String emailReg="^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
        AssertUtil.isTrue(!user.getEmail().matches(emailReg),"邮箱格式不正确",ExceptionType.PARAMS);
        AssertUtil.isTrue(StringUtils.isBlank(user.getPhone()),"手机号不能为空",ExceptionType.PARAMS);
        AssertUtil.isTrue(!PhoneUtil.isMobile(user.getPhone()),"手机号格式不正确",ExceptionType.PARAMS);


    }

    @Transactional(rollbackFor = Exception.class)
    public void updateUser(User user) {
        Integer id=user.getId();
        AssertUtil.isTrue(id==null,"待更新记录不存在",ExceptionType.PARAMS);
        User temp=userMapper.selectByPrimaryKey(id);
        AssertUtil.isTrue(temp==null,"待更新记录不存在",ExceptionType.PARAMS);
        checkUserParams(user,temp.getId());
        user.setUpdateDate(new Date());
        AssertUtil.isTrue(userMapper.updateByPrimaryKeySelective(user)<1,"用户更新失败",ExceptionType.EXECUTION_FAIL);

        relationUserRole(user.getId(),user.getRoleIds());
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(Integer[] ids) {
        AssertUtil.isTrue(ArrayUtils.isEmpty(ids),"请选择要删除的记录",ExceptionType.PARAMS);
        AssertUtil.isTrue(userMapper.deleteBatch(ids)!=ids.length,"删除失败",ExceptionType.EXECUTION_FAIL);
    }

    public List<User> queryAllCustomerMgr() {
        return userMapper.queryAllCustomerMgr();
    }
}
