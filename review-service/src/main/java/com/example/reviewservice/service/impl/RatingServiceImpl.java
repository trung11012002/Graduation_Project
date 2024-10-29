package com.example.reviewservice.service.impl;

import com.example.reviewservice.dto.ApiResponse;
import com.example.reviewservice.dto.request.RatingRequest;
import com.example.reviewservice.dto.response.RatingResponse;
import com.example.reviewservice.entity.Film;
import com.example.reviewservice.entity.Rating;
import com.example.reviewservice.entity.User;
import com.example.reviewservice.exception.AppException;
import com.example.reviewservice.exception.ErrorCode;
import com.example.reviewservice.httpclient.FilmClient;
import com.example.reviewservice.mapper.RatingMapper;
import com.example.reviewservice.repository.FilmRepository;
import com.example.reviewservice.repository.RatingRepository;
import com.example.reviewservice.repository.UserRepository;
import com.example.reviewservice.service.RatingService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RatingServiceImpl implements RatingService {

    private FilmRepository filmRepository;

    private RatingRepository ratingRepository;

    private UserRepository userRepository;

    private RatingMapper ratingMapper;
    private FilmClient client;

    @Override
    public ApiResponse createRating(RatingRequest dto) {
        User user = userRepository.findById(dto.getUserId()).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        Film film = filmRepository.findById(dto.getFilmId()).orElseThrow(() -> new AppException(ErrorCode.FILM_NOT_FOUND));

        Rating rating = new Rating();
        rating.setStar(dto.getStar());
        rating.setComment(dto.getComment());
        rating.setCreatedAt(LocalDateTime.now());
        rating.setFilm(film);
        rating.setUser(user);
        ratingRepository.save(rating);

        ApiResponse filmResponse = client.updateScore(dto.getFilmId());

        return ApiResponse.builder().msg("Success").data(filmResponse.getData()).build();
    }

    @Override
    public ApiResponse getFilmRatings(Integer filmId) {
        List<Rating> ratings = ratingRepository.findAllByFilmId(filmId);
        List<RatingResponse> ratingResponses = ratings.stream().map(rating -> ratingMapper.toRatingResponse(rating)).toList();
        return ApiResponse.builder().data(ratingResponses).msg("Success").build();
    }

    @Override
    public RatingResponse getRatingInFilmByUser(Integer filmId, Integer userId) {
        Rating rating = ratingRepository.findByFilmIdAndUserId(filmId, userId)
                .orElse(null);
        return (rating != null) ? ratingMapper.toRatingResponse(rating) : null; // Trả về đối tượng rỗng nếu không có rating

    }
}
