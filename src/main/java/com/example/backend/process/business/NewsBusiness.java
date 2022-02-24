package com.example.backend.process.business;

import com.example.backend.entity.News;
import com.example.backend.exception.BaseException;
import com.example.backend.model.Response;
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

    public Object getDetailById(Integer id) {
        return new Response().ok("", "", "");
    }

    public Object save(News news) throws BaseException {
        tokenService.checkAdminByToken();
        //TODO : SAVE
        return new Response().success("");
    }

    public Object getList() {
        List<News> all = service.findAll();
        return new Response().ok("", "", all);
    }
}
