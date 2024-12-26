package com.cinema.scheduleservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.cinema.scheduleservice.dto.request.ScheduleDto;
import com.cinema.scheduleservice.dto.response.*;
import com.cinema.scheduleservice.service.ScheduleService;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    @Autowired
    private ScheduleService scheduleService;

    @PostMapping("/create")
    public ApiResponse<ScheduleCreateResponse> scheduleCreate(
            @RequestHeader(value = "Authorization") String token, @RequestBody ScheduleDto scheduleDto) {
        return ApiResponse.<ScheduleCreateResponse>builder()
                .code(1000)
                .msg("Success")
                .data(scheduleService.createSchedule(scheduleDto))
                .build();
    }

    @GetMapping("/get-by-id")
    public ApiResponse<ScheduleCreateResponse> getScheduleById(@RequestParam Integer scheduleId) {
        return ApiResponse.<ScheduleCreateResponse>builder()
                .code(200)
                .msg("Success")
                .data(scheduleService.getScheduleById(scheduleId))
                .build();
    }

    @PutMapping("/edit")
    public ApiResponse<ScheduleCreateResponse> editSchedule(@RequestBody ScheduleDto scheduleDto) {
        return ApiResponse.<ScheduleCreateResponse>builder()
                .code(200)
                .msg("Success")
                .data(scheduleService.updateSchedule(scheduleDto))
                .build();
    }

    @DeleteMapping("/delete")
    public ApiResponse deleteSchedule(@RequestParam Integer scheduleId) {
        scheduleService.deleteSchedule(scheduleId);
        return ApiResponse.builder()
                .code(1000)
                .msg("Delete success with scheduleId: " + scheduleId)
                .build();
    }

    @PostMapping("/schedule-by-cinema")
    public ApiResponse<List<ScheduleResponse>> getCurrentScheduleInCinema(@RequestParam Integer cinemaId) {
        return ApiResponse.<List<ScheduleResponse>>builder()
                .code(200)
                .msg("Success")
                .data(scheduleService.findAllCurrentScheduleInCinema(cinemaId))
                .build();
    }

    @GetMapping("/schedule-by-cinema_by_page")
    public ApiResponse<ListScheduleResponseByPage> getCurrentScheduleInCinemaByPage(
            @RequestParam Integer cinemaId,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "5") Integer perPage) {
        return ApiResponse.<ListScheduleResponseByPage>builder()
                .code(200)
                .msg("Success")
                .data(scheduleService.findAllCurrentScheduleInCinemaByPage(cinemaId, page, perPage))
                .build();
    }

    @GetMapping("/schedule-history-by-cinema")
    public ApiResponse<ListScheduleResponseByPage> getHistoryScheduleInCinemaByPage(
            @RequestParam Integer cinemaId,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "5") Integer perPage) {
        return ApiResponse.<ListScheduleResponseByPage>builder()
                .code(200)
                .msg("Success")
                .data(scheduleService.findAllHistoryScheduleInCinemaByPage(cinemaId, page, perPage))
                .build();
    }

    @PostMapping("/cinema-by-day")
    public ApiResponse<List<ShowFilmByDayResponse>> listScheduleInCinemaByDay(
            @RequestParam Integer cinemaId, @RequestParam String date) { // lay tat ca film theo schedule
        return ApiResponse.<List<ShowFilmByDayResponse>>builder()
                .code(200)
                .msg("Success")
                .data(scheduleService.findAllScheduleInCinemaByDay(cinemaId, date))
                .build();
    }

    @GetMapping("/view-all-orders")
    public ApiResponse<List<OrderedResponse>> viewAllOrdered(@RequestParam Integer scheduleId) {
        return ApiResponse.<List<OrderedResponse>>builder()
                .code(200)
                .msg("Success")
                .data(scheduleService.findAllOrdered(scheduleId))
                .build();
    }

    @GetMapping("/get-seats-status")
    public ApiResponse<SeatsStatus> getAllSeatsBookedInSchedule(@RequestParam Integer scheduleId) {
        return ApiResponse.<SeatsStatus>builder()
                .code(200)
                .msg("Success")
                .data(scheduleService.findAllBookedSeat(scheduleId))
                .build();
    }
}
