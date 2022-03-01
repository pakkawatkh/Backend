package com.example.backend.process.business;

import com.example.backend.entity.News;
import com.example.backend.exception.BaseException;
import com.example.backend.exception.MainException;
import com.example.backend.model.Response;
import com.example.backend.model.newsModel.NewsReq;
import com.example.backend.process.service.NewsService;
import com.example.backend.process.service.token.TokenService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsBusiness {

    private final NewsService service;
    private final TokenService tokenService;

    public NewsBusiness(NewsService service, TokenService tokenService) {
        this.service = service;
        this.tokenService = tokenService;
    }

    public Object getDetailById(Integer id) throws BaseException {

        News byId = service.findById(id);
        return new Response().ok("ok", "news", byId);
    }

    public Object save(NewsReq req) throws BaseException {
        tokenService.checkAdminByToken();

        if (!req.isValid()) throw MainException.requestInvalid();
        if (req.isBlank()) throw MainException.requestIsBlank();

        service.save(req.getTitle(), req.getDetail(), req.getPicture(), req.getReference());

        return new Response().success("create success");
    }

    public Object edit(Integer id, NewsReq req) throws BaseException {
        tokenService.checkAdminByToken();

        if (!req.isValid()) throw MainException.requestInvalid();
        if (req.isBlank()) throw MainException.requestIsBlank();

        service.edit(id, req.getTitle(), req.getDetail(), req.getPicture(), req.getReference());

        return new Response().success("edit success");
    }

    public Object getList() {
        List<News> all = service.findAll();

        return new Response().ok("ok", "news", all);
    }

    public Object delete(Integer id) throws BaseException {
        tokenService.checkAdminByToken();

        service.deleteById(id);

        return new Response().success("delete success");
    }
}
