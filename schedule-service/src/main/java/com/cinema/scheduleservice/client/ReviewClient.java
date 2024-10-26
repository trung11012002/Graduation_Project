package com.cinema.scheduleservice.client;

import com.cinema.scheduleservice.configuration.AuthenticationRequestInterceptor;
import com.cinema.scheduleservice.dto.response.ApiResponse;
import com.cinema.scheduleservice.dto.response.RatingDtoRepsonse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "review-service", url = "${app.services.review}", configuration = {AuthenticationRequestInterceptor.class})
public interface ReviewClient {
    @GetMapping(value = "/check-rating", produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse<RatingDtoRepsonse> checkUserRatingInAFilm(
            @RequestParam("userId") Integer userId,
            @RequestParam("filmId") Integer filmId);
}