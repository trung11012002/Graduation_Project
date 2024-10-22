package com.cinema.scheduleservice.repositories;

import com.cinema.scheduleservice.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
    Optional<List<Booking>> findAllByUserId(Long userId);

}
