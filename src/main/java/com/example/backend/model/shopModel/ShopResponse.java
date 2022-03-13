package com.example.backend.model.shopModel;

import com.example.backend.model.userModel.UserResponse;
import lombok.Data;

@Data
public class ShopResponse {

    private Integer id;

    private String name;

    private Boolean active;

    private Long latitude;

    private Long longitude;

    private String number;

    private UserResponse user;

}
