package com.example.backend.process.business;

import com.example.backend.entity.Shop;
import com.example.backend.entity.User;
import com.example.backend.exception.BaseException;
import com.example.backend.exception.MainException;
import com.example.backend.exception.UserException;
import com.example.backend.mapper.UserMapper;
import com.example.backend.model.Response;
import com.example.backend.model.adminModel.AUserActiveReq;
import com.example.backend.model.adminModel.AUserResponse;
import com.example.backend.model.userModel.*;
import com.example.backend.process.service.UserService;
import com.example.backend.process.service.token.TokenService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserBusiness {
    private final UserService service;
    private final TokenService tokenService;
    private final UserMapper mapper;
    private final String MS = "OK";

    public UserBusiness(UserService userService, TokenService tokenService, UserMapper mapper) {
        this.service = userService;
        this.tokenService = tokenService;
        this.mapper = mapper;
    }

    public Object register(RegisterReq req) throws BaseException {
        if (Objects.isNull(req.getEmail()) || Objects.isNull(req.getFirstname()) || Objects.isNull(req.getLastname()) || Objects.isNull(req.getPassword()))
            throw MainException.requestInvalid();
        if (req.getEmail().isBlank() || req.getFirstname().isBlank() || req.getLastname().isBlank() || req.getPassword().isBlank())
            throw MainException.requestIsBlank();

        service.createUser(req.getFirstname(), req.getLastname(), req.getPassword(), req.getEmail());

        return new Response().success("register success");
    }

    public Object loginUser(LoginReq req) throws BaseException {
        User user = login(req);
        if (!service.matchPassword(req.getPassword(), user.getPassword())) throw UserException.notFound();

        String token = tokenService.tokenizeLogin(user);

        return new Response().ok("login success", "token", token);
    }

    public Object loginAdmin(LoginReq req) throws BaseException {
        User user = login(req);
        if (user.getRole() != User.Role.ADMIN) throw UserException.notFound();

        String token = tokenService.tokenizeLogin(user);

        return new Response().ok("login success", "token", token);
    }

    public User login(LoginReq req) throws BaseException {
        if (Objects.isNull(req.getEmail()) || Objects.isNull(req.getPassword())) throw MainException.requestInvalid();
        if (req.getEmail().isBlank() || req.getPassword().isBlank()) throw MainException.requestIsBlank();

        User user = service.findByEmail(req.getEmail());
        if (!service.matchPassword(req.getPassword(), user.getPassword())) throw UserException.notFound();

        return user;
    }

    public Object editProfile(UserEditReq req) throws BaseException {
        User user = tokenService.getUserByToken();
        if (Objects.isNull(req.getFirstname()) || Objects.isNull(req.getLastname()))
            throw MainException.requestInvalid();
        if (req.getFirstname().isBlank() || req.getLastname().isBlank()) throw MainException.requestIsBlank();

        service.editUserById(user, req.getFirstname(), req.getLastname(), req.getFacebook(), req.getLine());

        return new Response().success("edit profile success");
    }

    public Object editPhone(UserEditReq req) throws BaseException {
        User user = tokenService.getUserByToken();
        if (Objects.isNull(req.getEmail())) throw MainException.requestInvalid();
        if (req.getEmail().isBlank()) throw MainException.requestIsBlank();

        service.editEmailById(user, req.getEmail());

        return new Response().success("edit phone success");
    }

    public Object profile() throws BaseException {
        User user = tokenService.getUserByToken();

        UserResponse userResponse = mapper.toUserResponse(user);

        return new Response().ok(MS, "profile", userResponse);
    }

    public Object changPassword(UserPasswordReq req) throws BaseException {
        User user = tokenService.getUserByToken();
        if (Objects.isNull(req.getPasswordOld()) || Objects.isNull(req.getPasswordNew()))
            throw MainException.requestInvalid();
        if (req.getPasswordOld().isBlank() || req.getPasswordNew().isBlank()) throw MainException.requestIsBlank();

        if (!service.matchPassword(req.getPasswordOld(), user.getPassword())) throw UserException.passwordIncorrect();

        service.updatePassword(user, req.getPasswordNew());

        return new Response().success("update password success");
    }


    // Admin ///
    public Object updateUserActive(AUserActiveReq req) throws BaseException {
        tokenService.checkAdminByToken();
        if (Objects.isNull(req.getId()) || Objects.isNull(req.getActive())) throw MainException.requestInvalid();
        if (req.getId().isBlank()) throw MainException.requestIsBlank();

        User user = service.findById(req.getId());
        if (user.getRole() == User.Role.ADMIN) throw MainException.accessDenied();

        service.updateUserActive(user, req.getActive());

        return new Response().success("update success");
    }

    public Object userList() throws BaseException {
        tokenService.checkAdminByToken();

        List<AUserResponse> aUserResponses = mapper.toListAUserResponse(service.findAll());

        return new Response().ok(MS, "user", aUserResponses);
    }

    public Object userById(User req) throws BaseException {
        tokenService.checkAdminByToken();
        if (Objects.isNull(req.getId())) throw MainException.requestInvalid();
        if (req.getId().isBlank()) throw MainException.requestIsBlank();

        User user = service.findById(req.getId());
        AUserResponse aUserResponse = mapper.toAUserResponse(user);

        return new Response().ok(MS, "user", aUserResponse);
    }

    public Object userByShop(Shop req) throws BaseException {
        tokenService.checkAdminByToken();
        if (Objects.isNull(req.getId())) throw MainException.requestInvalid();

        User user = service.findByShop(req.getId());

        return new Response().ok(MS, "user", user);
    }

    public Object saveUserById(UserEditReq req) throws BaseException {
        tokenService.checkAdminByToken();
        if (Objects.isNull(req.getId()) || Objects.isNull(req.getFirstname()) || Objects.isNull(req.getLastname()))
            throw MainException.requestInvalid();
        if (req.getId().isBlank() || req.getFirstname().isBlank() || req.getLastname().isBlank())
            throw MainException.requestIsBlank();

        User user = service.findById(req.getId());
        service.saveByUser(user, req.getFirstname(), req.getLastname(), req.getAddress(), req.getFacebook(), req.getLine());

        return new Response().success("Edit Profile Success");
    }

    public Object refreshToken() throws BaseException {
        User user = tokenService.getUserByToken();
        String token = tokenService.tokenizeLogin(user);

        return new Response().ok("login success", "token", token);
    }

    public Object loginSocial(LoginSocialRequest request) throws BaseException {
        User user = service.saveLoginSocial(request.getFirstname(), request.getLastname(), request.getEmail(), request.getId(), request.getLogin());
        String token = tokenService.tokenizeLogin(user);

        return new Response().ok("login success", "token", token);
    }

}
