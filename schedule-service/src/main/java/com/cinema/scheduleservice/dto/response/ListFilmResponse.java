package com.cinema.scheduleservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListFilmResponse {
    private List<FilmResponse> films;
    private PageInfo pageInfo;
}
