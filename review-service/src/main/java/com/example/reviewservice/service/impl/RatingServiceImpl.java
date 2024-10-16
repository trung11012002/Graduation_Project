package com.example.reviewservice.service.impl;

import com.example.reviewservice.dto.ApiResponse;
import com.example.reviewservice.dto.request.RatingRequest;
import com.example.reviewservice.dto.response.RatingResponse;
import com.example.reviewservice.entity.Film;
import com.example.reviewservice.entity.Rating;
import com.example.reviewservice.entity.User;
import com.example.reviewservice.repository.FilmRepository;
import com.example.reviewservice.repository.RatingRepository;
import com.example.reviewservice.repository.UserRepository;
import com.example.reviewservice.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RatingServiceImpl implements RatingService {

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public ApiResponse createRating(RatingRequest dto) {
        Optional<User> user = userRepository.findById(dto.getUserId());
        if(!user.isPresent()){
            return ApiResponse.builder().code(1004).message("Người dùng không tồn tại").build();
        }
        Optional<Film> film = filmRepository.findById(dto.getFilmId());
        if(!film.isPresent()){
            return ApiResponse.builder().code(1004).message("Phim không tồn tại").build();
        }
        Rating rating = new Rating();
        rating.setStar(dto.getStar());
        rating.setComment(dto.getComment());
        rating.setCreatedAt(LocalDateTime.now());
        rating.setFilm(film.get());
        rating.setUser(user.get());
        ratingRepository.save(rating);

//        String url = "http://localhost:8083/api/v1/film/update-score";
//        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url)
//                .queryParam("filmId", dto.getFilmId());



        return ApiResponse.builder().message("Lưu thành công").build();
    }

    @Override
    public ApiResponse getFilmRatings(Integer filmId) {
        List<Rating> ratings = ratingRepository.findAllByFilmId(filmId);
        return ApiResponse.builder().result(ratings).message("Lấy thành công").build();
    }

    @Override
    public RatingResponse getRatingInFilmByUser(Integer filmId, Integer userId) {
        Optional<Rating> op = ratingRepository.findByFilmIdAndUserId(filmId, userId);
        return op.map(rating -> new RatingResponse(rating.getStar(), rating.getComment())).orElse(null);
    }
}
