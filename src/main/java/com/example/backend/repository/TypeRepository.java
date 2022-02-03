package com.example.backend.repository;

import com.example.backend.entity.Type;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TypeRepository extends JpaRepository<Type, Integer> {

    @Override
    List<Type> findAll();

    List<Type> findAllByActiveIsTrue();

//    Optional<Type> findByName();

    boolean existsByName(String name);
}
