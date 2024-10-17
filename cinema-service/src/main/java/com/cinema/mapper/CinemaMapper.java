package com.cinema.mapper;

import com.cinema.dto.response.CinemaResponse;
import com.cinema.entity.Cinema;
import com.cinema.dto.request.CinemaDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CinemaMapper {
    Cinema toCinema(CinemaDto dto);
    CinemaDto toCinemaDto(Cinema cinema);
    CinemaResponse toCinemaResponse(Cinema cinema);
    List<Cinema> toCinemas(List<CinemaDto> dtos);
    List<CinemaResponse> toCinemaResponses(List<Cinema> cinemas);
}
