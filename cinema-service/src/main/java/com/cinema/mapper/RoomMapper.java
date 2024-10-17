package com.cinema.mapper;

import com.cinema.dto.request.CinemaDto;
import com.cinema.dto.request.RoomDto;
import com.cinema.dto.response.CinemaResponse;
import com.cinema.dto.response.RoomResponse;
import com.cinema.entity.Cinema;
import com.cinema.entity.Room;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoomMapper {
    RoomResponse toRoomResponse(Room entity);
    Room toRoom(RoomDto dto);
    List<RoomResponse> toRoomResponses(List<Room> entities);
}
