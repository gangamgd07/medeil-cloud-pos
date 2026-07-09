package com.medeil.authservice.mapper;

import org.mapstruct.Mapper;

import com.medeil.authservice.dto.RegisterRequest;
import com.medeil.authservice.dto.UserResponse;
import com.medeil.authservice.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(RegisterRequest request);

    UserResponse toResponse(User user);
}
