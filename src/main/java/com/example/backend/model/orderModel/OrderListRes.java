package com.example.backend.model.orderModel;

import lombok.Data;

import java.util.List;

@Data
public class OrderListRes {

    private List<OrderRes> order;
}
