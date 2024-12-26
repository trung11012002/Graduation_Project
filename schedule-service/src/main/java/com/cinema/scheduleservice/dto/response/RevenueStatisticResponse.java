package com.cinema.scheduleservice.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RevenueStatisticResponse {
    private List<ScheduleRevenueStatistic> scheduleRevenueStatistic;
    private Long totalRevenue;
}
