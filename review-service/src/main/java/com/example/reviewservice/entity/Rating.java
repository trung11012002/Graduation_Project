package com.example.reviewservice.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "rating")
@Data
@NoArgsConstructor
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "star")
    private Integer star;

    @Column(name = "comment")
    private String comment;

    @Column(name = "createdAt")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "film_id", nullable = false)
    private Film film;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
