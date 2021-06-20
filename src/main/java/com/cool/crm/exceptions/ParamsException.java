package com.cool.crm.exceptions;


/**
 * 自定义参数异常类
 *
 * 乐字节：专注线上IT培训
 * 答疑老师微信：lezijie
 */
public class ParamsException extends BaseException {

    public ParamsException(String msg) {
        super(400,msg);
    }

}
