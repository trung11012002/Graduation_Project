package com.cinema.scheduleservice.repositories;

import com.cinema.scheduleservice.entity.Booking;
import com.cinema.scheduleservice.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    Optional<List<Ticket>> findByScheduleId(Integer id);

    Integer countByScheduleId(Integer id);
    // tham số đầu vào đầu tiep theo là id của schedule
    @Query(value = "SELECT t.booking FROM Ticket t WHERE t.schedule.id = ?1 GROUP BY t.booking", nativeQuery = true)
    Optional<List<Booking>> findBookingsByScheduleId(Integer scheduleId);

}