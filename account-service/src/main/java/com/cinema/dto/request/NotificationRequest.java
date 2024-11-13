package com.cinema.dto.request;

import com.cinema.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationRequest {

    private String message;

    private String type; // global or only user

    private Integer user_id;

    private User user;
}
