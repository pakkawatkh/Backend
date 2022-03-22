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
import com.example.backend.model.orderModel.OrderSearchResponse;
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
    private final String MS = "OK";

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

        req.isValid();
        req.isBlank();

        Type type = typeService.findById(req.getTypeId());

        service.createOrder(user, type, req.getWeight(), req.getPicture(), req.getProvince(), req.getDistrict(), req.getName(), req.getDetail(), req.getPrice());

        return new Response().success("create " + MS);

    }

    public Object listByUser(int page) throws BaseException {
        User user = tokenService.getUserByToken();

        Long count = service.countByUser(user);
        List<Orders> list = service.findByUser(user, page);

        List<OrderRes> orderRes = mapper.toListOrderRes(list);
        orderRes = service.updateListOrder(orderRes);

        return new Response().ok2(MS, "product", orderRes, "count", count);
    }


    public Object getById(Integer id) throws BaseException {
        User user = tokenService.getUserByToken();

        OrderRes orderRes = mapper.toOrderRes(service.findByIdAndUser(id, user));
        orderRes = service.updateOrder(orderRes);

        return new Response().ok(MS, "product", orderRes);
    }

    public Object edit(Integer id, OrderReq req) throws BaseException {
        User user = tokenService.getUserByToken();
        Orders order = service.findByIdAndUser(id, user);
        Type type = typeService.findById(req.getTypeId());

        service.update(order, type, req.getWeight(), req.getPicture(), req.getProvince(), req.getDistrict(), req.getName(), req.getDetail(), req.getPrice());

        return new Response().success("update success");
    }

    public Object getDetailById(Integer id) throws BaseException {
        Orders order = service.findById(id);
        OrderRes orderRes = mapper.toOrderRes(order);
        orderRes = service.updateOrder(orderRes);

        return new Response().ok(MS, "product", orderRes);
    }


    // --ADMIN
    public Object getOrderAllUser() throws BaseException {
        tokenService.checkAdminByToken();
        List<OrderRes> orderRes = mapper.toListOrderRes(service.findAll());
        orderRes = service.updateListOrder(orderRes);

        return new Response().ok(MS, "product", orderRes);
    }

    public Object getOrderByUser(String id) throws BaseException {
        tokenService.checkAdminByToken();

        User user = userService.findById(id);
        List<OrderRes> orderRes = mapper.toListOrderRes(service.findAllByUser(user));
        orderRes = service.updateListOrder(orderRes);

        return new Response().ok(MS, "product", orderRes);
    }

    public Object deleteById(Integer id) throws BaseException {
        User user = tokenService.getUserByToken();
        service.deleteById(id, user);

        return new Response().success("ลบข้อมูลสำเร็จ");
    }

    public Object getListFilter(OrderBuyFillerReq req) throws BaseException {
        Orders.Status status = Orders.Status.BUY;

        int limit;
        long count;
        List<Orders> orders;
        Object getType;
        Object getProvince;
        Object getDistrict = null;

        // MAP = 50
        // LIST = 16
        limit = req.filter.equals(OrderBuyFillerReq.Filter.LIST) ? 16 : 50;

        if (req.getTypeId() == null && req.getProvince() == null) {
            orders = service.findAllByPage(req.getPage(), limit, req.getOrderBy(), status);
            count = service.countAll(status);

            //COUNT TYPE ALL []
            getType = service.getAllType();

            //COUNT PROVINCE ALL []
            getProvince = service.getProvince();

        } else if (req.getTypeId() != null && req.getProvince() == null) {
            Type type = typeService.findById(req.getTypeId());

            orders = service.findAllByPageAndType(req.getPage(), limit, type, req.getOrderBy(), status);
            count = service.countAllByType(type, status);
            //COUNT TYPE
            getType = service.getByType(type, status);
            //COUNT PROVINCE ALL []
            getProvince = service.getAllProvinceByType(type, status);
        } else if (req.getTypeId() == null && req.getProvince() != null && req.getDistrict() == null) {

            orders = service.findAllByPageAndProvince(req.getPage(), limit, req.getOrderBy(), req.getProvince(), status);
            count = service.countAllByProvince(req.getProvince(), status);

            //COUNT TYPE ALL []
            getType = service.getAllTypeByProvince(req.getProvince(), status);
            //COUNT PROVINCE
            getProvince = service.getByProvince(req.getProvince(), status);

            getDistrict = service.getDistrictByProvince(req.getProvince(), status);

        } else if (req.getTypeId() == null && req.getProvince() != null && req.getDistrict() != null) {
            //TODO : District
            orders = service.findAllByPageAndProvinceAndDistrict(req.getPage(), limit, req.getOrderBy(), req.getProvince(), req.getDistrict(), status);
            count = service.countAllByProvinceAndDistrict(req.getProvince(), req.getDistrict(), status);

            //COUNT TYPE ALL []
            getType = service.getAllTypeByProvinceAndDistrict(req.getProvince(), req.getDistrict(), status);
            //COUNT PROVINCE
            getProvince = service.getByProvinceAndDistrict(req.getProvince(), req.getDistrict(), status);

            getDistrict = service.getDistrictByProvinceAndDistrict(req.getProvince(), req.getDistrict(), status);


        } else if (req.getTypeId() != null && req.getProvince() != null && req.getDistrict() != null) {
            //TODO : District
            Type type = typeService.findById(req.typeId);

            orders = service.findAllByStatusAndTypeAndProvinceAndDistrictOrderByDateDesc(req.getPage(), limit, type, req.getOrderBy(), req.getProvince(), req.getDistrict(), status);
            count = service.countAllByTypeAndProvinceAndDistrict(type, req.getProvince(), req.getDistrict(), status);

            //COUNT TYPE ALL []
            getType = service.getTypeByTypeAndProvinceAndDistrict(type, req.getProvince(), req.getDistrict(), status);
            //COUNT PROVINCE
            getProvince = service.getProvinceByTypeAndProvinceAndDistrict(type, req.getProvince(), req.getDistrict(), status);

            getDistrict = service.getDistrictByProvinceAndDistrictAndType(type, req.getProvince(), req.getDistrict(), status);
        } else {
            Type type = typeService.findById(req.typeId);

            orders = service.findAllByPageAndProvinceAndType(req.getPage(), limit, type, req.getOrderBy(), req.getProvince(), status);
            count = service.countAllByProvinceAndType(type, req.getProvince(), status);
            //COUNT TYPE
            getType = service.getTypeByTypeAndProvince(type, req.getProvince(), status);
            //COUNT PROVINCE
            getProvince = service.getProvinceByTypeAndProvince(type, req.getProvince(), status);

            getDistrict = service.getDistrictByProvinceAndType(type, req.getProvince(), status);
        }

        List<OrderRes> orderRes = mapper.toListOrderRes(orders);
        orderRes = service.updateListOrder(orderRes);

        return new Response().filterOrder(MS, "order", orderRes, "count", count, "type", getType, "province", getProvince, "district", getDistrict);
    }

    public Object orderListByUser(String number) throws BaseException {
        User user = userService.findByNumber(number);

        List<Orders> orders = service.findAllUserIsBuy(user);
        List<OrderRes> orderResList = mapper.toListOrderRes(orders);
        orderResList = service.updateListOrder(orderResList);

        return new Response().ok(MS, "product", orderResList);
    }

    public Object selectProvinceIsBy() {
        List<Object> province = service.getProvince();
        return new Response().ok(MS, "province", province);
    }

    public Object random() {
        List<Orders> orders = service.randomLimitAndStatus(8, Orders.Status.BUY.toString());
        List<OrderRes> orderRes = mapper.toListOrderRes(orders);
        orderRes = service.updateListOrder(orderRes);

        return new Response().ok(MS, "order", orderRes);
    }

    public Object recommend(Integer id) {
        List<Orders> orders = service.recommend(id, 6, "BUY");
        List<OrderRes> orderRes = mapper.toListOrderRes(orders);
        orderRes = service.updateListOrder(orderRes);

        return new Response().ok(MS, "order", orderRes);
    }

    public Object searchName(String name) {
        boolean check;
        List<OrderSearchResponse> search;

        List<Orders> orders = service.searchName(name, Orders.Status.BUY);
        if (!orders.isEmpty()) {
            search = mapper.toSearchResponse(orders);
            search = service.updateSearch(search);
            check = true;
        } else {
            check = false;
            search = null;
        }
        return new Response().ok2(MS, "product", search, "check", check);
    }

}
