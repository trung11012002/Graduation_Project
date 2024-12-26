package com.cinema.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cinema.dto.request.RoomDto;
import com.cinema.dto.response.RoomResponse;
import com.cinema.entity.Cinema;
import com.cinema.entity.Room;
import com.cinema.exception.AppException;
import com.cinema.exception.ErrorCode;
import com.cinema.mapper.RoomMapper;
import com.cinema.repository.CinemaRepository;
import com.cinema.repository.RoomRepository;
import com.cinema.service.RoomService;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private CinemaRepository cinemaRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private RoomMapper roomMapper;

    @Transactional
    @Override
    public RoomResponse createRoom(RoomDto request) {
        Room room = roomMapper.toRoom(request);

        Cinema cinema = cinemaRepository
                .findById(request.getCinemaId())
                .orElseThrow(() -> new AppException(ErrorCode.CINEMA_NOT_FOUND));

        room.setCinema(cinema);

        roomRepository.save(room);

        List<Room> rooms = cinema.getRooms();
        if (rooms == null) rooms = new ArrayList<>();
        rooms.add(room);
        cinema.setRooms(rooms);
        cinemaRepository.save(cinema);
        return roomMapper.toRoomResponse(room);
    }

    @Override
    public RoomResponse findRoomById(Integer id) {
        Room room = roomRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.ROOM_NOT_FOUND));

        return roomMapper.toRoomResponse(room);
    }

    @Override
    public List<RoomResponse> findAllRoomInCinema(Integer cinemaId) {
        Cinema cinema =
                cinemaRepository.findById(cinemaId).orElseThrow(() -> new AppException(ErrorCode.CINEMA_NOT_FOUND));

        List<Room> rooms = roomRepository.findByCinema(cinema);

        return roomMapper.toRoomResponses(rooms);
    }

    @Override
    public RoomResponse editRoom(Integer roomId, RoomDto dto) {
        Room room = roomRepository.findById(roomId).get();
        room.setName(dto.getName());
        room.setHorizontalSeats(dto.getHorizontalSeats());
        room.setVerticalSeats(dto.getVerticalSeats());
        Cinema cinema = cinemaRepository
                .findById(dto.getCinemaId())
                .orElseThrow(() -> new AppException(ErrorCode.CINEMA_NOT_FOUND));
        room.setCinema(cinema);
        roomRepository.save(room);
        return roomMapper.toRoomResponse(room);
    }
}
