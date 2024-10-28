package com.cinema.controller;

import com.cinema.dto.ApiResponse;
import com.cinema.dto.request.RoomDto;
import com.cinema.dto.response.RoomResponse;
import com.cinema.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/room")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @PostMapping("/create-room")
    public ApiResponse<RoomResponse> createRoom(
            @RequestHeader(value = "Auth") String token,
            @RequestBody RoomDto roomDto) {
        return ApiResponse.<RoomResponse>builder()
                .code(1000)
                .data(roomService.createRoom(roomDto))
                .build();
    }

    @PostMapping("/{id}")
    public ApiResponse<RoomResponse> findRoomById(@PathVariable Integer id) {
        return ApiResponse.<RoomResponse>builder()
                .code(1000)
                .data(roomService.findRoomById(id))
                .build();
    }

    @PostMapping("/room-in-cinema")
    public ApiResponse<List<RoomResponse>> findRoomByCinema(@RequestParam Integer cinemaId) {
        return ApiResponse.<List<RoomResponse>>builder()
                .code(1000)
                .data(roomService.findAllRoomInCinema(cinemaId))
                .build();
    }
}
