package com.cool.crm;

import com.alibaba.fastjson.JSON;
import com.cool.crm.base.ResultInfo;
import com.cool.crm.exceptions.BaseException;
import com.cool.crm.exceptions.NoLoginException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class GlobalExceptionResolver implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        if(ex instanceof NoLoginException){
            return new ModelAndView("redirect:/index");
        }

        ModelAndView modelAndView=new ModelAndView("error");
        modelAndView.addObject("code",500);
        modelAndView.addObject("msg","系统异常,请重试!");
        if(handler instanceof HandlerMethod){
            HandlerMethod method= (HandlerMethod) handler;
            ResponseBody responseBody=method.getMethod().getDeclaredAnnotation(ResponseBody.class);
            if(responseBody==null){
                //返回视图
                if(ex instanceof BaseException){
                    BaseException b= (BaseException) ex;
                    modelAndView.addObject("code",b.getCode());
                    modelAndView.addObject("msg",b.getMessage());
                }
            }else{
                //返回json数据
                ResultInfo resultInfo=new ResultInfo();
                resultInfo.setCode(500);
                resultInfo.setMsg("系统异常,请重试!");
                if(ex instanceof BaseException){
                    BaseException b= (BaseException) ex;
                    resultInfo.setCode(b.getCode());
                    resultInfo.setMsg(b.getMessage());
                }
                PrintWriter out=null;
                try {
                    response.setContentType("application/json;charset=UTF-8");
                    out=response.getWriter();
                    out.write(JSON.toJSONString(resultInfo));
                } catch (IOException e) {
                    e.printStackTrace();
                }finally{
                    if(out!=null){
                        out.close();
                    }
                }
                return null;
            }
        }
        return modelAndView;
    }
}
