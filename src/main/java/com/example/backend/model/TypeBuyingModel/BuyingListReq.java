package com.example.backend.model.TypeBuyingModel;

import lombok.Data;

@Data
public class BuyingListReq {

    private String name;

    private Integer price;

    private Integer buyingId;

    public boolean isValid() {
        return name != null && price != null && buyingId != null;
    }

    //validate is blank
    public boolean isBlank() {
        return name.isBlank();
    }
}
