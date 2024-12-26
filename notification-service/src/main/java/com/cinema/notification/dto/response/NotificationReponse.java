package com.cinema.notification.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NotificationReponse {

    private Integer id;

    private String message;

    private String type; // global or only user

    private UserResponse user;
}
