package com.example.backend.model.TypeBuyingModel;

import lombok.Data;

import java.util.List;

@Data
public class BuyingResponse {
    private Integer id;
    private String name;
    private List<BuyingListResponse> buyingLists;
}
