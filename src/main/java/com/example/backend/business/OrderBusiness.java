package com.example.backend.business;

import com.example.backend.entity.Orders;
import com.example.backend.entity.User;
import com.example.backend.exception.BaseException;
import com.example.backend.mapper.OrderMapper;
import com.example.backend.model.Response;
import com.example.backend.model.orderModel.OrderReq;
import com.example.backend.model.orderModel.OrderStatusReq;
import com.example.backend.service.OrderService;
import com.example.backend.service.UserService;
import com.example.backend.service.token.TokenService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderBusiness {

    private String MS = "OK";
    private final OrderService service;
    private final TokenService tokenService;
    private final OrderMapper mapper;
    private final UserService userService;

    public OrderBusiness(OrderService service, TokenService tokenService, OrderMapper mapper, UserService userService) {
        this.service = service;
        this.tokenService = tokenService;
        this.mapper = mapper;
        this.userService = userService;
    }

    public Object changeStatus(OrderStatusReq req, Orders.Status status) throws BaseException {

        User user = tokenService.getUserByToken();

        service.changeStatus(req.getId(), status, user);

        return new Response().success(MS);

    }

    public Object create(OrderReq req) throws BaseException {

        User user = tokenService.getUserByToken();

        service.createOrder(user, req.getType(), req.getWeight(), req.getPicture(), req.getLatitude(), req.getLongitude());

        return new Response().success("create "+MS);


    }

    public Object listByUser() throws BaseException {

        User user = tokenService.getUserByToken();

        Object orderByUser = service.findByUser(user);

        return new Response().ok(MS,"product",orderByUser);
    }

    public Object getOrderAllUser() throws BaseException {
        tokenService.checkAdminByToken();
        List<Orders> all = service.findAll();

        return new Response().ok(MS,"product",all);
    }

    public Object getOrderByUser(User req) throws BaseException {

        tokenService.checkAdminByToken();

        User user = userService.findById(req.getId());

        Object orderByUser = service.findByUser(user);
        return new Response().ok(MS,"product",orderByUser);
    }

    public Object getById(OrderReq req) throws BaseException {
        User user = tokenService.getUserByToken();

        Orders orderById;
        if (user.getRole()== User.Role.ADMIN){

            orderById = service.findById(req.getId());

        }else {
            orderById = service.findByIdAndUser(req.getId(), user);
        }
        return new Response().ok(MS, "product", orderById);
    }


}
