package com.example.backend.model;

import com.example.backend.entity.User;
import lombok.Data;

@Data
public class MUserRequest {
    private String name;
    private String email;
    private String password;
    private String phone;
    private User.Role role;
}
