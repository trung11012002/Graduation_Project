package com.cinema.mapper;

import com.cinema.dto.request.RegisterRequest;
import com.cinema.dto.response.CinemaResponse;
import com.cinema.dto.response.UserResponse;
import com.cinema.entity.Cinema;
import com.cinema.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CinemaMapper {
    CinemaResponse toCinemaResponse(Cinema entity);
}
