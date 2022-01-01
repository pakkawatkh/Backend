package com.example.backend.model.userModel;

import lombok.Data;

@Data
public class UserPasswordReq {

    private String passwordOld;

    private String passwordNew;
}
