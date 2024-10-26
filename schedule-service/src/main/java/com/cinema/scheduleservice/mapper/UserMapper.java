package com.cinema.scheduleservice.mapper;


import com.cinema.scheduleservice.dto.response.UserResponse;
import com.cinema.scheduleservice.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponse toUserResponse(User entity);
}
