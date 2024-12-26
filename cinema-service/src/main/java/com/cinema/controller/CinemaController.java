package com.cinema.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cinema.dto.ApiResponse;
import com.cinema.dto.request.CinemaDto;
import com.cinema.dto.response.CinemaResponse;
import com.cinema.service.CinemaService;

@RestController
@RequestMapping("/cinema")
public class CinemaController {
    @Autowired
    private CinemaService cinemaService;

    @PostMapping("/create-cinema")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ApiResponse<CinemaResponse> createCinema(@RequestBody CinemaDto request) {
        return ApiResponse.<CinemaResponse>builder()
                .code(1000)
                .data(cinemaService.createCinema(request))
                .build();
    }

    @PostMapping("/by-admin")
    public ApiResponse<CinemaResponse> findCinemaByAdmin(@RequestParam Integer adminId) {
        return ApiResponse.<CinemaResponse>builder()
                .code(1000)
                .data(cinemaService.findCinemaByAdmin(adminId))
                .build();
    }

    @PostMapping("/{id}")
    public ApiResponse<CinemaResponse> findCinema(@PathVariable Integer id) {
        return ApiResponse.<CinemaResponse>builder()
                .code(1000)
                .data(cinemaService.findCinemaById(id))
                .build();
    }

    @PostMapping
    public ApiResponse<List<CinemaResponse>> findAllCinema() {
        return ApiResponse.<List<CinemaResponse>>builder()
                .code(1000)
                .data(cinemaService.findAll())
                .build();
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PostMapping("/status")
    public ApiResponse<List<CinemaResponse>> findAllCinemaByStatus() {
        return ApiResponse.<List<CinemaResponse>>builder()
                .code(1000)
                .data(cinemaService.findAllByStatus())
                .build();
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PutMapping("/{id}")
    public ApiResponse<CinemaResponse> updateStatusCinema(@PathVariable Integer id) {
        return ApiResponse.<CinemaResponse>builder()
                .code(1000)
                .data(cinemaService.updateStatusCinema(id))
                .build();
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PutMapping("/edit")
    public ApiResponse<CinemaResponse> updateCinema(@RequestBody CinemaDto request) {
        return ApiResponse.<CinemaResponse>builder()
                .code(1000)
                .data(cinemaService.updateCinema(request))
                .build();
    }
}
