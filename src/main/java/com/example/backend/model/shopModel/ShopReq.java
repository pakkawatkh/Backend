package com.example.backend.model.shopModel;

import lombok.Data;

@Data
public class ShopReq {

    private String name;

    private Integer id;

    private Boolean active;

    private Long latitude;

    private Long longitude;

    //validate is not null
    public boolean isValid() {
        return name != null && latitude != null&& longitude != null;
    }

    //validate is blank
    public boolean isBlank() {
        return name.isBlank();
    }

    //validate is not null
    public boolean isValid2() {
        return active != null && id != null;
    }
}
