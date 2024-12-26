package com.cinema.scheduleservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cinema.scheduleservice.entity.Film;

@Repository
public interface FilmRepository extends JpaRepository<Film, Integer> {}
