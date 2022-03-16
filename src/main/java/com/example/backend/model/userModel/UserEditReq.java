package com.example.backend.model.userModel;

import lombok.Data;

@Data
public class UserEditReq {

    private String firstname;

    private String lastname;

    private String facebook;

    private String line;

    private String address;

    private String shopName;


    //validate is not null
    public boolean isValid() {
        return firstname != null && lastname != null;
    }

    //validate is blank
    public boolean isBlank() {
        return firstname.isBlank() || lastname.isBlank();
    }

}
