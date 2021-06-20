package com.cool.crm.query;

import com.cool.crm.base.BaseQuery;

/**
 * @Author 许俊青
 * @Date: 2021-04-10 14:28
 */
public class OrderDetailsQuery extends BaseQuery {
    private Integer orderId;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }
}
