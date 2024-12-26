package com.example.paymentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest implements Serializable {
    private Long amount;
    private String orderInfo;
    private String ipAddress;
    private Integer scheduleId;
    private Integer userId;
    private List<String> seats;
}
