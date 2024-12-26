package com.example.reviewservice.dto.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RatingResponse {
    private Integer id;
    private Integer star;
    private String comment;
    private LocalDateTime createdAt;
    private Integer filmId;
    private Integer userId;
}
