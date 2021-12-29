package com.example.backend.model.userModel;

import lombok.Data;

@Data
public class UserEditReq {

    private String firstname;

    private String lastname;

    private String password;

    private String phone;

    private String facebook;

    private String line;

    private String latitude;

    private String longitude;

    private String address;


}
