package com.example.backend.model.userModel;

import com.example.backend.entity.User;
import lombok.Data;

@Data
public class LoginSocialRequest {

    private String firstname;
    private String lastname;
    private String id;
    private User.Login login;
    private String email;

    //validate is not null
    public boolean isValid() {
        return firstname != null && id != null&& login != null;
    }

    //validate is blank
    public boolean isBlank() {
        return firstname.isBlank() || id.isBlank();
    }
}
