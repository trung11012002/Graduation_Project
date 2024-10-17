package com.example.filmservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.filmservice.entity.Thumnail;

@Repository
public interface ThumbnailsRepository extends JpaRepository<Thumnail, Integer> {}
