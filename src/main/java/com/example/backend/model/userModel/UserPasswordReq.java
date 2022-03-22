package com.example.backend.model.userModel;

import com.example.backend.exception.BaseException;
import com.example.backend.exception.MainException;
import lombok.Data;

@Data
public class UserPasswordReq {

    private String passwordOld;

    private String passwordNew;

    //validate is not null
    public void isValid() throws BaseException {
        boolean valid = passwordOld == null || passwordNew == null;
        if (valid) throw MainException.requestInvalid();
    }

    //validate is blank
    public void isBlank() throws BaseException {
        boolean valid =  passwordOld.isBlank() || passwordNew.isBlank();
        if (valid) throw MainException.requestIsBlank();
    }
}
