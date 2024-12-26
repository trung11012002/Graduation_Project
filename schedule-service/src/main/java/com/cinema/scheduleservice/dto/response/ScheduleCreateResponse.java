package com.cinema.scheduleservice.dto.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class ScheduleCreateResponse {

    private Integer id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer filmId;
    private String filmName;
    private Integer roomId;
    private String roomName;
    private LocalDateTime createdAt;
    private LocalDateTime lastModifyAt;
    private String createdBy;
    private String lastModifyBy;
}
