package com.example.filmservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.filmservice.entity.Film;

public interface FilmRepository extends JpaRepository<Film, Integer> {}
