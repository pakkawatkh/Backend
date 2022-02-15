package com.example.backend.SetDefault;

import com.example.backend.entity.User;
import lombok.Data;

import java.util.Date;

@Data
public class DataToken {
    private String id = "ccd6bcd9-ed08-4fb5-b05a-29f27ced5375";
    private String firstname = "guest";
    private String lastname = "guest";
    //        private String email = "admin@gmail.com";
    private String password = "guest";
    private User.Role role = User.Role.USER;
    private Boolean active = false;
    private String phone = "0000000000";
    private Date last_password = new Date(2020, 1, 1, 1, 1);;
}
