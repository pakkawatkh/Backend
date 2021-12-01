package com.example.backend.api;

import com.example.backend.business.UserBusiness;
import com.example.backend.model.MUserRequest;
import com.example.backend.model.MUserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserApi {

    private final UserBusiness userBusiness;

    public UserApi(UserBusiness userBusiness) {
        this.userBusiness = userBusiness;
    }

    @GetMapping("/{id}")
    public ResponseEntity<MUserResponse> getUser(@PathVariable("id") String id) {
        MUserResponse response=  userBusiness.getId(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<MUserResponse> register(@RequestBody MUserRequest mUserRequest){

   MUserResponse response= userBusiness.register(mUserRequest);
   return ResponseEntity.ok(response);
    }
}
