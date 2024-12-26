package com.cinema.notification.dto.request;

import com.cinema.notification.entity.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRequest {

    private String message;

    private String type; // global or only user

    private Integer user_id;

    private User user;
}
