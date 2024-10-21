package com.cinema.mapper;

import com.cinema.dto.request.RegisterRequest;
import com.cinema.dto.response.RoleResponse;
import com.cinema.dto.response.UserResponse;
import com.cinema.entity.Role;
import com.cinema.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleResponse toRoleResponse(Role entity);
}
