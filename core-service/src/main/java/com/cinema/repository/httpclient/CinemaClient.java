package com.cinema.repository.httpclient;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;

import com.cinema.configuration.AuthenticationRequestInterceptor;
import com.cinema.dto.ApiResponse;
import com.cinema.dto.response.CinemaResponse;

@FeignClient(
        name = "cinema-service",
        url = "${app.services.cinema}",
        configuration = {AuthenticationRequestInterceptor.class})
public interface CinemaClient {
    @PostMapping(value = "/cinema", produces = MediaType.APPLICATION_JSON_VALUE)
    //    ApiResponse<List<CinemaResponse>> testFeignClient(@RequestBody ProfileCreationRequest request);
    ApiResponse<List<CinemaResponse>> testFeignClient();
}
