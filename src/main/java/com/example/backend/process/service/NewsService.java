package com.example.backend.process.service;

import com.example.backend.entity.News;
import com.example.backend.exception.BaseException;
import com.example.backend.exception.MainException;
import com.example.backend.exception.NewsException;
import com.example.backend.process.repository.NewsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NewsService {

    private final NewsRepository repository;

    public NewsService(NewsRepository repository) {
        this.repository = repository;
    }

    public void save(News news) throws BaseException {
        //TODO: create news
        try {
            repository.save(news);
        } catch (Exception e) {
            throw MainException.errorSave();
        }
    }

    public News findById(Integer id) throws BaseException {
        Optional<News> byId = repository.findById(id);
        if (byId.isEmpty()) throw NewsException.notFound();

        return byId.get();
    }

    public List<News> findAll() {
        return repository.findAll();
    }
}
