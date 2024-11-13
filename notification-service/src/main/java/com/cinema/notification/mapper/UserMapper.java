package com.cinema.notification.mapper;

import com.cinema.notification.dto.response.UserResponse;
import com.cinema.notification.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponse toUserResponse(User entity);
}
