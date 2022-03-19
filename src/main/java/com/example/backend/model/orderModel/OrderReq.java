package com.example.backend.model.orderModel;

import lombok.Data;

@Data
public class OrderReq {

    private String weight;

    private Integer typeId;

    private String picture;

    private String province;

    private String district;

    private String price;

    private String name;

    private String detail;

    private Long lat;

    private Long lng;

    private String address;

    //validate is not null
    public boolean isValid() {
        return typeId != null && picture != null && province != null && district != null && detail != null && name != null && price != null && weight != null && lat != null && lng != null && address != null;
    }

    //validate is blank
    public boolean isBlank() {
        return picture.isBlank() || province.isBlank() || district.isBlank() || detail.isBlank() || name.isBlank() || price.isBlank() || weight.isBlank()|| address.isBlank();
    }
}
