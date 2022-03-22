package com.example.backend.model.userModel;

import com.example.backend.exception.BaseException;
import com.example.backend.exception.MainException;
import lombok.Data;

@Data
public class LoginReq {

    private String email;

    private String password;


    //validate is not null
    public void isValid() throws BaseException {
        boolean valid =   password == null || email == null;
        if (valid) throw MainException.requestInvalid();
    }

    //validate is blank
    public void isBlank() throws BaseException {
        boolean valid =   password.isBlank() || email.isBlank();
        if (valid) throw MainException.requestIsBlank();
    }
}
