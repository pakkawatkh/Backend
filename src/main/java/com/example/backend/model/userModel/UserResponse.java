package com.example.backend.model.userModel;

import com.example.backend.entity.User;
import com.example.backend.model.orderModel.OrderRes;
import com.example.backend.model.shopModel.ShopResponse;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class UserResponse {

    private String firstname;

    private String lastname;

    private String email;

    private String phone;

    private User.Role role;

    private String facebook;

    private String line;

    private String address;

    private Boolean active;

    private String picture;

    private List<OrderRes> order;

    private ShopResponse shop;

    private Date date;

}
