package com.cinema.scheduleservice.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cinema.scheduleservice.entity.Booking;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
    Optional<List<Booking>> findAllByUserId(Long userId);
}
