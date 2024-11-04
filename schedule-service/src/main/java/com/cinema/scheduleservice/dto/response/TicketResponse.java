package com.cinema.scheduleservice.dto.response;

import com.cinema.scheduleservice.entity.Ticket;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketResponse {

    private Integer id;
    private Integer ticketClass;
    private Long price;
    private Integer seatNumberHorizontal;
    private Integer seatNumberVertical;
    public static List<TicketResponse> converToTicketResponse(List<Ticket> ticket){
        List<TicketResponse> ticketResponses = new ArrayList<>();
        for(Ticket t : ticket){
            TicketResponse ticketResponse = new TicketResponse();
            ticketResponse.setId(t.getId());
            ticketResponse.setTicketClass(t.getTicketClass());
            ticketResponse.setPrice(t.getPrice());
            ticketResponse.setSeatNumberHorizontal(t.getSeatNumberHorizontal());
            ticketResponse.setSeatNumberVertical(t.getSeatNumberVertical());
            ticketResponses.add(ticketResponse);
        }
        return ticketResponses;
    }


}
