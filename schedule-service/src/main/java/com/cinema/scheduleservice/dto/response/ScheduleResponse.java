package com.cinema.scheduleservice.dto.response;

import com.cinema.scheduleservice.entity.Film;
import com.cinema.scheduleservice.entity.Schedule;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

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
