package com.example.paymentservice.service;

import com.example.paymentservice.dto.ApiResponse;
import com.example.paymentservice.dto.TransactionDTO;

public interface BookingService {
    ApiResponse createBooking(TransactionDTO dto);

    ApiResponse createBookingWithStatusPendingPayment(TransactionDTO dto);
}
