package com.example.paymentservice.dto;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
