package com.cinema.repository;

import com.cinema.entity.Cinema;
import com.cinema.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CinemaRepository extends JpaRepository<Cinema, Integer> {
    Optional<Cinema> findByAdmin(User user);
}
