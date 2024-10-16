package com.example.reviewservice.repository;

import com.example.reviewservice.entity.Film;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilmRepository extends JpaRepository<Film, Integer> {
}
