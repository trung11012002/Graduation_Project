package com.example.paymentservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.paymentservice.entity.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {}
