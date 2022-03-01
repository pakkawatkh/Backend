package com.example.backend.model.userModel;

import lombok.Data;

@Data
public class UserForgetPasswordReq {

    private String email;

    private String password;

    //validate is not null
    public boolean isValid() {
        return email != null && password != null;
    }

    //validate is blank
    public boolean isBlank() {
        return email.isBlank() || password.isBlank();
    }
}
