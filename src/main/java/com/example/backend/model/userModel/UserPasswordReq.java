package com.example.backend.model.userModel;

import lombok.Data;

@Data
public class UserPasswordReq {

    private String passwordOld;

    private String passwordNew;

    //validate is not null
    public boolean isValid() {
        return passwordOld != null && passwordNew != null;
    }

    //validate is blank
    public boolean isBlank() {
        return passwordOld.isBlank() || passwordNew.isBlank();
    }
}
