package com.cinema.dto.response;

import com.cinema.entity.Cinema;
import com.cinema.entity.Schedule;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomResponse {


    private Integer id;

    private String name;

    private Integer horizontalSeats;

    private Integer verticalSeats;

    @JsonIgnore
    private Cinema cinema;

    @JsonIgnore
    private List<Schedule> schedules;

    private LocalDateTime createdAt;

    private LocalDateTime lastModifyAt;

    private String createdBy;

    private String lastModifyBy;
}
