package com.cinema.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.cinema.dto.request.RoomDto;
import com.cinema.dto.response.RoomResponse;
import com.cinema.entity.Room;

@Mapper(componentModel = "spring")
public interface RoomMapper {
    RoomResponse toRoomResponse(Room entity);

    Room toRoom(RoomDto dto);

    List<RoomResponse> toRoomResponses(List<Room> entities);
}
