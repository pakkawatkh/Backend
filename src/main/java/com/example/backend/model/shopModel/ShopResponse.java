package com.example.backend.model.shopModel;

import lombok.Data;

@Data
public class ShopResponse {

    private String name;

    private Boolean active;

    private Long latitude;

    private Long longitude;

}
