package com.cinema.orderservice.dto.response;

import com.cinema.orderservice.dto.request.ItemFoodDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderFoodItemResponse {
    private Integer id;
    private Integer orderFoodId;
    private ItemFoodDto itemFoodDto;
    private Integer quantity;
    private Double unitPrice;
}
