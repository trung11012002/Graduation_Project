package com.cinema.scheduleservice.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.cinema.scheduleservice.dto.response.TicketResponse;
import com.cinema.scheduleservice.entity.Ticket;

@Mapper(componentModel = "spring")
public interface TicketMapper {

    TicketResponse ticketToTicketResponse(Ticket ticket);

    List<TicketResponse> ticketsToTicketResponses(List<Ticket> tickets);
}
