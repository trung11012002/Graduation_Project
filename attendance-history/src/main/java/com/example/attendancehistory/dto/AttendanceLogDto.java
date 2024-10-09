package com.example.attendancehistory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceLogDto {
    private String msv;
    private String name;
    private LocalDateTime currentTime;
}

