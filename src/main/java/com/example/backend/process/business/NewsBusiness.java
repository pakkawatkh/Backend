package com.example.backend.process.business;

import com.example.backend.entity.News;
import com.example.backend.exception.BaseException;
import com.example.backend.exception.MainException;
import com.example.backend.mapper.NewsMapper;
import com.example.backend.model.BaseUrlFile;
import com.example.backend.model.Response;
import com.example.backend.model.newsModel.NewsDetailResponse;
import com.example.backend.model.newsModel.NewsListResponse;
import com.example.backend.model.newsModel.NewsReq;
import com.example.backend.process.service.NewsService;
import com.example.backend.process.service.token.TokenService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@Service
public class NewsBusiness {

    private final NewsService service;
    private final TokenService tokenService;
    private final NewsMapper mapper;
    private final FileBusiness fileBusiness;
    private BaseUrlFile url = new BaseUrlFile();

    public NewsBusiness(NewsService service, TokenService tokenService, NewsMapper mapper, FileBusiness fileBusiness) {
        this.service = service;
        this.tokenService = tokenService;
        this.mapper = mapper;
        this.fileBusiness = fileBusiness;
    }

    public Object getDetailById(Integer id) throws BaseException {

        News byId = service.findById(id);
        NewsDetailResponse news = mapper.toNewsDetailResponse(byId);

        news.setPicture(updateImage(byId.getPicture()));
        news.setParagraph(updateParagraph(byId.getParagraph()));

        return new Response().ok("ok", "news", news);
    }

    public Object save(NewsReq req) throws BaseException {
        tokenService.checkAdminByToken();

        if (!req.isValid()) throw MainException.requestInvalid();
        if (req.isBlank()) throw MainException.requestIsBlank();

        service.save(req.getTitle(), req.getParagraph(), req.getPicture(), req.getReference(), req.getLinkRef());

        return new Response().success("create success");
    }

    public Object edit(Integer id, NewsReq req) throws BaseException {
        tokenService.checkAdminByToken();

        if (!req.isValid()) throw MainException.requestInvalid();
        if (req.isBlank()) throw MainException.requestIsBlank();

        service.edit(id, req.getTitle(), req.getParagraph(), req.getPicture(), req.getReference(), req.getLinkRef());

        return new Response().success("edit success");
    }

    public Object getList() {
        List<News> all = service.findAll();
        List<NewsListResponse> res = mapper.toNewsListResponse(all);
        List<NewsListResponse> newsListResponses = updateListNews(res);

        return new Response().ok("ok", "news", newsListResponses);
    }

    public Object delete(Integer id) throws BaseException {
        tokenService.checkAdminByToken();

        service.deleteById(id);

        return new Response().success("delete success");
    }

    public Object getRecommend(Integer id) {
        List<News> news = service.getRandomLimitByStatus(4, true,id);
        List<NewsListResponse> res = mapper.toNewsListResponse(news);
        List<NewsListResponse> newsListResponses = updateListNews(res);

        return new Response().ok("ok", "news", newsListResponses);
    }

    public Object create(MultipartFile[] file, String[] paragraphReq, String reference, String linkRef, String title) throws BaseException {
        tokenService.checkAdminByToken();

        //paragraphToString
        ArrayList<String> paragraph = new ArrayList<>(Arrays.asList(paragraphReq));
        String paragraphToString = paragraph.toString();
        paragraphToString = paragraphToString.substring(1, paragraphToString.length() - 1);

        //update OrderReq
        NewsReq req = new NewsReq();
        req.setParagraph(paragraphToString);
        req.setReference(reference);
        req.setLinkRef(linkRef);
        req.setTitle(title);

        //verify
        if (!req.isValid()) throw MainException.requestInvalid();
        if (req.isBlank()) throw MainException.requestIsBlank();

        //save img
        ArrayList<String> picture = new ArrayList<>();
        for (MultipartFile file1 : file) {
            String imgName = fileBusiness.saveImgNews2(file1);
            picture.add(imgName);
        }
        String pictureString = picture.toString();
        pictureString = pictureString.substring(1, pictureString.length() - 1);

        //save news
        service.save(req.getTitle(), req.getParagraph(), pictureString, req.getReference(), req.getLinkRef());

        return new Response().success("create success");
    }

    public Stream<String> updateImage(String img) {
        String[] picture = img.split(", ");
        return Arrays.stream(picture).map(s -> url.getDomain() + url.getImageNewsUrl() + s);
    }

    public String[] updateParagraph(String paragraph) {
        return paragraph.split(", ");
    }

    public List<NewsListResponse> updateListNews(List<NewsListResponse> data){
        for (NewsListResponse response:data){

            String[] paragraph = updateParagraph((String) response.getParagraph());
            response.setParagraph(paragraph[0]);

            Stream<String> picture = updateImage((String) response.getPicture());
            response.setPicture(picture.findFirst());
        }
        return data;
    }

}
