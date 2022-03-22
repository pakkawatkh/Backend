package com.example.backend.model.orderModel;

import com.example.backend.entity.Orders;
import com.example.backend.entity.Type;
import com.example.backend.entity.User;
import com.example.backend.model.userModel.UserInOrderResponse;
import com.example.backend.model.userModel.UserResponse;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Data
public class OrderRes {

    private Integer id;

    private Orders.Status status;

    private Date date;

    private String weight;

    private String picture;

    private String pictureUrl;

    private Type type;

    private String province ;

    private String district ;

    private String name;

    private String price;

    private String statusTh;

    private String detail;

    private UserInOrderResponse user;

    private String lat;

    private String lng;

    private String address;
}
