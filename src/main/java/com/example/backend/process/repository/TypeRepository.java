package com.example.backend.process.repository;

import com.example.backend.entity.Type;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TypeRepository extends JpaRepository<Type, Integer> {

    List<Type> findAllByActiveIsTrue();

//    Optional<Type> findByName();

    boolean existsByName(String name);
}
