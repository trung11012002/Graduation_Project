package com.cinema.scheduleservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketResponse {

    private Integer id;
    private Integer ticketClass;
    private Long price;
    private Integer seatNumberHorizontal;
    private Integer seatNumberVertical;

}
