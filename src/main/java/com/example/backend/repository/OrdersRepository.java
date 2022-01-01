package com.example.backend.repository;

import com.example.backend.entity.Orders;
import com.example.backend.entity.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrdersRepository extends JpaRepository<Orders, Integer> {

    List<Orders> findByUser(User user);


   Optional<Orders> findByIdAndUser(Integer integer, User user);

    List<Orders> findAllByUser(User user);
}
