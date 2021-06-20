package com.cool.crm.utils;


import com.cool.crm.enums.ExceptionType;
import com.cool.crm.exceptions.AuthException;
import com.cool.crm.exceptions.ExecutionFailException;
import com.cool.crm.exceptions.NoLoginException;
import com.cool.crm.exceptions.ParamsException;

/**
 * 校验类
 *
 * 乐字节：专注线上IT培训
 * 答疑老师微信：lezijie
 */
public class AssertUtil {


    /**
     * 判断条件是否满足
     *  如果条件满足，则抛出参数异常
     *
     *
     * 乐字节：专注线上IT培训
     * 答疑老师微信：lezijie
     * @param flag
     * @param msg
     * @return void
     */
    public  static void isTrue(Boolean flag, String msg, ExceptionType exceptionType){
        if(flag){
            switch (exceptionType){
                case PARAMS:
                    throw  new ParamsException(msg);
                case AUTH:
                    throw  new AuthException();
                case EXECUTION_FAIL:
                    throw  new ExecutionFailException(msg);
                case NO_LOGIN:
                    throw new NoLoginException();
            }

        }
    }

}
