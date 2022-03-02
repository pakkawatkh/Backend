package com.example.backend.process.repository;

import com.example.backend.entity.TypeBuying;
import com.example.backend.entity.TypeBuyingList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TypeBuyingListRepository extends JpaRepository<TypeBuyingList, Integer> {

    Optional<TypeBuyingList> findByIdAndBuying(Integer integer, TypeBuying buying);

    boolean existsByIdAndBuying(Integer integer, TypeBuying buying);

    boolean existsByName(String name);

    List<TypeBuyingList> findAllByBuying(TypeBuying buying);
}
