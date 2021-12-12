package com.example.backend.business;

import com.example.backend.service.OrderService;
import org.springframework.stereotype.Service;

@Service
public class OrderBusiness {

    private final OrderService orderService;

    public OrderBusiness(OrderService orderService) {
        this.orderService = orderService;
    }


}
