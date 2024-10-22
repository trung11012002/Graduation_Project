package com.cinema.repository.httpclient;

import com.cinema.configuration.AuthenticationRequestInterceptor;
import com.cinema.dto.ApiResponse;
import com.cinema.dto.request.ProfileCreationRequest;
import com.cinema.dto.response.CinemaResponse;
import com.cinema.dto.response.UserProfileResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
@FeignClient(name = "cinema-service", url = "${app.services.cinema}",
        configuration = { AuthenticationRequestInterceptor.class })
public interface CinemaClient {
    @PostMapping(value = "/cinema", produces = MediaType.APPLICATION_JSON_VALUE)
//    ApiResponse<List<CinemaResponse>> testFeignClient(@RequestBody ProfileCreationRequest request);
    ApiResponse<List<CinemaResponse>> testFeignClient();

}
