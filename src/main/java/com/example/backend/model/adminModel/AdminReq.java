package com.example.backend.model.adminModel;

import com.example.backend.entity.User;
import lombok.Data;

@Data
public class AdminReq {
        private String firstname = "admin";
        private String lastname = "admin";
//        private String email = "admin@gmail.com";
        private String password = "admin";
        private User.Role role = User.Role.ADMIN;
        private Boolean active = true;
        private String email = "pakkawat@gmail.com";
        private String number = "123456";
}
