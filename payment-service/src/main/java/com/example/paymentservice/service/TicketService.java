package com.example.paymentservice.service;

import com.example.paymentservice.dto.SeatDTO;
import com.example.paymentservice.entity.Booking;
import com.example.paymentservice.entity.Ticket;

import java.util.List;

public interface TicketService {
    List<Ticket> createTickets(Booking booking, Integer scheduleId, List<SeatDTO> seatDTOs);
}
