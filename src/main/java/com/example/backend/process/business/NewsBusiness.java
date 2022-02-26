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
import java.util.Objects;

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
        if (Objects.isNull(req.getTitle()) || Objects.isNull(req.getDetail())) throw MainException.requestInvalid();
        if (req.getTitle().isBlank() || req.getDetail().isBlank()) throw MainException.requestIsBlank();

        if (Objects.isNull(req.getId())) {
            service.save(req.getTitle(), req.getDetail(), req.getPicture(), req.getReference());
        }
        else {
            service.edit(req.getId(), req.getTitle(), req.getDetail(), req.getPicture(), req.getReference());
        }

        return new Response().success("create success");
    }

    public Object getList() {
        List<News> all = service.findAll();
        return new Response().ok("ok", "news", all);
    }

    public Object delete(Integer id) throws BaseException {
        service.deleteById(id);

        return new Response().success("delete success");
    }
}
