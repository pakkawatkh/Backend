package com.example.backend.model.userModel;

import com.example.backend.exception.BaseException;
import com.example.backend.exception.MainException;
import lombok.Data;

@Data
public class RegisterReq {

    private String firstname;

    private String lastname;

    private String password;

    private String email;

    //validate is not null
    public void isValid() throws BaseException {
        boolean valid =  firstname == null || lastname == null || password == null || email == null;
        if (valid) throw MainException.requestInvalid();

    }

    //validate is blank
    public void isBlank() throws BaseException {
        boolean valid =  firstname.isBlank() || lastname.isBlank() || password.isBlank() || email.isBlank();
        if (valid) throw MainException.requestIsBlank();
    }
}
