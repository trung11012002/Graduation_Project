package com.cinema.notification.service;

import com.cinema.notification.dto.request.NotificationRequest;
import com.cinema.notification.dto.response.NotificationReponse;

import java.util.List;

public interface INotificationService {
    NotificationReponse createNotification(NotificationRequest request);

    List<NotificationReponse> initNotification(String username);
}
