package com.cinema.scheduleservice.service;

import java.util.List;

import com.cinema.scheduleservice.dto.request.BookingDto;
import com.cinema.scheduleservice.dto.response.BookingResponse;
import com.cinema.scheduleservice.dto.response.HistoryBookingResponse;

public interface BookingService {
    BookingResponse checkAvailableSeats(BookingDto bookingDto);

    List<HistoryBookingResponse> HISTORY_BOOKING_RESPONSES(String token);
}
