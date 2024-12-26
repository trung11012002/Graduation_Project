package com.cinema.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cinema.dto.request.CinemaDto;
import com.cinema.dto.response.CinemaResponse;
import com.cinema.entity.Cinema;
import com.cinema.entity.User;
import com.cinema.enums.RoleEnums;
import com.cinema.exception.AppException;
import com.cinema.exception.ErrorCode;
import com.cinema.mapper.CinemaMapper;
import com.cinema.repository.CinemaRepository;
import com.cinema.repository.UserRepository;
import com.cinema.service.CinemaService;

@Service
public class CinemaServiceImpl implements CinemaService {

    @Autowired
    private CinemaRepository cinemaRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CinemaMapper cinemaMapper;

    @Override
    //    @Transactional
    public CinemaResponse createCinema(CinemaDto dto) {
        Cinema cinema = cinemaMapper.toCinema(dto);
        User user = userRepository
                .findById(dto.getAdminId())
                .orElseThrow(() -> new AppException(ErrorCode.ADMIN_NOT_FOUND));

        if (!user.getRole().getId().equals(RoleEnums.ADMIN.getCode())) throw new AppException(ErrorCode.USER_NOT_ADMIN);

        cinema.setAdmin(user);
        cinema.setStatus(true);
        cinemaRepository.save(cinema);

        return cinemaMapper.toCinemaResponse(cinema);
    }

    @Override
    public CinemaResponse findCinemaById(Integer id) {
        Cinema cinema = cinemaRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.CINEMA_NOT_FOUND));

        return cinemaMapper.toCinemaResponse(cinema);
    }

    @Override
    public CinemaResponse findCinemaByAdmin(Integer id) {
        Cinema cinema = cinemaRepository
                .findByAdminId(id)
                .orElseThrow(() -> new AppException(ErrorCode.CURRENT_USER_NOT_MANAGE_CINEMA));

        return cinemaMapper.toCinemaResponse(cinema);
    }

    @Override
    public List<CinemaResponse> findAllByStatus() {
        var cinemas = cinemaRepository.findAll();
        List<Cinema> result = new ArrayList<>();
        for (Cinema cinema : cinemas) {
            if (cinema.getStatus()) {
                result.add(cinema);
            }
        }
        return cinemaMapper.toCinemaResponses(result);
    }

    @Override
    public List<CinemaResponse> findAll() {
        var cinemas = cinemaRepository.findAll();
        return cinemaMapper.toCinemaResponses(cinemas);
    }

    @Override
    public CinemaResponse updateStatusCinema(Integer id) {
        Cinema cinema = cinemaRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.CINEMA_NOT_FOUND));

        if (cinema.getStatus()) {
            cinema.setStatus(false);
        } else {
            cinema.setStatus(true);
        }
        cinemaRepository.save(cinema);
        return null;
    }

    @Override
    public CinemaResponse updateCinema(CinemaDto request) {
        Cinema cinema = cinemaRepository
                .findById(request.getId())
                .orElseThrow(() -> new AppException(ErrorCode.CINEMA_NOT_FOUND));

        User user = userRepository
                .findById(request.getAdminId())
                .orElseThrow(() -> new AppException(ErrorCode.ADMIN_NOT_FOUND));

        if (user.getManagedCinema() != null) {
            throw new AppException(ErrorCode.USER_MANAGE_CINEMA);
        }
        cinema.setName(request.getName());
        cinema.setAddress(request.getAddress());
        cinema.setAdmin(user);

        cinemaRepository.save(cinema);
        return null;
    }
}
