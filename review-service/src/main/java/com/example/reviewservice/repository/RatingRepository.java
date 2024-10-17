package com.example.reviewservice.repository;

import com.example.reviewservice.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating, Integer> {

    List<Rating> findAllByFilmId(Integer filmId);

    Optional<Rating> findByFilmIdAndUserId(Integer filmId, Integer userId);
}
