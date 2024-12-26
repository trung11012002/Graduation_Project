package com.cinema.scheduleservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cinema.scheduleservice.entity.Cinema;

@Repository
public interface CinemaRepository extends JpaRepository<Cinema, Integer> {}
