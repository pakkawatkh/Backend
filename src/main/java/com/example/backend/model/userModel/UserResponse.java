package com.example.backend.model.userModel;

import com.example.backend.entity.User;
import com.example.backend.process.shopModel.ShopResponse;
import lombok.Data;

import java.util.Date;

@Data
public class UserResponse {

    private String number;

    private String firstname;

    private String lastname;

    private String email;

    private String phone;

    private User.Role role;

    private String facebook;

    private String line;

    private String address;

    private Long lat;

    private Long lng;

    private Boolean active;

    private String picture;

    private String pictureUrl;

    private ShopResponse shop;

    private Date date;

}
