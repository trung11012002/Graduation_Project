package com.example.filmservice.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.filmservice.entity.Film;
import com.example.filmservice.entity.Rating;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Integer> {
    List<Rating> findAllByFilm(Film film);
}
