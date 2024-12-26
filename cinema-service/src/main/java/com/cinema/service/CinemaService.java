package com.cinema.service;

import java.util.List;

import com.cinema.dto.request.CinemaDto;
import com.cinema.dto.response.CinemaResponse;

public interface CinemaService {
    CinemaResponse createCinema(CinemaDto request);

    CinemaResponse findCinemaById(Integer id);

    CinemaResponse findCinemaByAdmin(Integer id);

    List<CinemaResponse> findAll();

    List<CinemaResponse> findAllByStatus();

    CinemaResponse updateStatusCinema(Integer id);

    CinemaResponse updateCinema(CinemaDto request);
}
