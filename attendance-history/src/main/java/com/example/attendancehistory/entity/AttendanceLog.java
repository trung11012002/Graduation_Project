package com.example.attendancehistory.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "attendance-log")
@Entity
public class AttendanceLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "msv")
    private String msv;

//    @Column(name = "status")
//    private Integer status;

    @Column(name = "log_time", columnDefinition = "DATETIME")
    private LocalDateTime currentTime;

}
