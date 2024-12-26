package com.example.reviewservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.reviewservice.dto.ApiResponse;
import com.example.reviewservice.dto.request.RatingRequest;
import com.example.reviewservice.service.RatingService;

@RestController
public class RatingController {

    @Autowired
    private RatingService service;

    @PostMapping("/create-rating")
    public ApiResponse createRating(@RequestBody RatingRequest dto) {
        return service.createRating(dto);
    }

    @GetMapping("/film-ratings/{filmId}")
    public ApiResponse getRatings(@PathVariable Integer filmId) {
        return service.getFilmRatings(filmId);
    }

    @GetMapping("/check-rating")
    public ApiResponse checkUserRating(
            @RequestParam(name = "filmId") Integer filmId, @RequestParam(name = "userId") Integer userId) {
        return ApiResponse.builder()
                .code(1000)
                .data(service.getRatingInFilmByUser(filmId, userId))
                .msg("Success")
                .build();
    }
}
