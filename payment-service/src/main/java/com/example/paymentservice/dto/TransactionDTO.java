package com.example.paymentservice.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDTO {
    private Integer scheduleId;
    private Integer userId;
    private List<String> seats;
    private String responseCode;
    private Integer bookingId;
}
