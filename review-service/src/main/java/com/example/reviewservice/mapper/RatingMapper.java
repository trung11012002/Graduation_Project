package com.example.reviewservice.mapper;

import com.example.reviewservice.dto.response.RatingResponse;
import com.example.reviewservice.entity.Film;
import com.example.reviewservice.entity.Rating;
import com.example.reviewservice.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@Mapper(componentModel = "spring")
public interface RatingMapper {
    @Mapping(target = "filmId", source = "film")
    @Mapping(target = "userId", source = "user")
    RatingResponse toRatingResponse(Rating rating);

    default Integer mapFilmToFilmId(Film film){
        if(film == null){
            return null;
        }
        Integer filmId = film.getId();
        return filmId;
    }

    default Integer mapUserToUserId(User user){
        if(user == null){
            return null;
        }
        return user.getId();
    }
    List<RatingResponse> toListRatingResponse(List<Rating> ratings);
}
