package com.example.backend.service;

import com.example.backend.entity.Orders;
import com.example.backend.entity.Product;
import com.example.backend.entity.TypeProduct;
import com.example.backend.model.productModel.ProductReq;
import com.example.backend.repository.OrdersRepository;
import com.example.backend.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository repository;
    private final OrdersRepository ordersRepository;

    public ProductService(ProductRepository repository, OrdersRepository ordersRepository) {
        this.repository = repository;
        this.ordersRepository = ordersRepository;
    }

    public List<Product> createOneProduct(Orders order, List<ProductReq> productList, TypeProduct typeProduct) {

        for (ProductReq var : productList) {
            Product product = new Product();
            product.setDate(new Date());
            product.setName(var.getName());
            product.setPrice(var.getPrice());
            product.setOrders(order);
            product.setTypeProduct(typeProduct);
            repository.save(product);
        }

        Optional<Orders> orderOb = ordersRepository.findById(order.getId());

        List<Product> byOrderId = repository.findByOrders(orderOb);

        return byOrderId;
    }

//    public boolean createProduct(Orders order, MOrderRequest requests , TypeProduct typeProduct){
//
//
//    }

}
