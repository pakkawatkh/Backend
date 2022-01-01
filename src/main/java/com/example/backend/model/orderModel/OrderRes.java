package com.example.backend.model.orderModel;

import com.example.backend.entity.Orders;
import com.example.backend.entity.Type;
import com.example.backend.entity.User;
import com.example.backend.model.userModel.UserResponse;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Data
public class OrderRes {

    private Orders.Status status;

    private Date date;

    private Float weight;

    private String picture;

    private Type type;

    private Long latitude ;

    private Long longitude ;

    private Optional<UserResponse> user;


}
