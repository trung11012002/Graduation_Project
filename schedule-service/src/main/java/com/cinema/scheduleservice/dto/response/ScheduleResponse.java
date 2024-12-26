package com.cinema.scheduleservice.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleResponse {
    private Integer id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer filmId;
    private String filmName;
    private Integer roomId;
    private String roomName;
    private Integer totalSeats;
    private Integer availables;
    private List<String> thumnails;
}
