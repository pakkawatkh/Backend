package com.example.backend.mapper;

import com.example.backend.entity.Shop;
import com.example.backend.entity.User;
import com.example.backend.model.shopModel.ShopResponse;
import com.example.backend.model.userModel.UserResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ShopMapper {
    ShopResponse toShopResponse(Shop shop);
}
