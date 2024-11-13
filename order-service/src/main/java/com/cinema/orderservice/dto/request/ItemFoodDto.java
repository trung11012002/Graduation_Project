package com.cinema.orderservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemFoodDto {
    private Integer id;
    private String name;
    private Integer quantity;
    private Double price;
    private String supplierName;
}
