package com.example.backend.model.userModel;

import lombok.Data;

@Data
public class UserEditInfoReq {

    private String id;

    private String firstname;

    private String lastname;

    private String email;

    private String address;

}
