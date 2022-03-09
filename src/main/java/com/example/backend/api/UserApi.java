package com.example.backend.api;

import com.example.backend.exception.BaseException;
import com.example.backend.model.userModel.*;
import com.example.backend.process.business.UserBusiness;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserApi {

    private final UserBusiness business;

    public UserApi(UserBusiness userBusiness) {
        this.business = userBusiness;
    }

    //Token Verify
    @GetMapping("/confirm-account")
    public ResponseEntity<Object> confirmAccount() throws BaseException {
        Object res = business.confirmAccount();

        return ResponseEntity.ok(res);
    }

    @GetMapping("/profile")
    public ResponseEntity<Object> profile() throws BaseException {
        Object profile = business.profile();

        return ResponseEntity.ok(profile);
    }

    @PutMapping("/editProfile")
    public ResponseEntity<Object> editProfile(@RequestBody UserEditReq req) throws BaseException {
        Object edit = business.editProfile(req);

        return ResponseEntity.ok(edit);
    }

    @PutMapping("/changPassword")
    public ResponseEntity<Object> changPassword(@RequestBody UserPasswordReq req) throws BaseException {
        Object res = business.changPassword(req);

        return ResponseEntity.ok(res);
    }

    @PostMapping("/forgetPassword")
    public ResponseEntity<Object> forgetPassword(@RequestBody UserForgetPasswordReq req) throws BaseException {
        Object res = business.forgetPassword(req);

        return ResponseEntity.ok(res);
    }
    @GetMapping("/refreshToken")
    public ResponseEntity<Object> refreshToken() throws BaseException {
        Object res = business.refreshToken();

        return ResponseEntity.ok(res);
    }

}
