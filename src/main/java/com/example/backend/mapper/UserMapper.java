package com.example.backend.mapper;

import com.example.backend.entity.User;
import com.example.backend.model.MUserResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    MUserResponse toMUserResponse(User user);
}
