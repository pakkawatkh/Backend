package com.example.backend.repository;

import com.example.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);

    boolean existsByPhone(String email);

    Optional<User> findById(String id);

    boolean existsById(String id);

    boolean existsByEmail(String email);
}
