package com.cinema.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.cinema.dto.request.RegisterRequest;
import com.cinema.dto.response.UserResponse;
import com.cinema.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(RegisterRequest dto);

    UserResponse toUserResponse(User entity);

    List<UserResponse> toUserResponses(List<User> entities);
}
