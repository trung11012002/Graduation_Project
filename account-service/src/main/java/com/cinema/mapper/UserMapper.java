package com.cinema.mapper;

import com.cinema.dto.request.RegisterRequest;
import com.cinema.dto.response.UserResponse;
import com.cinema.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(RegisterRequest dto);
    UserResponse toUserResponse(User entity);
}
