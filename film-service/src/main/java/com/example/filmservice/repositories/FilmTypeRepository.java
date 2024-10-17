package com.example.filmservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.filmservice.entity.Type;

@Repository
public interface FilmTypeRepository extends JpaRepository<Type, Integer> {}
