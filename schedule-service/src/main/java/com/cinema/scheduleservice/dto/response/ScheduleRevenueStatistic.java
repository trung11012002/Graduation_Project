package com.cinema.scheduleservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleRevenueStatistic {
    private LocalDateTime showDate;
    private FilmResponse filmResponse;
    private Integer roomId;
    private Integer ticketsSold;
    private Long revenue;
}