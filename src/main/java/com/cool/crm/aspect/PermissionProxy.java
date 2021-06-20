package com.cool.crm.aspect;

import com.cool.crm.annotation.RequiredPermission;
import com.cool.crm.exceptions.AuthException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@Component
@Aspect
public class PermissionProxy {

    @Resource
    private HttpSession session;

    @Around("@annotation(com.cool.crm.annotation.RequiredPermission)")
    public Object checkPermission(ProceedingJoinPoint pjp) throws Throwable {
        List<String> permissions= (List<String>) session.getAttribute("permissions");
        if(CollectionUtils.isEmpty(permissions)){
            throw new AuthException();
        }
        MethodSignature methodSignature= (MethodSignature) pjp.getSignature();
       RequiredPermission requiredPermission=methodSignature.getMethod().getDeclaredAnnotation(RequiredPermission.class);
       if(!permissions.contains(requiredPermission.code())){
           throw new AuthException();
       }
       return pjp.proceed();
    }
}
