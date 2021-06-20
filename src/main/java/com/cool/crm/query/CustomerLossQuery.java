package com.cool.crm.query;

import com.cool.crm.base.BaseQuery;

/**
 * @Author 许俊青
 * @Date: 2021-04-13 10:13
 */
public class CustomerLossQuery extends BaseQuery {

    private String cusNo;

    private String cusName;

    private Integer state;

    public String getCusNo() {
        return cusNo;
    }

    public void setCusNo(String cusNo) {
        this.cusNo = cusNo;
    }

    public String getCusName() {
        return cusName;
    }

    public void setCusName(String cusName) {
        this.cusName = cusName;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
