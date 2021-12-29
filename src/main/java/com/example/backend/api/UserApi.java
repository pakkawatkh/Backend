package com.example.backend.api;

import com.example.backend.business.UserBusiness;
import com.example.backend.config.token.TokenFilter;
import com.example.backend.exception.BaseException;
import com.example.backend.model.userModel.LoginReq;
import com.example.backend.model.userModel.RegisterReq;
import com.example.backend.model.userModel.UserEditReq;
import com.example.backend.model.userModel.UserResponse;
import com.example.backend.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@CrossOrigin(origins = "*")
@RequestMapping("/user")
public class UserApi {

    private final UserBusiness business;
    private final UserRepository repository;
    private final TokenFilter tokenFilter;

    public UserApi(UserBusiness userBusiness, UserRepository repository, TokenFilter tokenFilter) {
        this.business = userBusiness;
        this.repository = repository;
        this.tokenFilter = tokenFilter;
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody RegisterReq req) throws BaseException {
        Object response = business.register(req);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginReq req) throws BaseException {
        Object res = business.login(req);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/profile")
    public ResponseEntity<UserResponse> profile() throws BaseException {
        UserResponse profile = business.profile();
        return ResponseEntity.ok(profile);
    }

    @PostMapping("/editProfile")
    public ResponseEntity<Object> editProfile(@RequestBody UserEditReq req) throws BaseException {
        Object edit = business.editProfile(req);

        return ResponseEntity.ok(edit);
    }

    @PostMapping("/editPhone")
    public ResponseEntity<Object> editPhone(@RequestBody UserEditReq req) throws BaseException {
        Object edit = business.editPhone(req);

        return ResponseEntity.ok(edit);
    }

}
