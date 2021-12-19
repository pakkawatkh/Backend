package com.example.backend.service;

import com.example.backend.entity.Orders;
import com.example.backend.entity.User;
import com.example.backend.exception.BaseException;
import com.example.backend.exception.OrderException;
import com.example.backend.model.Response;
import com.example.backend.repository.OrdersRepository;
import com.example.backend.repository.TypeRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderService {

    private final OrdersRepository repository;
    private final TypeRepository typeProductRepository;

    public OrderService(OrdersRepository repository, TypeRepository typeProductRepository) {
        this.repository = repository;
        this.typeProductRepository = typeProductRepository;
    }

//    public Optional<Orders> createOrder(User user, Integer status, Date date){
//
//        Orders order = new Orders();
//        order.setDate(date);
//        order.setStatus(Orders.Status.BUY);
//        order.setUser(user);
//        repository.save(order);
//
//        Optional<Type> typeId = typeProductRepository.findById(1);
//
//
//        Optional<Orders> orderById = repository.findById(order.getId());
//
//        HashMap<Orders, Object> data = new HashMap<>();
//
////        orderById.map(orders -> oneProduct);
//        return orderById;
//
//    }

    public Object changeStatus(Integer id, Orders.Status status, User user) throws BaseException {

        Optional<Orders> orders = repository.findByIdAndUser(id, user);
        if (orders.isEmpty()) {
            throw OrderException.orderNotFound();
        }

        Orders entity = orders.get();
        entity.setStatus(status);

        repository.save(entity);

        return new Response().success(status+" Success");
    }
}
