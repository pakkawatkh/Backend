package com.example.backend.api;

import com.example.backend.business.OrderBusiness;
import com.example.backend.entity.Orders;
import com.example.backend.exception.BaseException;
import com.example.backend.model.orderModel.OrderReq;
import com.example.backend.model.orderModel.OrderStatusReq;
import com.example.backend.repository.OrdersRepository;
import com.example.backend.repository.UserRepository;
import com.example.backend.service.OrderService;
import com.example.backend.service.token.TokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderApi {

    private final OrdersRepository orderRepository;
    private final UserRepository userRepository;
    private final OrderService orderService;
    private final TokenService tokenService;
    private final OrderBusiness business;

    public OrderApi(OrdersRepository orderRepository, UserRepository userRepository, OrderService orderService, TokenService tokenService, OrderBusiness business) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.orderService = orderService;
        this.tokenService = tokenService;
        this.business = business;
    }

    @PostMapping("/list")
    public ResponseEntity<Object> list() throws BaseException {
        Object list = business.listByUser();
        return ResponseEntity.ok(list);
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createOrder(@RequestBody OrderReq req) throws BaseException {
        //GET USER FROM TOKEN (TYPE IS OBJECT)
        Object order = business.create(req);
        return ResponseEntity.ok(order);
    }


    @PostMapping("/cancel")
    public ResponseEntity<Object> cancel(@RequestBody OrderStatusReq req) throws BaseException {

        Object res = business.changeStatus(req, Orders.Status.CANCEL);
        return ResponseEntity.ok(res);
    }


    @PostMapping("/success")
    public ResponseEntity<Object> success(@RequestBody OrderStatusReq req) throws BaseException {

        Object res = business.changeStatus(req, Orders.Status.SUCCESS);
        return ResponseEntity.ok(res);
    }


    @PostMapping("/byId")
    public ResponseEntity<Object> byId(@RequestBody OrderReq req) throws BaseException {
        Object order = business.getById(req);
        return ResponseEntity.ok(order);
    }





}
