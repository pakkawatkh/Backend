package com.example.backend.model.userModel;

import lombok.Data;

@Data
public class LoginReq {

    private String email;

    private String password;


    //validate is not null
    public boolean isValid() {
        return  password != null && email != null;
    }

    //validate is blank
    public boolean isBlank() {
        return  password.isBlank() || email.isBlank();
    }
}
