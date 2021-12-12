package com.example.backend.repository;

import com.example.backend.entity.Orders;
import com.example.backend.entity.TypeProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TypeProductRepository extends JpaRepository<TypeProduct, Integer> {

}
