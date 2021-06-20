package com.cool.crm.exceptions;


/**
 * 自定义参数异常类
 *
 * 乐字节：专注线上IT培训
 * 答疑老师微信：lezijie
 */
public class NoLoginException extends BaseException {

    public NoLoginException() {
        super(401,"用户未登录!");
    }

}
