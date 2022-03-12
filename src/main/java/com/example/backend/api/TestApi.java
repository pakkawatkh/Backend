package com.example.backend.api;

import com.example.backend.entity.News;
import com.example.backend.entity.Orders;
import com.example.backend.exception.BaseException;
import com.example.backend.model.BaseUrlFile;
import com.example.backend.model.newsModel.NewsReq;
import com.example.backend.process.business.FileBusiness;
import com.example.backend.process.repository.NewsRepository;
import com.example.backend.process.repository.OrdersRepository;
import com.example.backend.process.service.NewsService;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

@RestController
@Validated
@RequestMapping("/test")
public class TestApi {
    private final OrdersRepository ordersRepository;
    private final FileBusiness fileBusiness;
    private final NewsRepository newsRepository;
    private final NewsService newsService;


    public TestApi(OrdersRepository ordersRepository, FileBusiness fileBusiness, NewsRepository newsRepository, NewsService newsService) {
        this.ordersRepository = ordersRepository;
        this.fileBusiness = fileBusiness;
        this.newsRepository = newsRepository;
        this.newsService = newsService;
    }

    @GetMapping("/all")
    public Object all() {

        Orders.Status buy = Orders.Status.BUY;
        PageRequest limit = PageRequest.of(0, 6);

        return ordersRepository.findAllByStatusOrderByDateAsc(buy, limit);
    }

    @PostMapping("/file")
    public Object file(@RequestParam("file") MultipartFile[] file) throws BaseException {
        ArrayList<String> picture = new ArrayList<>();
        for (MultipartFile file1 : file) {
            String fileName = fileBusiness.saveImgNews2(file1);
            picture.add(fileName);
        }
//        for(String name1 :name){
//            picture.add(name1);
//        }
        String s = picture.toString();
        String substring = s.substring(1, s.length() - 1);
        NewsReq req = new NewsReq();
        req.setTitle("title");
        req.setReference("ref");
        req.setParagraph("one");
        req.setLinkRef("link");
        req.setPicture(substring);

        newsService.save(req.getTitle(), req.getParagraph(), req.getPicture(), req.getReference(), req.getLinkRef());
        return picture;
    }

    @GetMapping("news/{id}")
    public Object news(@PathVariable("id") Integer id) {
        Optional<News> byId = newsRepository.findById(id);
        News news = byId.get();
        String pictureOne = news.getPicture();
        String[] split = pictureOne.split(", ");
        Stream<String> stream = Arrays.stream(split).map(s -> new BaseUrlFile().getImageNewsUrl() + s);
        return news;
    }

    @GetMapping("/order")
    public Object getProvince() {
        return ordersRepository.getProvince(Orders.Status.BUY);
    }
}
