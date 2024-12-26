package com.cinema.service;

import java.util.List;

import com.cinema.dto.request.RoomDto;
import com.cinema.dto.response.RoomResponse;

public interface RoomService {
    RoomResponse createRoom(RoomDto dto);

    RoomResponse findRoomById(Integer id);

    List<RoomResponse> findAllRoomInCinema(Integer cinemaId);

    RoomResponse editRoom(Integer roomId, RoomDto dto);
}
