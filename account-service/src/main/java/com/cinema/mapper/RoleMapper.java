package com.cinema.mapper;

import org.mapstruct.Mapper;

import com.cinema.dto.response.RoleResponse;
import com.cinema.entity.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleResponse toRoleResponse(Role entity);
}
