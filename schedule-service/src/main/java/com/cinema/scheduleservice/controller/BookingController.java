package com.cinema.scheduleservice.controller;

import com.cinema.scheduleservice.dto.request.BookingDto;
import com.cinema.scheduleservice.dto.response.ApiResponse;
import com.cinema.scheduleservice.dto.response.BookingResponse;
import com.cinema.scheduleservice.dto.response.HistoryBookingResponse;
import com.cinema.scheduleservice.service.BookingService;
import com.cinema.scheduleservice.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/booking")
public class BookingController {
    @Autowired
    private BookingService bookingService;
    @Autowired
    private TokenUtils  tokenUtils;
    @PostMapping("/check-available-seats")
    public ApiResponse<BookingResponse> checkSeatsBooking(@RequestBody BookingDto bookingDto) {
        return ApiResponse.<BookingResponse>builder()
                .code(1000)
                .data(bookingService.checkAvailableSeats(bookingDto))
                .build();
    }

    @GetMapping("/history")
    public ApiResponse<List<HistoryBookingResponse>> getListHistoryBooking(@RequestHeader(value = "Authorization") String token) {
        List<HistoryBookingResponse> historyBookingResponses = bookingService.HISTORY_BOOKING_RESPONSES(token);

//        System.out.println(tokenUtils.getUsernameFromToken(token));
        return ApiResponse.<List<HistoryBookingResponse>>builder()
                .code(1000)
                .data(historyBookingResponses)
                .build();
    }
}
