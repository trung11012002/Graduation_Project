package com.cinema.service;

import com.cinema.dto.request.CinemaDto;
import com.cinema.dto.response.CinemaResponse;

import java.util.List;

public interface CinemaService {
    CinemaResponse createCinema(CinemaDto request);

    CinemaResponse findCinemaById(Integer id);

    CinemaResponse findCinemaByAdmin(Integer id);

    List<CinemaResponse> findAll();

    CinemaResponse updateStatusCinema(Integer id);

    CinemaResponse updateCinema(CinemaDto request);
}
