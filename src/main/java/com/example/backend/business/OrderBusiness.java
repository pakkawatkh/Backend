package com.example.backend.business;

import com.example.backend.entity.Orders;
import com.example.backend.entity.User;
import com.example.backend.exception.BaseException;
import com.example.backend.model.orderModel.OrderStatusReq;
import com.example.backend.service.OrderService;
import com.example.backend.service.token.TokenService;
import org.springframework.stereotype.Service;

@Service
public class OrderBusiness {

    private final OrderService service;
    private final TokenService tokenService;

    public OrderBusiness(OrderService service, TokenService tokenService) {
        this.service = service;
        this.tokenService = tokenService;
    }


    public Object changeStatus(OrderStatusReq req, Orders.Status status) throws BaseException {
        User user = tokenService.getUserByToken();

        Object change = service.changeStatus(req.getId(), status, user);




        return change;
    }
}
