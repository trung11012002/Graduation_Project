package com.cinema.orderservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemFoodResponse {
    private Integer id;
    private String name;
    private String description;
    private Integer price;
    private String status;
    private Integer quantity;

}
