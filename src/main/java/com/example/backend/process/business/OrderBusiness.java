package com.example.backend.process.business;

import com.example.backend.entity.Orders;
import com.example.backend.entity.Type;
import com.example.backend.entity.User;
import com.example.backend.exception.BaseException;
import com.example.backend.exception.MainException;
import com.example.backend.mapper.OrderMapper;
import com.example.backend.model.Response;
import com.example.backend.model.orderModel.OrderReq;
import com.example.backend.model.orderModel.OrderRes;
import com.example.backend.model.orderModel.OrderStatusReq;
import com.example.backend.process.service.OrderService;
import com.example.backend.process.service.TypeService;
import com.example.backend.process.service.UserService;
import com.example.backend.process.service.token.TokenService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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
        if (Objects.isNull(req.getId()) || Objects.isNull(status)) throw MainException.requestInvalid();

        service.changeStatus(req.getId(), status, user);

        return new Response().success(MS);

    }

    public Object create(OrderReq req) throws BaseException {
        User user = tokenService.getUserByToken();
        if (Objects.isNull(req.getType()) || Objects.isNull(req.getWeight()) || req.getPicture().isBlank() || Objects.isNull(req.getLatitude()) || Objects.isNull(req.getLongitude()))
            throw MainException.requestInvalid();

        Type type = typeService.findById(req.getType());

        service.createOrder(user, type, req.getWeight(), req.getPicture(), req.getLatitude(), req.getLongitude());

        return new Response().success("create " + MS);


    }

    public Object listByUser() throws BaseException {
        User user = tokenService.getUserByToken();
        List<OrderRes> orderRes = mapper.toListOrderRes(service.findByUser(user));

        return new Response().ok(MS, "product", orderRes);
    }


    public Object getById(OrderReq req) throws BaseException {
        User user = tokenService.getUserByToken();
        if (Objects.isNull(req.getId())) throw MainException.requestInvalid();

        OrderRes orderRes = mapper.toOrderRes(service.findByIdAndUser(req.getId(), user));

        return new Response().ok(MS, "product", orderRes);
    }


    // --ADMIN
    public Object getOrderAllUser() throws BaseException {
        tokenService.checkAdminByToken();
        List<OrderRes> orderRes = mapper.toListOrderRes(service.findAll());

        return new Response().ok(MS, "product", orderRes);
    }

    public Object getOrderByUser(User req) throws BaseException {
        tokenService.checkAdminByToken();
        if (req.getId().isBlank()) throw MainException.requestInvalid();

        User user = userService.findById(req.getId());
        List<OrderRes> orderRes = mapper.toListOrderRes(service.findByUser(user));

        return new Response().ok(MS, "product", orderRes);
    }
}
