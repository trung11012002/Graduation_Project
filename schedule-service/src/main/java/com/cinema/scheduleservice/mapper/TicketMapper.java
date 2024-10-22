package com.cinema.scheduleservice.mapper;

import com.cinema.scheduleservice.dto.response.TicketResponse;
import com.cinema.scheduleservice.entity.Ticket;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TicketMapper {


    TicketResponse ticketToTicketResponse(Ticket ticket);
    List<TicketResponse> ticketsToTicketResponses(List<Ticket> tickets);

}
