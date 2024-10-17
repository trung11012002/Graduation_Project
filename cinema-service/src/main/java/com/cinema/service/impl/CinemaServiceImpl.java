package com.cinema.service.impl;

import com.cinema.entity.Cinema;
import com.cinema.service.CinemaService;
import com.cinema.dto.request.CinemaDto;
import com.cinema.dto.response.CinemaResponse;
import com.cinema.entity.User;
import com.cinema.enums.RoleEnums;
import com.cinema.exception.AppException;
import com.cinema.exception.ErrorCode;
import com.cinema.mapper.CinemaMapper;
import com.cinema.repository.CinemaRepository;
import com.cinema.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class CinemaServiceImpl implements CinemaService {

    @Autowired
    private CinemaRepository cinemaRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CinemaMapper cinemaMapper;

    @Override
    @Transactional
    public CinemaResponse createCinema(CinemaDto dto) {
        Cinema cinema = cinemaMapper.toCinema(dto);

        User user = userRepository.findById(dto.getAdminId())
                .orElseThrow(() -> new AppException(ErrorCode.ADMIN_NOT_FOUND));

        if (!user.getRole().getId().equals(RoleEnums.ADMIN.getCode()))
            throw new AppException(ErrorCode.USER_NOT_ADMIN);

        cinema.setAdmin(user);

        cinemaRepository.save(cinema);
        user.setManagedCinema(cinema);

        userRepository.save(user);
        return cinemaMapper.toCinemaResponse(cinema);
    }

    @Override
    public CinemaResponse findCinemaById(Integer id) {
        Cinema cinema = cinemaRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CINEMA_NOT_FOUND));

        return cinemaMapper.toCinemaResponse(cinema);
    }

    @Override
    public CinemaResponse findCinemaByAdmin(Integer id) {
        Cinema cinema = cinemaRepository.findByAdminId(id)
                .orElseThrow(() -> new AppException(ErrorCode.CURRENT_USER_NOT_MANAGE_CINEMA));

        return cinemaMapper.toCinemaResponse(cinema);
    }

    @Override
    public List<CinemaResponse> findAll() {
        return cinemaMapper.toCinemaResponses(cinemaRepository.findAll());
    }
}
