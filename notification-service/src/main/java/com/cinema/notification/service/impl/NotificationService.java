package com.cinema.notification.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cinema.notification.dto.request.NotificationRequest;
import com.cinema.notification.dto.response.NotificationReponse;
import com.cinema.notification.entity.Notification;
import com.cinema.notification.entity.User;
import com.cinema.notification.exception.AppException;
import com.cinema.notification.exception.ErrorCode;
import com.cinema.notification.mapper.NotificationMapper;
import com.cinema.notification.repository.NotificationRepository;
import com.cinema.notification.repository.UserRepository;
import com.cinema.notification.service.INotificationService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotificationService implements INotificationService {

    NotificationRepository notificationRepository;

    UserRepository userRepository;

    NotificationMapper notificationMapper;

    @Override
    public NotificationReponse createNotification(NotificationRequest request) {
        //        User user = userRepository.findById(request.getUser_id())
        //                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        Notification notification = notificationMapper.toNotification(request);

        notification = notificationRepository.save(notification);

        return (notificationMapper.toNotificationReponse(notification));
    }

    @Override
    public List<NotificationReponse> initNotification(String username) {
        User user =
                userRepository.findByUsername(username).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        List<Notification> notifications = notificationRepository.findByUserId(user.getId());

        return notificationMapper.toNotificationReponses(notifications);
    }
}
