package com.example.backend.model.userModel;

import com.example.backend.entity.User;
import lombok.Data;

@Data
public class UserResponse {


    private String firstname;

    private String lastname;

    private String email;

    private String phone;

    private User.Role role;

    private String facebook;

    private String line;

    private String address;

    private Boolean active;

}
