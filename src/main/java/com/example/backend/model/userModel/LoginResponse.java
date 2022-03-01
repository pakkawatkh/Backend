package com.example.backend.model.userModel;

import com.example.backend.entity.User;
import lombok.Data;

@Data
public class LoginResponse {
    String firstname;
    String lastname;
    User.Role role;
}
