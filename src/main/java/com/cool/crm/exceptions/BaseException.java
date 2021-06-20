package com.cool.crm.exceptions;

public class BaseException extends RuntimeException {

    private Integer code=500;

    public BaseException() {

    }
    public BaseException(String message) {
        super(message);
    }
    public BaseException(Integer code,String message) {
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }


}
