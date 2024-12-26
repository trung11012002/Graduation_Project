package com.cinema.scheduleservice.mapper;

import org.mapstruct.Mapper;

import com.cinema.scheduleservice.dto.response.CinemaResponse;
import com.cinema.scheduleservice.entity.Cinema;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface CinemaMapper {
    CinemaResponse toCinemaResponse(Cinema cinema);
}
