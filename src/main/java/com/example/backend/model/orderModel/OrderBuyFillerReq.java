package com.example.backend.model.orderModel;

import lombok.Data;

@Data
public class OrderBuyFillerReq {

    public Integer typeId;
    public String province;
    public Integer page = 0;
    public OrderBy orderBy = OrderBy.NEW;
    public String district;
    public Filter filter = Filter.LIST;

    public enum OrderBy {
        OLD, NEW
    }

    public enum Filter{
        MAP,LIST
    }
}
