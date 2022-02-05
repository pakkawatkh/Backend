package com.example.backend.model.adminModel;

import com.example.backend.entity.User;
import com.example.backend.model.orderModel.OrderRes;
import lombok.Data;

import java.util.List;

@Data
public class AUserResponse {

    private String id;

    private String firstname;

    private String lastname;

    private String phone;

    private User.Role role;

    private String facebook;

    private String line;

    private String address;

    private Boolean active;

    private String picture;

    private List<OrderRes> order;

}
