package com.example.backend.repository;

import com.example.backend.entity.Orders;
import com.example.backend.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findByOrders(Optional<Orders> id);
}
