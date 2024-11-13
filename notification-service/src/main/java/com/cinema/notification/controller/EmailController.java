package com.cinema.notification.controller;

import com.cinema.notification.dto.ApiResponse;
import com.cinema.notification.dto.request.Recipient;
import com.cinema.notification.dto.request.SendEmailRequest;
import com.cinema.notification.dto.response.EmailResponse;
import com.cinema.notification.service.impl.EmailService;
import com.cinema.notification.utils.GenerateHtmlEmail;
import com.event.dto.NotificationEvent;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/email")
public class EmailController {
    EmailService emailService;
    //    SimpMessagingTemplate messagingTemplate;
    SimpMessageSendingOperations messagingTemplate;
//    @NonFinal
//    GenerateHtmlEmail generateHtmlEmail;
    @PostMapping("/send")
    ApiResponse<EmailResponse> sendEmail(@RequestBody SendEmailRequest request) {
        return ApiResponse.<EmailResponse>builder()
                .data(emailService.sendEmail(request))
                .build();
    }

    @KafkaListener(topics = "notification-delivery")
    public void listenNotificationDelivery(NotificationEvent message) {
        log.info("Message received: {}", message);
        String htmlContent = GenerateHtmlEmail.generateHtmlEmailWelcome(message.getBody());
        emailService.sendEmail(SendEmailRequest.builder()
                .to(Recipient.builder()
                        .email(message.getRecipient())
                        .build())
                .subject(message.getSubject())
                .htmlContent(htmlContent)
                .build());
    }
}
