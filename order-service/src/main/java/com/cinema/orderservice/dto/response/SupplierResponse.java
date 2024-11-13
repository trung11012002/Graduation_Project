package com.cinema.orderservice.dto.response;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupplierResponse {
    private Integer id;
    private String name;
    private String description;
    private String phoneNumber;
    private String address;
    private String status;
    private List<ItemFoodResponse> itemFoodResponses;
}
