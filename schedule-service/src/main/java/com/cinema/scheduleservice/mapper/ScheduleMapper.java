package com.cinema.scheduleservice.mapper;

import com.cinema.scheduleservice.dto.response.ScheduleCreateResponse;
import com.cinema.scheduleservice.dto.response.ScheduleResponse;
import com.cinema.scheduleservice.entity.Schedule;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ScheduleMapper {


    @Mapping(source = "film.id", target = "filmId")
    @Mapping(source = "film.name", target = "filmName")
    @Mapping(source = "room.id", target = "roomId")
    @Mapping(source = "room.name", target = "roomName")
    ScheduleCreateResponse toScheduleCreateResponse(Schedule schedule);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "film.id", target = "filmId")
    @Mapping(source = "film.name", target = "filmName")
    @Mapping(source = "room.id", target = "roomId")
    @Mapping(source = "room.name", target = "roomName")
    @Mapping(target = "totalSeats", ignore = true)
    @Mapping(target = "availables", ignore = true)
    ScheduleResponse toScheduleResponse(Schedule schedule);
    List<ScheduleResponse> toScheduleResponseList(List<Schedule> schedules);

}