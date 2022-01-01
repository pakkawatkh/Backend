package com.example.backend.repository;

import com.example.backend.entity.Shop;
import com.example.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ShopRepository extends JpaRepository<Shop, Integer> {

    Optional<Shop> findByUser(User user);

    Optional<Shop> findByIdAndUser(Integer integer,User user);

    boolean existsByNumber(String number);


    boolean existsByUser(User user);
    boolean existsByName(String name);

    Optional<Shop> findByIdAndActive(Integer integer,Boolean active);

    List<Shop> findAllByActive(Boolean active);
}
