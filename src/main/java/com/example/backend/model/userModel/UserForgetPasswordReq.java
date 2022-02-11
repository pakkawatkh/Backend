package com.example.backend.model.userModel;

import lombok.Data;

@Data
public class UserForgetPasswordReq {

    private String phone;

    private String password;
}
