package com.example.attendancehistory.repository;

import com.example.attendancehistory.entity.AttendanceLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface AttendanceLogRepository extends JpaRepository<AttendanceLog, Integer> {
    List<AttendanceLog> findAllByMsv(String msv);

    @Query(value = "select a from AttendanceLog a where a.currentTime >= :startDate and a.currentTime <= :endDate")
    List<AttendanceLog> findByCurrentTime(@Param("startDate") String startDate, @Param("endDate") String endDate);
}
