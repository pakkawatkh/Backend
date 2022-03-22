package com.example.backend.model.userModel;

import com.example.backend.entity.User;
import com.example.backend.exception.BaseException;
import com.example.backend.exception.MainException;
import lombok.Data;

@Data
public class LoginSocialRequest {

    private String firstname;
    private String lastname;
    private String id;
    private User.Login login;

    //validate is not null
    public void isValid() throws BaseException {
        boolean valid =  firstname == null || id == null|| login == null;
        if (valid) throw MainException.requestInvalid();
    }

    //validate is blank
    public void isBlank() throws BaseException {
        boolean valid =  firstname.isBlank() || id.isBlank();
        if (valid) throw MainException.requestIsBlank();
    }
}
