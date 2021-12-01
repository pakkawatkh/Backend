package com.example.backend.business;

import com.example.backend.entity.User;
import com.example.backend.mapper.UserMapper;
import com.example.backend.model.MUserRequest;
import com.example.backend.model.MUserResponse;
import com.example.backend.service.UserService;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
public class UserBusiness {

    private final UserService userService;
    private  final UserMapper mapper;

    public UserBusiness(UserService userService, UserMapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    @SneakyThrows
    public MUserResponse getId(String id){
        User user= userService.getUser(id);
        return mapper.toMUserResponse(user);
    }
    public MUserResponse register(MUserRequest req){
        User user = userService.createUser(req.getName(),req.getEmail(),req.getPassword(),req.getPhone(),req.getRole());
        return mapper.toMUserResponse(user);
    }
}
