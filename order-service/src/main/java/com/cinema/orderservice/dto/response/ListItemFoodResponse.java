package com.cinema.orderservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListItemFoodResponse {
    List<ItemFoodResponse> itemFoodResponses;
}
