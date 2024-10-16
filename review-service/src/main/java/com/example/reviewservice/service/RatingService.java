package com.example.reviewservice.service;

import com.example.reviewservice.dto.ApiResponse;
import com.example.reviewservice.dto.request.RatingRequest;
import com.example.reviewservice.dto.response.RatingResponse;

public interface RatingService {
    ApiResponse createRating(RatingRequest dto);

    ApiResponse getFilmRatings(Integer filmId);

    RatingResponse getRatingInFilmByUser(Integer filmId, Integer userId);
}
