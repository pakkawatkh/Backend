package com.example.backend.api;

import com.example.backend.entity.Orders;
import com.example.backend.repository.OrdersRepository;
import com.example.backend.repository.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@RestController
@RequestMapping("/test")
public class TestApi {
    public final OrdersRepository repository;
    public final UserRepository userRepository;

    public TestApi(OrdersRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
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


}
