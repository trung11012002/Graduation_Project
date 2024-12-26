package com.cinema.notification.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.cinema.notification.dto.request.NotificationRequest;
import com.cinema.notification.dto.response.NotificationReponse;
import com.cinema.notification.entity.Notification;

@Mapper(componentModel = "spring")
public interface NotificationMapper {
    Notification toNotification(NotificationRequest dto);

    NotificationReponse toNotificationReponse(Notification entity);

    List<NotificationReponse> toNotificationReponses(List<Notification> entities);
}
