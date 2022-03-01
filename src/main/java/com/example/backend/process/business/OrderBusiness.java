package com.example.backend.process.business;

import com.example.backend.entity.Orders;
import com.example.backend.entity.Type;
import com.example.backend.entity.User;
import com.example.backend.exception.BaseException;
import com.example.backend.exception.MainException;
import com.example.backend.exception.OrderException;
import com.example.backend.mapper.OrderMapper;
import com.example.backend.model.Response;
import com.example.backend.model.orderModel.OrderReq;
import com.example.backend.model.orderModel.OrderRes;
import com.example.backend.process.service.OrderService;
import com.example.backend.process.service.TypeService;
import com.example.backend.process.service.UserService;
import com.example.backend.process.service.token.TokenService;
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

    public Object changeStatus(Integer id, Orders.Status status) throws BaseException {
        User user = tokenService.getUserByToken();
        if (id == null || status == null) throw MainException.requestInvalid();

        service.changeStatus(id, status, user);

        return new Response().success(MS);

    }

    public Object create(OrderReq req) throws BaseException {
        User user = tokenService.getUserByToken();

        if (!req.isValid()) throw MainException.requestInvalid();
        if (req.isBlank()) throw MainException.requestIsBlank();

        Type type = typeService.findById(req.getTypeId());

        service.createOrder(user, type, req.getWeight(), req.getPicture(), req.getProvince(), req.getDistrict(), req.getName(), req.getDetail(), req.getPrice());

        return new Response().success("create " + MS);

    }

    public Object listByUser(int page) throws BaseException {
        User user = tokenService.getUserByToken();

        Long count = service.countByUser(user);
        List<Orders> list = service.findByUser(user,page);

        List<OrderRes> orderRes = mapper.toListOrderRes(list);
        List<OrderRes> setStatus = service.setListStatusThOrder(orderRes);

        return new Response().ok2(MS, "product", setStatus,"count",count);
    }


    public Object getById(Integer id) throws BaseException {
        User user = tokenService.getUserByToken();

        OrderRes orderRes = mapper.toOrderRes(service.findByIdAndUser(id, user));

        OrderRes setStatus = service.setStatusThOrder(orderRes);

        return new Response().ok(MS, "product", setStatus);
    }


    // --ADMIN
    public Object getOrderAllUser() throws BaseException {
        tokenService.checkAdminByToken();
        List<OrderRes> orderRes = mapper.toListOrderRes(service.findAll());
        List<OrderRes> setStatus = service.setListStatusThOrder(orderRes);

        return new Response().ok(MS, "product", setStatus);
    }

    public Object getOrderByUser(String id) throws BaseException {
        tokenService.checkAdminByToken();

        User user = userService.findById(id);
        List<OrderRes> orderRes = mapper.toListOrderRes(service.findAllByUser(user));

        List<OrderRes> setStatus = service.setListStatusThOrder(orderRes);

        return new Response().ok(MS, "product", setStatus);
    }

    public Object deleteById(Integer id) throws BaseException {
        User user = tokenService.getUserByToken();
        service.deleteById(id,user);

        return new Response().success("ลบข้อมูลสำเร็จ");
    }

}
