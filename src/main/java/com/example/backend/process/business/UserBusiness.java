package com.example.backend.process.business;

import com.example.backend.entity.Shop;
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
import com.example.backend.process.service.ShopService;
import com.example.backend.process.service.UserService;
import com.example.backend.process.service.token.TokenService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserBusiness {
    private final UserService service;
    private final ShopService shopService;
    private final TokenService tokenService;
    private final EmailBusiness emailBusiness;
    private final UserMapper mapper;
    private final String MS = "OK";

    public UserBusiness(UserService userService, ShopService shopService, TokenService tokenService, EmailBusiness emailBusiness, UserMapper mapper) {
        this.service = userService;
        this.shopService = shopService;
        this.tokenService = tokenService;
        this.emailBusiness = emailBusiness;
        this.mapper = mapper;
    }

    // not Verify
    public Object register(RegisterReq req) throws BaseException {
        req.isValid();
        req.isBlank();

        User user = service.createUser(req.getFirstname(), req.getLastname(), req.getPassword(), req.getEmail());

        String token = tokenService.tokenizeRegister(user);

        // sendEmail activate account
        emailBusiness.sendActivateUserEmail(user.getEmail(), user.getFirstname(), token);

        return new Response().success("โปรดยืนยันอีเมลภายใน 5 นาที");
    }

    public Object loginUser(LoginReq req) throws BaseException {
        User user = login(req);
        if (service.matchPassword(req.getPassword(), user.getPassword())) throw UserException.notFound();

        String token = tokenService.tokenizeLogin(user);
        String refreshToken = tokenService.tokenizeRefreshToken(user);

        LoginResponse profile = mapper.toLoginResponse(user);
        profile = this.updateUserLoginResponse(profile);

        return new Response().login("login success", "token", token, "refreshToken", refreshToken, "profile", profile);
    }

    public Object loginAdmin(LoginReq req) throws BaseException {
        User user = login(req);
        if (user.getRole() != User.Role.ADMIN) throw UserException.notFound();

        String token = tokenService.tokenizeLogin(user);
        String refreshToken = tokenService.tokenizeRefreshToken(user);

        return new Response().ok2("login success", "token", token, "refreshToken", refreshToken);
    }

    public User login(LoginReq req) throws BaseException {
        req.isValid();
        req.isBlank();

        User user = service.findByEmail(req.getEmail());
        if (service.matchPassword(req.getPassword(), user.getPassword())) throw UserException.notFound();

        if (!user.getRegister()) {
            String tokenizeRegister = tokenService.tokenizeRegister(user);
            emailBusiness.sendActivateUserEmail(user.getEmail(), user.getFirstname(), tokenizeRegister);
            throw UserException.pleaseConfirmAccount();
        }

        return user;
    }

    public Object loginSocial(LoginSocialRequest req) throws BaseException {

        req.isValid();
        req.isBlank();

        User user = service.saveLoginSocial(req.getFirstname(), req.getLastname(), req.getId(), req.getLogin());
        String token = tokenService.tokenizeLogin(user);
        String refreshToken = tokenService.tokenizeRefreshToken(user);

        LoginResponse profile = mapper.toLoginResponse(user);

        return new Response().login("login success", "token", token, "refreshToken", refreshToken, "profile", profile);
    }

    public Object forgetPassword(UserForgetPasswordReq req) throws BaseException {

        req.isValid();
        req.isBlank();

        User user = service.findByEmail(req.getEmail());
        String token = tokenService.tokenizeRegister(user);

        //forgetPassword send email to reset
        emailBusiness.sendResetPasswordUserEmail(user.getEmail(), user.getFirstname(), token);

        return new Response().success("เราได้ส่งอีเมลไปให้คุณ โปรดทำรายการภายใน 5 นาที");
    }

    public Object userByNumber(String number) throws BaseException {
        User user = service.findByNumber(number);

        UserResponse profile = mapper.toUserResponse(user);
        profile = this.updateUserResponse(profile);

        return new Response().ok(MS, "profile", profile);
    }

    //Token Verify
    public Object confirmAccount() throws BaseException {
        User user = tokenService.getUserByTokenRegister();
        if (user.getRegister()) throw MainException.confirmAccountExpires();

        service.RegisterActive(user);
        String token = tokenService.tokenizeLogin(user);
        String refreshToken = tokenService.tokenizeRefreshToken(user);
        LoginResponse profile = mapper.toLoginResponse(user);

        return new Response().login("ยืนยันตัวตนสำเร็จ", "token", token, "refreshToken", refreshToken, "profile", profile);
    }


    public Object editProfile(UserEditReq req) throws BaseException {
        User user = tokenService.getUserByToken();

        req.isValid();
        req.isBlank();

        if (user.getRole().equals(User.Role.SHOP)) {
            Shop shop = user.getShop();
            shop.setName(req.getShopName());
            shopService.updateShop(shop);
        }

        service.editUserById(user, req.getFirstname(), req.getLastname(), req.getAddress(), req.getLat(), req.getLng(), req.getProvince(), req.getDistrict(), req.getPicture());

        return new Response().success("edit profile success");
    }

    public Object profile() throws BaseException {
        User user = tokenService.getUserByToken();

        UserResponse userResponse = mapper.toUserResponse(user);
        userResponse = updateUserResponse(userResponse);

        return new Response().ok(MS, "profile", userResponse);
    }

    public Object changPassword(UserPasswordReq req) throws BaseException {
        User user = tokenService.getUserByToken();

        req.isValid();
        req.isBlank();

        if (service.matchPassword(req.getPasswordOld(), user.getPassword())) throw UserException.passwordIncorrect();

        service.updatePassword(user, req.getPasswordNew());

        return new Response().success("update password success");
    }

    public Object refreshToken() throws BaseException {
        User user = tokenService.getUserByToken();
        String token = tokenService.tokenizeLogin(user);
        String refreshToken = tokenService.tokenizeRefreshToken(user);

        return new Response().ok2("refreshToken success", "token", token, "refreshToken", refreshToken);
    }


    // Admin ///
    public Object updateUserActive(String id, AUserActiveReq req) throws BaseException {
        tokenService.checkAdminByToken();

        req.isValid();

        User user = service.findById(id);
        if (user.getRole() == User.Role.ADMIN) throw MainException.accessDenied();

        service.updateUserActive(user, req.getActive());

        return new Response().success("update success");
    }

    public Object userList() throws BaseException {
        tokenService.checkAdminByToken();

        List<AUserResponse> aUserResponses = mapper.toListAUserResponse(service.findAllByRoleIsNot());

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

    public Object editUserById(String id, UserEditReq req) throws BaseException {
        tokenService.checkAdminByToken();

        req.isValid();
        req.isBlank();

        User user = service.findById(id);
        service.saveEditByUser(user, req.getFirstname(), req.getLastname(), req.getAddress());

        return new Response().success("Edit Profile Success");
    }

    public UserResponse updateUserResponse(UserResponse response) {
        BaseUrlFile file = new BaseUrlFile();
        if (response.getPicture() != null)
            response.setPictureUrl(file.getDomain() + file.getImageProfileUrl() + response.getPicture());
        return response;
    }

    public LoginResponse updateUserLoginResponse(LoginResponse response) {
        BaseUrlFile file = new BaseUrlFile();
        if (response.getPicture() != null)
            response.setPicture(file.getDomain() + file.getImageProfileUrl() + response.getPicture());
        return response;
    }
}
