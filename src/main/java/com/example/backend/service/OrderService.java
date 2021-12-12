package com.example.backend.service;

import com.example.backend.entity.Base.RandomNumber;
import com.example.backend.entity.Orders;
import com.example.backend.entity.Product;
import com.example.backend.entity.TypeProduct;
import com.example.backend.entity.User;
import com.example.backend.model.productModel.ProductReq;
import com.example.backend.repository.OrdersRepository;
import com.example.backend.repository.TypeProductRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrdersRepository repository;
    private final ProductService productService;
    private final TypeProductRepository typeProductRepository;

    public OrderService(OrdersRepository repository, ProductService productService, TypeProductRepository typeProductRepository) {
        this.repository = repository;
        this.productService = productService;
        this.typeProductRepository = typeProductRepository;
    }

    public Optional<Orders> createOrder(User user, Integer status, Date date, List<ProductReq> product) {

        Orders order = new Orders();
        order.setDate(date);
        order.setStatus(status);
        order.setUser(user);
        order.setNumber("#"+new RandomNumber().ramDom());
        repository.save(order);

        Optional<TypeProduct> typeId = typeProductRepository.findById(1);

        List<Product> oneProduct = productService.createOneProduct(order, product, typeId.get());

        Optional<Orders> orderById = repository.findById(order.getId());

        HashMap<Orders, Object> data = new HashMap<>();

//        orderById.map(orders -> oneProduct);
        return orderById;

    }
}
