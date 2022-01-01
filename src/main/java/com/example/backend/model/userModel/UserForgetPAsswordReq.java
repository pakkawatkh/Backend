package com.example.backend.model.userModel;

import lombok.Data;

@Data
public class UserForgetPAsswordReq {
    private String Email;
    private String phone;
    private String password;
}
