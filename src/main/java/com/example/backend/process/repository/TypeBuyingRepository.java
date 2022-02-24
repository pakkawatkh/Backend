package com.example.backend.process.repository;

import com.example.backend.entity.TypeBuying;
import com.example.backend.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TypeBuyingRepository extends JpaRepository<TypeBuying,Integer> {

    Optional<TypeBuying> findByShop(Shop shop);

    boolean existsByShopAndName(Shop shop,String name);

    List<TypeBuying> findAllByShop(Shop shop);
}
