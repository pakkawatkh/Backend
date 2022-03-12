package com.example.backend.process.repository;

import com.example.backend.entity.News;
import com.example.backend.entity.Orders;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NewsRepository extends JpaRepository<News, Integer> {


    List<News> findAllByStatusIsTrueOrderByDateDesc(Pageable pageable);

    @Query(value = "SELECT * FROM News WHERE News.status =:status ORDER BY RAND() LIMIT :limit", nativeQuery = true)
    List<News> randomByStatusLimit(@Param("status") boolean status, @Param("limit") Integer limit);
}
