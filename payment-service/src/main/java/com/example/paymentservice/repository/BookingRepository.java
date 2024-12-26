package com.example.paymentservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.paymentservice.entity.Booking;

public interface BookingRepository extends JpaRepository<Booking, Integer> {}
