package com.example.reviewservice.dto.response;

import com.example.reviewservice.entity.Film;
import com.example.reviewservice.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RatingResponse {
    private Integer id;
    private Integer star;
    private String comment;
    private LocalDateTime createdAt;
    private Film film;
    private User user;
}
