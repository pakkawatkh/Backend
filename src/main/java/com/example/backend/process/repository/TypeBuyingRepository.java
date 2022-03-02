package com.example.backend.process.repository;

import com.example.backend.entity.Shop;
import com.example.backend.entity.TypeBuying;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TypeBuyingRepository extends JpaRepository<TypeBuying, Integer> {

    Optional<TypeBuying> findByShop(Shop shop);

    boolean existsByShopAndName(Shop shop, String name);
    boolean existsByIdAndShop(Integer id,Shop shop);

    List<TypeBuying> findAllByShop(Shop shop);

    Optional<TypeBuying> findByIdAndShop(Integer integer, Shop shop);

    List<TypeBuying> findAllByShop(Shop shop, Pageable pageable);

    @Query(value = "SELECT count(t) FROM TypeBuying as t where t.shop = :shop")
    Long count(@Param("shop") Shop shop);



}
