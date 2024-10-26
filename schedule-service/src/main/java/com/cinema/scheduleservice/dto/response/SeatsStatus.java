package com.cinema.scheduleservice.dto.response;

import com.cinema.scheduleservice.entity.Schedule;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeatsStatus {
    private ScheduleResponse scheduleResponse;
    private Integer row;
    private Integer column;
    private List<String> bookedSeats;
}