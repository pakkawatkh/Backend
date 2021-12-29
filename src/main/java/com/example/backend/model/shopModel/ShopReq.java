package com.example.backend.model.shopModel;

import lombok.Data;

@Data
public class ShopReq {

    private String name;

    private Integer id;

    private Boolean active;

    private Long latitude;

    private Long longitude;

}
