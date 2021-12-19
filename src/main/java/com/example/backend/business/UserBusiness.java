package com.example.backend.business;

import com.example.backend.entity.User;
import com.example.backend.exception.BaseException;
import com.example.backend.exception.UserException;
import com.example.backend.mapper.UserMapper;
import com.example.backend.model.userModel.LoginReq;
import com.example.backend.model.userModel.RegisterReq;
import com.example.backend.model.userModel.MUserResponse;
import com.example.backend.service.token.TokenService;
import com.example.backend.service.UserService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

@Service
public class UserBusiness {

    private final UserService userService;
    private final UserMapper mapper;
    private final TokenService tokenService;

    public UserBusiness(UserService userService, UserMapper mapper, TokenService tokenService) {
        this.userService = userService;
        this.mapper = mapper;
        this.tokenService = tokenService;
    }


    public MUserResponse getId(String id) throws UserException {
        User user = userService.getUser(id);
        return mapper.toMUserResponse(user);
    }

    public MUserResponse register(RegisterReq req) throws BaseException {
        User user = userService.createUser(req.getFirstname(),req.getLastname(), req.getPassword(), req.getPhone());
        return mapper.toMUserResponse(user);
    }

    public Object login(LoginReq req) throws BaseException {
        Optional<User> opt = userService.findByEmail(req.getEmail());
        User user = opt.get();
        if (!userService.matchPassword(req.getPassword(), user.getPassword())) {
            throw UserException.notFound();
        }
        String token = tokenService.tokenize(user);
        HashMap<Object, Object> data = new HashMap<>();
        data.put("token", token);
        data.put("status", 1);
        return data;
    }
}
