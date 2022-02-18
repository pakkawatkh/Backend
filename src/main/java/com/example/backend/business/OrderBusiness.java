package com.example.backend.business;

import com.example.backend.entity.Orders;
import com.example.backend.entity.Type;
import com.example.backend.entity.User;
import com.example.backend.exception.BaseException;
import com.example.backend.exception.OrderException;
import com.example.backend.mapper.OrderMapper;
import com.example.backend.model.Response;
import com.example.backend.model.orderModel.OrderReq;
import com.example.backend.model.orderModel.OrderRes;
import com.example.backend.model.orderModel.OrderStatusReq;
import com.example.backend.service.OrderService;
import com.example.backend.service.TypeService;
import com.example.backend.service.UserService;
import com.example.backend.service.token.TokenService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderBusiness {

    private final OrderService service;
    private final TokenService tokenService;
    private final OrderMapper mapper;
    private final UserService userService;
    private final TypeService typeService;
    private String MS = "OK";

    public OrderBusiness(OrderService service, TokenService tokenService, OrderMapper mapper, UserService userService, TypeService typeService) {
        this.service = service;
        this.tokenService = tokenService;
        this.mapper = mapper;
        this.userService = userService;
        this.typeService = typeService;
    }

    public Object changeStatus(OrderStatusReq req, Orders.Status status) throws BaseException {

        User user = tokenService.getUserByToken();

        if (req.getId() == null || status == null) {
            throw OrderException.requestInvalid();
        }

        service.changeStatus(req.getId(), status, user);

        return new Response().success(MS);

    }

    public Object create(OrderReq req) throws BaseException {

        User user = tokenService.getUserByToken();

        if ( req.getType() == null || req.getWeight() == null || req.getPicture() == null || req.getLatitude() == null || req.getLongitude() == null){
            throw OrderException.requestInvalid();
        }

            Type type = typeService.findById(req.getType());

        service.createOrder(user, type, req.getWeight(), req.getPicture(), req.getLatitude(), req.getLongitude());

        return new Response().success("create " + MS);


    }

    public Object listByUser() throws BaseException {

        User user = tokenService.getUserByToken();

        List<Orders> byUser = service.findByUser(user);

        List<OrderRes> orderRes = mapper.toListOrderRes(byUser);
        return new Response().ok(MS, "product", orderRes);
    }


    public Object getById(OrderReq req) throws BaseException {
        User user = tokenService.getUserByToken();

        if (req.getId()==null){
            throw OrderException.requestInvalid();
        }
        Orders orderById = service.findByIdAndUser(req.getId(), user);

        OrderRes orderRes = mapper.toOrderRes(orderById);

        return new Response().ok(MS, "product", orderRes);
    }


    // --ADMIN
    public Object getOrderAllUser() throws BaseException {
        tokenService.checkAdminByToken();
        List<Orders> all = service.findAll();

        List<OrderRes> orderRes = mapper.toListOrderRes(all);

        return new Response().ok(MS, "product", orderRes);
    }

    public Object getOrderByUser(User req) throws BaseException {

        tokenService.checkAdminByToken();

        if (req.getId()==null){
            throw OrderException.requestInvalid();
        }

        User user = userService.findById(req.getId());

        List<Orders> byUser = service.findByUser(user);

        List<OrderRes> orderRes = mapper.toListOrderRes(byUser);
        return new Response().ok(MS, "product", orderRes);
    }
}
