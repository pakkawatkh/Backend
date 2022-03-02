package com.example.backend.process.business;

import com.example.backend.entity.User;
import com.example.backend.exception.BaseException;
import com.example.backend.exception.MainException;
import com.example.backend.exception.UserException;
import com.example.backend.mapper.UserMapper;
import com.example.backend.model.BaseUrlFile;
import com.example.backend.model.Response;
import com.example.backend.model.adminModel.AUserActiveReq;
import com.example.backend.model.adminModel.AUserResponse;
import com.example.backend.model.userModel.*;
import com.example.backend.process.service.UserService;
import com.example.backend.process.service.token.TokenService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserBusiness {
    private final UserService service;
    private final TokenService tokenService;
    private final EmailBusiness emailBusiness;
    private final UserMapper mapper;
    private final String MS = "OK";

    public UserBusiness(UserService userService, TokenService tokenService, EmailBusiness emailBusiness, UserMapper mapper) {
        this.service = userService;
        this.tokenService = tokenService;
        this.emailBusiness = emailBusiness;
        this.mapper = mapper;
    }

    public Object register(RegisterReq req) throws BaseException {
        if (!req.isValid()) throw MainException.requestInvalid();
        if (req.isBlank()) throw MainException.requestIsBlank();

        User user = service.createUser(req.getFirstname(), req.getLastname(), req.getPassword(), req.getEmail());

        String token = tokenService.tokenizeRegister(user);

        // sendEmail activate account
        emailBusiness.sendActivateUserEmail(user.getEmail(), user.getFirstname(), token);

        return new Response().success("โปรดยืนยันอีเมลภายใน 5 นาที");
    }

    public Object confirmAccount() throws BaseException {
        User user = tokenService.getUserByTokenRegister();
        if (user.getRegister()) throw MainException.expires();

        user.setRegister(true);
        service.RegisterActive(user);
        String token = tokenService.tokenizeLogin(user);

        return new Response().ok("ยืนยันตัวตนสำเร็จ", "token", token);
    }

    public Object loginUser(LoginReq req) throws BaseException {
        User user = login(req);
        if (!service.matchPassword(req.getPassword(), user.getPassword())) throw UserException.notFound();

        String token = tokenService.tokenizeLogin(user);

        LoginResponse loginResponse = mapper.toLoginResponse(user);

        LoginResponse profile = this.updateUser(loginResponse);

        return new Response().ok2("login success", "token", token,"profile",profile);
    }

    public Object loginAdmin(LoginReq req) throws BaseException {
        User user = login(req);
        if (user.getRole() != User.Role.ADMIN) throw UserException.notFound();

        String token = tokenService.tokenizeLogin(user);

        return new Response().ok("login success", "token", token);
    }

    public User login(LoginReq req) throws BaseException {
        if (!req.isValid()) throw MainException.requestInvalid();
        if (req.isBlank()) throw MainException.requestIsBlank();

        User user = service.findByEmail(req.getEmail());
        if (!service.matchPassword(req.getPassword(), user.getPassword())) throw UserException.notFound();

        if (!user.getRegister()) {
            String tokenizeRegister = tokenService.tokenizeRegister(user);
            emailBusiness.sendActivateUserEmail(user.getEmail(), user.getFirstname(), tokenizeRegister);
            throw UserException.pleaseConfirmAccount();
        }

        return user;
    }

    public Object editProfile(UserEditReq req) throws BaseException {
        User user = tokenService.getUserByToken();

        if (!req.isValid()) throw MainException.requestInvalid();
        if (req.isBlank()) throw MainException.requestIsBlank();

        service.editUserById(user, req.getFirstname(), req.getLastname(), req.getFacebook(), req.getLine());

        return new Response().success("edit profile success");
    }

//    public Object editPhone(UserEditReq req) throws BaseException {
//        User user = tokenService.getUserByToken();
//        if (Objects.isNull(req.getEmail())) throw MainException.requestInvalid();
//        if (req.getEmail().isBlank()) throw MainException.requestIsBlank();
//
//        service.editEmailById(user, req.getEmail());
//
//        return new Response().success("edit phone success");
//    }

    public Object profile() throws BaseException {
        User user = tokenService.getUserByToken();

        UserResponse userResponse = mapper.toUserResponse(user);

        return new Response().ok(MS, "profile", userResponse);
    }

    public Object changPassword(UserPasswordReq req) throws BaseException {
        User user = tokenService.getUserByToken();

        if (!req.isValid()) throw MainException.requestInvalid();
        if (req.isBlank()) throw MainException.requestIsBlank();

        if (!service.matchPassword(req.getPasswordOld(), user.getPassword())) throw UserException.passwordIncorrect();

        service.updatePassword(user, req.getPasswordNew());

        return new Response().success("update password success");
    }


    // Admin ///
    public Object updateUserActive(String id,AUserActiveReq req) throws BaseException {
        tokenService.checkAdminByToken();

        if (!req.isValid()) throw MainException.requestInvalid();

        User user = service.findById(id);
        if (user.getRole() == User.Role.ADMIN) throw MainException.accessDenied();

        service.updateUserActive(user, req.getActive());

        return new Response().success("update success");
    }

    public Object userList() throws BaseException {
        tokenService.checkAdminByToken();

        List<AUserResponse> aUserResponses = mapper.toListAUserResponse(service.findAll());

        return new Response().ok(MS, "user", aUserResponses);
    }

    public Object userById(String id) throws BaseException {
        tokenService.checkAdminByToken();

        User user = service.findById(id);
        AUserResponse aUserResponse = mapper.toAUserResponse(user);

        return new Response().ok(MS, "user", aUserResponse);
    }

    public Object userByShop(Integer id) throws BaseException {
        tokenService.checkAdminByToken();

        User user = service.findByShop(id);

        return new Response().ok(MS, "user", user);
    }

    public Object editUserById(String id,UserEditReq req) throws BaseException {
        tokenService.checkAdminByToken();

        if (!req.isValid2()) throw MainException.requestInvalid();
        if (req.isBlank2()) throw MainException.requestIsBlank();

        User user = service.findById(id);
        service.saveEditByUser(user, req.getFirstname(), req.getLastname(), req.getAddress(), req.getFacebook(), req.getLine());

        return new Response().success("Edit Profile Success");
    }

    public Object refreshToken() throws BaseException {
        User user = tokenService.getUserByToken();
        String token = tokenService.tokenizeLogin(user);

        return new Response().ok("login success", "token", token);
    }

    public Object loginSocial(LoginSocialRequest req) throws BaseException {

        if (!req.isValid()) throw MainException.requestInvalid();
        if (req.isBlank()) throw MainException.requestIsBlank();

        User user = service.saveLoginSocial(req.getFirstname(), req.getLastname(), req.getEmail(), req.getId(), req.getLogin());
        String token = tokenService.tokenizeLogin(user);

        return new Response().ok("login success", "token", token);
    }

    public Object forgetPassword(UserForgetPasswordReq req) throws BaseException {

        if (!req.isValid()) throw MainException.requestInvalid();
        if (req.isBlank()) throw MainException.requestIsBlank();

        User user = service.findByEmail(req.getEmail());
        String token = tokenService.tokenizeRegister(user);

        //forgetPassword send email to reset
        emailBusiness.sendResetPasswordUserEmail(user.getEmail(), user.getFirstname(),token);

        return new Response().success("เราได้ส่งอีเมลไปให้คุณ โปรดทำรายการภายใน 5 นาที");
    }

    public LoginResponse updateUser(LoginResponse response){
        BaseUrlFile file = new BaseUrlFile();
        if (response.getPicture()!=null) response.setPicture(file.getDomain()+file.getImageProfileUrl()+response.getPicture());

        return response;
    }

//    public Object resetPassword(UserForgetPasswordReq req) throws BaseException {
//        User user = tokenService.getUserByTokenRegister();
//        if (Objects.isNull(req.getPassword())) throw MainException.requestInvalid();
//        if (req.getPassword().isBlank()) throw MainException.requestIsBlank();
//        if (req.getPassword().length()<8) throw UserException.passwordIsShort();
//
//        service.updatePassword(user,req.getPassword());
//        String token = tokenService.tokenizeLogin(user);
//
//        return new Response().ok("เปลี่ยนรหัสผ่านสำเร็จ","token",token);
//
//
//    }

}
