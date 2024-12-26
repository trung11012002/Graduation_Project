package com.cinema.scheduleservice.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoryBookingResponse {
    private Integer id;
    private CinemaResponse cinema;
    private FilmResponse filmResponse;
    private LocalDateTime timeBooking;
    private List<TicketResponse> tickets;
    private Long totalPrice;
    private boolean isRated;
    private RatingDtoRepsonse ratingDtoRepsonse;
}
