package com.example.backend.business;

import com.example.backend.entity.Orders;
import com.example.backend.entity.User;
import com.example.backend.exception.BaseException;
import com.example.backend.model.orderModel.OrderReq;
import com.example.backend.model.orderModel.OrderStatusReq;
import com.example.backend.service.OrderService;
import com.example.backend.service.token.TokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class OrderBusiness {

    private final OrderService service;
    private final TokenService tokenService;

    public OrderBusiness(OrderService service, TokenService tokenService) {
        this.service = service;
        this.tokenService = tokenService;
    }

    public ResponseEntity<Object> changeStatus(OrderStatusReq req, Orders.Status status) throws BaseException {

        User user = tokenService.getUserByToken();

        Object change = service.changeStatus(req.getId(), status, user);

        return ResponseEntity.ok(change);
    }

    public ResponseEntity<Object> create(OrderReq req) throws BaseException {

        User user = tokenService.getUserByToken();

        Object order = service.createOrder(user, req.getType(), req.getWeight(), req.getPicture(), req.getLatitude(), req.getLongitude());
        return ResponseEntity.ok(order);
    }

    public ResponseEntity<Object> listByUser() throws BaseException {

        User user = tokenService.getUserByToken();

        Object orderByUser = service.getOrderByUser(user);

        return ResponseEntity.ok(orderByUser);
    }
}
