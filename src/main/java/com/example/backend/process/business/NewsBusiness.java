package com.example.backend.process.business;

import com.example.backend.entity.News;
import com.example.backend.exception.BaseException;
import com.example.backend.exception.MainException;
import com.example.backend.mapper.NewsMapper;
import com.example.backend.model.Response;
import com.example.backend.model.newsModel.NewsDetailResponse;
import com.example.backend.model.newsModel.NewsListParagraphResponse;
import com.example.backend.model.newsModel.NewsListResponse;
import com.example.backend.model.newsModel.NewsReq;
import com.example.backend.process.service.NewsService;
import com.example.backend.process.service.token.TokenService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class NewsBusiness {

    private final NewsService service;
    private final TokenService tokenService;
    private final NewsMapper mapper;
    private final FileBusiness fileBusiness;

    public NewsBusiness(NewsService service, TokenService tokenService, NewsMapper mapper, FileBusiness fileBusiness) {
        this.service = service;
        this.tokenService = tokenService;
        this.mapper = mapper;
        this.fileBusiness = fileBusiness;
    }

    public Object getDetailById(Integer id) throws BaseException {

        News byId = service.findById(id);
        NewsDetailResponse news = mapper.toNewsDetailResponse(byId);
        NewsListParagraphResponse paragraph = mapper.toNewsListParagraphResponse(byId);
        news.setParagraph(paragraph);
        return new Response().ok("ok", "news", news);
    }

    public Object save(NewsReq req) throws BaseException {
        tokenService.checkAdminByToken();

        if (!req.isValid()) throw MainException.requestInvalid();
        if (req.isBlank()) throw MainException.requestIsBlank();

        service.save(req.getTitle(), req.getParagraphOne(), req.getParagraphTwo(), req.getParagraphThree(), req.getParagraphFour(), req.getParagraphFive(), req.getPicture(), req.getReference(),req.getLinkRef());

        return new Response().success("create success");
    }

    public Object edit(Integer id, NewsReq req) throws BaseException {
        tokenService.checkAdminByToken();

        if (!req.isValid()) throw MainException.requestInvalid();
        if (req.isBlank()) throw MainException.requestIsBlank();

        service.edit(id, req.getTitle(), req.getParagraphOne(), req.getParagraphTwo(), req.getParagraphThree(), req.getParagraphFour(), req.getParagraphFive(), req.getPicture(), req.getReference(),req.getLinkRef());

        return new Response().success("edit success");
    }

    public Object getList() {
        List<News> all = service.findAll();
        List<NewsListResponse> res = mapper.toNewsListResponse(all);

        return new Response().ok("ok", "news", res);
    }

    public Object delete(Integer id) throws BaseException {
        tokenService.checkAdminByToken();

        service.deleteById(id);

        return new Response().success("delete success");
    }

    public Object getRecommend() {
        List<News> all = service.findAllLimit();
        List<NewsListResponse> res = mapper.toNewsListResponse(all);

        return new Response().ok("ok", "news", res);
    }
    public Object create(MultipartFile[] file,NewsReq req) throws BaseException {
        tokenService.checkAdminByToken();

        if (!req.isValid()) throw MainException.requestInvalid();
        if (req.isBlank()) throw MainException.requestIsBlank();
        ArrayList<String> picture = new ArrayList<>();
        for (MultipartFile file1 :file){
            String imgName = fileBusiness.saveImgNews2(file1);
            picture.add(imgName);
        }
        String pictureString = picture.toString();
        pictureString = pictureString.substring(1,pictureString.length()-1);

        req.setPicture(pictureString);

        service.save(req.getTitle(), req.getParagraphOne(), req.getParagraphTwo(), req.getParagraphThree(), req.getParagraphFour(), req.getParagraphFive(), req.getPicture(), req.getReference(),req.getLinkRef());

        return new Response().success("create success");
    }
}
