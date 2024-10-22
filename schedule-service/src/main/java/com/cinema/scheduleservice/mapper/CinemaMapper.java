package com.cinema.scheduleservice.mapper;

import com.cinema.scheduleservice.dto.response.CinemaResponse;
import com.cinema.scheduleservice.entity.Cinema;
import org.mapstruct.Mapper;
import org.springframework.context.annotation.Configuration;

@Mapper(componentModel = "spring",uses = UserMapper.class)
public interface CinemaMapper {
    CinemaResponse toCinemaResponse(Cinema cinema);
}
