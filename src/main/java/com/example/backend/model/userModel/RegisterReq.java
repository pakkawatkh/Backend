package com.example.backend.model.userModel;

import lombok.Data;

@Data
public class RegisterReq {

    private String name;

    private String email;

    private String password;

    private String phone;

}
