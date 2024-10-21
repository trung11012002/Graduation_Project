package com.example.filmservice.dto.response;

import java.util.List;

import com.example.filmservice.entity.Film;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListFilmResponse {
    private List<FilmResponse> films;
    private PageInfo pageInfo;
}
