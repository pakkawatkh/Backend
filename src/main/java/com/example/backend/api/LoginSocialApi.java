package com.example.backend.api;


import com.example.backend.business.UserBusiness;
import com.example.backend.entity.User;
import com.example.backend.exception.BaseException;
import com.example.backend.model.Response;
import com.example.backend.model.userModel.LoginSocialRequest;
import com.example.backend.service.token.TokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/loginSocial")
public class LoginSocialApi {

    @Value("${app.login-social.user-id}")
    private String userId;

    private final TokenService tokenService;
    private final UserBusiness userBusiness;

    public LoginSocialApi(TokenService tokenService, UserBusiness userBusiness) {
        this.tokenService = tokenService;
        this.userBusiness = userBusiness;
    }

    @GetMapping("/getToken")
    public Object token() {

        User user = new User();
        user.setId(this.userId);

        return new Response().ok("ok", "token", tokenService.tokenizeSocial(user));
    }

    @PostMapping("/login")
    public Object login(@RequestBody LoginSocialRequest request) throws BaseException {

        tokenService.checkLoginSocial();

        return userBusiness.loginSocial(request);
    }


}
