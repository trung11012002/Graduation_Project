package com.example.paymentservice.service;

import com.example.paymentservice.dto.ApiResponse;
import com.example.paymentservice.dto.TransactionDTO;
import com.example.paymentservice.entity.Booking;

public interface BookingService {
    ApiResponse<String> updateBooking(TransactionDTO dto);

    Booking createBookingWithStatusPendingPayment(TransactionDTO dto);
}
