package com.cinema.service;


import com.cinema.dto.request.RoomDto;
import com.cinema.dto.response.RoomResponse;

import javax.xml.transform.Result;
import java.util.List;


public interface RoomService {
    RoomResponse createRoom(RoomDto dto);

    RoomResponse findRoomById(Integer id);

    List<RoomResponse> findAllRoomInCinema(Integer cinemaId);
}
