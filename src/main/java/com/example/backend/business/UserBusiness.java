package com.example.backend.business;

import com.example.backend.entity.User;
import com.example.backend.exception.BaseException;
import com.example.backend.exception.UserException;
import com.example.backend.mapper.UserMapper;
import com.example.backend.model.Response;
import com.example.backend.model.userModel.LoginReq;
import com.example.backend.model.userModel.RegisterReq;
import com.example.backend.model.userModel.UserEditReq;
import com.example.backend.model.userModel.UserResponse;
import com.example.backend.service.token.TokenService;
import com.example.backend.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserBusiness {

    private final UserService service;
    private final TokenService tokenService;
    private final UserMapper mapper;

    public UserBusiness(UserService userService, TokenService tokenService, UserMapper mapper) {
        this.service = userService;
        this.tokenService = tokenService;
        this.mapper = mapper;
    }


    public Object register(RegisterReq req) throws BaseException {
        Object user = service.createUser(req.getFirstname(),req.getLastname(), req.getPassword(), req.getPhone(),req.getEmail());
        return user;
    }

    public Object login(LoginReq req) throws BaseException {
        Optional<User> opt = service.findByEmail(req.getEmail());
        User user = opt.get();
        if (!service.matchPassword(req.getPassword(), user.getPassword())) {
            throw UserException.notFound();
        }
        String token = tokenService.tokenize(user);

        return new Response().success("login success","token",token);
    }

    public Object editProfile(UserEditReq req) throws BaseException {
        User user = tokenService.getUserByToken();

        Object edit = service.editUserById(user, req);
        return edit;
    }

    public Object editPhone(UserEditReq req) throws BaseException {
        User user = tokenService.getUserByToken();

        Object edit = service.editPhoneById(user, req);
        return edit;
    }

    public UserResponse profile() throws BaseException {
        User user = tokenService.getUserByToken();
        return mapper.toUserResponse(user);
//    return user;
    }
}
