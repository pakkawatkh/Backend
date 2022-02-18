package com.example.backend.api;

import com.example.backend.business.UserBusiness;
import com.example.backend.exception.BaseException;
import com.example.backend.model.userModel.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserApi {

    private final UserBusiness business;

    public UserApi(UserBusiness userBusiness) {
        this.business = userBusiness;

    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody RegisterReq req) throws BaseException {
        Object response = business.register(req);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginReq req) throws BaseException {
        Object res = business.loginUser(req);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/profile")
    public ResponseEntity<Object> profile() throws BaseException {
        Object profile = business.profile();
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

    @PostMapping("/changPassword")
    public ResponseEntity<Object> changPassword(@RequestBody UserPasswordReq req) throws BaseException {
        Object res = business.changPassword(req);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/forgetPassword")
    public ResponseEntity<Object> forgetPassword(@RequestBody UserForgetPasswordReq req) {

    //TODO: forgetPassword
        return ResponseEntity.ok("a");

    }

    @GetMapping("/refreshToken")
    public ResponseEntity<Object> refreshToken() throws BaseException {
        Object res = business.refreshToken();

        return ResponseEntity.ok(res);
    }

}
