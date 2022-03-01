package com.example.backend.model.userModel;

import lombok.Data;

@Data
public class RegisterReq {

    private String firstname;

    private String lastname;

    private String password;

    private String email;

    private String address;

    //validate is not null
    public boolean isValid() {
        return firstname != null && lastname != null && password != null && email != null;
    }

    //validate is blank
    public boolean isBlank() {
        return firstname.isBlank() || lastname.isBlank() || password.isBlank() || email.isBlank();
    }


}
