package com.example.backend.api;

import com.example.backend.entity.Orders;
import com.example.backend.entity.Product;
import com.example.backend.repository.OrdersRepository;
import com.example.backend.repository.ProductRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductApi {
    private final ProductRepository productRepository;
    private final OrdersRepository ordersRepository;

    public ProductApi(ProductRepository productRepository, OrdersRepository ordersRepository) {
        this.productRepository = productRepository;
        this.ordersRepository = ordersRepository;
    }

    @PostMapping("/get")
    public List<Product> createOrder() {
        //GET USER FROM TOKEN (TYPE IS OBJECT)
        Optional<Orders> byId = ordersRepository.findById(46);
        List<Product> byOrderId = productRepository.findByOrders(byId);
        return byOrderId;

    }
}
