package com.example.backend.model.orderModel;

import com.example.backend.model.typeModel.TypeResponse;
import lombok.Data;

@Data
public class OrderSearchResponse {
    private String name;
    private Integer id;
    private String picture;
    private TypeResponse type;
}
