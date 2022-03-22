package com.example.backend.model.userModel;

import com.example.backend.exception.BaseException;
import com.example.backend.exception.MainException;
import lombok.Data;

@Data
public class UserForgetPasswordReq {

    private String email;

    private String password;

    //validate is not null
    public void isValid() throws BaseException {
        boolean valid =  email == null || password == null;
        if (valid) throw MainException.requestInvalid();
    }

    //validate is blank
    public void isBlank() throws BaseException {
        boolean valid =  email.isBlank() || password.isBlank();
        if (valid) throw MainException.requestIsBlank();
    }
}
