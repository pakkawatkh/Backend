package com.example.backend.process.shopModel;

import com.example.backend.model.userModel.UserInOrderResponse;
import lombok.Data;

@Data
public class ShopResponse {

    private Integer id;

    private String name;

    private Boolean active;

    private Long latitude;

    private Long longitude;

    private String number;

    private String detail;

    private UserInOrderResponse user;

    private Long count;

}
