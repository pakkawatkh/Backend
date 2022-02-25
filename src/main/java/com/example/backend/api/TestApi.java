package com.example.backend.api;

import com.example.backend.entity.Orders;
import com.example.backend.exception.BaseException;
import com.example.backend.exception.MainException;
import com.example.backend.model.newsModel.NewsReq;
import com.example.backend.process.repository.NewsRepository;
import com.example.backend.process.repository.OrdersRepository;
import com.example.backend.process.repository.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

@RestController
@Validated
@RequestMapping("/test")
public class TestApi {
    public final OrdersRepository repository;
    public final UserRepository userRepository;
    public final NewsRepository newsRepository;

    public TestApi(OrdersRepository repository, UserRepository userRepository, NewsRepository newsRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.newsRepository = newsRepository;
    }

    @GetMapping("/user/{page}/{size}")
    public Object userList(@PathVariable Integer page, @PathVariable Integer size) {

        PageRequest limit = PageRequest.of(page, size);
        Stream<Orders> ordersStream = repository.findAllByStatus(Orders.Status.BUY, limit).get();

        int all = repository.findAll().size();

        Map<Object, Object> data = new HashMap<>();

        data.put("count", all);
        data.put("limit", ordersStream);

        return data;
    }

    @GetMapping("/group")
    public Object getUser() {
        List<Object[]> objects = userRepository.countUser();
        return objects;
    }

    @PostMapping("/news")
    public Object saveNews(@RequestBody NewsReq news) throws BaseException {

        if (Objects.isNull(news.getTitle())) throw MainException.accessDenied();
        if (news.getTitle().isBlank()) throw MainException.requestInvalid();

        return ResponseEntity.ok(news);
    }

}
