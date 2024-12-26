package com.example.reviewservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.reviewservice.entity.Film;

public interface FilmRepository extends JpaRepository<Film, Integer> {}
