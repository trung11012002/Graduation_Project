package com.example.paymentservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.paymentservice.entity.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {}
