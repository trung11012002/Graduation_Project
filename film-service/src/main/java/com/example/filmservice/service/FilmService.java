package com.example.filmservice.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.example.filmservice.dto.request.EditFilmDto;
import com.example.filmservice.dto.request.FilmDto;
import com.example.filmservice.dto.response.ApiResponse;
import com.example.filmservice.dto.response.FilmResponse;

public interface FilmService {
    ApiResponse<List<FilmResponse>> getAllFilms();

    ApiResponse<List<FilmResponse>> getFilms(int page, int size);

    ApiResponse createFilm(FilmDto filmDto);

    Page<FilmResponse> getAllFilmsOrderByCreateDate(int page, int size);

    ApiResponse editFilm(EditFilmDto editFilmDto);

    ApiResponse deleteFilmById(Integer id);
    ApiResponse getFilmById(Integer id);
    ApiResponse searchFilm(String keyword);

}
