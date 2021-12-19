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
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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

    @PostMapping("/create")
    public String createOrder(@RequestBody OrderReq req) throws BaseException {
        //GET USER FROM TOKEN (TYPE IS OBJECT)
//        User user = tokenService.getUserByToken();
//        Optional<Orders> order = orderService.createOrder(user, req.getStatus(), new Date());
        return "order";
    }


    @PostMapping("/getOrder")
    public Optional<Orders> getOrders(Integer id) {
        Optional<Orders> byId = orderRepository.findById(id);
        return byId;
    }

    @PostMapping("/cancel")
    public Object cancel(@RequestBody OrderStatusReq req) throws BaseException {

        Object res = business.changeStatus(req, Orders.Status.CANCEL);
        return res;
    }

    @PostMapping("/success")
    public Object success(@RequestBody OrderStatusReq req) throws BaseException {

        Object res = business.changeStatus(req, Orders.Status.SUCCESS);
        return res;
    }

}
