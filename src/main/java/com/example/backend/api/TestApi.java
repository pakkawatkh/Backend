package com.example.backend.api;

import com.example.backend.entity.Orders;
import com.example.backend.model.newsModel.NewsReq;
import com.example.backend.process.repository.OrdersRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Validated
@RequestMapping("/test")
public class TestApi {
    private final OrdersRepository ordersRepository;


    public TestApi(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

    @GetMapping("/all")
    public Object all(){
//        Orders orders = new Orders();
        Orders.Status buy = Orders.Status.BUY;
        PageRequest limit = PageRequest.of(0, 6);

        return ordersRepository.findAllByStatusOrderByDateAsc(buy,limit);
    }
    @PostMapping("/file")
    public Object file(@RequestParam("file") MultipartFile file){
        return file;
    }
}
