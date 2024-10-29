package com.cinema.scheduleservice.mapper;

import com.cinema.scheduleservice.dto.response.ScheduleCreateResponse;
import com.cinema.scheduleservice.dto.response.ScheduleResponse;
import com.cinema.scheduleservice.entity.Schedule;
import com.cinema.scheduleservice.entity.Thumnail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

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
    @Mapping(source = "film.thumnails", target = "thumnails")
    @Mapping(source = "room.id", target = "roomId")
    @Mapping(source = "room.name", target = "roomName")
    @Mapping(target = "totalSeats", ignore = true)
    @Mapping(target = "availables", ignore = true)
    ScheduleResponse toScheduleResponse(Schedule schedule);
    List<ScheduleResponse> toScheduleResponseList(List<Schedule> schedules);
    default List<String> mapThumbnails(List<Thumnail> thumbnails) {
        if (thumbnails == null) {
            return null;
        }
        return thumbnails.stream().map(Thumnail::getUrl).collect(Collectors.toList());
    }
}