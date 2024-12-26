package com.cinema.scheduleservice.dto.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderedResponse {
    private String fullname;
    private String email;
    private LocalDateTime bookedTime;
    private Integer numberOfTicket;
    private Integer vips;
    private Integer regulars;
    private String seats;
    private Long totalPaid;
}
