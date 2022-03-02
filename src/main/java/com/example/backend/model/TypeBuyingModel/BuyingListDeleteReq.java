package com.example.backend.model.TypeBuyingModel;

import lombok.Data;

@Data
public class BuyingListDeleteReq {

    private Integer buyingId;

    public boolean isValid() {
        return buyingId != null;
    }

}
