package com.cinema.dto.response;

import com.cinema.entity.Room;
import com.cinema.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CinemaResponse {
    private Integer id;

    private String name;

    private String address;

//    @JsonIgnore
    private UserResponse admin;

    @JsonIgnore
    private List<Room> rooms;

    private LocalDateTime createdAt;

    private LocalDateTime lastModifyAt;

    private String createdBy;

    private String lastModifyBy;

    private Boolean status;
}
