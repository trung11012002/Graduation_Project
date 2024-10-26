package com.cinema.scheduleservice.repositories;

import com.cinema.scheduleservice.entity.Cinema;
import com.cinema.scheduleservice.entity.Room;
import com.cinema.scheduleservice.entity.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
    @Query(value = "SELECT * FROM Schedule WHERE start_time >= ?1 AND start_time < ?2 AND room_id = ?3", nativeQuery = true)
    List<Schedule> findByDateRangeAndRoomId(LocalDateTime startDate, LocalDateTime endDate, Integer roomId);
    List<Schedule> findByRoomAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(Room room, LocalDateTime startTime, LocalDateTime endTime);

    List<Schedule> findByRoom_CinemaAndEndTimeBefore(Cinema cinema, LocalDateTime time);
    List<Schedule> findByRoom_CinemaAndEndTimeAfter(Cinema cinema, LocalDateTime time);
    Page<Schedule> findByRoom_CinemaAndEndTimeBefore(Cinema cinema, LocalDateTime endTime, Pageable pageable);
    Page<Schedule> findByRoom_CinemaAndEndTimeAfter(Cinema cinema, LocalDateTime endTime, Pageable pageable);
    List<Schedule> findAllByRoom_CinemaAndStartTimeAfterAndStartTimeBefore(Cinema cinema, LocalDateTime start, LocalDateTime end);


}
