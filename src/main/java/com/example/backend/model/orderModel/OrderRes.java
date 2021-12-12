package com.example.backend.model.orderModel;

import com.example.backend.model.productModel.ProductReq;
import lombok.Data;

import java.util.List;

@Data
public class OrderRes {

    private Integer status;

    private List<ProductReq> product;
}
