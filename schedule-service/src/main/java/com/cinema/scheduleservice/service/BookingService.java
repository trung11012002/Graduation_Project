package com.cinema.scheduleservice.service;

import com.cinema.scheduleservice.dto.request.BookingDto;
import com.cinema.scheduleservice.dto.response.BookingResponse;
import com.cinema.scheduleservice.dto.response.HistoryBookingResponse;

import java.util.List;

public interface BookingService {
    BookingResponse checkAvailableSeats(BookingDto bookingDto);
    List<HistoryBookingResponse> HISTORY_BOOKING_RESPONSES(String token);
}
