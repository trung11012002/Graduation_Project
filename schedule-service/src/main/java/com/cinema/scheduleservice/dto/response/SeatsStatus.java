package com.cinema.scheduleservice.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeatsStatus {
    private ScheduleResponse scheduleResponse;
    private Integer row;
    private Integer column;
    private List<String> bookedSeats;
}
