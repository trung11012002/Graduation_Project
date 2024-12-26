package com.example.paymentservice.repository.httpclient;

import com.example.paymentservice.configuration.AuthenticationRequestInterceptor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "notification-service", url = "${app.services.notification}")
public interface NotificationClient {
    @PostMapping(value = "/notification/send-url-payment", produces = MediaType.APPLICATION_JSON_VALUE)
    String sendUrlPayment(@RequestParam String urlPayment, @RequestParam String username);

//    @PostMapping("/send-url-payment")
//    public String sendUrlPayment(@RequestParam String urlPayment, @RequestParam String username) {
//        messagingTemplate.convertAndSendToUser(username, "/notification-user/messages", urlPayment);
//        return "Send url payment to user " + urlPayment;
//    }
}
