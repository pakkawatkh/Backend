package com.example.backend.api;

import com.example.backend.business.UserBusiness;
import com.example.backend.config.token.TokenFilter;
import com.example.backend.entity.User;
import com.example.backend.exception.BaseException;
import com.example.backend.exception.UserException;
import com.example.backend.model.userModel.LoginReq;
import com.example.backend.model.userModel.RegisterReq;
import com.example.backend.model.userModel.MUserResponse;
import com.example.backend.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserApi {

    private final UserBusiness userBusiness;
private final UserRepository repository;
private  final TokenFilter tokenFilter;
    public UserApi(UserBusiness userBusiness, UserRepository repository, TokenFilter tokenFilter) {
        this.userBusiness = userBusiness;
        this.repository = repository;
        this.tokenFilter = tokenFilter;
    }

    @PostMapping("/register")
    public ResponseEntity<MUserResponse> register(@RequestBody RegisterReq req) throws BaseException {
        MUserResponse response = userBusiness.register(req);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginReq req) throws BaseException {
        Object res = userBusiness.login(req);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/getUser/{id}")
    public Optional<User> getUser(@PathVariable String id) throws BaseException {

        Optional<User> user = repository.findByEmail(id);
        if (user.isEmpty()){
            throw UserException.notFound();
        }
        return  user;
    }
}
