package com.example.backend;

import com.example.backend.exception.BaseException;
import com.example.backend.process.business.EmailBusiness;
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
class EmailTests {

    @Autowired
    private EmailBusiness business;

    @Test
    void sendEmail() throws BaseException {
        business.sendActivateUserEmail(data.email,data.name,data.token);
    }



    interface data {
        String email = "3a5eqpku6@gmail.com";
        String name = "pakkawat";
        String token = "asdasdasdsadasdasd";
    }
}

