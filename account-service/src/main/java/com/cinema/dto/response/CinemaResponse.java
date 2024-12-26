package com.cinema.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import com.cinema.entity.Room;
import com.cinema.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CinemaResponse {
    private Integer id;

    private String name;

    private String address;

    @JsonIgnore
    private User admin;

    @JsonIgnore
    private List<Room> rooms;

    private LocalDateTime createdAt;

    private LocalDateTime lastModifyAt;

    private String createdBy;

    private String lastModifyBy;
}
