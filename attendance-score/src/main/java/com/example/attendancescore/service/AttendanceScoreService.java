package com.example.attendancescore.service;

import com.example.attendancescore.entity.AttendanceScore;

import java.util.List;

public interface AttendanceScoreService {
    void update( AttendanceScore score);
    AttendanceScore retrieve(String msv);
    List<AttendanceScore> getAll();
    AttendanceScore create(AttendanceScore entity);
}
