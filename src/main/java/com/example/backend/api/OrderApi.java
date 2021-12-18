package com.example.backend.api;

import com.example.backend.entity.Orders;
import com.example.backend.entity.User;
import com.example.backend.exception.BaseException;
import com.example.backend.exception.UserException;
import com.example.backend.model.orderModel.OrderReq;
import com.example.backend.repository.OrdersRepository;
import com.example.backend.repository.UserRepository;
import com.example.backend.service.OrderService;
import com.example.backend.service.token.TokenService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("/order")
public class OrderApi {

    private final OrdersRepository orderRepository;
    private final UserRepository userRepository;
    private final OrderService orderService;
    private final TokenService tokenService;

    public OrderApi(OrdersRepository orderRepository, UserRepository userRepository, OrderService orderService, TokenService tokenService) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.orderService = orderService;
        this.tokenService = tokenService;
    }

//    @PostMapping("/getByUId")
//    public List<Orders> login(@RequestBody MOrderRequest request) {
//        Optional<User> user = userRepository.findById(request.getUser_id());
//        User users = user.get();
//        List<Orders> res = orderRepository.findByUserIdAndStatus(users.getId(),2);
//        System.out.println(randomNumber.ramDom());
//        return res;
//    }

    @PostMapping("/create")
    public Optional<Orders> createOrder(@RequestBody OrderReq req) throws BaseException {
        //GET USER FROM TOKEN (TYPE IS OBJECT)
        User user = tokenService.getUserByToken();
        Optional<Orders> order = orderService.createOrder(user, req.getStatus(), new Date(), req.getProduct());
        return order;
    }


    @PostMapping("/getOrder")
    public Optional<Orders> getOrders(Integer id) {
        Optional<Orders> byId = orderRepository.findById(id);
        return byId;
    }
}
