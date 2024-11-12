package com.cinema.orderservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderFoodResponse {
    private Integer id;
    private String fullName;
    private String email;
    private String cinemaName;
    private Double totalPrice;
    private LocalDateTime orderTime;
    private List<OrderFoodItemResponse> orderFoodItemResponses;

}
