package com.example.backend.repository;

import com.example.backend.entity.RecycleList;
import com.example.backend.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecycleListRepository extends JpaRepository<RecycleList,Integer> {

    Optional<RecycleList> findByShop(Shop shop);
}
