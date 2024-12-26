package com.example.reviewservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.reviewservice.entity.Rating;

public interface RatingRepository extends JpaRepository<Rating, Integer> {

    List<Rating> findAllByFilmId(Integer filmId);

    Optional<Rating> findByFilmIdAndUserId(Integer filmId, Integer userId);
}
