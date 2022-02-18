package com.example.backend.mapper;

import com.example.backend.entity.Shop;
import com.example.backend.model.shopModel.ShopResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ShopMapper {
    ShopResponse toShopResponse(Shop shop);

    List<ShopResponse> toListShopRes(List<Shop> shops);

}
