package com.example.backend.mapper;

import com.example.backend.entity.Orders;
import com.example.backend.model.orderModel.OrderListRes;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {
//    OrderListRes toOrderListResponse(Orders orders);
}
