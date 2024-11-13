package com.cinema.notification.service.impl;

import com.cinema.notification.dto.request.EmailRequest;
import com.cinema.notification.dto.request.SendEmailRequest;
import com.cinema.notification.dto.request.Sender;
import com.cinema.notification.dto.response.EmailResponse;
import com.cinema.notification.exception.AppException;
import com.cinema.notification.exception.ErrorCode;
import com.cinema.notification.repository.httpclient.EmailClient;
import com.cinema.notification.utils.GenerateHtmlEmail;
import feign.FeignException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmailService {
    EmailClient emailClient;

    @Value("${notification.email.brevo-apikey}")
    @NonFinal
    String apiKey;
//    @NonFinal
//    GenerateHtmlEmail generateHtmlEmail;

    public EmailResponse sendEmail(SendEmailRequest request) {
        String body = GenerateHtmlEmail.generateHtmlEmailWelcome(request.getTo().getEmail());
        EmailRequest emailRequest = EmailRequest.builder()
                .sender(Sender.builder()
                        .name("Cinema Welcome")
                        .email("vuquangtrung0987654321@gmail.com")
                        .build())
                .to(List.of(request.getTo()))
                .subject(request.getSubject())
                .htmlContent(request.getHtmlContent())
                .build();
        try {
            return emailClient.sendEmail(apiKey, emailRequest);
        } catch (FeignException e){
            throw new AppException(ErrorCode.CANNOT_SEND_EMAIL);
        }
    }
}
