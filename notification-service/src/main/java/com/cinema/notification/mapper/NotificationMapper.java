package com.cinema.notification.mapper;

import com.cinema.notification.dto.request.NotificationRequest;
import com.cinema.notification.dto.response.NotificationReponse;
import com.cinema.notification.dto.response.UserResponse;
import com.cinema.notification.entity.Notification;
import com.cinema.notification.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NotificationMapper {
    Notification toNotification(NotificationRequest dto);

    NotificationReponse toNotificationReponse(Notification entity);

    List<NotificationReponse> toNotificationReponses(List<Notification> entities);
}
