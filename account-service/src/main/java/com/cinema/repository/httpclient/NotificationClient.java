package com.cinema.repository.httpclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.cinema.config.AuthenticationRequestInterceptor;
import com.cinema.dto.request.NotificationRequest;
import com.cinema.dto.response.ApiResponse;
import com.cinema.dto.response.NotificationReponse;

@FeignClient(
        name = "notification-service",
        url = "${app.services.notification}",
        configuration = {AuthenticationRequestInterceptor.class})
public interface NotificationClient {
    @PostMapping(value = "/notification/create", produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse<NotificationReponse> createNotification(@RequestBody NotificationRequest request);
}
