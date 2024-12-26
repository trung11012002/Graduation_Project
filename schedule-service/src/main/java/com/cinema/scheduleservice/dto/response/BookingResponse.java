package com.cinema.scheduleservice.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponse {
    private Integer scheduleId;
    private List<Seat> seats;
    private List<Integer> prices;
}
