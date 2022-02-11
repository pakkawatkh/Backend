package com.example.backend.business;

import com.example.backend.entity.Shop;
import com.example.backend.entity.User;
import com.example.backend.exception.BaseException;
import com.example.backend.exception.UserException;
import com.example.backend.mapper.UserMapper;
import com.example.backend.model.Response;
import com.example.backend.model.adminModel.AUserActiveReq;
import com.example.backend.model.adminModel.AUserResponse;
import com.example.backend.model.userModel.*;
import com.example.backend.service.UserService;
import com.example.backend.service.token.TokenService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserBusiness {
    private final UserService service;
    private final TokenService tokenService;
    private final UserMapper mapper;
    private String MS = "OK";

    public UserBusiness(UserService userService, TokenService tokenService, UserMapper mapper) {
        this.service = userService;
        this.tokenService = tokenService;
        this.mapper = mapper;
    }


    public Object register(RegisterReq req) throws BaseException {

        if (req.getPhone() == null || req.getFirstname() == null || req.getLastname() == null || req.getPassword() == null) {
            throw UserException.requestInvalid();
        }
        service.createUser(req.getFirstname(), req.getLastname(), req.getPassword(), req.getPhone());
        return new Response().success("register success");

    }

    public Object loginUser(LoginReq req) throws BaseException {

        User user = login(req);
        if (!service.matchPassword(req.getPassword(), user.getPassword())) {
            throw UserException.notFound();
        }
        String token = tokenService.tokenize(user);

        return new Response().ok("login success", "token", token);
    }

    public Object loginAdmin(LoginReq req) throws BaseException {

        User user = login(req);

        if (user.getRole() != User.Role.ADMIN) {
            throw UserException.notFound();
        }
        String token = tokenService.tokenize(user);

        return new Response().ok("login success", "token", token);
    }

    public User login(LoginReq req) throws BaseException {
        if (req.getPhone() == null || req.getPassword() == null) {
            throw UserException.requestInvalid();
        }
        User user = service.findByPhone(req.getPhone());

        if (!service.matchPassword(req.getPassword(), user.getPassword())) {
            throw UserException.notFound();
        }
        return user;
    }

    public Object editProfile(UserEditReq req) throws BaseException {
        User user = tokenService.getUserByToken();
        if (req.getFirstname() == null || req.getLastname() == null) {
            throw UserException.requestInvalid();
        }

        service.editUserById(user, req.getFirstname(), req.getLastname(), req.getFacebook(), req.getLine());
        return new Response().success("edit profile success");

    }

    public Object editPhone(UserEditReq req) throws BaseException {
        User user = tokenService.getUserByToken();
        if (req.getPhone() == null) {
            throw UserException.requestInvalid();
        }
        service.editPhoneById(user, req.getPhone());
        return new Response().success("edit phone success");

    }

    public Object profile() throws BaseException {
        User user = tokenService.getUserByToken();

        UserResponse userResponse = mapper.toUserResponse(user);

        return new Response().ok(MS, "profile", userResponse);

    }


    public Object changPassword(UserPasswordReq req) throws BaseException {

        User user = tokenService.getUserByToken();
        if (req.getPasswordOld() == null || req.getPasswordNew() == null) {
            throw UserException.requestInvalid();
        }

        if (!service.matchPassword(req.getPasswordOld(), user.getPassword())) {
            throw UserException.passwordIncorrect();
        }

        service.updatePassword(user, req.getPasswordNew());
        return new Response().success("update password success");
    }

    // Admin ///
    public Object updateUserActive(AUserActiveReq req) throws BaseException {

        tokenService.checkAdminByToken();
        if (req.getId() == null || req.getActive() == null) {
            throw UserException.requestInvalid();
        }

        User user = service.findById(req.getId());

        if (user.getRole() == User.Role.ADMIN) {
            throw UserException.accessDenied();
        }

        service.updateUserActive(user, req.getActive());

        return new Response().success("update success");

    }

    public Object userList() throws BaseException {

        tokenService.checkAdminByToken();

        List<User> all = service.findAll();

        List<AUserResponse> aUserResponses = mapper.toListAUserResponse(all);
        return new Response().ok(MS, "user", aUserResponses);
    }

    public Object userById(User req) throws BaseException {

        tokenService.checkAdminByToken();
        if (req.getId() == null) {
            throw UserException.requestInvalid();
        }

        User user = service.findById(req.getId());

        AUserResponse aUserResponse = mapper.toAUserResponse(user);
        return new Response().ok(MS, "user", aUserResponse);
    }

    public Object userByShop(Shop req) throws BaseException {
        tokenService.checkAdminByToken();
        if (req.getId() == null) {
            throw UserException.requestInvalid();
        }
        User user = service.findByShop(req.getId());
        return new Response().ok(MS, "user", user);
    }

    public Object saveUserById(UserEditReq req) throws BaseException {
        tokenService.checkAdminByToken();
        if (req.getId() == null || req.getFirstname() == null || req.getLastname() == null) {
            throw UserException.requestInvalid();
        }
        User user = service.findById(req.getId());
        service.saveByUser(user, req.getFirstname(), req.getLastname(), req.getAddress(), req.getFacebook(), req.getLine());

        return new Response().success("Edit Profile Success");
    }

    public Object refreshToken() throws BaseException {
        User user = tokenService.getUserByToken();

        String token = tokenService.tokenize(user);

        return new Response().ok("login success", "token", token);

    }

}
