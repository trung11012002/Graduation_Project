package com.cinema.scheduleservice.dto.request;

import com.cinema.scheduleservice.dto.response.Seat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingDto {
    private Integer scheduleId;
    private List<Seat> seats;
}