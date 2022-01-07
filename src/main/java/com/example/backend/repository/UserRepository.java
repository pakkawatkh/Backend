package com.example.backend.repository;

import com.example.backend.entity.Shop;
import com.example.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

//    Optional<User> findByEmail(String email);

    Optional<User> findByPhone(String phone);
    boolean existsByPhone(String phone);

    Optional<User> findById(String id);

    boolean existsById(String id);

    Optional<User> findByShop(Shop shop);

//    boolean existsByEmail(String email);
}
