package com.cinema.notification.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private Integer id;

    private String username;

    private String password;

    private String fullname;

    private String dateOfBirth;

    private String address;

    private String email;

    private String phone;

    private boolean isBlocked = false;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
