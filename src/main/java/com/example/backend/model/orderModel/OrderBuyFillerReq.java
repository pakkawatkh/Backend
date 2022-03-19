package com.example.backend.model.orderModel;

import com.example.backend.entity.Orders;
import lombok.Data;

@Data
public class OrderBuyFillerReq {

    public Integer typeId ;
    public String province;
    public Integer page = 0;
    public OrderBy orderBy = OrderBy.NEW;
    public Orders.Status status = Orders.Status.BUY;
    public String district;

    public enum OrderBy {
        OLD, NEW
    }
}
