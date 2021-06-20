package com.cool.crm.interceptor;

import com.cool.crm.dao.UserMapper;
import com.cool.crm.exceptions.NoLoginException;
import com.cool.crm.utils.LoginUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NoLoginInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Integer userId= LoginUserUtil.releaseUserIdFromCookie(request);
        if(userId==null||userMapper.selectByPrimaryKey(userId)==null){
            throw new NoLoginException();
        }
        return true;
    }
}
