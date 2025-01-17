package com.example.reviewservice.httpclient;

import com.example.reviewservice.configuration.AuthenticationRequestInterceptor;
import com.example.reviewservice.dto.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
@Component
@FeignClient(name = "film-service", url = "${app.services.film}", configuration = {AuthenticationRequestInterceptor.class})
public interface FilmClient {
    @GetMapping("/films/update-score")
    ApiResponse updateScore(@RequestParam Integer filmId);
}
