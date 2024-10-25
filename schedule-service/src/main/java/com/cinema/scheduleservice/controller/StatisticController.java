package com.cinema.scheduleservice.controller;

import com.cinema.scheduleservice.dto.response.ApiResponse;
import com.cinema.scheduleservice.dto.response.RevenueStatisticResponse;
import com.cinema.scheduleservice.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/statistic")
public class StatisticController {
    @Autowired
    private ScheduleService scheduleService;
    @PostMapping("/view-revenue-statistic")
    public ApiResponse<RevenueStatisticResponse> getRevenueStatistic(@RequestParam Integer cinemaId,
                                                                     @RequestParam String startDate,
                                                                     @RequestParam(required = false) String endDate) {
        return ApiResponse.<RevenueStatisticResponse>builder()
                .code(200)
                .msg("Success")
                .data(scheduleService.getRevenueStatistic(cinemaId, startDate, endDate))
                .build();
    }
}
