package com.cinema.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.cinema.dto.request.CinemaDto;
import com.cinema.dto.response.CinemaResponse;
import com.cinema.entity.Cinema;

@Mapper(componentModel = "spring")
public interface CinemaMapper {
    Cinema toCinema(CinemaDto dto);

    CinemaDto toCinemaDto(Cinema cinema);

    CinemaResponse toCinemaResponse(Cinema cinema);

    List<Cinema> toCinemas(List<CinemaDto> dtos);

    List<CinemaResponse> toCinemaResponses(List<Cinema> cinemas);
}
