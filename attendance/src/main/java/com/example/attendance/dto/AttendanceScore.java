package com.example.attendance.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceScore {
    private Integer id;

    private String name;

    private String msv;

    private Integer attendanceScore;

    private Integer baseScore;

    private Integer status;
}
