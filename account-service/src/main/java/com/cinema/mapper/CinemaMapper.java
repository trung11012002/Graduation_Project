package com.cinema.mapper;

import org.mapstruct.Mapper;

import com.cinema.dto.response.CinemaResponse;
import com.cinema.entity.Cinema;

@Mapper(componentModel = "spring")
public interface CinemaMapper {
    CinemaResponse toCinemaResponse(Cinema entity);
}
