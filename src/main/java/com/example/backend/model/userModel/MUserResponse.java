package com.example.backend.model.userModel;

import com.example.backend.entity.User;
import lombok.Data;

@Data
public class MUserResponse {

    private String name;

    private String email;

    private String phone;

    private User.Role role;

}
