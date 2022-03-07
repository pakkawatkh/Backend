package com.example.backend.process.service;

import com.example.backend.entity.News;
import com.example.backend.exception.BaseException;
import com.example.backend.exception.MainException;
import com.example.backend.exception.NewsException;
import com.example.backend.process.repository.NewsRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NewsService {

    private final NewsRepository repository;

    public NewsService(NewsRepository repository) {
        this.repository = repository;
    }

    public void save(String title, String paragraphOne, String paragraphTwo, String paragraphThree, String paragraphFour, String paragraphFive, String picture, String ref,String link) throws BaseException {
        News entity = new News();
        entity.setParagraphOne(paragraphOne);
        entity.setParagraphTwo(paragraphTwo);
        entity.setParagraphThree(paragraphThree);
        entity.setParagraphFour(paragraphFour);
        entity.setParagraphFive(paragraphFive);
        entity.setPicture(picture);
        entity.setTitle(title);
        entity.setReference(ref);
        entity.setLinkRef(link);
        try {
            repository.save(entity);
        } catch (Exception e) {
            throw MainException.errorSave();
        }
    }

    public void edit(Integer id, String title, String paragraphOne, String paragraphTwo, String paragraphTree, String paragraphFour, String paragraphFive, String picture, String ref,String link) throws BaseException {
        Optional<News> byId = repository.findById(id);
        if (byId.isEmpty()) throw NewsException.notFound();
        News news = byId.get();
        news.setParagraphOne(paragraphOne);
        news.setParagraphTwo(paragraphTwo);
        news.setParagraphThree(paragraphTree);
        news.setParagraphFour(paragraphFour);
        news.setParagraphFive(paragraphFive);
        news.setPicture(picture);
        news.setTitle(title);
        news.setReference(ref);
        news.setLinkRef(link);
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

    public void deleteById(Integer id) throws BaseException {
        if (!repository.existsById(id)) throw NewsException.notFound();

        try {
            repository.deleteById(id);
        } catch (Exception e) {
            throw getMainException();
        }
    }
    public List<News> findAllLimit(){
        PageRequest limit = PageRequest.of(0, 4);

        List<News> all = repository.findAllByStatusIsTrueOrderByDateDesc(limit);
        return all;
    }

    private MainException getMainException() {
        return MainException.errorSave();
    }
}
