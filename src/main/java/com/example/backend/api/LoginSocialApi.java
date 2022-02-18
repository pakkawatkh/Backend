package com.example.backend.api;


import com.example.backend.entity.User;
import com.example.backend.exception.BaseException;
import com.example.backend.model.Response;
import com.example.backend.model.userModel.LoginSocialRequest;
import com.example.backend.service.token.TokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/loginSocial")
public class LoginSocialApi {

    @Value("${app.login-social.user-id}")
    private String userId;

    private final TokenService tokenService;

    public LoginSocialApi(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @GetMapping("/getToken")
    public Object token() {

        User user = new User();
        user.setId(this.userId);
        user.setLast_password(new Date());

        String tokenize = tokenService.tokenizeSocial(user);

        return new Response().ok("ok", "token", tokenize);
    }

    @PostMapping("/login")
    public Object login(LoginSocialRequest request) throws BaseException {

        tokenService.checkLoginSocial();

        return new Response().success("ok");
    }


}
