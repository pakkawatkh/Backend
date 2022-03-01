package com.example.backend.model.orderModel;

import lombok.Data;

@Data
public class OrderReq {

    private Float weight;

    private Integer typeId;

    private String picture;

    private String province;

    private String district;

    private Integer price;

    private String name;

    private String detail;

//    private List<>

    //validate is not null
    public boolean isValid() {
        return typeId != null && weight != null && picture != null && province != null && district != null && detail != null && name != null;
    }

    //validate is blank
    public boolean isBlank() {
        return picture.isBlank() || province.isBlank() || district.isBlank() || detail.isBlank() || name.isBlank();
    }
}
