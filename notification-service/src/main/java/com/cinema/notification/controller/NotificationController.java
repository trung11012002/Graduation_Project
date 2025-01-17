package com.cinema.notification.controller;


import com.cinema.notification.dto.ApiResponse;
import com.cinema.notification.dto.NotifyMessage;
import com.cinema.notification.dto.request.NotificationRequest;
import com.cinema.notification.dto.response.NotificationReponse;
import com.cinema.notification.entity.Notification;
import com.cinema.notification.service.INotificationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequestMapping("/notification")
public class NotificationController {

    SimpMessageSendingOperations messagingTemplate;

    INotificationService notificationService;

//    public NotificationController(SimpMessagingTemplate messagingTemplate) {
//        this.messagingTemplate = messagingTemplate;
//    }

    // Gửi thông báo đến tất cả người dùng
    @MessageMapping("/notification-global")
    @SendTo("/notification-global")
    public NotifyMessage notifyAllUsers(@Payload NotifyMessage request) {
        log.info("Message received: {}", request.getContent());
        return request;
    }

    // Gửi thông báo đến một user cụ thể
    @MessageMapping("/notification-user/{username}")
//    @SendTo("/notification-user")
    public String notifyUser(SimpMessageHeaderAccessor sha, @PathVariable String username) {
        String message = "Hello from " + sha.getUser().getName();
        System.out.println("message: " + message);
        messagingTemplate.convertAndSendToUser(username, "/notification-user/messages", message);
        return "Thông báo đã được gửi đến user " + username + ": " + message;
    }

    @GetMapping("/notify/globalll")
    public String notifyAllUserss(@RequestParam String message) {
        System.out.println("message: " + message);
        NotifyMessage notifyMessage = NotifyMessage.builder()
                .content(message)
                .sender("System")
                .build();
        messagingTemplate.convertAndSend("/notification-global", notifyMessage);
        return "Thông báo toàn hệ thống đã được gửi: " + message;
    }

    @GetMapping("/user/{username}")
    public String notifyAllUsersss(@PathVariable String username, @RequestParam String message) {
        log.info("Message send to : {}", username + " with message: " + message);
        NotifyMessage notifyMessage = NotifyMessage.builder()
                .content(message)
                .sender("System")
                .build();
        messagingTemplate.convertAndSendToUser(username, "/notification-user/messages", notifyMessage);
        return "Send " + message + " to " + username;
    }

    @PostMapping("/create")
    public ApiResponse<NotificationReponse> createNotification(@RequestBody NotificationRequest request) {
        return ApiResponse.<NotificationReponse>builder()
                .code(1000)
                .data(notificationService.createNotification(request))
                .build();
    }

    @GetMapping("/init")
    public ApiResponse<List<NotificationReponse>> initNotification(@RequestParam String username) {
        return ApiResponse.<List<NotificationReponse>>builder()
                .code(1000)
                .data(notificationService.initNotification(username))
                .build();
    }
}
