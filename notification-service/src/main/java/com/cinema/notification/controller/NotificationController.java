package com.cinema.notification.controller;


import com.cinema.notification.dto.NotifyMessage;
import lombok.AccessLevel;
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
import org.springframework.web.bind.annotation.PathVariable;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Controller
//@RestController
public class NotificationController {
//    @MessageMapping("/chat.sendMessage")
//    @SendTo("/topic/public")
//    public void sendMessage() {
//        return chatMessage;
//    }
//
//    @MessageMapping("/chat.addUser")
//    @SendTo("/topic/public")
//    public ChatMessage addUser(@Payload ChatMessage chatMessage,
//                               SimpMessageHeaderAccessor headerAccessor) {
//        // Add username in web socket session
//        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
//        return chatMessage;
//    }

//    @Autowired
//    SimpMessagingTemplate messagingTemplate;
//

    SimpMessageSendingOperations messagingTemplate;

    public NotificationController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    // Gửi thông báo đến tất cả người dùng
    @MessageMapping("/chat.sendMessage")
    @SendTo("/notification-global")
    public NotifyMessage notifyAllUsers(@Payload NotifyMessage request) {
        log.info("Message received: {}", request.getContent());
        System.out.println("message: " + request.getContent());
        return request;
    }

    // Gửi thông báo đến một user cụ thể
    @MessageMapping("/hello")
//    @SendTo("/notification-user")
    public String notifyUser(SimpMessageHeaderAccessor sha, @PathVariable String username) {
        String message = "Hello from " + sha.getUser().getName();
        System.out.println("message: " + message);
        messagingTemplate.convertAndSendToUser(username, "/notification-user/messages", message);
        return "Thông báo đã được gửi đến user " + username + ": " + message;
    }

}
