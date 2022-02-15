package com.example.backend.api;

import com.example.backend.SetDefault.DataToken;
import com.example.backend.entity.User;
import com.example.backend.exception.BaseException;
import com.example.backend.model.Response;
import com.example.backend.repository.UserRepository;
import com.example.backend.service.token.TokenService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/loginSocial")
public class LoginSocialApi {


    private final TokenService tokenService;

    private final UserRepository userRepository;

    public LoginSocialApi(TokenService tokenService, UserRepository userRepository) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }

    @GetMapping("/getToken")
    public Object token() {
        DataToken dataToken = new DataToken();
        User user = new User();
        user.setId(dataToken.getId());
        user.setLast_password(dataToken.getLast_password());
        String tokenize = tokenService.tokenize(user, false);

        return new Response().ok("ok", "token", tokenize);
    }

    @PostMapping("/login")
    public Object login() throws BaseException {
        boolean userByToken = tokenService.checkLoginSocial();

        return new Response().success("ok");
    }
}
