package com.cinema.notification.service;

import java.util.List;

import com.cinema.notification.dto.request.NotificationRequest;
import com.cinema.notification.dto.response.NotificationReponse;

public interface INotificationService {
    NotificationReponse createNotification(NotificationRequest request);

    List<NotificationReponse> initNotification(String username);
}
