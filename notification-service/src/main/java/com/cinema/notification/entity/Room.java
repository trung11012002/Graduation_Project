package com.cinema.notification.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

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

