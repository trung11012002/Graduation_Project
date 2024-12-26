package com.example.paymentservice.service;

import java.util.List;

import com.example.paymentservice.dto.SeatDTO;
import com.example.paymentservice.entity.Booking;
import com.example.paymentservice.entity.Ticket;

public interface TicketService {
    List<Ticket> createTickets(Booking booking, Integer scheduleId, List<SeatDTO> seatDTOs);
}
