package com.example.backend.mapper;

import com.example.backend.entity.User;
import com.example.backend.model.userModel.UserResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponse toUserResponse(User user);
}
