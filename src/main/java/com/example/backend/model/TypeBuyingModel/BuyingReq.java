package com.example.backend.model.TypeBuyingModel;

import lombok.Data;

@Data
public class BuyingReq {

    private String name;

    //validate is not null
    public boolean isValid() {
        return name != null;
    }

    //validate is blank
    public boolean isBlank() {
        return name.isBlank();
    }
}
