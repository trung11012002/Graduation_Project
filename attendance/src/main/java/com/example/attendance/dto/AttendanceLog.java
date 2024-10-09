package com.example.attendance.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceLog {
    private Integer id;
    private String name;
    private String msv;
    private LocalDateTime currentTime;
}
