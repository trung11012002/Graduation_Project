package com.cinema.scheduleservice.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cinema.scheduleservice.entity.Cinema;
import com.cinema.scheduleservice.entity.Room;
import com.cinema.scheduleservice.entity.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
    @Query(
            value =
                    "SELECT * FROM Schedule WHERE start_time >= ?1 AND start_time < ?2 AND room_id = ?3 ORDER BY start_time ASC",
            nativeQuery = true)
    List<Schedule> findByDateRangeAndRoomId(LocalDateTime startDate, LocalDateTime endDate, Integer roomId);

    List<Schedule> findByRoomAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(
            Room room, LocalDateTime startTime, LocalDateTime endTime);

    List<Schedule> findByRoom_CinemaAndEndTimeBefore(Cinema cinema, LocalDateTime time);
    //    @Query("SELECT s FROM Schedule s WHERE s.room.cinema = :cinema AND s.endTime < :now")
    //    List<Schedule> findByCinemaAndEndTimeBefore(@Param("cinema") Cinema cinema, @Param("now") LocalDateTime now);

    List<Schedule> findByRoom_CinemaAndEndTimeAfter(Cinema cinema, LocalDateTime time);

    Page<Schedule> findByRoom_CinemaAndEndTimeBefore(Cinema cinema, LocalDateTime endTime, Pageable pageable);

    Page<Schedule> findByRoom_CinemaAndEndTimeAfter(Cinema cinema, LocalDateTime endTime, Pageable pageable);

    List<Schedule> findAllByRoom_CinemaAndStartTimeAfterAndStartTimeBefore(
            Cinema cinema, LocalDateTime start, LocalDateTime end);
}
