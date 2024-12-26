package com.cinema.scheduleservice.mapper;

import org.mapstruct.Mapper;

import com.cinema.scheduleservice.dto.response.UserResponse;
import com.cinema.scheduleservice.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponse toUserResponse(User entity);
}
