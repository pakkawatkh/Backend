package com.example.backend.process.repository;

import com.example.backend.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News ,Integer> {

}
