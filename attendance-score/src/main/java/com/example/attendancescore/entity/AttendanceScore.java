package com.example.attendancescore.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "attendance-score")
@Entity
public class AttendanceScore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "msv")
    private String msv;

    @Column(name = "attendance_score")
    private Integer attendanceScore;

    @Column(name = "base_score")
    private Integer baseScore;

    @Column(name = "status")
    private Integer status;

}
