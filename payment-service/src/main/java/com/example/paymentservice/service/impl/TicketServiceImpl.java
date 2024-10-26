package com.example.paymentservice.service.impl;

import com.example.paymentservice.dto.SeatDTO;
import com.example.paymentservice.entity.Booking;
import com.example.paymentservice.entity.Schedule;
import com.example.paymentservice.entity.Ticket;
import com.example.paymentservice.enums.TicketClassEnums;
import com.example.paymentservice.exception.AppException;
import com.example.paymentservice.exception.ErrorCode;
import com.example.paymentservice.repository.BookingRepository;
import com.example.paymentservice.repository.ScheduleRepository;
import com.example.paymentservice.repository.TicketRepository;
import com.example.paymentservice.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Override
    public List<Ticket> createTickets(Booking booking, Integer scheduleId, List<SeatDTO> seatDTOs) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new AppException(ErrorCode.SCHEDULE_NOT_FOUND));
        List<Ticket> tickets = new ArrayList<>();
        for(SeatDTO seatDTO : seatDTOs){
            Ticket ticket = new Ticket();
            if(seatDTO.getRow() <= 4){
                ticket.setPrice(50000L);
                ticket.setTicketClass(TicketClassEnums.REGULAR.getCode());
            }else{
                ticket.setPrice(80000L);
                ticket.setTicketClass(TicketClassEnums.VIP.getCode());
            }
            ticket.setBooking(booking);
            ticket.setSchedule(schedule);
            ticket.setSeatNumberVertical(seatDTO.getRow());
            ticket.setSeatNumberHorizontal(seatDTO.getColumn());
            ticketRepository.save(ticket);
            tickets.add(ticket);
        }
        return tickets;
    }
}
