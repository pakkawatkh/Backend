package com.example.backend.service;

import com.example.backend.entity.User;
import com.example.backend.exception.UserException;
import com.example.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserService {
    private final UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(String name, String email, String password, String phone, User.Role role){

        //TODO : Validate


        //save data to table user
        User entity = new User();
        entity.setEmail(email);
        entity.setName(name);
        entity.setPassword(password);
        entity.setPhone(phone);
        entity.setRole(role);
        entity.setDate(new Date());
        return userRepository.save(entity);

    }
    public User getUser(String id) throws UserException {
        if (!userRepository.existsById(id)){
            throw UserException.notId();
        }
        User entity = new User();
        entity.getEmail();
        return userRepository.findById(id).get();
    }



}
