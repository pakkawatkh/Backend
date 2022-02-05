package com.example.backend.mapper;

import com.example.backend.entity.Orders;
import com.example.backend.model.orderModel.OrderRes;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    List<OrderRes> toListOrderRes(List<Orders> orders);

    OrderRes toOrderRes(Orders orders);

}
