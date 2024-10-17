package com.cinema.controller;

import com.cinema.dto.ApiResponse;
import com.cinema.dto.request.CinemaDto;
import com.cinema.dto.response.CinemaResponse;
import com.cinema.service.CinemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/cinema")
public class CinemaController {
    @Autowired
    private CinemaService cinemaService;

    @PostMapping("/create-cinema")
    public ApiResponse<CinemaResponse> createCinema(@RequestBody CinemaDto request) {
        return ApiResponse.<CinemaResponse>builder()
                .code(1000)
                .result(cinemaService.createCinema(request))
                .build();
    }

    @PostMapping("/by-admin")
    public  ApiResponse<CinemaResponse> findCinemaByAdmin(@RequestParam Integer adminId) {
        return ApiResponse.<CinemaResponse>builder()
                .code(1000)
                .result(cinemaService.findCinemaByAdmin(adminId))
                .build();
    }

    @PostMapping("/{id}")
    public ApiResponse<CinemaResponse> findCinema(@PathVariable Integer id) {
        return ApiResponse.<CinemaResponse>builder()
                .code(1000)
                .result(cinemaService.findCinemaById(id))
                .build();
    }

    @PostMapping
    public ApiResponse<List<CinemaResponse>> findAllCinema() {
        return ApiResponse.<List<CinemaResponse>>builder()
                .code(1000)
                .result(cinemaService.findAll())
                .build();
    }
}
