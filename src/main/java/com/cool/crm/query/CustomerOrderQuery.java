package com.cool.crm.query;

import com.cool.crm.base.BaseQuery;

/**
 * @Author 许俊青
 * @Date: 2021-04-10 13:51
 */
public class CustomerOrderQuery extends BaseQuery {

    private Integer cusId;

    public Integer getCusId() {
        return cusId;
    }

    public void setCusId(Integer cusId) {
        this.cusId = cusId;
    }
}
