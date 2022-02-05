package com.example.backend.mapper;

import com.example.backend.entity.User;
import com.example.backend.model.adminModel.AUserResponse;
import com.example.backend.model.userModel.UserResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponse toUserResponse(User user);

   List<AUserResponse> toListAUserResponse(List<User> userList);

   AUserResponse toAUserResponse(User user);
}
