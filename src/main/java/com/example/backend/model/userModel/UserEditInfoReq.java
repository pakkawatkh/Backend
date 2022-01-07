package com.example.backend.model.userModel;

import com.example.backend.entity.User;
import lombok.Data;

import java.util.Date;

@Data
public class UserEditInfoReq {

    private String id;

    private String firstname;

    private String lastname;


    private String phone;

    private String address;

}
