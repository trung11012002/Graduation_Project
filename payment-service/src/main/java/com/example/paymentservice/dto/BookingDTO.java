package com.example.paymentservice.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingDTO {
    private Integer scheduleId;
    private Integer userId;
    private List<SeatDTO> seats;
}
