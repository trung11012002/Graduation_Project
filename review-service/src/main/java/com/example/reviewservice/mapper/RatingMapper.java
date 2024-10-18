package com.example.reviewservice.mapper;

import com.example.reviewservice.dto.response.RatingResponse;
import com.example.reviewservice.entity.Rating;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RatingMapper {
    RatingResponse toRatingResponse(Rating rating);
    List<RatingResponse> toListRatingResponse(List<Rating> ratings);
}
