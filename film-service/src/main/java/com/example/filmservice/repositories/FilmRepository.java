package com.example.filmservice.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.filmservice.entity.Film;

public interface FilmRepository extends JpaRepository<Film, Integer> {
    @Query(value = "SELECT * FROM Film e WHERE e.name LIKE %?1%", nativeQuery = true)
    Page<Film> searchByName(String name, Pageable pageable);
}
