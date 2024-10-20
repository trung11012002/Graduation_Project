package com.example.reviewservice.controller;

import com.example.reviewservice.dto.ApiResponse;
import com.example.reviewservice.dto.request.RatingRequest;
import com.example.reviewservice.dto.response.RatingResponse;
import com.example.reviewservice.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
public class RatingController {

    @Autowired
    private RatingService service;

    @PostMapping("/create-rating")
    public ApiResponse createRating(RatingRequest dto){
        return service.createRating(dto);
    }

    @GetMapping("/film-ratings")
    public ApiResponse getRatings(Integer filmId){
        return service.getFilmRatings(filmId);
    }

    @GetMapping("/check-rating")
    public ApiResponse checkUserRating(Integer filmId, Integer userId){
        return ApiResponse.builder().
                code(1000).
                data(service.getRatingInFilmByUser(filmId, userId)).
                msg("Success").
                build();
    }
}
