package com.example.backend;

import com.example.backend.process.service.OrderService;
import com.example.backend.process.service.UserService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) //จัดลำดับการรัน function @Test
class ApplicationTests {

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @Test
    void contextLoads() {
    }

//    @Test
//    public void createOrder() {
//        Optional<User> byEmail = userService.findByEmail("pakkawat@gmail.com");
//        User user = byEmail.get();
//        Integer id = orderService.createOrder(user, orderData.status, orderData.date);
//        System.out.println("id = "+id);
//    }


    interface orderData {
        Integer status = 1;
        Date date = new Date();
    }
}

