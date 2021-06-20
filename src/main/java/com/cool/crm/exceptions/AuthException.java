package com.cool.crm.exceptions;


/**
 * 自定义权限异常类
 *
 * 乐字节：专注线上IT培训
 * 答疑老师微信：lezijie
 */
public class AuthException extends BaseException {
    public AuthException() {
        super(403,"暂无权限!");
    }
}
