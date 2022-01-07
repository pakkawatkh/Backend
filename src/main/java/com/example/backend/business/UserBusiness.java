package com.example.backend.business;

import com.example.backend.entity.Shop;
import com.example.backend.entity.User;
import com.example.backend.exception.BaseException;
import com.example.backend.exception.UserException;
import com.example.backend.mapper.UserMapper;
import com.example.backend.model.Response;
import com.example.backend.model.adminModel.UserActiveReq;
import com.example.backend.model.userModel.*;
import com.example.backend.service.UserService;
import com.example.backend.service.token.TokenService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserBusiness {
private String MS="OK";

    private final UserService service;
    private final TokenService tokenService;
    private final UserMapper mapper;

    public UserBusiness(UserService userService, TokenService tokenService, UserMapper mapper) {
        this.service = userService;
        this.tokenService = tokenService;
        this.mapper = mapper;
    }


    public Object register(RegisterReq req) throws BaseException {
         service.createUser(req.getFirstname(), req.getLastname(), req.getPassword(), req.getPhone());
        return new Response().success("register success");

    }

    public Object login(LoginReq req) throws BaseException {
        Optional<User> opt = service.findByPhone(req.getPhone());
        User user = opt.get();
        if (!service.matchPassword(req.getPassword(), user.getPassword())) {
            throw UserException.notFound();
        }
        String token = tokenService.tokenize(user);

        return new Response().ok("login success", "token", token);
    }
    public Object loginAdmin(LoginReq req) throws BaseException {
        Optional<User> opt = service.findByPhone(req.getPhone());
        User user = opt.get();
        if (!service.matchPassword(req.getPassword(), user.getPassword())) {
            throw UserException.notFound();
        }

        if (user.getRole()!= User.Role.ADMIN){
            throw UserException.notFound();
        }
        String token = tokenService.tokenize(user);

        return new Response().ok("login success", "token", token);
    }

    public Object editProfile(UserEditReq req) throws BaseException {
        User user = tokenService.getUserByToken();

        service.editUserById(user, req);
        return new Response().ok("edit profile success", null, null);

    }

    public Object editPhone(UserEditReq req) throws BaseException {
        User user = tokenService.getUserByToken();

        service.editPhoneById(user, req);
        return new Response().ok("edit phone success", null, null);

    }

    public Object profile() throws BaseException {
        User user = tokenService.getUserByToken();

        UserResponse userResponse = mapper.toUserResponse(user);
        return new Response().ok(MS,"profile",userResponse);
//    return user;
    }

    public Object updateUserActive(UserActiveReq req) throws BaseException {

        tokenService.checkAdminByToken();

        User user = service.findById(req.getId());

        if (user.getRole()== User.Role.ADMIN){
            throw UserException.accessDenied();
        }

        service.updateUserActive(user, req.getActive());

        return new Response().success("update success");

    }

    public Object changPassword(UserPasswordReq req) throws BaseException {

        User user = tokenService.getUserByToken();

        if (!service.matchPassword(req.getPasswordOld(), user.getPassword())) {
            throw UserException.passwordIncorrect();
        }

        service.updatePassword(user, req.getPasswordNew());
        return new Response().success("update password success");
    }

    public Object userList() throws BaseException {

        tokenService.checkAdminByToken();

        List<User> all = service.findAll();
        return new Response().ok(MS,"user",all);
    }

    public Object userById(User req) throws BaseException {

        tokenService.checkAdminByToken();

        User user = service.findById(req.getId());
        return new Response().ok(MS,"user",user);
    }

    public Object userByShop(Shop req) throws BaseException {
        tokenService.checkAdminByToken();

        User user = service.findByShop(req.getId());
        return new Response().ok(MS,"user",user);
    }
}
