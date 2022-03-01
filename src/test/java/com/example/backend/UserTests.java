package com.example.backend;

import com.example.backend.entity.User;
import com.example.backend.exception.BaseException;
import com.example.backend.model.userModel.UserResponse;
import com.example.backend.process.repository.UserRepository;
import com.example.backend.process.service.UserService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.BreakIterator;
import java.util.Optional;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) //จัดลำดับการรัน function @Test
class UserTests {

    @Autowired
    private UserService service;
    @Autowired
    private UserRepository repository;

    @Order(1)
    @Test
    void saveUser() throws BaseException {
        service.createUser(data.firstname, data.lastname, data.password, data.email);
    }
    @Order(2)
    @Test
    void updateRegister(){
        Optional<User> byEmail = repository.findByEmail(data.email);
        if (byEmail.isEmpty()) return;
        User user = byEmail.get();
        user.setRegister(true);
        repository.save(user);
    }


    interface data {
        String firstname = "boss";
        String lastname = "solo";
        String password = "12345678";
        User.Role role = User.Role.USER;
        Boolean active = true;
        String email = "boss2@gmail.com";
        Boolean register = true;
    }
}

