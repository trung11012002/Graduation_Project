package com.cinema.scheduleservice.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cinema.scheduleservice.entity.Booking;
import com.cinema.scheduleservice.entity.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    Optional<List<Ticket>> findByScheduleId(Integer id);

//    Integer countByScheduleId(Integer id);
    @Query(
            value = """
        SELECT count(*)
        FROM ticket t
        JOIN booking b ON t.booking_id = b.id
        JOIN schedule s ON t.schedule_id = s.id
        WHERE b.status = 'APPROVED' AND s.id = :scheduleId
    """,
            nativeQuery = true
    )
    Integer countByScheduleId(@Param("scheduleId") Integer scheduleId);

    // tham số đầu vào đầu tiep theo là id của schedule
    @Query(value = "SELECT t.booking FROM Ticket t WHERE t.schedule.id = ?1 GROUP BY t.booking", nativeQuery = true)
    Optional<List<Booking>> findBookingsByScheduleId(Integer scheduleId);

    @Query(
            value = """
        SELECT t.*
        FROM ticket t
        JOIN booking b ON t.booking_id = b.id
        JOIN schedule s ON t.schedule_id = s.id
        WHERE b.status = 'APPROVED' AND s.id = :scheduleId
    """,
            nativeQuery = true
    )
    Optional<List<Ticket>> findApprovedTickets(@Param("scheduleId") Integer scheduleId);
}
