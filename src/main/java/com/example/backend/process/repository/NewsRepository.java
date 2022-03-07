package com.example.backend.process.repository;

import com.example.backend.entity.News;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NewsRepository extends JpaRepository<News, Integer> {


    List<News> findAllByStatusIsTrueOrderByDateDesc(Pageable pageable);
}
