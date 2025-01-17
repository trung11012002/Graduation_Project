package com.cinema.dto.response;

import com.cinema.entity.Cinema;
import com.cinema.entity.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    private Role role;

//    @JsonIgnore
    private CinemaResponse managedCinema;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
