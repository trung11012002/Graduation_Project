package com.cinema.scheduleservice.service;

import com.cinema.scheduleservice.dto.request.ScheduleDto;
import com.cinema.scheduleservice.dto.response.*;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ScheduleService {
    ScheduleCreateResponse createSchedule(ScheduleDto scheduleDto);
    ScheduleCreateResponse getScheduleById(Integer id);
    ScheduleCreateResponse updateSchedule(ScheduleDto scheduleDto);
    ScheduleCreateResponse deleteSchedule(Integer id);
    List<ScheduleResponse> findAllCurrentScheduleInCinema(Integer cinemaId);
    ListScheduleResponseByPage  findAllCurrentScheduleInCinemaByPage(Integer cinemaId, int page, int size);
    ListScheduleResponseByPage findAllHistoryScheduleInCinemaByPage(Integer cinemaId, int page, int size);
    List<ShowFilmByDayResponse> findAllScheduleInCinemaByDay(Integer cinemaId, String date);
    List<OrderedResponse> findAllOrdered(Integer scheduleId);
    RevenueStatisticResponse getRevenueStatistic(Integer cinemaId, String startDate, String endDate);
    SeatsStatus findAllBookedSeat(Integer scheduleId);

}
