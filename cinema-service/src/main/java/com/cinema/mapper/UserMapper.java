package com.cinema.mapper;

import com.cinema.dto.response.UserResponse;
import com.cinema.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponse toUserResponse(User entity);
    List<UserResponse> toUserResponses(List<User> entities);
}
