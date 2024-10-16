package com.example.filmservice.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "room")
@Data
@NoArgsConstructor
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "horizontalSeats")
    private Integer horizontalSeats;

    @Column(name = "verticalSeats")
    private Integer verticalSeats;

    @ManyToOne
    @JoinColumn(name = "cinema_id", nullable = false)
    private Cinema cinema;

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Schedule> schedules;

    @Column(name = "createdAt")
    private LocalDateTime createdAt;

    @Column(name = "lastModifyAt")
    private LocalDateTime lastModifyAt;

    @Column(name = "createdBy")
    private String createdBy;

    @Column(name = "lastModifyBy")
    private String lastModifyBy;
}
