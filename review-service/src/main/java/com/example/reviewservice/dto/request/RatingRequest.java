package com.example.reviewservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RatingRequest {
    private Integer filmId;
    private Integer userId;
    private Integer star;
    private String comment;
}
