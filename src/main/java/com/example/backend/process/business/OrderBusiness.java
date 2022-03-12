package com.example.backend.process.business;

import com.example.backend.entity.Orders;
import com.example.backend.entity.Type;
import com.example.backend.entity.User;
import com.example.backend.exception.BaseException;
import com.example.backend.exception.MainException;
import com.example.backend.mapper.OrderMapper;
import com.example.backend.model.Response;
import com.example.backend.model.orderModel.OrderBuyFillerReq;
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
        List<Orders> list = service.findByUser(user, page);

        List<OrderRes> orderRes = mapper.toListOrderRes(list);
        List<OrderRes> setStatus = service.updateListOrder(orderRes);

        return new Response().ok2(MS, "product", setStatus, "count", count);
    }


    public Object getById(Integer id) throws BaseException {
        User user = tokenService.getUserByToken();

        OrderRes orderRes = mapper.toOrderRes(service.findByIdAndUser(id, user));

        OrderRes setStatus = service.updateOrder(orderRes);

        return new Response().ok(MS, "product", setStatus);
    }

    public Object edit(Integer id,OrderReq req) throws BaseException {
        User user = tokenService.getUserByToken();
        Orders order = service.findByIdAndUser(id, user);
        Type type = typeService.findById(req.getTypeId());

        service.update(order,type,req.getWeight(), req.getPicture(), req.getProvince(), req.getDistrict(), req.getName(), req.getDetail(), req.getPrice());

        return new Response().success("update success");
    }


    // --ADMIN
    public Object getOrderAllUser() throws BaseException {
        tokenService.checkAdminByToken();
        List<OrderRes> orderRes = mapper.toListOrderRes(service.findAll());
        List<OrderRes> setStatus = service.updateListOrder(orderRes);

        return new Response().ok(MS, "product", setStatus);
    }

    public Object getOrderByUser(String id) throws BaseException {
        tokenService.checkAdminByToken();

        User user = userService.findById(id);
        List<OrderRes> orderRes = mapper.toListOrderRes(service.findAllByUser(user));

        List<OrderRes> setStatus = service.updateListOrder(orderRes);

        return new Response().ok(MS, "product", setStatus);
    }

    public Object deleteById(Integer id) throws BaseException {
        User user = tokenService.getUserByToken();
        service.deleteById(id, user);

        return new Response().success("ลบข้อมูลสำเร็จ");
    }

    public Object getListFilter(OrderBuyFillerReq req) throws BaseException {
        long count;
        List<Orders> orders;
        if (req.getTypeId() == null && req.getProvince() == null) {
            orders = service.findAllByPage(req.getPage(), req.getOrderBy(), req.getStatus());
            count = service.countAll(req.getStatus());
        } else if (req.getTypeId() != null && req.getProvince() == null) {
            Type type = typeService.findById(req.typeId);

            orders = service.findAllByPageAndType(req.getPage(), type, req.getOrderBy(), req.getStatus());
            count = service.countAllByType(type, req.getStatus());
        } else if (req.getTypeId() == null && req.getProvince() != null) {

            orders = service.findAllByPageAndProvince(req.getPage(), req.getOrderBy(), req.getProvince(), req.getStatus());
            count = service.countAllByProvince(req.province, req.getStatus());
        } else {
            Type type = typeService.findById(req.typeId);

            orders = service.findAllByPageAndProvinceAndType(req.getPage(), type, req.getOrderBy(), req.getProvince(), req.getStatus());
            count = service.countAllByProvinceAndType(type, req.getProvince(), req.getStatus());
        }

        List<OrderRes> orderRes = mapper.toListOrderRes(orders);

        List<OrderRes> res = service.updateListOrder(orderRes);

        return new Response().ok2(MS,"order",res,"count",count);
    }

    public Object orderListByUser(String number) throws BaseException {
        User user = userService.findByNumber(number);

        List<Orders> orders = service.findAllUserIsBuy(user);
        List<OrderRes> orderResList = mapper.toListOrderRes(orders);

        List<OrderRes> res = service.updateListOrder(orderResList);

        return new Response().ok(MS,"product",res);
    }

    public Object selectProvinceIsBy(){
        Object province = service.getProvince();
        return new Response().ok(MS,"province",province);
    }

}
