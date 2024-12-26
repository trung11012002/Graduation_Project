package com.cinema.notification.mapper;

import org.mapstruct.Mapper;

import com.cinema.notification.dto.response.UserResponse;
import com.cinema.notification.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponse toUserResponse(User entity);
}
