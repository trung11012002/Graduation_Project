package com.cinema.notification.controller;

import com.cinema.notification.dto.ApiResponse;
import com.cinema.notification.dto.NotifyMessage;
import com.cinema.notification.dto.request.Recipient;
import com.cinema.notification.dto.request.SendEmailRequest;
import com.cinema.notification.dto.response.EmailResponse;
import com.cinema.notification.service.EmailService;
import com.event.dto.NotificationEvent;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmailController {
    EmailService emailService;
    //    SimpMessagingTemplate messagingTemplate;
    SimpMessageSendingOperations messagingTemplate;

    @PostMapping("/email/send")
    ApiResponse<EmailResponse> sendEmail(@RequestBody SendEmailRequest request) {
        return ApiResponse.<EmailResponse>builder()
                .data(emailService.sendEmail(request))
                .build();
    }

    @KafkaListener(topics = "notification-delivery")
    public void listenNotificationDelivery(NotificationEvent message) {
        log.info("Message received: {}", message);
        emailService.sendEmail(SendEmailRequest.builder()
                .to(Recipient.builder()
                        .email(message.getRecipient())
                        .build())
                .subject(message.getSubject())
                .htmlContent(message.getBody())
                .build());
    }

    @GetMapping("/notify/globall")
    public String notifyAllUsers(@RequestParam String message) {
        System.out.println("message: " + message);
        messagingTemplate.convertAndSend("/notification-global", message);
        return "Thông báo toàn hệ thống đã được gửi: " + message;
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

    @GetMapping("/notify/globallll")
    public String notifyAllUsersss(@RequestParam String message) {

        System.out.println("message: " + message);
        NotifyMessage notifyMessage = NotifyMessage.builder()
                .content(message)
                .sender("System")
                .build();
        messagingTemplate.convertAndSendToUser("test", "/notification-user/messages", notifyMessage);
        return "Thông báo toàn hệ thống đã được gửi: " + message;
    }

}
