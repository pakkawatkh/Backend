package com.example.backend.service;

import com.example.backend.entity.News;
import com.example.backend.repository.NewsRepository;
import org.springframework.stereotype.Service;

@Service
public class NewsService {

    private final NewsRepository repository;

    public NewsService(NewsRepository repository) {
        this.repository = repository;
    }

    public void save(News news) {
        //TODO: create news
        repository.save(news);
    }
}
