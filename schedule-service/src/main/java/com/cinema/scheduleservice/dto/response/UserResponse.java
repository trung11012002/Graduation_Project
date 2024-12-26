package com.cinema.scheduleservice.dto.response;

import java.time.LocalDateTime;

import com.cinema.scheduleservice.entity.Cinema;
import com.cinema.scheduleservice.entity.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;

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

    @JsonIgnore
    private Role role;

    @JsonIgnore
    private Cinema managedCinema;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
