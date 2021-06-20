package com.cool.crm.query;

import com.cool.crm.base.BaseQuery;

/**
 * @Author 许俊青
 * @Date: 2021-04-13 15:29
 */
public class CustomerServeQuery extends BaseQuery {

    private String customer;
    private String serveType;
    private String state;

    private Integer assigner;

    public Integer getAssigner() {
        return assigner;
    }

    public void setAssigner(Integer assigner) {
        this.assigner = assigner;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getServeType() {
        return serveType;
    }

    public void setServeType(String serveType) {
        this.serveType = serveType;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
