package com.example.backend.model.userModel;

import com.example.backend.entity.User;
import lombok.Data;

@Data
public class LoginSocialRequest {

    private String firstname;
    private String lastname;
    private String id;
    private User.Login login;

}
