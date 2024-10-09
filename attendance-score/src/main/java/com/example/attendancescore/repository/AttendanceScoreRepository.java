package com.example.attendancescore.repository;

import com.example.attendancescore.entity.AttendanceScore;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceScoreRepository extends JpaRepository<AttendanceScore, Integer> {
    AttendanceScore findAttendanceScoreByMsv(String msv);
}
